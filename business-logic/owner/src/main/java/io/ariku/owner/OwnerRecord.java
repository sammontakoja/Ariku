package io.ariku.owner;

/**
 * @author Ari Aaltonen
 */
public class OwnerRecord {

    private String userId = "";
    private String competitionId = "";

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(String competitionId) {
        this.competitionId = competitionId;
    }

    @Override
    public String toString() {
        return "OwnerRecord{" +
                "userId='" + userId + '\'' +
                ", competitionId='" + competitionId + '\'' +
                '}';
    }
}
