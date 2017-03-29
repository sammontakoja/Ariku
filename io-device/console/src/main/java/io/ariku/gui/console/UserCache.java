package io.ariku.gui.console;

import ariku.util.AuthorizeRequest;

/**
 * @author Ari Aaltonen
 */
public class UserCache {

    public static String username;
    public static String securityToken;

    public static AuthorizeRequest authorizeRequest() {
        return new AuthorizeRequest(username, securityToken);
    }

    public static void clear() {
        username = "";
        securityToken = "";
    }

    public static void printToConsole() {
        System.out.println(String.format("UserCache [username:%s] [securityToken:%s]", username, securityToken));
    }

}
