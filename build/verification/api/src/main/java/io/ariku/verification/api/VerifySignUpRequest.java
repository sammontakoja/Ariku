package io.ariku.verification.api;

/**
 * @author Ari Aaltonen
 */
public class VerifySignUpRequest {

    public String userId = "";

    public VerifySignUpRequest() {
    }

    public VerifySignUpRequest(String userId) {
        this.userId = userId;
    }
}
