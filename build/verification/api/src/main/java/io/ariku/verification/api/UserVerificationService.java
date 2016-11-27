package io.ariku.verification.api;

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

    public boolean login(LoginRequest loginRequest) {
        UserVerification userVerification = userVerificationDatabase.readUserVerification(loginRequest.userId);

        boolean canLogin = !userVerification.userId.isEmpty() && userVerification.isSignedIn
                && userVerification.isSignedInConfirmed;

        if (canLogin) {
            userVerification.isLoggedIn = true;
            userVerificationDatabase.updateUserVerification(userVerification);
        }

        return canLogin;
    }

    public boolean logout(LogoutRequest logoutRequest) {

        UserVerification userVerification = userVerificationDatabase.readUserVerification(logoutRequest.userId);

        boolean canLogout = !userVerification.userId.isEmpty() && userVerification.isSignedIn
                && userVerification.isSignedInConfirmed && userVerification.isLoggedIn;

        if (canLogout) {
            userVerification.isLoggedIn = false;
            userVerificationDatabase.updateUserVerification(userVerification);
        }

        return canLogout;
    }

    public boolean isUserLoggedIn(String userId) {
        return userVerificationDatabase.readUserVerification(userId).isLoggedIn;
    }

    public boolean isUserSignedIn(String userId) {
        return userVerificationDatabase.readUserVerification(userId).isSignedIn;
    }

    public boolean isUserSignedInConfirmed(String userId) {
        return userVerificationDatabase.readUserVerification(userId).isSignedInConfirmed;
    }

}
