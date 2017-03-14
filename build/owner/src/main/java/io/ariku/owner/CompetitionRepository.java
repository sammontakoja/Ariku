package io.ariku.owner;

import io.ariku.util.data.Competition;

import java.util.List;
import java.util.Optional;

/**
 * @author Ari Aaltonen
 */
public interface CompetitionRepository {
    void store(Competition competition);
    Optional<Competition> get(String competitionId);
    List<Competition> list(Competition competition);
    void update(Competition competition);
    void delete(String competitionId);
}
