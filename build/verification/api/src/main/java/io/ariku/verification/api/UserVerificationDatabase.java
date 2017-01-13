package io.ariku.verification.api;

import java.util.List;

/**
 * @author Ari Aaltonen
 */
public interface UserVerificationDatabase {

    void createUserVerification(String userId);

    UserVerification readUserVerification(String userId);

    void deleteUserVerification(String userId);

    void updateUserVerification(UserVerification userVerification);

    List<UserVerification> userVerifications();
}
