package io.ariku.composer;

import io.ariku.database.simple.SimpleDatabase;
import io.ariku.owner.OwnerService;
import io.ariku.verification.SecurityCleaner;
import io.ariku.verification.UserVerificationService;

/**
 * @author Ari Aaltonen
 */
public enum Composer {

    SIMPLE;

    public final SimpleDatabase database = new SimpleDatabase();
    public final UserVerificationService userVerificationService;
    public final OwnerService ownerService;

    public void printDatabaseContentToConsole() {
        System.out.println("Users:" + database.userVerifications());
        System.out.println("Competitions:" + database.competitions());
        System.out.println("Owners:" + database.owners());
    }

    Composer() {

        // Build services using database
        userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationDatabase = database;

        SecurityCleaner securityCleaner = new SecurityCleaner();
        securityCleaner.userVerificationDatabase = database;
        securityCleaner.wipeTokensWhichAreOlderThan(60, 900);

        ownerService = new OwnerService();
        ownerService.competitionDatabase = database;
        ownerService.competitionStateDatabase = database;
        ownerService.ownerDatabase = database;
        ownerService.userAuthorizer = userVerificationService;
        ownerService.userDatabase = database;
    }

}
