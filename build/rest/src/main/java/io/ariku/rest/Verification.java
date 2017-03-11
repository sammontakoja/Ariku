package io.ariku.rest;

import io.ariku.verification.*;

import java.util.Optional;

/**
 * @author Ari Aaltonen
 */
public class Verification {

    public UserVerificationService userVerificationService;
    
    public String signUp(String username) {
        userVerificationService.signUp(new SignUpRequest(username));

        Optional<UserVerification> userVerification = userVerificationService.userVerificationDatabase.findByUsername(username);
        if (userVerification.isPresent())
            return "OK";

        return "FAIL";
    }

    public String verifySignUp(String username) {
        userVerificationService.verifySignUp(new VerifySignUpRequest(username));

        Optional<UserVerification> userVerification = userVerificationService.userVerificationDatabase.findByUsername(username);
        if (userVerification.isPresent())
            if (userVerification.get().isSignedInConfirmed)
                return "OK";

        return "FAIL";
    }

    public String login(String username) {
        return userVerificationService.login(new LoginRequest(username));
    }

    public String logout(String username, String securityToken) {
        userVerificationService.logout(new AuthorizeRequest(username, securityToken));

        Optional<UserVerification> userVerification = userVerificationService.userVerificationDatabase.findByUsername(username);
        if (userVerification.isPresent())
            if (userVerification.get().securityMessage.token.isEmpty())
                return "OK";

        return "FAIL";
    }
    
}
