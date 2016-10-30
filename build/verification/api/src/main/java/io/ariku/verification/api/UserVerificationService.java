package io.ariku.verification.api;

import io.ariku.util.data.User;

/**
 * @author Ari Aaltonen
 */
public class UserVerificationService {

    public UserVerificationStore userVerificationStore;

    public void signUp(User user) {

    }

    public void verifySignUp() {

    }

    public void login(User user) {

    }

    public void logout() {

    }

    public boolean isUserLoggedIn(User user) {
        return userVerificationStore.findUserVerification(user).isLoggedIn;
    }

    public boolean isUserSignedIn(User user) {
        return userVerificationStore.findUserVerification(user).isSignedIn;
    }


    public boolean isUserSignedInConfirmed(User user) {
        return userVerificationStore.findUserVerification(user).isSignedInConfirmed;
    }
}
