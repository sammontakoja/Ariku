package io.ariku.owner;

import io.ariku.util.data.Competition;

import java.util.List;
import java.util.Optional;

/**
 * @author Ari Aaltonen
 */
public interface CompetitionDatabase {

    Competition createCompetition(String userId, String competitionName, String competitionType);

    void deleteCompetition(String id);

    void updateCompetitionName(String name, String id);

    void updateCompetitionType(String type, String id);

    List<Competition> competitionsByOwner(String userId);

    List<Competition> competitions();

    Optional<Competition> competitionById(String id);

}
