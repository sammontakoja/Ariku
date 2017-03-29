package ariku;

import ariku.owner.OwnerService;
import ariku.user.UserService;
import ariku.verification.UserVerificationService;

/**
 * @author Ari Aaltonen
 */
public interface CoreServices {
    UserVerificationService userVerificationService();
    OwnerService ownerService();
    UserService userService();
}
