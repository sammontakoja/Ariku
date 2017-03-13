package io.ariku.rest;

import io.ariku.owner.AddOwnerRightsRequest;
import io.ariku.owner.NewCompetitionRequest;
import io.ariku.owner.OwnerService;
import io.ariku.util.data.Competition;
import io.ariku.verification.AuthorizeRequest;

import java.util.List;
import java.util.Optional;

/**
 * @author Ari Aaltonen
 */
public class Owner {

    public OwnerService ownerService;
    
    public String newCompetition(String competitionName, String competitionType, String username, String securityToken) {

        NewCompetitionRequest request = new NewCompetitionRequest();
        request.authorizeRequest = new AuthorizeRequest(username, securityToken);
        request.competitionName = competitionName;
        request.competitionType = competitionType;

        Optional<Competition> newCompetition = ownerService.createNewCompetition(request);
        
        if (newCompetition.isPresent())
            return "OK";

        return "FAIL";
    }

    public String addOwnerToCompetition(String competitionId, String usernameOfNewOwner, String usernameOfExistingOwner, String securityToken) {
        AddOwnerRightsRequest request = new AddOwnerRightsRequest();
        request.usernameOfNewOwner = usernameOfNewOwner;
        request.competitionId = competitionId;
        request.authorizeRequest = new AuthorizeRequest(usernameOfExistingOwner, securityToken);
        ownerService.addOwnerRights(request);
        return "OK";
    }

    public List<Competition> listOwnedCompetitions(String username, String securityToken) {
        return ownerService.findOwnedCompetitions(new AuthorizeRequest(username, securityToken));
    }
    
}
