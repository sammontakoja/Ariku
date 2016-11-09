package io.ariku.verification.api;

/**
 * @author Ari Aaltonen
 */
public interface UserVerificationStore {
    UserVerification findUserVerification(String userId);
}
