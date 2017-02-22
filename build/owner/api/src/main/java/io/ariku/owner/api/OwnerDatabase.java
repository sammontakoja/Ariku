package io.ariku.owner.api;

import java.util.List;

/**
 * @author Ari Aaltonen
 */
public interface OwnerDatabase {
    void addOwner(String userId, String competitionId);
    List<String> ownersByCompetition(String competitionId);
}
