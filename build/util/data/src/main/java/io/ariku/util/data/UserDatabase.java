package io.ariku.util.data;

import java.util.Optional;

/**
 * @author Ari Aaltonen
 */
public interface UserDatabase {
    Optional<User> findUserByUsername(String usernameOfNewOwner);
    void store(User user);
}
