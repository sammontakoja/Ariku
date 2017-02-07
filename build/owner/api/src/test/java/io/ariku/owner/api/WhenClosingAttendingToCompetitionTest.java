package io.ariku.owner.api;

import io.ariku.verification.api.AuthorizeRequest;
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

        AttendingDatabase attendingDatabase = mock(AttendingDatabase.class);
        ownerService.attendingDatabase = attendingDatabase;
        ownerService.ownerDatabase = mock(OwnerDatabase.class);

        AttendingRequest request = new AttendingRequest();
        request.authorizeRequest = new AuthorizeRequest();
        request.competitionId = UUID.randomUUID().toString();
        request.userId = UUID.randomUUID().toString();
        request.authorizeRequest.userId = request.userId;

        when(ownerService.ownerDatabase.competitionOwners(request.competitionId))
                .thenReturn(Arrays.asList(request.userId));

        ownerService.closeAttending(request);

        verify(ownerService.attendingDatabase, never()).closeAttending(eq(request.userId), eq(request.competitionId));
    }

    @Test
    public void authorized_non_owner_user_cannot_open_attending_to_competition() {

        OwnerService ownerService = new OwnerService();
        ownerService.userAuthorizer = authorizeRequest -> true;

        AttendingDatabase attendingDatabase = mock(AttendingDatabase.class);
        ownerService.attendingDatabase = attendingDatabase;
        ownerService.ownerDatabase = mock(OwnerDatabase.class);

        AttendingRequest request = new AttendingRequest();
        request.authorizeRequest = new AuthorizeRequest();
        request.competitionId = UUID.randomUUID().toString();
        request.userId = UUID.randomUUID().toString();
        request.authorizeRequest.userId = request.userId;

        ownerService.closeAttending(request);

        verify(ownerService.attendingDatabase, never()).closeAttending(eq(request.userId), eq(request.competitionId));
    }
    
    @Test
    public void authorized_owner_can_open_attending_to_competition() {

        OwnerService ownerService = new OwnerService();
        ownerService.userAuthorizer = authorizeRequest -> true;

        AttendingDatabase attendingDatabase = mock(AttendingDatabase.class);
        ownerService.attendingDatabase = attendingDatabase;
        ownerService.ownerDatabase = mock(OwnerDatabase.class);

        AttendingRequest request = new AttendingRequest();
        request.authorizeRequest = new AuthorizeRequest();
        request.competitionId = UUID.randomUUID().toString();
        request.userId = UUID.randomUUID().toString();
        request.authorizeRequest.userId = request.userId;

        when(ownerService.ownerDatabase.competitionOwners(request.competitionId))
                .thenReturn(Arrays.asList(request.userId));

        ownerService.closeAttending(request);

        verify(ownerService.attendingDatabase, atLeastOnce()).closeAttending(eq(request.userId), eq(request.competitionId));
    }

}