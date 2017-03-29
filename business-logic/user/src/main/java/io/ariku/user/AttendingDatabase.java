package io.ariku.user;

import io.ariku.util.data.Competition;

import java.util.List;

/**
 * @author Ari Aaltonen
 */
public interface AttendingDatabase {

    void add(AttendingInfo attendingInfo);
    void remove(AttendingInfo any);
    List<Competition> competitionsByAttendingUser(String userId);
}
