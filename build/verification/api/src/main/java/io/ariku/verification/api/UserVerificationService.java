package io.ariku.verification.api;

/**
 * @author Ari Aaltonen
 */
public class UserVerificationService {

    public UserVerificationDatabase userVerificationDatabase;

    public boolean signUp(SignUpRequest signUpRequest) {
        UserVerification userVerification = userVerificationDatabase.readUserVerification(signUpRequest.userId);
        return !userVerification.isFound;
    }

    public boolean verifySignUp(VerifySignUpRequest verifySignUpRequest) {
        UserVerification userVerification = userVerificationDatabase.readUserVerification(verifySignUpRequest.userId);
        return userVerification.isFound && userVerification.isSignedIn;
    }

    public boolean login(LoginRequest loginRequest) {
        UserVerification userVerification = userVerificationDatabase.readUserVerification(loginRequest.userId);
        return userVerification.isFound && userVerification.isSignedIn && userVerification.isSignedInConfirmed;
    }

    public boolean logout(LogoutRequest logoutRequest) {
        UserVerification userVerification = userVerificationDatabase.readUserVerification(logoutRequest.userId);
        return userVerification.isFound && userVerification.isLoggedIn;
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
