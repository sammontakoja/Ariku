package ariku.verification;

import ariku.util.AuthorizeRequest;

/**
 * @author Ari Aaltonen
 */
public interface UserAuthorizer {

    String authorizedUser(AuthorizeRequest authorizeRequest);

}
