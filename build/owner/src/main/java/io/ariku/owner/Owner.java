package io.ariku.owner;

/**
 * @author Ari Aaltonen
 */
public class Owner {

    public String userId;
    public String competitionId;

    public Owner() {
    }

    public Owner(String userId, String competitionId) {
        this.userId = userId;
        this.competitionId = competitionId;
    }

    public Owner userId(String userId) {
        this.userId = userId;
        return this;
    }

    public Owner competitionId(String competitionId) {
        this.competitionId = competitionId;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Owner owner = (Owner) o;

        if (userId != null ? !userId.equals(owner.userId) : owner.userId != null) return false;
        return competitionId != null ? competitionId.equals(owner.competitionId) : owner.competitionId == null;

    }

    @Override
    public String toString() {
        return "Owner{" +
                "userId='" + userId + '\'' +
                ", competitionId='" + competitionId + '\'' +
                '}';
    }
}
