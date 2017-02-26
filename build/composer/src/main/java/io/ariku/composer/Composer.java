package io.ariku.composer;

import io.ariku.competition.skeet.api.SkeetCompetitionService;
import io.ariku.competition.skeet.simple.SimpleCompetitionDatabase;
import io.ariku.database.simple.SimpleUserVerificationDatabase;
import io.ariku.verification.api.*;

/**
 * @author Ari Aaltonen
 */
public enum Composer {

    COMPOSER;

    public final String arikuVersion;
    public final UserVerificationService userVerificationService;
    public final SkeetCompetitionService skeetCompetitionService;

    Composer() {
        StringBuilder sb = new StringBuilder();

        SimpleUserVerificationDatabase simpleUserVerificationDatabase = new SimpleUserVerificationDatabase();
        sb.append("\nSimpleUserVerificationDatabase version 1.0-SNAPSHOT (Competition data will be lost after program is closed)");

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationDatabase = simpleUserVerificationDatabase;
        sb.append("UserVerificationService version 1.0-SNAPSHOT");

        SkeetCompetitionService skeetCompetitionService = new SkeetCompetitionService();
        sb.append("\nSkeetCompetitionService version 1.0-SNAPSHOT");

        skeetCompetitionService.competitionDatabase = new SimpleCompetitionDatabase();
        sb.append("\nSimpleCompetitionDatabase version 1.0-SNAPSHOT (Competition data will be lost after program is closed)");

        System.out.println("User with userId 'user' verified signed up");
        userVerificationService.signUp(new SignUpRequest("user"));
        userVerificationService.verifySignUp(new VerifySignUpRequest("user"));

        SecurityCleaner securityCleaner = new SecurityCleaner();
        securityCleaner.userVerificationDatabase = simpleUserVerificationDatabase;
        securityCleaner.wipeTokensWhichAreOlderThan(60, 900);
        sb.append("\nStarted SecurityCleaner version 1.0-SNAPSHOT, scan interval 1 minute, clean security tokens older than 15 minutes");

        this.userVerificationService = userVerificationService;
        this.skeetCompetitionService = skeetCompetitionService;
        this.arikuVersion = sb.toString();
    }

}
