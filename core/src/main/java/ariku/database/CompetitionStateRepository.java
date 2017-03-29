package ariku.database;

import ariku.util.Competition;
import ariku.util.CompetitionState;

import java.util.List;
import java.util.Optional;

/**
 * @author Ari Aaltonen
 */
public interface CompetitionStateRepository {
    void store(CompetitionState competition);
    Optional<CompetitionState> get(String competitionId);
    List<CompetitionState> list(Competition competition);
    void update(CompetitionState competition);
    void delete(CompetitionState competitionId);
}
