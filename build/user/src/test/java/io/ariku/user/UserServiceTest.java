package io.ariku.user;

import com.googlecode.junittoolbox.ParallelRunner;
import io.ariku.util.data.Competition;
import io.ariku.util.data.CompetitionState;
import io.ariku.util.data.CompetitionStateDatabase;
import io.ariku.verification.AuthorizeRequest;
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

        userService.attendingDatabase = mock(AttendingDatabase.class);

        ParticipateRequest request = new ParticipateRequest();
        request.authorizeRequest = new AuthorizeRequest(userId, "");
        request.competitionId = competitionId;

        userService.attendToCompetition(request);

        verify(userService.attendingDatabase, atLeastOnce()).add(any(AttendingInfo.class));
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

        userService.attendingDatabase = mock(AttendingDatabase.class);

        ParticipateRequest request = new ParticipateRequest();
        request.authorizeRequest = new AuthorizeRequest(userId, "");
        request.competitionId = competitionId;

        userService.attendToCompetition(request);

        verify(userService.attendingDatabase, never()).add(any(AttendingInfo.class));
    }

    @Test
    public void non_authorized_user_cannot_participate_to_open_competition() {

        String userId = UUID.randomUUID().toString();
        String competitionId = UUID.randomUUID().toString();

        UserService userService = new UserService();
        userService.userAuthorizer = authorizeRequest -> false;

        userService.attendingDatabase = mock(AttendingDatabase.class);

        ParticipateRequest request = new ParticipateRequest();
        request.authorizeRequest = new AuthorizeRequest(userId, "");
        request.competitionId = competitionId;

        userService.attendToCompetition(request);

        verify(userService.attendingDatabase, never()).add(any(AttendingInfo.class));
    }

    @Test
    public void authorized_user_can_cancel_participating_when_competition_approve_attending() {

        String userId = UUID.randomUUID().toString();
        String competitionId = UUID.randomUUID().toString();

        UserService userService = new UserService();
        userService.userAuthorizer = authorizeRequest -> true;

        userService.attendingDatabase = mock(AttendingDatabase.class);

        CompetitionState competitionState = new CompetitionState();
        competitionState.attending = true;

        userService.competitionStateDatabase = mock(CompetitionStateDatabase.class);
        when(userService.competitionStateDatabase.competitionState(competitionId))
                .thenReturn(Optional.of(competitionState));

        ParticipateRequest request = new ParticipateRequest();
        request.authorizeRequest = new AuthorizeRequest(userId, "");
        request.competitionId = competitionId;

        userService.cancelAttendingToCompetition(request);

        verify(userService.attendingDatabase, atLeastOnce()).remove(any(AttendingInfo.class));
    }

    @Test
    public void authorized_user_cannot_cancel_participating_when_competition_disapprove_attending() {

        String userId = UUID.randomUUID().toString();
        String competitionId = UUID.randomUUID().toString();

        UserService userService = new UserService();
        userService.userAuthorizer = authorizeRequest -> true;

        userService.attendingDatabase = mock(AttendingDatabase.class);

        CompetitionState competitionState = new CompetitionState();
        competitionState.attending = false;

        userService.competitionStateDatabase = mock(CompetitionStateDatabase.class);
        when(userService.competitionStateDatabase.competitionState(competitionId))
                .thenReturn(Optional.of(competitionState));

        ParticipateRequest request = new ParticipateRequest();
        request.authorizeRequest = new AuthorizeRequest(userId, "");
        request.competitionId = competitionId;

        userService.cancelAttendingToCompetition(request);

        verify(userService.attendingDatabase, never()).remove(any(AttendingInfo.class));
    }

    @Test
    public void non_authorized_user_cannot_cancel_participating_to_competition() {

        String userId = UUID.randomUUID().toString();
        String competitionId = UUID.randomUUID().toString();

        UserService userService = new UserService();
        userService.userAuthorizer = authorizeRequest -> false;

        userService.attendingDatabase = mock(AttendingDatabase.class);

        ParticipateRequest request = new ParticipateRequest();
        request.authorizeRequest = new AuthorizeRequest(userId, "");
        request.competitionId = competitionId;

        userService.cancelAttendingToCompetition(request);

        verify(userService.attendingDatabase, never()).remove(any(AttendingInfo.class));
    }

    @Test
    public void authorized_user_can_list_own_competitions() {

        String userId = UUID.randomUUID().toString();

        UserService userService = new UserService();
        userService.userAuthorizer = authorizeRequest -> true;

        userService.attendingDatabase = mock(AttendingDatabase.class);

        userService.competitions(new AuthorizeRequest(userId, ""));

        verify(userService.attendingDatabase, atLeastOnce()).competitionsByAttendingUser(eq(userId));
    }

    @Test
    public void non_authorized_user_cannot_list_competitions() {
        String userId = UUID.randomUUID().toString();

        UserService userService = new UserService();
        userService.userAuthorizer = authorizeRequest -> false;

        userService.attendingDatabase = mock(AttendingDatabase.class);

        List<Competition> competitions = userService.competitions(new AuthorizeRequest(userId, ""));
        assertThat(competitions.size(), is(0));

        verify(userService.attendingDatabase, never()).competitionsByAttendingUser(eq(userId));
    }

}