package io.ariku.owner.api;

import io.ariku.verification.api.AuthorizeRequest;

/**
 * @author Ari Aaltonen
 */
public class AddOwnerRightsRequest {

    public String userIdNewOwner;
    public String userIdExistingOwner;
    public String competitionId;
    public AuthorizeRequest authorizeRequest;

}
