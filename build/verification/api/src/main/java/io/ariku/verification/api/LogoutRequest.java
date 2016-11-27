package io.ariku.verification.api;

/**
 * @author Ari Aaltonen
 */
public class LogoutRequest {

    public String userId = "";

    public LogoutRequest() {
    }

    public LogoutRequest(String userId) {
        this.userId = userId;
    }
}
