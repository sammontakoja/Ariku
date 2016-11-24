package io.ariku.competition.skeet.api;

import java.util.List;

/**
 * @author Ari Aaltonen
 */
public class SkeetCompetitionService {

    public CompetitionDatabase competitionDatabase;

    public void createNewCompetition(String userid, String competitionName) {
        Competition competition = new Competition(competitionName);
        competitionDatabase.create(userid, competition);
    }

    public List<Competition> listCompetitions(String userid) {
        return competitionDatabase.competitionsByUser(userid);
    }

}
