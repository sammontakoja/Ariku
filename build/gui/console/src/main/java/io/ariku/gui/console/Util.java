package io.ariku.gui.console;

import io.ariku.verification.AuthorizeRequest;

/**
 * @author Ari Aaltonen
 */
public class Util {

    public static String userId;
    public static String securityMessage;

    public static AuthorizeRequest authorizeRequestBasedOnLoggedInContent() {
        return new AuthorizeRequest(userId, securityMessage);
    }

}
