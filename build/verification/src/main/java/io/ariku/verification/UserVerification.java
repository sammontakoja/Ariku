package io.ariku.verification;

/**
 * @author Ari Aaltonen
 */
public class UserVerification {

    public String username = "";
    public String userId = "";
    public SecurityMessage securityMessage = new SecurityMessage();
    public boolean isSignedInConfirmed;

    public UserVerification() {
    }

    public UserVerification(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserVerification{" +
                "username='" + username + '\'' +
                ", username='" + userId + '\'' +
                ", securityToken=" + securityMessage +
                ", isSignedInConfirmed=" + isSignedInConfirmed +
                '}';
    }
}
