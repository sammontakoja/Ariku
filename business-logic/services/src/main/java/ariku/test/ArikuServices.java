package ariku.test;

import io.ariku.owner.OwnerService;
import io.ariku.user.UserService;
import io.ariku.verification.UserVerificationService;

/**
 * @author Ari Aaltonen
 */
public interface ArikuServices {
    UserVerificationService userVerificationService();
    OwnerService ownerService();
    UserService userService();
}
