package io.ariku.owner;

import io.ariku.verification.AuthorizeRequest;

/**
 * @author Ari Aaltonen
 */
public class AddOwnerRightsRequest {

    public String usernameOfNewOwner;
    public String competitionId;
    public AuthorizeRequest authorizeRequest;

    public AddOwnerRightsRequest usernameOfNewOwner(String usernameOfNewOwner) {
        this.usernameOfNewOwner = usernameOfNewOwner;
        return this;
    }

    public AddOwnerRightsRequest competitionId(String competitionId) {
        this.competitionId = competitionId;
        return this;
    }

    public AddOwnerRightsRequest authorizeRequest(AuthorizeRequest authorizeRequest) {
        this.authorizeRequest = authorizeRequest;
        return this;
    }
}
