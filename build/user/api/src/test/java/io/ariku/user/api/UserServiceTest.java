package io.ariku.user.api;

import com.googlecode.junittoolbox.ParallelRunner;
import io.ariku.util.data.Competition;
import io.ariku.util.data.CompetitionState;
import io.ariku.util.data.CompetitionStateDatabase;
import io.ariku.verification.api.AuthorizeRequest;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Ari Aaltonen
 */
@RunWith(ParallelRunner.class)
public class UserServiceTest {

    @Test
    public void authorized_user_can_attend_to_competition_which_approve_attending() {

        String userId = UUID.randomUUID().toString();
        String competitionId = UUID.randomUUID().toString();

        UserService userService = new UserService();

        userService.userAuthorizer = authorizeRequest -> true;

        CompetitionState competitionState = new CompetitionState();
        competitionState.attending = true;

        userService.competitionStateDatabase = mock(CompetitionStateDatabase.class);
        when(userService.competitionStateDatabase.competitionState(competitionId))
                .thenReturn(Optional.of(competitionState));

        userService.partiticipateDatabase = mock(PartiticipateDatabase.class);

        ParticipateRequest request = new ParticipateRequest();
        request.authorizeRequest = new AuthorizeRequest(userId, "");
        request.competitionId = competitionId;

        userService.participateToCompetition(request);

        verify(userService.partiticipateDatabase, atLeastOnce()).addParticipation(any(Partiticipation.class));
    }

    @Test
    public void authorized_user_cannot_attend_to_competition_which_disapprove_attending() {

        String userId = UUID.randomUUID().toString();
        String competitionId = UUID.randomUUID().toString();

        UserService userService = new UserService();

        userService.userAuthorizer = authorizeRequest -> true;

        CompetitionState competitionState = new CompetitionState();
        competitionState.attending = false;

        userService.competitionStateDatabase = mock(CompetitionStateDatabase.class);
        when(userService.competitionStateDatabase.competitionState(competitionId))
                .thenReturn(Optional.of(competitionState));

        userService.partiticipateDatabase = mock(PartiticipateDatabase.class);

        ParticipateRequest request = new ParticipateRequest();
        request.authorizeRequest = new AuthorizeRequest(userId, "");
        request.competitionId = competitionId;

        userService.participateToCompetition(request);

        verify(userService.partiticipateDatabase, never()).addParticipation(any(Partiticipation.class));
    }

    @Test
    public void non_authorized_user_cannot_participate_to_open_competition() {

        String userId = UUID.randomUUID().toString();
        String competitionId = UUID.randomUUID().toString();

        UserService userService = new UserService();
        userService.userAuthorizer = authorizeRequest -> false;

        userService.partiticipateDatabase = mock(PartiticipateDatabase.class);

        ParticipateRequest request = new ParticipateRequest();
        request.authorizeRequest = new AuthorizeRequest(userId, "");
        request.competitionId = competitionId;

        userService.participateToCompetition(request);

        verify(userService.partiticipateDatabase, never()).addParticipation(any(Partiticipation.class));
    }

    @Test
    public void authorized_user_can_cancel_participating_when_competition_approve_attending() {

        String userId = UUID.randomUUID().toString();
        String competitionId = UUID.randomUUID().toString();

        UserService userService = new UserService();
        userService.userAuthorizer = authorizeRequest -> true;

        userService.partiticipateDatabase = mock(PartiticipateDatabase.class);

        CompetitionState competitionState = new CompetitionState();
        competitionState.attending = true;

        userService.competitionStateDatabase = mock(CompetitionStateDatabase.class);
        when(userService.competitionStateDatabase.competitionState(competitionId))
                .thenReturn(Optional.of(competitionState));

        ParticipateRequest request = new ParticipateRequest();
        request.authorizeRequest = new AuthorizeRequest(userId, "");
        request.competitionId = competitionId;

        userService.cancelParticipateToCompetition(request);

        verify(userService.partiticipateDatabase, atLeastOnce()).removeParticipation(any(Partiticipation.class));
    }

    @Test
    public void authorized_user_cannot_cancel_participating_when_competition_disapprove_attending() {

        String userId = UUID.randomUUID().toString();
        String competitionId = UUID.randomUUID().toString();

        UserService userService = new UserService();
        userService.userAuthorizer = authorizeRequest -> true;

        userService.partiticipateDatabase = mock(PartiticipateDatabase.class);

        CompetitionState competitionState = new CompetitionState();
        competitionState.attending = false;

        userService.competitionStateDatabase = mock(CompetitionStateDatabase.class);
        when(userService.competitionStateDatabase.competitionState(competitionId))
                .thenReturn(Optional.of(competitionState));

        ParticipateRequest request = new ParticipateRequest();
        request.authorizeRequest = new AuthorizeRequest(userId, "");
        request.competitionId = competitionId;

        userService.cancelParticipateToCompetition(request);

        verify(userService.partiticipateDatabase, never()).removeParticipation(any(Partiticipation.class));
    }

    @Test
    public void non_authorized_user_cannot_cancel_participating_to_competition() {

        String userId = UUID.randomUUID().toString();
        String competitionId = UUID.randomUUID().toString();

        UserService userService = new UserService();
        userService.userAuthorizer = authorizeRequest -> false;

        userService.partiticipateDatabase = mock(PartiticipateDatabase.class);

        ParticipateRequest request = new ParticipateRequest();
        request.authorizeRequest = new AuthorizeRequest(userId, "");
        request.competitionId = competitionId;

        userService.cancelParticipateToCompetition(request);

        verify(userService.partiticipateDatabase, never()).removeParticipation(any(Partiticipation.class));
    }

    @Test
    public void authorized_user_can_list_own_competitions() {

        String userId = UUID.randomUUID().toString();

        UserService userService = new UserService();
        userService.userAuthorizer = authorizeRequest -> true;

        userService.partiticipateDatabase = mock(PartiticipateDatabase.class);

        userService.competitions(new AuthorizeRequest(userId, ""));

        verify(userService.partiticipateDatabase, atLeastOnce()).competitions(eq(userId));
    }

    @Test
    public void non_authorized_user_cannot_list_competitions() {
        String userId = UUID.randomUUID().toString();

        UserService userService = new UserService();
        userService.userAuthorizer = authorizeRequest -> false;

        userService.partiticipateDatabase = mock(PartiticipateDatabase.class);

        List<Competition> competitions = userService.competitions(new AuthorizeRequest(userId, ""));
        assertThat(competitions.size(), is(0));

        verify(userService.partiticipateDatabase, never()).competitions(eq(userId));
    }

}