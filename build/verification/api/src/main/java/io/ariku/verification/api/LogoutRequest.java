package io.ariku.verification.api;

/**
 * @author Ari Aaltonen
 */
public class LogoutRequest {

    public String userId = "";
    public String securityMessage = "";

    public LogoutRequest() {
    }

    public LogoutRequest(String userId) {
        this.userId = userId;
    }

    public LogoutRequest(String userId, String securityMessage) {
        this.userId = userId;
        this.securityMessage = securityMessage;
    }
}
