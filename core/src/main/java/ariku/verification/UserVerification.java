package ariku.verification;

/**
 * @author Ari Aaltonen
 */
public class UserVerification {

    private String username = "";
    private String userId = "";
    private SecurityMessage securityMessage = new SecurityMessage();
    private boolean signedInConfirmed;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public SecurityMessage getSecurityMessage() {
        return securityMessage;
    }

    public void setSecurityMessage(SecurityMessage securityMessage) {
        this.securityMessage = securityMessage;
    }

    public boolean isSignedInConfirmed() {
        return signedInConfirmed;
    }

    public void setSignedInConfirmed(boolean signedInConfirmed) {
        this.signedInConfirmed = signedInConfirmed;
    }

    @Override
    public String toString() {
        return "UserVerification{" +
                "username='" + username + '\'' +
                ", userId='" + userId + '\'' +
                ", securityMessage=" + securityMessage +
                ", signedInConfirmed=" + signedInConfirmed +
                '}';
    }
}
