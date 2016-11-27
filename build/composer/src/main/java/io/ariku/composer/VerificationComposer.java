package io.ariku.composer;

import io.ariku.verification.api.UserVerificationService;
import io.ariku.verification.simple.SimpleUserVerificationDatabase;

/**
 * @author Ari Aaltonen
 */
public class VerificationComposer {
    public String version = "";

    public UserVerificationService userVerificationService() {
        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationDatabase = new SimpleUserVerificationDatabase();
        version += "UserVerificationService version 1.0-SNAPSHOT";
        version += "\nSimpleUserVerificationDatabase version 1.0-SNAPSHOT (Competition data will be lost after program is closed)";
        return userVerificationService;
    }
}
