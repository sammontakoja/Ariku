package io.ariku.owner;

import io.ariku.util.data.CompetitionStateDatabase;
import io.ariku.verification.AuthorizeRequest;
import org.junit.Test;

import java.util.Arrays;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Ari Aaltonen
 */
public class WhenClosingAttendingToCompetitionTest {

    @Test
    public void non_authorized_owner_cannot_close_attending_to_competition() {

        OwnerService ownerService = new OwnerService();
        ownerService.userAuthorizer = authorizeRequest -> false;

        CompetitionStateDatabase competitionStateDatabase = mock(CompetitionStateDatabase.class);
        ownerService.competitionStateDatabase = competitionStateDatabase;
        ownerService.ownerDatabase = mock(OwnerDatabase.class);

        OwnerCompetitionRequest request = new OwnerCompetitionRequest();
        request.authorizeRequest = new AuthorizeRequest();
        request.competitionId = UUID.randomUUID().toString();
        request.userId = UUID.randomUUID().toString();
        request.authorizeRequest.username = request.userId;

        when(ownerService.ownerDatabase.ownersByCompetition(request.competitionId))
                .thenReturn(Arrays.asList(new Owner().userId(request.userId)));

        ownerService.closeAttending(request);

        verify(ownerService.competitionStateDatabase, never()).closeAttending(eq(request.competitionId));
    }

    @Test
    public void authorized_non_owner_user_cannot_close_attending_to_competition() {

        OwnerService ownerService = new OwnerService();
        ownerService.userAuthorizer = authorizeRequest -> true;

        CompetitionStateDatabase competitionStateDatabase = mock(CompetitionStateDatabase.class);
        ownerService.competitionStateDatabase = competitionStateDatabase;
        ownerService.ownerDatabase = mock(OwnerDatabase.class);

        OwnerCompetitionRequest request = new OwnerCompetitionRequest();
        request.authorizeRequest = new AuthorizeRequest();
        request.competitionId = UUID.randomUUID().toString();
        request.userId = UUID.randomUUID().toString();
        request.authorizeRequest.username = request.userId;

        ownerService.closeAttending(request);

        verify(ownerService.competitionStateDatabase, never()).closeAttending(eq(request.competitionId));
    }
    
    @Test
    public void authorized_owner_can_close_attending_to_competition() {

        OwnerService ownerService = new OwnerService();
        ownerService.userAuthorizer = authorizeRequest -> true;

        CompetitionStateDatabase competitionStateDatabase = mock(CompetitionStateDatabase.class);
        ownerService.competitionStateDatabase = competitionStateDatabase;
        ownerService.ownerDatabase = mock(OwnerDatabase.class);

        OwnerCompetitionRequest request = new OwnerCompetitionRequest();
        request.authorizeRequest = new AuthorizeRequest();
        request.competitionId = UUID.randomUUID().toString();
        request.userId = UUID.randomUUID().toString();
        request.authorizeRequest.username = request.userId;

        when(ownerService.ownerDatabase.ownersByCompetition(request.competitionId))
                .thenReturn(Arrays.asList(new Owner().userId(request.userId)));

        ownerService.closeAttending(request);

        verify(ownerService.competitionStateDatabase, atLeastOnce()).closeAttending(eq(request.competitionId));
    }

}