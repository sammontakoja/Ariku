package io.ariku.composer;

import io.ariku.database.simple.SimpleDatabase;
import io.ariku.owner.CompetitionDatabase;
import io.ariku.owner.OwnerDatabase;
import io.ariku.owner.OwnerService;
import io.ariku.user.UserService;
import io.ariku.util.data.CompetitionStateDatabase;
import io.ariku.util.data.UserDatabase;
import io.ariku.verification.SecurityCleaner;
import io.ariku.verification.UserVerificationDatabase;
import io.ariku.verification.UserVerificationService;

/**
 * @author Ari Aaltonen
 */
public class Composer {

    // Services
    public final UserVerificationService userVerificationService;
    public final OwnerService ownerService;
    public final UserService userService;

    // Database implementations
    private final UserVerificationDatabase userVerificationDatabase;
    private final CompetitionDatabase competitionDatabase;
    private final OwnerDatabase ownerDatabase;
    private final UserDatabase userDatabase;
    private final CompetitionStateDatabase competitionStateDatabase;

    public Composer() {

        SimpleDatabase simpleDatabase = new SimpleDatabase();
        this.userVerificationDatabase = simpleDatabase;
        this.competitionDatabase = simpleDatabase;
        this.ownerDatabase = simpleDatabase;
        this.userDatabase = simpleDatabase;
        this.competitionStateDatabase = simpleDatabase;

        // Build services using implemented databases
        userVerificationService = new UserVerificationService();
        userVerificationService.userDatabase = userDatabase;
        userVerificationService.userVerificationDatabase = userVerificationDatabase;

        SecurityCleaner securityCleaner = new SecurityCleaner();
        securityCleaner.userVerificationDatabase = userVerificationDatabase;
        securityCleaner.wipeTokensWhichAreOlderThan(60, 900);

        ownerService = new OwnerService();
        ownerService.competitionDatabase = competitionDatabase;
        ownerService.competitionStateDatabase = competitionStateDatabase;
        ownerService.ownerDatabase = ownerDatabase;

        userService = new UserService();
        userService.userDatabase = userDatabase;
    }

}
