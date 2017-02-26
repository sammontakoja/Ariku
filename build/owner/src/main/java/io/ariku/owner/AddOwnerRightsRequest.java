package io.ariku.owner;

import io.ariku.verification.AuthorizeRequest;

/**
 * @author Ari Aaltonen
 */
public class AddOwnerRightsRequest {

    public String userIdNewOwner;
    public String userIdExistingOwner;
    public String competitionId;
    public AuthorizeRequest authorizeRequest;

}
