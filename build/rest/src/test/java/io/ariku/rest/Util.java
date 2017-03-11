package io.ariku.rest;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequestWithBody;

/**
 * @author Ari Aaltonen
 */
public class Util {

    public static HttpRequestWithBody signUpRequest(String username) {
        return Unirest.post(signUpUrl()).queryString("username", username);
    }

    public static HttpRequestWithBody verifySignUpRequest(String username) {
        return Unirest.post(verifySignUpUrl()).queryString("username", username);
    }

    public static HttpRequestWithBody loginRequest(String username) {
        return Unirest.post(loginUrl()).queryString("username", username);
    }

    public static HttpRequestWithBody logoutRequest(String username, String securityToken) {
        return Unirest.post(logoutUrl()).queryString("username", username).queryString("security_token", securityToken);
    }

    public static HttpRequestWithBody newCompetitionRequest(String competitionName, String competitionType, String username, String securityToken) {
        return Unirest.post(logoutUrl())
                .queryString("competition_name", competitionName)
                .queryString("competition_type", competitionType)
                .queryString("username", username)
                .queryString("security_token", securityToken);
    }

    public static String signUpUrl() {
        return "http://localhost:5000/verification/signup";
    }

    public static String verifySignUpUrl() {
        return "http://localhost:5000/verification/verifysignup";
    }

    public static String loginUrl() {
        return "http://localhost:5000/verification/login";
    }

    public static String logoutUrl() {
        return "http://localhost:5000/verification/logout";
    }

}
