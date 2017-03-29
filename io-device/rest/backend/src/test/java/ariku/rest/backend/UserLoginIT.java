package ariku.rest.backend;

import com.mashape.unirest.http.exceptions.UnirestException;
import ariku.rest.client.RestClient;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Ari Aaltonen
 */
public class UserLoginIT {

    private static RestClient restClient = new RestClient();

    @BeforeClass
    public static void startArikuRestService() {
        Util.startServerAndLetClientKnowAboutTCPPort();
        restClient.restSettings = Util.restSettings;
    }

    @AfterClass
    public static void stopArikuRestService() {
        ArikuRest.stop();
    }

    @Test
    public void sign_up_is_ok() throws UnirestException {
        String username = UUID.randomUUID().toString();
        assertThat(restClient.signUpRequest(username).get(), is("OK"));
    }

    @Test
    public void verify_sign_up_ok_when_sign_up_is_done_before() throws UnirestException {
        String username = UUID.randomUUID().toString();
        restClient.signUpRequest(username);
        assertThat(restClient.verifySignUpRequest(username).get(), is("OK"));
    }

    @Test
    public void after_successful_login_uuid_security_token_given_back() throws UnirestException {
        String username = UUID.randomUUID().toString();
        restClient.signUpRequest(username);
        restClient.verifySignUpRequest(username);
        String securityToken = restClient.loginRequest(username).get();
        assertThat(UUID.fromString(securityToken).toString().length(), is(36));
    }

    @Test
    public void log_out_successful_when_using_given_securityToken() throws UnirestException {
        String username = UUID.randomUUID().toString();
        restClient.signUpRequest(username);
        restClient.verifySignUpRequest(username);
        String securityToken = restClient.loginRequest(username).get();
        String response = restClient.logoutRequest(username, securityToken).get();
        assertThat(response, is("OK"));
    }


}
