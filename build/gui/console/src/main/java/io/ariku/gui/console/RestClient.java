package io.ariku.gui.console;

import com.mashape.unirest.http.Unirest;
import io.ariku.util.data.RestSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ari Aaltonen
 */
public class RestClient {
    public static Logger logger = LoggerFactory.getLogger(RestClient.class);

    public RestSettings restSettings;

    public String signUpRequest(String username) {
        return new HttpCall().doCall(restSettings.signUpUrl(), username);
    }

    public String verifySignUpRequest(String username) {
        return new HttpCall().doCall(restSettings.verifySignUpUrl(), username);
    }

    public String loginRequest(String username) {
        return new HttpCall().doCall(restSettings.loginUrl(), username);
    }

    public String logoutRequest(String username, String securityToken) {
        return new HttpCall().doRestrictedCall(restSettings.logoutUrl(), username, securityToken);
    }

    private class HttpCall {
        String doCall(String url, String username) {
            try {
                return Unirest.post(url).queryString("username", username).asString().getBody();
            } catch (Exception e) {
                logger.error("Failed to send request to '{}'", url, e);
                return "FAIL";
            }
        }
        String doRestrictedCall(String url, String username, String securityToken) {
            try {
                return Unirest.post(url)
                        .queryString("username", username)
                        .queryString("security_token", securityToken)
                        .asString().getBody();
            } catch (Exception e) {
                logger.error("Failed to send request to '{}'", url, e);
                return "FAIL";
            }
        }
    }

}
