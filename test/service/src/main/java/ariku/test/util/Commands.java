package ariku.test.util;

import io.ariku.verification.LoginRequest;
import io.ariku.verification.SignUpRequest;
import io.ariku.verification.UserVerificationService;
import io.ariku.verification.VerifySignUpRequest;

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
