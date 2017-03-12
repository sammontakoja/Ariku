package io.ariku.gui.console;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.ariku.util.data.RestSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ari Aaltonen
 */
public class RestClient {

    public RestSettings restSettings;

    public String signUpRequest(String username) {
        return HttpCaller.call(() ->
                Unirest.post(restSettings.signUpUrl())
                        .queryString("username", username)
                        .asString().getBody());
    }

    public String verifySignUpRequest(String username) {
        return HttpCaller.call(() ->
                Unirest.post(restSettings.verifySignUpUrl())
                        .queryString("username", username)
                        .asString().getBody());
    }

    public String loginRequest(String username) {
        return HttpCaller.call(() ->
                Unirest.post(restSettings.loginUrl())
                        .queryString("username", username)
                        .asString().getBody());
    }

    public String logoutRequest(String username, String securityToken) {
        return HttpCaller.call(() ->
                Unirest.post(restSettings.logoutUrl())
                        .queryString("username", username)
                        .queryString("security_token", securityToken)
                        .asString().getBody());
    }

}

class HttpCaller {
    public static Logger logger = LoggerFactory.getLogger(RestClient.class);

    static String call(HttpCall httpCall) {
        try {
            return httpCall.doCall();
        } catch (Exception e) {
            logger.error("Failed to send request!", e);
            return "FAIL";
        }
    }

    interface HttpCall {
        String doCall() throws UnirestException;
    }
}
