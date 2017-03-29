package ariku.database;

import ariku.user.AttendingInfo;
import ariku.util.Competition;

import java.util.List;

/**
 * @author Ari Aaltonen
 */
public interface AttendingRepository {

    void add(AttendingInfo attendingInfo);
    void remove(AttendingInfo any);
    List<Competition> competitionsByAttendingUser(String userId);
}
