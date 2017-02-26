package io.ariku.owner;

import com.googlecode.junittoolbox.ParallelRunner;
import io.ariku.util.data.CompetitionStateDatabase;
import io.ariku.verification.AuthorizeRequest;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Ari Aaltonen
 */
@RunWith(ParallelRunner.class)
public class WhenClosingCompetitionTest {

    @Test
    public void non_authorized_owner_cannot_close_competition() {

        OwnerService ownerService = new OwnerService();
        ownerService.userAuthorizer = authorizeRequest -> false;

        CompetitionStateDatabase competitionStateDatabase = mock(CompetitionStateDatabase.class);
        ownerService.competitionStateDatabase = competitionStateDatabase;
        ownerService.ownerDatabase = mock(OwnerDatabase.class);

        OwnerCompetitionRequest request = new OwnerCompetitionRequest();
        request.authorizeRequest = new AuthorizeRequest();
        request.competitionId = UUID.randomUUID().toString();
        request.userId = UUID.randomUUID().toString();
        request.authorizeRequest.userId = request.userId;

        when(ownerService.ownerDatabase.ownersByCompetition(request.competitionId))
                .thenReturn(Arrays.asList(request.userId));

        ownerService.closeCompetition(request);

        verify(ownerService.competitionStateDatabase, never()).closeCompetition(eq(request.competitionId));
    }

    @Test
    public void authorized_non_owner_user_cannot_open__competition() {

        OwnerService ownerService = new OwnerService();
        ownerService.userAuthorizer = authorizeRequest -> true;

        CompetitionStateDatabase competitionStateDatabase = mock(CompetitionStateDatabase.class);
        ownerService.competitionStateDatabase = competitionStateDatabase;
        ownerService.ownerDatabase = mock(OwnerDatabase.class);

        OwnerCompetitionRequest request = new OwnerCompetitionRequest();
        request.authorizeRequest = new AuthorizeRequest();
        request.competitionId = UUID.randomUUID().toString();
        request.userId = UUID.randomUUID().toString();
        request.authorizeRequest.userId = request.userId;

        ownerService.closeCompetition(request);

        verify(ownerService.competitionStateDatabase, never()).closeCompetition(eq(request.competitionId));
    }

    @Test
    public void authorized_owner_can_open_competition() {

        OwnerService ownerService = new OwnerService();
        ownerService.userAuthorizer = authorizeRequest -> true;

        CompetitionStateDatabase competitionStateDatabase = mock(CompetitionStateDatabase.class);
        ownerService.competitionStateDatabase = competitionStateDatabase;
        ownerService.ownerDatabase = mock(OwnerDatabase.class);

        OwnerCompetitionRequest request = new OwnerCompetitionRequest();
        request.authorizeRequest = new AuthorizeRequest();
        request.competitionId = UUID.randomUUID().toString();
        request.userId = UUID.randomUUID().toString();
        request.authorizeRequest.userId = request.userId;

        when(ownerService.ownerDatabase.ownersByCompetition(request.competitionId))
                .thenReturn(Arrays.asList(request.userId));

        ownerService.closeCompetition(request);

        verify(ownerService.competitionStateDatabase, atLeastOnce()).closeCompetition(eq(request.competitionId));
    }

}