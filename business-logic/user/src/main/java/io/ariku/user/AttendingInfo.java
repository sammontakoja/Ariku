package io.ariku.user;

/**
 * @author Ari Aaltonen
 */
public class AttendingInfo {
    public String userId;
    public String competitionId;

    public AttendingInfo(String userId, String competitionId) {
        this.userId = userId;
        this.competitionId = competitionId;
    }
}
