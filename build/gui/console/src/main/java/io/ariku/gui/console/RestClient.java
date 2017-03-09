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
        return new HttpCall().response(restSettings.signUpUrl(), username);
    }

    public String verifySignUpRequest(String username) {
        return new HttpCall().response(restSettings.verifySignUpUrl(), username);
    }

    public String loginRequest(String username) {
        return new HttpCall().response(restSettings.loginUrl(), username);
    }

    public String logoutRequest(String username, String securityToken) {
        return new HttpCall().response(restSettings.logoutUrl(), username);
    }

    private class HttpCall {
        String response(String url, String username) {
            try {
                return Unirest.post(url).queryString("username", username).asString().getBody();
            } catch (Exception e) {
                logger.error("Failed to send request to '{}'", url, e);
                return "FAIL";
            }
        }
    }

}
