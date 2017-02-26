package io.ariku.verification;

/**
 * @author Ari Aaltonen
 */
public interface UserAuthorizer {

    boolean isAuthorized(AuthorizeRequest authorizeRequest);

}
