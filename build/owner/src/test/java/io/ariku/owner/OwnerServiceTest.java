package io.ariku.owner;

import com.googlecode.junittoolbox.ParallelRunner;
import io.ariku.util.data.Competition;
import io.ariku.util.data.User;
import io.ariku.verification.AuthorizeRequest;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Ari Aaltonen
 */
@RunWith(ParallelRunner.class)
public class OwnerServiceTest {

    @Test
    public void authorized_user_can_create_new_competition() {

        OwnerService ownerService = new OwnerService();
        ownerService.userAuthorizer = authorizeRequest -> true;

        ownerService.competitionDatabase = mock(CompetitionDatabase.class);

        NewCompetitionRequest newCompetitionRequest = new NewCompetitionRequest();
        newCompetitionRequest.competitionName = "NastaGP";
        newCompetitionRequest.competitionType = "Skeet";
        newCompetitionRequest.authorizeRequest = new AuthorizeRequest("userId", "token");

        ownerService.createNewCompetition(newCompetitionRequest);

        verify(ownerService.competitionDatabase).createCompetition(anyString(),anyString(),anyString());
    }

    @Test
    public void non_authorized_user_cannot_create_new_competition() {

        OwnerService ownerService = new OwnerService();
        ownerService.userAuthorizer = authorizeRequest -> false;

        ownerService.competitionDatabase = mock(CompetitionDatabase.class);

        NewCompetitionRequest newCompetitionRequest = new NewCompetitionRequest();
        newCompetitionRequest.competitionName = "NastaGP";
        newCompetitionRequest.competitionType = "Skeet";
        newCompetitionRequest.authorizeRequest = new AuthorizeRequest();

        ownerService.createNewCompetition(newCompetitionRequest);

        verify(ownerService.competitionDatabase, never()).createCompetition(anyString(),anyString(),anyString());
    }

    @Test
    public void authorized_user_can_list_own_competitions() {

        OwnerService ownerService = new OwnerService();
        ownerService.userAuthorizer = authorizeRequest -> true;

        ownerService.competitionDatabase = mock(CompetitionDatabase.class);

        when(ownerService.competitionDatabase.competitionsByOwner("userId"))
                .thenReturn(Arrays.asList(new Competition("1"), new Competition("2")));

        List<Competition> ownedCompetitions = ownerService.findOwnedCompetitions(new AuthorizeRequest("userId", ""));

        assertThat(ownedCompetitions.size(), is(2));
    }

    @Test
    public void non_authorized_user_cannot_list_own_competitions() {

        OwnerService ownerService = new OwnerService();
        ownerService.userAuthorizer = authorizeRequest -> false;

        ownerService.competitionDatabase = mock(CompetitionDatabase.class);

        when(ownerService.competitionDatabase.competitionsByOwner("userId"))
                .thenReturn(Arrays.asList(new Competition("1"), new Competition("2")));

        List<Competition> ownedCompetitions = ownerService.findOwnedCompetitions(new AuthorizeRequest("userId", ""));

        assertThat(ownedCompetitions.size(), is(0));
    }

    @Test
    public void authorized_owner_can_add_another_user_to_be_competition_owner() {

        OwnerService ownerService = new OwnerService();
        ownerService.userAuthorizer = authorizeRequest -> true;

        ownerService.ownerDatabase = mock(OwnerDatabase.class);
        ownerService.competitionDatabase = mock(CompetitionDatabase.class);
        ownerService.userDatabase = mock(UserDatabase.class);

        Competition competition = new Competition();
        competition.id = UUID.randomUUID().toString();
        competition.name = UUID.randomUUID().toString();

        User user = new User();
        user.username = UUID.randomUUID().toString();
        user.id = UUID.randomUUID().toString();

        AddOwnerRightsRequest request = new AddOwnerRightsRequest();
        request.authorizeRequest = new AuthorizeRequest();
        request.competitionName = competition.name;
        request.userIdExistingOwner = UUID.randomUUID().toString();
        request.authorizeRequest.userId = request.userIdExistingOwner;
        request.usernameOfNewOwner = user.username;

        when(ownerService.competitionDatabase.competitionsByOwner(request.userIdExistingOwner))
                .thenReturn(Arrays.asList(competition));

        when(ownerService.ownerDatabase.ownersByCompetition(request.competitionName))
                .thenReturn(Arrays.asList(request.userIdExistingOwner));

        when(ownerService.userDatabase.findUserByUsername(request.usernameOfNewOwner))
                .thenReturn(Optional.of(user));

        ownerService.addOwnerRights(request);

        verify(ownerService.ownerDatabase, atLeastOnce()).addOwner(eq(user.id), eq(competition.id));
    }

    @Test
    public void when_caller_is_different_user_than_owner_then_adding_new_owner_fails() {

        OwnerService ownerService = new OwnerService();
        ownerService.userAuthorizer = authorizeRequest -> true;

        ownerService.ownerDatabase = mock(OwnerDatabase.class);

        AddOwnerRightsRequest request = new AddOwnerRightsRequest();
        request.authorizeRequest = new AuthorizeRequest();
        request.authorizeRequest.userId = UUID.randomUUID().toString();
        request.competitionName = UUID.randomUUID().toString();
        request.userIdExistingOwner = UUID.randomUUID().toString();
        request.usernameOfNewOwner = UUID.randomUUID().toString();

        when(ownerService.ownerDatabase.ownersByCompetition(request.competitionName))
                .thenReturn(Arrays.asList(request.userIdExistingOwner));

        ownerService.addOwnerRights(request);

        verify(ownerService.ownerDatabase, never()).addOwner(any(), any());
    }

    @Test
    public void non_authorized_owner_cannot_add_another_user_to_be_competition_owner() {
        OwnerService ownerService = new OwnerService();
        ownerService.userAuthorizer = authorizeRequest -> false;

        ownerService.ownerDatabase = mock(OwnerDatabase.class);

        AddOwnerRightsRequest request = new AddOwnerRightsRequest();
        request.authorizeRequest = new AuthorizeRequest();
        request.competitionName = UUID.randomUUID().toString();
        request.userIdExistingOwner = UUID.randomUUID().toString();
        request.usernameOfNewOwner = UUID.randomUUID().toString();

        ownerService.addOwnerRights(request);

        verify(ownerService.ownerDatabase, never()).addOwner(any(), any());
    }

    @Test
    public void non_owner_user_cannot_add_another_user_to_be_competition_owner() {

        OwnerService ownerService = new OwnerService();
        ownerService.userAuthorizer = authorizeRequest -> true;

        ownerService.ownerDatabase = mock(OwnerDatabase.class);
        ownerService.competitionDatabase = mock(CompetitionDatabase.class);

        AddOwnerRightsRequest request = new AddOwnerRightsRequest();
        request.authorizeRequest = new AuthorizeRequest();
        request.competitionName = UUID.randomUUID().toString();
        request.userIdExistingOwner = UUID.randomUUID().toString();
        request.authorizeRequest.userId = request.userIdExistingOwner;
        request.usernameOfNewOwner = UUID.randomUUID().toString();

        when(ownerService.competitionDatabase.competitionsByOwner(request.userIdExistingOwner))
                .thenReturn(Arrays.asList());

        ownerService.addOwnerRights(request);

        verify(ownerService.ownerDatabase, never()).addOwner(any(), any());
    }

}