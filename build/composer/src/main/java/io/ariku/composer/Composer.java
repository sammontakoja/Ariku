package io.ariku.composer;

import io.ariku.database.simple.SimpleDatabase;
import io.ariku.owner.OwnerService;
import io.ariku.verification.SecurityCleaner;
import io.ariku.verification.SignUpRequest;
import io.ariku.verification.UserVerificationService;
import io.ariku.verification.VerifySignUpRequest;

/**
 * @author Ari Aaltonen
 */
public enum Composer {

    SIMPLE;

    public final UserVerificationService userVerificationService;
    public final OwnerService ownerService;

    Composer() {

        SimpleDatabase database = new SimpleDatabase();

        // Build services using database
        userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationDatabase = database;

        ownerService = new OwnerService();
        ownerService.competitionDatabase = database;
        ownerService.competitionStateDatabase = database;
        ownerService.ownerDatabase = database;
        ownerService.userAuthorizer = userVerificationService;

        SecurityCleaner securityCleaner = new SecurityCleaner();
        securityCleaner.userVerificationDatabase = database;
        securityCleaner.wipeTokensWhichAreOlderThan(60, 900);

        // Construct initial setup using services
        System.out.println("User with userId 'user' verified signed up");
        userVerificationService.signUp(new SignUpRequest("user"));
        userVerificationService.verifySignUp(new VerifySignUpRequest("user"));
    }

}
