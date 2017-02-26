package io.ariku.verification;

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
