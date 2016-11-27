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
            userVerificationDatabase.updateUserVerification(userVerification);
            return true;
        }
        return false;
    }

    public boolean login(LoginRequest loginRequest) {
        UserVerification userVerification = userVerificationDatabase.readUserVerification(loginRequest.userId);
        return !userVerification.userId.isEmpty() && userVerification.isSignedIn && userVerification.isSignedInConfirmed;
    }

    public boolean logout(LogoutRequest logoutRequest) {
        UserVerification userVerification = userVerificationDatabase.readUserVerification(logoutRequest.userId);
        return !userVerification.userId.isEmpty() && userVerification.isLoggedIn;
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
