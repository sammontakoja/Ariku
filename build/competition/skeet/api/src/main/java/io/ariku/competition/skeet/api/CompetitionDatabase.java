package io.ariku.competition.skeet.api;

import java.util.List;

/**
 * @author Ari Aaltonen
 */
public interface CompetitionDatabase {

    void create(String user, Competition competition);
    List<Competition> competitionsByUser(String user);
    List<Competition> competitions();
    void delete(String user, String competitionId);
    void update(String user, String competitionId);
}
