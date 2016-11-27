package io.ariku.verification.api;

import io.ariku.util.data.User;

/**
 * @author Ari Aaltonen
 */
public class LoginRequest {
    public String userId = "";

    public LoginRequest() {
    }

    public LoginRequest(String userId) {
        this.userId = userId;
    }
}
