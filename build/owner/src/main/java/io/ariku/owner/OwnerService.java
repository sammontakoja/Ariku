package io.ariku.owner;

import io.ariku.util.data.Competition;
import io.ariku.util.data.CompetitionStateDatabase;
import io.ariku.verification.AuthorizeRequest;
import io.ariku.verification.UserAuthorizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Ari Aaltonen
 */
public class OwnerService {

    public UserAuthorizer userAuthorizer;
    public CompetitionDatabase competitionDatabase;
    public OwnerDatabase ownerDatabase;
    public UserDatabase userDatabase;
    public CompetitionStateDatabase competitionStateDatabase;

    public Optional<Competition> createNewCompetition(NewCompetitionRequest request) {
        String authorizedUserId = userAuthorizer.authorizedUserId(request.authorizeRequest);
        if (!authorizedUserId.isEmpty()) {
            Competition competition = competitionDatabase.createCompetition(authorizedUserId, request.competitionName, request.competitionType);
            return Optional.of(competition);
        }
        return Optional.empty();
    }

    public List<Competition> findOwnedCompetitions(AuthorizeRequest request) {
        String authorizedUserId = userAuthorizer.authorizedUserId(request);
        if (!authorizedUserId.isEmpty())
            return competitionDatabase.competitionsByOwner(authorizedUserId);
        return new ArrayList<>();
    }

    public void addOwnerRights(AddOwnerRightsRequest request) {
        if (!userAuthorizer.authorizedUserId(request.authorizeRequest).isEmpty())
            if (request.authorizeRequest.username.equals(request.userIdExistingOwner))
                competitionDatabase.competitionsByOwner(request.userIdExistingOwner).stream()
                        .filter(ownedCompetition -> ownedCompetition.name.equals(request.competitionName))
                        .findFirst()
                        .ifPresent(ownedAndLookedForCompetition -> userDatabase.findUserByUsername(request.usernameOfNewOwner)
                                .ifPresent(user -> ownerDatabase.addOwner(new Owner(user.id, ownedAndLookedForCompetition.id))));
    }

    public void openAttending(OwnerCompetitionRequest request) {
        if (!userAuthorizer.authorizedUserId(request.authorizeRequest).isEmpty())
            if (userIsOwner(request.userId, request.competitionId))
                competitionStateDatabase.openAttending(request.competitionId);
    }

    public void closeAttending(OwnerCompetitionRequest request) {
        if (!userAuthorizer.authorizedUserId(request.authorizeRequest).isEmpty())
            if (userIsOwner(request.userId, request.competitionId))
                competitionStateDatabase.closeAttending(request.competitionId);
    }

    public void openCompetition(OwnerCompetitionRequest request) {
        if (!userAuthorizer.authorizedUserId(request.authorizeRequest).isEmpty())
            if (userIsOwner(request.userId, request.competitionId))
                competitionStateDatabase.openCompetition(request.competitionId);
    }

    public void closeCompetition(OwnerCompetitionRequest request) {
        if (!userAuthorizer.authorizedUserId(request.authorizeRequest).isEmpty())
            if (userIsOwner(request.userId, request.competitionId))
                competitionStateDatabase.closeCompetition(request.competitionId);
    }

    private boolean userIsOwner(String userId, String competitionId) {
        return ownerDatabase.ownersByCompetition(competitionId).stream().anyMatch(x -> x.userId.equals(userId));
    }
}
