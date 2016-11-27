package io.ariku.verification.api;

/**
 * @author Ari Aaltonen
 */
public interface UserVerificationDatabase {

    void createUserVerification(String userId);

    UserVerification readUserVerification(String userId);

    void deleteUserVerification(String userId);

    void updateUserVerification(UserVerification userVerification);

}
