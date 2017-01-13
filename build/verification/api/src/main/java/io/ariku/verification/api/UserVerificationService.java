package io.ariku.verification.api;

import java.util.UUID;

/**
 * @author Ari Aaltonen
 */
public class UserVerificationService {

    public UserVerificationDatabase userVerificationDatabase;

    public boolean signUp(SignUpRequest signUpRequest) {
        UserVerification userVerification = userVerificationDatabase.readUserVerification(signUpRequest.userId);
        if (userVerification.userId.isEmpty()) {
            userVerificationDatabase.createUserVerification(signUpRequest.userId);
            return true;
        } else
            return false;
    }

    public boolean verifySignUp(VerifySignUpRequest verifySignUpRequest) {
        UserVerification userVerification = userVerificationDatabase.readUserVerification(verifySignUpRequest.userId);
        if (!userVerification.userId.isEmpty() && userVerification.isSignedIn) {
            userVerification.isSignedInConfirmed = true;
            userVerificationDatabase.updateUserVerification(userVerification);
            return true;
        }
        return false;
    }

    public String login(LoginRequest loginRequest) {
        UserVerification userVerification = userVerificationDatabase.readUserVerification(loginRequest.userId);

        boolean canLogin = !userVerification.userId.isEmpty() && userVerification.isSignedIn && userVerification.isSignedInConfirmed;

        if (canLogin) {
            String securityMessage = UUID.randomUUID().toString();
            userVerification.securityMessage = securityMessage;
            userVerificationDatabase.updateUserVerification(userVerification);
            return securityMessage;
        }

        return "";
    }

    public boolean logout(LogoutRequest logoutRequest) {

        UserVerification userVerification = userVerificationDatabase.readUserVerification(logoutRequest.userId);

        boolean canLogout = !userVerification.userId.isEmpty() && userVerification.isSignedIn
                && userVerification.isSignedInConfirmed
                && userVerification.securityMessage.equals(logoutRequest.securityMessage);

        if (canLogout) {
            userVerification.securityMessage = "";
            userVerificationDatabase.updateUserVerification(userVerification);
        }

        return canLogout;
    }

    public boolean isUserLoggedIn(String userId, String securityMessage) {
        UserVerification userVerification = userVerificationDatabase.readUserVerification(userId);
        return userVerification != null && userVerification.securityMessage.equals(securityMessage);
    }

    public boolean isUserSignedIn(String userId) {
        return userVerificationDatabase.readUserVerification(userId).isSignedIn;
    }

    public boolean isUserSignedInConfirmed(String userId) {
        return userVerificationDatabase.readUserVerification(userId).isSignedInConfirmed;
    }

}
