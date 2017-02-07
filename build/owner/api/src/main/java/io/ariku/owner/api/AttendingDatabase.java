package io.ariku.owner.api;

/**
 * @author Ari Aaltonen
 */
public interface AttendingDatabase {

    void openAttending(String userId, String competitionId);
    void closeAttending(String userId, String competitionId);
}
