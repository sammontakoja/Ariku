package io.ariku.composer;

import io.ariku.competition.skeet.api.SkeetCompetitionService;
import io.ariku.competition.skeet.simple.SimpleCompetitionDatabase;
import io.ariku.verification.api.UserVerificationService;
import io.ariku.verification.simple.SimpleUserVerificationDatabase;

/**
 * @author Ari Aaltonen
 */
public enum Composer {

    composer;

    public final String arikuVersion;
    public final UserVerificationService userVerificationService;
    public final SkeetCompetitionService skeetCompetitionService;

    Composer() {
        StringBuilder sb = new StringBuilder();

        UserVerificationService userVerificationService = new UserVerificationService();
        sb.append("UserVerificationService version 1.0-SNAPSHOT");

        userVerificationService.userVerificationDatabase = new SimpleUserVerificationDatabase();
        sb.append("\nSimpleUserVerificationDatabase version 1.0-SNAPSHOT (Competition data will be lost after program is closed)");

        SkeetCompetitionService skeetCompetitionService = new SkeetCompetitionService();
        sb.append("\nSkeetCompetitionService version 1.0-SNAPSHOT");

        skeetCompetitionService.competitionDatabase = new SimpleCompetitionDatabase();
        sb.append("\nSimpleCompetitionDatabase version 1.0-SNAPSHOT (Competition data will be lost after program is closed)");

        this.userVerificationService = userVerificationService;
        this.skeetCompetitionService = skeetCompetitionService;
        this.arikuVersion = sb.toString();
    }

}
