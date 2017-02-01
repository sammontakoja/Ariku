package io.ariku.verification.api;

/**
 * @author Ari Aaltonen
 */
public interface UserAuthorizer {

    boolean isAuthorized(AuthorizeRequest authorizeRequest);

}
