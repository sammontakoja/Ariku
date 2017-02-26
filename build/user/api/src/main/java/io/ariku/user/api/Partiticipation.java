package io.ariku.user.api;

/**
 * @author Ari Aaltonen
 */
public class Partiticipation {
    public String userId;
    public String competitionId;

    public Partiticipation(String userId, String competitionId) {
        this.userId = userId;
        this.competitionId = competitionId;
    }
}
