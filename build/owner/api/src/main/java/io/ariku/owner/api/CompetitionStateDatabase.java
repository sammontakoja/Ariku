package io.ariku.owner.api;

/**
 * @author Ari Aaltonen
 */
public interface CompetitionStateDatabase {

    void openAttending(String userId, String competitionId);
    void closeAttending(String userId, String competitionId);
    void openCompetition(String userId, String competitionId);
    void closeCompetition(String userId, String competitionId);
}
