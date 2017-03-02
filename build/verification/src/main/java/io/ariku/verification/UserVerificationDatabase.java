package io.ariku.verification;

import java.util.List;
import java.util.Optional;

/**
 * @author Ari Aaltonen
 */
public interface UserVerificationDatabase {

    void createUserVerification(String username);

    Optional<UserVerification> findByUserId(String userId);

    Optional<UserVerification> findByUsername(String username);

    void deleteUserVerification(String userId);

    void updateUserVerification(UserVerification userVerification);

    List<UserVerification> userVerifications();
}
