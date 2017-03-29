package ariku.database;

import ariku.util.User;

import java.util.List;
import java.util.Optional;

/**
 * @author Ari Aaltonen
 */
public interface UserRepository {
    void store(User owner);
    Optional<User> getById(String id);
    Optional<User> getByUsername(String username);
    List<User> list(User owner);
    void update(User owner);
    void delete(User owner);
}
