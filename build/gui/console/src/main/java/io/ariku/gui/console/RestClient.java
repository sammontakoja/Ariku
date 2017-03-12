package io.ariku.gui.console;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.ariku.util.data.RestSettings;
import io.ariku.verification.AuthorizeRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ari Aaltonen
 */
public class RestClient {

    public RestSettings restSettings;

    public String signUp(String username) {
        return HttpCaller.call(() ->
                Unirest.post(restSettings.signUpUrl())
                        .queryString("username", username)
                        .asString().getBody());
    }

    public String verifySignUp(String username) {
        return HttpCaller.call(() ->
                Unirest.post(restSettings.verifySignUpUrl())
                        .queryString("username", username)
                        .asString().getBody());
    }

    public String login(String username) {
        return HttpCaller.call(() ->
                Unirest.post(restSettings.loginUrl())
                        .queryString("username", username)
                        .asString().getBody());
    }

    public String logout(AuthorizeRequest request) {
        return HttpCaller.call(() ->
                Unirest.post(restSettings.logoutUrl())
                        .queryString("username", request.username)
                        .queryString("security_token", request.securityToken)
                        .asString().getBody());
    }

    public String newCompetition(String competitionName, String competitionType, AuthorizeRequest request) {
        return HttpCaller.call(() ->
                Unirest.post(restSettings.competitionNewUrl())
                        .queryString("competition_name", competitionName)
                        .queryString("competition_type", competitionType)
                        .queryString("username", request.username)
                        .queryString("security_token", request.securityToken)
                        .asString().getBody());
    }

    public String ownersCompetitions(AuthorizeRequest request) {
        return HttpCaller.call(() ->
                Unirest.get(restSettings.competitionListByOwnerUrl())
                        .queryString("username", request.username)
                        .queryString("security_token", request.securityToken)
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
