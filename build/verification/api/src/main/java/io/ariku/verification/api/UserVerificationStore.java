package io.ariku.verification.api;

import io.ariku.util.data.User;

/**
 * @author Ari Aaltonen
 */
public interface UserVerificationStore {
    UserVerification findUserVerification(User user);
}
