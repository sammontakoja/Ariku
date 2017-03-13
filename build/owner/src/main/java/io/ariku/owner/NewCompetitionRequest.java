package io.ariku.owner;

import io.ariku.verification.AuthorizeRequest;

/**
 * @author Ari Aaltonen
 */
public class NewCompetitionRequest {
    public String competitionName;
    public String competitionType;
    public AuthorizeRequest authorizeRequest;

    public NewCompetitionRequest competitionName(String competitionName) {
        this.competitionName = competitionName;
        return this;
    }

    public NewCompetitionRequest competitionType(String competitionType) {
        this.competitionType = competitionType;
        return this;
    }

    public NewCompetitionRequest authorizeRequest(AuthorizeRequest authorizeRequest) {
        this.authorizeRequest = authorizeRequest;
        return this;
    }
}
