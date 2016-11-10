package io.ariku.verification.api;

/**
 * @author Ari Aaltonen
 */
public class UserVerificationService {

    public UserVerificationCRUD userVerificationCRUD;

    public boolean signUp(SignUpRequest signUpRequest) {
        UserVerification userVerification = userVerificationCRUD.readUserVerification(signUpRequest.userId);
        return !userVerification.isFound;
    }

    public boolean verifySignUp(VerifySignUpRequest verifySignUpRequest) {
        UserVerification userVerification = userVerificationCRUD.readUserVerification(verifySignUpRequest.userId);
        return userVerification.isFound && userVerification.isSignedIn;
    }

    public boolean login(LoginRequest loginRequest) {
        UserVerification userVerification = userVerificationCRUD.readUserVerification(loginRequest.userId);
        return userVerification.isFound && userVerification.isSignedIn && userVerification.isSignedInConfirmed;
    }

    public boolean logout(LogoutRequest logoutRequest) {
        UserVerification userVerification = userVerificationCRUD.readUserVerification(logoutRequest.userId);
        return userVerification.isFound && userVerification.isLoggedIn;
    }

    public boolean isUserLoggedIn(String userId) {
        return userVerificationCRUD.readUserVerification(userId).isLoggedIn;
    }

    public boolean isUserSignedIn(String userId) {
        return userVerificationCRUD.readUserVerification(userId).isSignedIn;
    }

    public boolean isUserSignedInConfirmed(String userId) {
        return userVerificationCRUD.readUserVerification(userId).isSignedInConfirmed;
    }

}
