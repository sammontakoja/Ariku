package io.ariku.verification;

/**
 * @author Ari Aaltonen
 */
public interface UserAuthorizer {

    String authorizedUser(AuthorizeRequest authorizeRequest);

}
