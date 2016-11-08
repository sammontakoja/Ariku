package io.ariku.verification.api;

/**
 * @author Ari Aaltonen
 */
public class UserVerificationService {

    public UserVerificationStore userVerificationStore;

    public void signUp(SignUpRequest signUpRequest) {

    }

    public void verifySignUp() {

    }

    public boolean login(LoginRequest loginRequest) {
        UserVerification userVerification = userVerificationStore.findUserVerification(loginRequest.userId);
        return userVerification.isFound && userVerification.isSignedIn && userVerification.isSignedInConfirmed;
    }

    public boolean logout(LogoutRequest logoutRequest) {
        UserVerification userVerification = userVerificationStore.findUserVerification(logoutRequest.userId);
        return userVerification.isFound && userVerification.isLoggedIn;
    }

    public boolean isUserLoggedIn(String userId) {
        return userVerificationStore.findUserVerification(userId).isLoggedIn;
    }

    public boolean isUserSignedIn(String userId) {
        return userVerificationStore.findUserVerification(userId).isSignedIn;
    }

    public boolean isUserSignedInConfirmed(String userId) {
        return userVerificationStore.findUserVerification(userId).isSignedInConfirmed;
    }

    public boolean login(String userId) {
        return false;
    }
}
