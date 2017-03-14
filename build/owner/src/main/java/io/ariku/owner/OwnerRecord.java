package io.ariku.owner;

/**
 * @author Ari Aaltonen
 */
public class OwnerRecord {

    private String userId;
    private String competition;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCompetition() {
        return competition;
    }

    public void setCompetition(String competition) {
        this.competition = competition;
    }

    @Override
    public String toString() {
        return "OwnerRecord{" +
                "userId='" + userId + '\'' +
                ", competition='" + competition + '\'' +
                '}';
    }
}
