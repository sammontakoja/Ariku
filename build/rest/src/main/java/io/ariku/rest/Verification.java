package io.ariku.rest;

import io.ariku.composer.Composer;
import io.ariku.verification.*;

import java.util.Optional;

/**
 * @author Ari Aaltonen
 */
public class Verification {

    public Composer composer;
    
    public String signUp(String username) {
        composer.userVerificationService.signUp(new SignUpRequest(username));

        Optional<UserVerification> userVerification = composer.userVerificationService.userVerificationDatabase.findByUsername(username);
        if (userVerification.isPresent())
            return "OK";

        return "FAIL";
    }

    public String verifySignUp(String username) {
        composer.userVerificationService.verifySignUp(new VerifySignUpRequest(username));

        Optional<UserVerification> userVerification = composer.userVerificationService.userVerificationDatabase.findByUsername(username);
        if (userVerification.isPresent())
            if (userVerification.get().isSignedInConfirmed)
                return "OK";

        return "FAIL";
    }

    public String login(String username) {
        return composer.userVerificationService.login(new LoginRequest(username));
    }

    public String logout(String username, String securityToken) {
        composer.userVerificationService.logout(new AuthorizeRequest(username, securityToken));

        Optional<UserVerification> userVerification = composer.userVerificationService.userVerificationDatabase.findByUsername(username);
        if (userVerification.isPresent())
            if (userVerification.get().securityMessage.token.isEmpty())
                return "OK";

        return "FAIL";
    }
    
}
