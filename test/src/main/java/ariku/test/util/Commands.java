package ariku.test.util;

import ariku.verification.LoginRequest;
import ariku.verification.SignUpRequest;
import ariku.verification.UserVerificationService;
import ariku.verification.VerifySignUpRequest;

/**
 * @author Ari Aaltonen
 */
public class Commands {

    private final UserVerificationService service;

    public Commands(UserVerificationService service) {
        this.service = service;
    }

    public String loginWithUsername(String username) {
        service.signUp(new SignUpRequest(username));
        service.verifySignUp(new VerifySignUpRequest(username));
        return service.login(new LoginRequest(username));
    }

}
