package io.ariku.owner.api;

import io.ariku.verification.api.AuthorizeRequest;

/**
 * @author Ari Aaltonen
 */
public class NewCompetitionRequest {
    public String competitionName;
    public String competitionType;
    public AuthorizeRequest authorizeRequest;
}
