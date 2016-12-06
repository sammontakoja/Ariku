package io.ariku.console;

/**
 * @author Ari Aaltonen
 */
public enum ConsoleUser {

    CONSOLE_USER;

    private String userId;

    public String userId() {
        return userId;
    }

    public void userId(String userId) {
        this.userId = userId;
    }
}
