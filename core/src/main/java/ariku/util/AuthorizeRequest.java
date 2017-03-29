package ariku.util;

/**
 * @author Ari Aaltonen
 */
public class AuthorizeRequest {
    public String username;
    public String securityToken;

    public AuthorizeRequest(String username, String securityToken) {
        this.username = username;
        this.securityToken = securityToken;
    }

    public AuthorizeRequest() {
    }

    @Override
    public String toString() {
        return "AuthorizeRequest{" +
                "username='" + username + '\'' +
                ", securityToken='" + securityToken + '\'' +
                '}';
    }
}
