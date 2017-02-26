package io.ariku.verification;

import java.util.UUID;

/**
 * @author Ari Aaltonen
 */
public class UserVerificationService implements UserAuthorizer {

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
            userVerification.securityMessage.token = securityMessage;
            userVerificationDatabase.updateUserVerification(userVerification);
            return securityMessage;
        }

        return "";
    }

    public boolean logout(LogoutRequest logoutRequest) {

        UserVerification userVerification = userVerificationDatabase.readUserVerification(logoutRequest.userId);

        boolean canLogout = !userVerification.userId.isEmpty() && userVerification.isSignedIn
                && userVerification.isSignedInConfirmed
                && userVerification.securityMessage.token.equals(logoutRequest.securityMessage);

        if (canLogout) {
            userVerification.securityMessage.token = "";
            userVerificationDatabase.updateUserVerification(userVerification);
        }

        return canLogout;
    }

    public boolean isAuthorized(AuthorizeRequest authorizeRequest) {
        UserVerification userVerification = userVerificationDatabase.readUserVerification(authorizeRequest.userId);
        return userVerification != null && userVerification.securityMessage.token.equals(authorizeRequest.securityMessage);
    }

    public boolean isUserSignedIn(String userId) {
        return userVerificationDatabase.readUserVerification(userId).isSignedIn;
    }

    public boolean isUserSignedInConfirmed(String userId) {
        return userVerificationDatabase.readUserVerification(userId).isSignedInConfirmed;
    }

}
