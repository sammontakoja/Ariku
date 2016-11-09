package io.ariku.verification.api;

/**
 * @author Ari Aaltonen
 */
public class UserVerificationService {

    public UserVerificationStore userVerificationStore;

    public boolean signUp(SignUpRequest signUpRequest) {
        UserVerification userVerification = userVerificationStore.readUserVerification(signUpRequest.userId);
        return !userVerification.isFound;
    }

    public boolean verifySignUp(VerifySignUpRequest verifySignUpRequest) {
        UserVerification userVerification = userVerificationStore.readUserVerification(verifySignUpRequest.userId);
        return userVerification.isFound && userVerification.isSignedIn;
    }

    public boolean login(LoginRequest loginRequest) {
        UserVerification userVerification = userVerificationStore.readUserVerification(loginRequest.userId);
        return userVerification.isFound && userVerification.isSignedIn && userVerification.isSignedInConfirmed;
    }

    public boolean logout(LogoutRequest logoutRequest) {
        UserVerification userVerification = userVerificationStore.readUserVerification(logoutRequest.userId);
        return userVerification.isFound && userVerification.isLoggedIn;
    }

    public boolean isUserLoggedIn(String userId) {
        return userVerificationStore.readUserVerification(userId).isLoggedIn;
    }

    public boolean isUserSignedIn(String userId) {
        return userVerificationStore.readUserVerification(userId).isSignedIn;
    }

    public boolean isUserSignedInConfirmed(String userId) {
        return userVerificationStore.readUserVerification(userId).isSignedInConfirmed;
    }

}
