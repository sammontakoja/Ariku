package io.ariku.composer;

import io.ariku.verification.LoginRequest;
import io.ariku.verification.SignUpRequest;
import io.ariku.verification.VerifySignUpRequest;

/**
 * @author Ari Aaltonen
 */
public class Commands {

    private final Composer composer;

    public Commands(Composer composer) {
        this.composer = composer;
    }

    public String loginWithUsername(String username) {
        composer.userVerificationService.signUp(new SignUpRequest(username));
        composer.userVerificationService.verifySignUp(new VerifySignUpRequest(username));
        return composer.userVerificationService.login(new LoginRequest(username));
    }

}
