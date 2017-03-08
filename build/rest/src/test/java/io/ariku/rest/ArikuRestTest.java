package io.ariku.rest;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Ari Aaltonen
 */
public class ArikuRestTest {

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
        assertThat(Unirest.get(signUpUrl(username)).asString().getBody(), is("OK"));
    }

    @Test
    public void verify_sign_up_ok_when_sign_up_is_done_before() throws UnirestException {
        String username = UUID.randomUUID().toString();
        Unirest.get(signUpUrl(username)).asString();
        assertThat(Unirest.get(verifySignUpUrl(username)).asString().getBody(), is("OK"));
    }

    @Test
    public void verify_sign_up_fail_when_sign_up_is_not_done() throws UnirestException {
        String username = UUID.randomUUID().toString();
        assertThat(Unirest.get(verifySignUpUrl(username)).asString().getBody(), is("FAIL"));
    }

    @Test
    public void verify_login_fail_when_sign_up_verification_is_not_done() throws UnirestException {
        String username = UUID.randomUUID().toString();
        Unirest.get(signUpUrl(username)).asString();
        assertThat(Unirest.get(loginUrl(username)).asString().getBody(), is("FAIL"));
    }

    @Test
    public void after_successful_login_uuid_security_token_given_back() throws UnirestException {
        String username = UUID.randomUUID().toString();
        Unirest.get(signUpUrl(username)).asString();
        Unirest.get(verifySignUpUrl(username)).asString();
        String securityToken = Unirest.get(loginUrl(username)).asString().getBody();
        assertThat(UUID.fromString(securityToken).toString().length(), is(36));
    }

    private String signUpUrl(String username) {
        return "http://localhost:5000/verification/signup/" + username;
    }

    private String verifySignUpUrl(String username) {
        return "http://localhost:5000/verification/verifysignup/" + username;
    }

    private String loginUrl(String username) {
        return "http://localhost:5000/verification/login/" + username;
    }

}
