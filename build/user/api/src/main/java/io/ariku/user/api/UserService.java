package io.ariku.user.api;

import io.ariku.util.data.Competition;
import io.ariku.util.data.CompetitionState;
import io.ariku.util.data.CompetitionStateDatabase;
import io.ariku.verification.api.AuthorizeRequest;
import io.ariku.verification.api.UserAuthorizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Ari Aaltonen
 */
public class UserService {

    public UserAuthorizer userAuthorizer;
    public PartiticipateDatabase partiticipateDatabase;
    public CompetitionStateDatabase competitionStateDatabase;

    public void participateToCompetition(ParticipateRequest request) {
        if (userIsAuthorizedAndCompetitionIsOpenToAttending(request))
            partiticipateDatabase.addParticipation(new Partiticipation(request.authorizeRequest.userId, request.competitionId));

    }

    public void cancelParticipateToCompetition(ParticipateRequest request) {
        if (userIsAuthorizedAndCompetitionIsOpenToAttending(request))
            partiticipateDatabase.removeParticipation(new Partiticipation(request.authorizeRequest.userId, request.competitionId));
    }

    private boolean userIsAuthorizedAndCompetitionIsOpenToAttending(ParticipateRequest request) {
        if (userAuthorizer.isAuthorized(request.authorizeRequest)) {
            Optional<CompetitionState> competitionState = competitionStateDatabase.competitionState(request.competitionId);
            return (competitionState.isPresent() && competitionState.get().attending);
        }
        return false;
    }

    public List<Competition> competitions(AuthorizeRequest request) {
        if (userAuthorizer.isAuthorized(request))
            return partiticipateDatabase.competitions(request.userId);
        return new ArrayList<>();
    }
}
