package io.ariku.user;

import io.ariku.util.data.Competition;
import io.ariku.util.data.CompetitionState;
import io.ariku.util.data.CompetitionStateDatabase;
import io.ariku.verification.AuthorizeRequest;
import io.ariku.verification.UserAuthorizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Ari Aaltonen
 */
public class UserService {

    public UserAuthorizer userAuthorizer;
    public AttendingDatabase attendingDatabase;
    public CompetitionStateDatabase competitionStateDatabase;

    public void attendToCompetition(ParticipateRequest request) {
        if (userIsAuthorizedAndCompetitionIsOpenToAttending(request))
            attendingDatabase.add(new AttendingInfo(request.authorizeRequest.userId, request.competitionId));

    }

    public void cancelAttendingToCompetition(ParticipateRequest request) {
        if (userIsAuthorizedAndCompetitionIsOpenToAttending(request))
            attendingDatabase.remove(new AttendingInfo(request.authorizeRequest.userId, request.competitionId));
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
            return attendingDatabase.competitionsByAttendingUser(request.userId);
        return new ArrayList<>();
    }
}
