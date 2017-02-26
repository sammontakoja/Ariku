package io.ariku.user.api;

import io.ariku.verification.api.AuthorizeRequest;

/**
 * @author Ari Aaltonen
 */
public class ParticipateRequest {
    public String competitionId;
    public AuthorizeRequest authorizeRequest;
}
