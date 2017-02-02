package io.ariku.owner.api;

import io.ariku.verification.api.AuthorizeRequest;
import io.ariku.verification.api.UserAuthorizer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ari Aaltonen
 */
public class OwnerService {

    public UserAuthorizer userAuthorizer;
    public CompetitionDatabase competitionDatabase;
    public OwnerDatabase ownerDatabase;

    public void createNewCompetition(NewCompetitionRequest request) {
        if (userAuthorizer.isAuthorized(request.authorizeRequest))
            competitionDatabase.createCompetition(request.authorizeRequest.userId, request.competitionName, request.competitionType);
    }

    public List<Competition> findOwnedCompetitions(AuthorizeRequest request) {
        if (userAuthorizer.isAuthorized(request))
            return competitionDatabase.ownersCompetitions(request.userId);
        return new ArrayList<>();
    }

    public void addOwnerRights(AddOwnerRightsRequest request) {
        if (userAuthorizer.isAuthorized(request.authorizeRequest)) {

            if (request.authorizeRequest.userId.equals(request.userIdExistingOwner)) {

                boolean userIsOwner = ownerDatabase.competitionOwners(request.competitionId).stream()
                        .anyMatch(x -> x.equals(request.userIdExistingOwner));

                if (userIsOwner)
                    ownerDatabase.addOwner(request.userIdNewOwner, request.competitionId);
            }
        }

    }

}
