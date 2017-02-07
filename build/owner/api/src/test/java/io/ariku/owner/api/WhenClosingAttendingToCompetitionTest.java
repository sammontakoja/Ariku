package io.ariku.owner.api;

import io.ariku.verification.api.AuthorizeRequest;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Ari Aaltonen
 */
public class WhenOpeningAttendingToCompetitionTest {

    @Test
    public void non_authorized_owner_cannot_open_attending_to_competition() {

        OwnerService ownerService = new OwnerService();
        ownerService.userAuthorizer = authorizeRequest -> false;

        AttendingDatabase attendingDatabase = mock(AttendingDatabase.class);
        ownerService.attendingDatabase = attendingDatabase;
        ownerService.ownerDatabase = mock(OwnerDatabase.class);

        OpenAttendingRequest request = new OpenAttendingRequest();
        request.authorizeRequest = new AuthorizeRequest();
        request.competitionId = UUID.randomUUID().toString();
        request.userId = UUID.randomUUID().toString();
        request.authorizeRequest.userId = request.userId;

        when(ownerService.ownerDatabase.competitionOwners(request.competitionId))
                .thenReturn(Arrays.asList(request.userId));

        ownerService.openAttending(request);

        verify(ownerService.attendingDatabase, never()).addAttending(eq(request.userId), eq(request.competitionId));
    }

    @Test
    public void authorized_non_owner_user_cannot_open_attending_to_competition() {

        OwnerService ownerService = new OwnerService();
        ownerService.userAuthorizer = authorizeRequest -> true;

        AttendingDatabase attendingDatabase = mock(AttendingDatabase.class);
        ownerService.attendingDatabase = attendingDatabase;
        ownerService.ownerDatabase = mock(OwnerDatabase.class);

        OpenAttendingRequest request = new OpenAttendingRequest();
        request.authorizeRequest = new AuthorizeRequest();
        request.competitionId = UUID.randomUUID().toString();
        request.userId = UUID.randomUUID().toString();
        request.authorizeRequest.userId = request.userId;

        ownerService.openAttending(request);

        verify(ownerService.attendingDatabase, never()).addAttending(eq(request.userId), eq(request.competitionId));
    }
    
    @Test
    public void authorized_owner_can_open_attending_to_competition() {

        OwnerService ownerService = new OwnerService();
        ownerService.userAuthorizer = authorizeRequest -> true;

        AttendingDatabase attendingDatabase = mock(AttendingDatabase.class);
        ownerService.attendingDatabase = attendingDatabase;
        ownerService.ownerDatabase = mock(OwnerDatabase.class);

        OpenAttendingRequest request = new OpenAttendingRequest();
        request.authorizeRequest = new AuthorizeRequest();
        request.competitionId = UUID.randomUUID().toString();
        request.userId = UUID.randomUUID().toString();
        request.authorizeRequest.userId = request.userId;

        when(ownerService.ownerDatabase.competitionOwners(request.competitionId))
                .thenReturn(Arrays.asList(request.userId));

        ownerService.openAttending(request);

        verify(ownerService.attendingDatabase, atLeastOnce()).addAttending(eq(request.userId), eq(request.competitionId));
    }

}