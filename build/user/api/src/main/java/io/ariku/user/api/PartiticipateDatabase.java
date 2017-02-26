package io.ariku.user.api;

import io.ariku.util.data.Competition;

import java.util.List;

/**
 * @author Ari Aaltonen
 */
public interface PartiticipateDatabase {

    void addParticipation(Partiticipation partiticipation);
    void removeParticipation(Partiticipation any);
    List<Competition> competitions(String userId);
}
