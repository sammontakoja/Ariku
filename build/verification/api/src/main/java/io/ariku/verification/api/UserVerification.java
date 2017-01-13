package io.ariku.verification.api;

/**
 * @author Ari Aaltonen
 */
public class UserVerification {
    public String userId = "";
    public SecurityMessage securityMessage = new SecurityMessage();
    public boolean isSignedIn;
    public boolean isSignedInConfirmed;

    public UserVerification() {
    }

    public UserVerification(String userId) {
        this.userId = userId;
    }
}
