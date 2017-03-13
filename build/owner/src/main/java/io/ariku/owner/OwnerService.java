package io.ariku.owner;

import io.ariku.user.UserService;
import io.ariku.util.data.Competition;
import io.ariku.util.data.CompetitionStateDatabase;
import io.ariku.verification.AuthorizeRequest;
import io.ariku.verification.UserVerificationService;

import java.util.List;
import java.util.Optional;

/**
 * @author Ari Aaltonen
 */
public class OwnerService {

    public CompetitionDatabase competitionDatabase;
    public OwnerDatabase ownerDatabase;
    public UserService userService;
    public UserVerificationService userVerificationService;
    public CompetitionStateDatabase competitionStateDatabase;

    public Optional<Competition> createNewCompetition(NewCompetitionRequest request) {
        return userVerificationService.authorizedUser(request.authorizeRequest)
                .flatMap(authorizedUserId -> {
                    if (bothNameAndTypeValid(request)) {
                        Competition competition = competitionDatabase.createCompetition(authorizedUserId, request.competitionName, request.competitionType);
                        ownerDatabase.addOwner(new Owner().competitionId(competition.id).userId(authorizedUserId));
                        return Optional.of(competition);
                    }
                    return Optional.empty();
                });
    }

    private boolean bothNameAndTypeValid(NewCompetitionRequest request) {
        return request.competitionType.length() > 1 && request.competitionName.length() > 1;
    }

    public List<Competition> findOwnedCompetitions(AuthorizeRequest request) {
        return userVerificationService.authorizedUser(request)
                .flatMap(authorizedUserId -> Optional.of(competitionDatabase.competitionsByOwner(authorizedUserId))).get();
    }

    public void addOwnerRights(AddOwnerRightsRequest request) {
        userVerificationService.authorizedUser(request.authorizeRequest)
                .ifPresent(authorizedUserId -> competitionDatabase.competitionsByOwner(authorizedUserId).stream()
                        .filter(ownedCompetition -> ownedCompetition.id.equals(request.competitionId))
                        .findFirst()
                        .ifPresent(ownedAndLookedForCompetition -> userService.findUserByUsername(request.usernameOfNewOwner)
                                .ifPresent(user -> ownerDatabase.addOwner(new Owner(user.id, request.competitionId)))));
    }

    public void openAttending(OwnerCompetitionRequest request) {
        userVerificationService.authorizedUser(request.authorizeRequest).ifPresent(plaa -> {
            if (userIsOwner(request.userId, request.competitionId))
                competitionStateDatabase.openAttending(request.competitionId);
        });
    }

    public void closeAttending(OwnerCompetitionRequest request) {
//        if (!userVerificationService.authorizedUser(request.authorizeRequest).isEmpty())
//            if (userIsOwner(request.userId, request.competitionId))
//                competitionStateDatabase.closeAttending(request.competitionId);
    }

    public void openCompetition(OwnerCompetitionRequest request) {
//        if (!userVerificationService.authorizedUser(request.authorizeRequest).isEmpty())
//            if (userIsOwner(request.userId, request.competitionId))
//                competitionStateDatabase.openCompetition(request.competitionId);
    }

    public void closeCompetition(OwnerCompetitionRequest request) {
//        if (!userVerificationService.authorizedUser(request.authorizeRequest).isEmpty())
//            if (userIsOwner(request.userId, request.competitionId))
//                competitionStateDatabase.closeCompetition(request.competitionId);
    }

    private boolean userIsOwner(String userId, String competitionId) {
        return ownerDatabase.ownersByCompetition(competitionId).stream().anyMatch(x -> x.userId.equals(userId));
    }
}
