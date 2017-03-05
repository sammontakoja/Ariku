package io.ariku.owner;

import java.util.List;

/**
 * @author Ari Aaltonen
 */
public interface OwnerDatabase {
    void addOwner(Owner owner);
    List<Owner> ownersByCompetition(String competitionId);
    List<Owner> owners();
}
