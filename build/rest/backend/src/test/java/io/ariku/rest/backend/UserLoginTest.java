package io.ariku.rest.backend;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Ari Aaltonen
 */
public class UserLoginTest {

    @BeforeClass
    public static void startArikuRestService() {
        Util.startServerAndLetClientKnowAboutTCPPort();
    }

    @AfterClass
    public static void stopArikuRestService() {
        ArikuRest.stop();
    }

    @Test
    public void sign_up_is_ok() throws UnirestException {
        String username = UUID.randomUUID().toString();
        HttpRequestWithBody request = Util.signUpRequest(username);
        assertThat(request.asString().getBody(), is("OK"));
    }

    @Test
    public void verify_sign_up_ok_when_sign_up_is_done_before() throws UnirestException {
        String username = UUID.randomUUID().toString();
        Util.signUpRequest(username).asString();
        assertThat(Util.verifySignUpRequest(username).asString().getBody(), is("OK"));
    }

    @Test
    public void after_successful_login_uuid_security_token_given_back() throws UnirestException {
        String username = UUID.randomUUID().toString();
        Util.signUpRequest(username).asString();
        Util.verifySignUpRequest(username).asString();
        String securityToken = Util.loginRequest(username).asString().getBody();
        assertThat(UUID.fromString(securityToken).toString().length(), is(36));
    }

    @Test
    public void log_out_successful_when_using_given_securityToken() throws UnirestException {
        String username = UUID.randomUUID().toString();
        Util.signUpRequest(username).asString();
        Util.verifySignUpRequest(username).asString();
        String securityToken = Util.loginRequest(username).asString().getBody();
        String response = Util.logoutRequest(username, securityToken).asString().getBody();
        assertThat(response, is("OK"));
    }


}
