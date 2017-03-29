package io.ariku.verification;

import io.ariku.util.data.AuthorizeRequest;

/**
 * @author Ari Aaltonen
 */
public interface UserAuthorizer {

    String authorizedUser(AuthorizeRequest authorizeRequest);

}
