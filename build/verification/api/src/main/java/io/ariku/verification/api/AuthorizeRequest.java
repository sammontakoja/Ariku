package io.ariku.verification.api;

/**
 * @author Ari Aaltonen
 */
public class AuthorizeRequest {
    public String userId;
    public String securityMessage;

    public AuthorizeRequest(String userId, String securityMessage) {
        this.userId = userId;
        this.securityMessage = securityMessage;
    }

    public AuthorizeRequest() {
    }
}
