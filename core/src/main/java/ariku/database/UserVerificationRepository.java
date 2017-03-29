package ariku.database;

import ariku.verification.UserVerification;

import java.util.List;
import java.util.Optional;

/**
 * @author Ari Aaltonen
 */
public interface UserVerificationRepository {
    void store(UserVerification x);
    Optional<UserVerification> getByUsername(String username);
    List<UserVerification> list(UserVerification x);
    List<UserVerification> list();
    void update(UserVerification x);
    void delete(UserVerification x);
    String uniqueUserId();
}
