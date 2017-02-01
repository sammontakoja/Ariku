package io.ariku.owner.api;

import io.ariku.util.data.User;

import java.util.List;

/**
 * @author Ari Aaltonen
 */
public interface CompetitionDatabase {

    Competition createCompetition(String userId, String competitionName, String competitionType);

    void deleteCompetition(Competition competition);

    void updateCompetition(Competition competition);

    List<Competition> ownersCompetitions(String userId);

}
