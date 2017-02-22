package io.ariku.owner.api;

import java.util.Optional;

/**
 * @author Ari Aaltonen
 */
public interface CompetitionStateDatabase {

    Optional<CompetitionState> competitionState(String competitionId);
    void openAttending(String competitionId);
    void closeAttending(String competitionId);
    void openCompetition(String competitionId);
    void closeCompetition(String competitionId);
}
