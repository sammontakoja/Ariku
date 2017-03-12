package io.ariku.rest;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import io.ariku.util.data.RestSettings;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author Ari Aaltonen
 */
public class Util {

    static RestSettings restSettings = new RestSettings();

    public static void startServerAndLetClientKnowAboutTCPPort() {
        int freePort = freePort();
        ArikuRest.start(freePort);
        restSettings.port = new Integer(freePort).toString();
    }

    private static int freePort() {
        try (ServerSocket socket = new ServerSocket(0)) {
            socket.setReuseAddress(true);
            return socket.getLocalPort();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static HttpRequestWithBody signUpRequest(String username) {
        return Unirest.post(restSettings.signUpUrl()).queryString("username", username);
    }

    public static HttpRequestWithBody verifySignUpRequest(String username) {
        return Unirest.post(restSettings.verifySignUpUrl()).queryString("username", username);
    }

    public static HttpRequestWithBody loginRequest(String username) {
        return Unirest.post(restSettings.loginUrl()).queryString("username", username);
    }

    public static HttpRequestWithBody logoutRequest(String username, String securityToken) {
        return Unirest.post(restSettings.logoutUrl()).queryString("username", username).queryString("security_token", securityToken);
    }

    public static HttpRequestWithBody newCompetitionRequest(String competitionName, String competitionType, String username, String securityToken) {
        return Unirest.post(restSettings.competitionNewUrl())
                .queryString("competition_name", competitionName)
                .queryString("competition_type", competitionType)
                .queryString("username", username)
                .queryString("security_token", securityToken);
    }

    public static HttpRequest listOwnedCompetitionsRequest(String username, String securityToken) {
        return Unirest.get(restSettings.competitionListByOwnerUrl())
                .queryString("username", username)
                .queryString("security_token", securityToken);
    }

}
