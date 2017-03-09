package io.ariku.rest;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.UUID;

import static io.ariku.rest.Util.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Ari Aaltonen
 */
public class UserVerificationServiceTest {

    @BeforeClass
    public static void startArikuRestService() {
        ArikuRest.start();
    }

    @AfterClass
    public static void stopArikuRestService() {
        ArikuRest.stop();
    }

    @Test
    public void sign_up_is_ok() throws UnirestException {
        String username = UUID.randomUUID().toString();
        HttpRequestWithBody request = Unirest.post(signUpUrl()).queryString("username", username);
        assertThat(request.asString().getBody(), is("OK"));
    }

    @Test
    public void verify_sign_up_ok_when_sign_up_is_done_before() throws UnirestException {
        String username = UUID.randomUUID().toString();
        signUpRequest(username).asString();
        assertThat(verifySignUpRequest(username).asString().getBody(), is("OK"));
    }

    @Test
    public void verify_sign_up_fail_when_sign_up_is_not_done() throws UnirestException {
        String username = UUID.randomUUID().toString();
        assertThat(verifySignUpRequest(username).asString().getBody(), is("FAIL"));
    }

    @Test
    public void login_fail_when_sign_up_verification_is_not_done() throws UnirestException {
        String username = UUID.randomUUID().toString();
        Util.signUpRequest(username);
        assertThat(loginRequest(username).asString().getBody(), is("FAIL"));
    }

    @Test
    public void after_successful_login_uuid_security_token_given_back() throws UnirestException {
        String username = UUID.randomUUID().toString();
        signUpRequest(username).asString();
        verifySignUpRequest(username).asString();
        String securityToken = loginRequest(username).asString().getBody();
        assertThat(UUID.fromString(securityToken).toString().length(), is(36));
    }

    @Test
    public void log_out_successful_when_using_given_securityToken() throws UnirestException {
        String username = UUID.randomUUID().toString();
        signUpRequest(username).asString();
        verifySignUpRequest(username).asString();
        String securityToken = loginRequest(username).asString().getBody();
        String response = logoutRequest(username, securityToken).asString().getBody();
        assertThat(response, is("OK"));
    }


}
