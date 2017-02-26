package io.ariku.owner;

import java.util.List;

/**
 * @author Ari Aaltonen
 */
public interface OwnerDatabase {
    void addOwner(String userId, String competitionId);
    List<String> ownersByCompetition(String competitionId);
}
