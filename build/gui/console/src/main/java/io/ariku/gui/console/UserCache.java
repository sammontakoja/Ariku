package io.ariku.gui.console;

import io.ariku.verification.AuthorizeRequest;

/**
 * @author Ari Aaltonen
 */
public class UserCache {

    public static String username;
    public static String securityToken;

    public static AuthorizeRequest authorizeRequest() {
        return new AuthorizeRequest(username, securityToken);
    }

    public static void printToConsole() {
        System.out.println(String.format("UserCache [username:%s] [securityToken:%s]", username, securityToken));
    }

}
