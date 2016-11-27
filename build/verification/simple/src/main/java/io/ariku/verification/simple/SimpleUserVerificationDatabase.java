package io.ariku.verification.simple;


import io.ariku.verification.api.UserVerification;
import io.ariku.verification.api.UserVerificationDatabase;

import java.util.HashMap;

/**
 * @author Ari Aaltonen
 */
public class SimpleUserVerificationDatabase implements UserVerificationDatabase {

    private HashMap<String, UserVerification> userVerifications = new HashMap<>();

    @Override
    public void createUserVerification(String userId) {
        UserVerification userVerification = new UserVerification(userId);
        userVerifications.put(userId, userVerification);
    }

    @Override
    public UserVerification readUserVerification(String userId) {
        UserVerification foundUserVerification = userVerifications.get(userId);
        if (foundUserVerification != null)
            return foundUserVerification;
        else
            return new UserVerification();
    }

    @Override
    public void deleteUserVerification(String userId) {
        userVerifications.remove(userId);
    }

    @Override
    public void updateUserVerification(String userId, UserVerification userVerification) {
        UserVerification foundVerification = userVerifications.get(userId);
        if (foundVerification != null) {
            foundVerification.isSignedInConfirmed = userVerification.isSignedInConfirmed;
            foundVerification.isSignedIn = userVerification.isSignedIn;
            foundVerification.isLoggedIn = userVerification.isLoggedIn;
        }
    }

}
