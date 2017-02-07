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
    public AttendingDatabase attendingDatabase;

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
        if (userAuthorizer.isAuthorized(request.authorizeRequest))
            if (request.authorizeRequest.userId.equals(request.userIdExistingOwner))
                if (userIsOwner(request.userIdExistingOwner, request.competitionId))
                    ownerDatabase.addOwner(request.userIdNewOwner, request.competitionId);
    }

    public void openAttending(AttendingRequest request) {
        if (userAuthorizer.isAuthorized(request.authorizeRequest))
            if (userIsOwner(request.userId, request.competitionId))
                attendingDatabase.openAttending(request.userId, request.competitionId);
    }

    public void closeAttending(AttendingRequest request) {
        if (userAuthorizer.isAuthorized(request.authorizeRequest))
            if (userIsOwner(request.userId, request.competitionId))
                attendingDatabase.closeAttending(request.userId, request.competitionId);
    }

    private boolean userIsOwner(String userId, String competitionId) {
        return ownerDatabase.competitionOwners(competitionId).stream().anyMatch(x -> x.equals(userId));
    }
}
