package io.ariku.owner;

import io.ariku.util.data.Competition;
import io.ariku.util.data.CompetitionStateDatabase;
import io.ariku.verification.AuthorizeRequest;
import io.ariku.verification.UserAuthorizer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ari Aaltonen
 */
public class OwnerService {

    public UserAuthorizer userAuthorizer;
    public CompetitionDatabase competitionDatabase;
    public OwnerDatabase ownerDatabase;
    public UserDatabase userDatabase;
    public CompetitionStateDatabase competitionStateDatabase;

    public void createNewCompetition(NewCompetitionRequest request) {
        if (userAuthorizer.isAuthorized(request.authorizeRequest)) {
            competitionDatabase.createCompetition(request.authorizeRequest.userId, request.competitionName, request.competitionType);
        }

    }

    public List<Competition> findOwnedCompetitions(AuthorizeRequest request) {
        if (userAuthorizer.isAuthorized(request))
            return competitionDatabase.competitionsByOwner(request.userId);
        return new ArrayList<>();
    }

    public void addOwnerRights(AddOwnerRightsRequest request) {
        if (userAuthorizer.isAuthorized(request.authorizeRequest))
            if (request.authorizeRequest.userId.equals(request.userIdExistingOwner))
                competitionDatabase.competitionsByOwner(request.userIdExistingOwner).stream()
                        .filter(ownedCompetition -> ownedCompetition.name.equals(request.competitionName))
                        .findFirst()
                        .ifPresent(ownedAndLookedForCompetition -> userDatabase.findUserByUsername(request.usernameOfNewOwner)
                                .ifPresent(user -> ownerDatabase.addOwner(new Owner(user.id, ownedAndLookedForCompetition.id))));
    }

    public void openAttending(OwnerCompetitionRequest request) {
        if (userAuthorizer.isAuthorized(request.authorizeRequest))
            if (userIsOwner(request.userId, request.competitionId))
                competitionStateDatabase.openAttending(request.competitionId);
    }

    public void closeAttending(OwnerCompetitionRequest request) {
        if (userAuthorizer.isAuthorized(request.authorizeRequest))
            if (userIsOwner(request.userId, request.competitionId))
                competitionStateDatabase.closeAttending(request.competitionId);
    }

    public void openCompetition(OwnerCompetitionRequest request) {
        if (userAuthorizer.isAuthorized(request.authorizeRequest))
            if (userIsOwner(request.userId, request.competitionId))
                competitionStateDatabase.openCompetition(request.competitionId);
    }

    public void closeCompetition(OwnerCompetitionRequest request) {
        if (userAuthorizer.isAuthorized(request.authorizeRequest))
            if (userIsOwner(request.userId, request.competitionId))
                competitionStateDatabase.closeCompetition(request.competitionId);
    }

    private boolean userIsOwner(String userId, String competitionId) {
        return ownerDatabase.ownersByCompetition(competitionId).stream().anyMatch(x -> x.userId.equals(userId));
    }
}
