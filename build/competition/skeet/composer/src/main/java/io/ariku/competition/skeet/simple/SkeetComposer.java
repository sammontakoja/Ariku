package io.ariku.competition.skeet.simple;

import io.ariku.competition.skeet.api.SkeetCompetitionService;

/**
 * @author Ari Aaltonen
 */
public class SkeetComposer {

    public String version = "";

    public SkeetCompetitionService simpleSkeetCompetitionService() {
        SkeetCompetitionService skeetCompetitionService = new SkeetCompetitionService();
        skeetCompetitionService.competitionDatabase = new SimpleCompetitionDatabase();
        version += "SkeetCompetitionService version 1.0-SNAPSHOT";
        version += "\nSimpleCompetitionDatabase version 1.0-SNAPSHOT (Competition data will be lost after program is closed)";
        return skeetCompetitionService;
    }
}
