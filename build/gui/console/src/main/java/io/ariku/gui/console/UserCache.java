package io.ariku.gui.console;

import io.ariku.verification.AuthorizeRequest;

/**
 * @author Ari Aaltonen
 */
public class UserCache {

    public static String username;
    public static String securityMessage;

    public static AuthorizeRequest authorizeRequest() {
        return new AuthorizeRequest(username, securityMessage);
    }

    public static void printToConsole() {
        System.out.println(String.format("UserCache [username:%s] [securityMessage:%s]", username, securityMessage));
    }

}
