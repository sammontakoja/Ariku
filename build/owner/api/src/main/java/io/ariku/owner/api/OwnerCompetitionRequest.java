package io.ariku.owner.api;

import io.ariku.verification.api.AuthorizeRequest;

/**
 * @author Ari Aaltonen
 */
public class OwnerCompetitionRequest {
    public AuthorizeRequest authorizeRequest;
    public String competitionId;
    public String userId;
}
