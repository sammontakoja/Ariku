package io.ariku.owner;

import io.ariku.util.data.User;

import java.util.Optional;

/**
 * @author Ari Aaltonen
 */
public interface UserDatabase {
    Optional<User> findUserByUsername(String usernameOfNewOwner);
}
