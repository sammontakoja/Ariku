package io.ariku.verification;

/**
 * @author Ari Aaltonen
 */
public class AuthorizeRequest {
    public String username;
    public String securityMessage;

    public AuthorizeRequest(String username, String securityMessage) {
        this.username = username;
        this.securityMessage = securityMessage;
    }

    public AuthorizeRequest() {
    }

    @Override
    public String toString() {
        return "AuthorizeRequest{" +
                "username='" + username + '\'' +
                ", securityMessage='" + securityMessage + '\'' +
                '}';
    }
}
