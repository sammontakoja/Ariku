package io.ariku.verification.api;

/**
 * @author Ari Aaltonen
 */
public class SignUpRequest {
    public String userId = "";

    public SignUpRequest() {
    }

    public SignUpRequest(String userId) {
        this.userId = userId;
    }
}
