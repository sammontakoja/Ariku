package io.ariku.owner;

import com.googlecode.junittoolbox.ParallelRunner;
import io.ariku.util.data.Competition;
import io.ariku.util.data.User;
import io.ariku.verification.AuthorizeRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @author Ari Aaltonen
 */
@RunWith(ParallelRunner.class)
public class OwnerServiceTest {

    @Test
    public void authorized_user_can_create_new_competition() {

        String userId = UUID.randomUUID().toString();

        OwnerService ownerService = new OwnerService();
        ownerService.userAuthorizer = authorizeRequest -> userId;

        ownerService.competitionDatabase = mock(CompetitionDatabase.class);

        NewCompetitionRequest newCompetitionRequest = new NewCompetitionRequest();
        newCompetitionRequest.competitionName = "NastaGP";
        newCompetitionRequest.competitionType = "Skeet";
        newCompetitionRequest.authorizeRequest = new AuthorizeRequest("username", "token");

        String competitionId = UUID.randomUUID().toString();

        when(ownerService.competitionDatabase.createCompetition(anyString(),anyString(),anyString()))
                .thenReturn(new Competition(competitionId));

        Optional<Competition> newCompetition = ownerService.createNewCompetition(newCompetitionRequest);

        assertThat(newCompetition.isPresent(), is(true));
    }

    @Test
    public void non_authorized_user_cannot_create_new_competition() {

        OwnerService ownerService = new OwnerService();
        ownerService.userAuthorizer = authorizeRequest -> "";

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
        ownerService.userAuthorizer = authorizeRequest -> "userId";

        ownerService.competitionDatabase = mock(CompetitionDatabase.class);

        when(ownerService.competitionDatabase.competitionsByOwner("userId"))
                .thenReturn(Arrays.asList(new Competition("1"), new Competition("2")));

        List<Competition> ownedCompetitions = ownerService.findOwnedCompetitions(new AuthorizeRequest("username", ""));

        assertThat(ownedCompetitions.size(), is(2));
    }

    @Test
    public void non_authorized_user_cannot_list_own_competitions() {

        OwnerService ownerService = new OwnerService();
        ownerService.userAuthorizer = authorizeRequest -> "";

        ownerService.competitionDatabase = mock(CompetitionDatabase.class);

        when(ownerService.competitionDatabase.competitionsByOwner("username"))
                .thenReturn(Arrays.asList(new Competition("1"), new Competition("2")));

        List<Competition> ownedCompetitions = ownerService.findOwnedCompetitions(new AuthorizeRequest("username", ""));

        assertThat(ownedCompetitions.size(), is(0));
    }

    @Test
    public void authorized_owner_can_add_another_user_to_be_competition_owner() {

        OwnerService ownerService = new OwnerService();
        ownerService.userAuthorizer = authorizeRequest -> "userId";

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
        request.competitionId = competition.name;
        request.userIdExistingOwner = UUID.randomUUID().toString();
        request.authorizeRequest.username = request.userIdExistingOwner;
        request.usernameOfNewOwner = user.username;

        when(ownerService.competitionDatabase.competitionsByOwner(request.userIdExistingOwner))
                .thenReturn(Arrays.asList(competition));

        when(ownerService.ownerDatabase.ownersByCompetition(request.competitionId))
                .thenReturn(Arrays.asList(new Owner().userId(request.userIdExistingOwner)));

        when(ownerService.userDatabase.findUserByUsername(request.usernameOfNewOwner))
                .thenReturn(Optional.of(user));

        ownerService.addOwnerRights(request);

        ArgumentCaptor<Owner> argument = ArgumentCaptor.forClass(Owner.class);
        verify(ownerService.ownerDatabase).addOwner(argument.capture());
        assertThat(argument.getValue().userId, is(user.id));
        assertThat(argument.getValue().competitionId, is(competition.id));
    }

    @Test
    public void when_caller_is_different_user_than_owner_then_adding_new_owner_fails() {

        OwnerService ownerService = new OwnerService();
        ownerService.userAuthorizer = authorizeRequest -> "userId";

        ownerService.ownerDatabase = mock(OwnerDatabase.class);

        AddOwnerRightsRequest request = new AddOwnerRightsRequest();
        request.authorizeRequest = new AuthorizeRequest();
        request.authorizeRequest.username = UUID.randomUUID().toString();
        request.competitionId = UUID.randomUUID().toString();
        request.userIdExistingOwner = UUID.randomUUID().toString();
        request.usernameOfNewOwner = UUID.randomUUID().toString();

        when(ownerService.ownerDatabase.ownersByCompetition(request.competitionId))
                .thenReturn(Arrays.asList(new Owner().userId(request.userIdExistingOwner)));

        ownerService.addOwnerRights(request);

        verify(ownerService.ownerDatabase, never()).addOwner(any());
    }

    @Test
    public void non_authorized_owner_cannot_add_another_user_to_be_competition_owner() {
        OwnerService ownerService = new OwnerService();
        ownerService.userAuthorizer = authorizeRequest -> "";

        ownerService.ownerDatabase = mock(OwnerDatabase.class);

        AddOwnerRightsRequest request = new AddOwnerRightsRequest();
        request.authorizeRequest = new AuthorizeRequest();
        request.competitionId = UUID.randomUUID().toString();
        request.userIdExistingOwner = UUID.randomUUID().toString();
        request.usernameOfNewOwner = UUID.randomUUID().toString();

        ownerService.addOwnerRights(request);

        verify(ownerService.ownerDatabase, never()).addOwner(any());
    }

    @Test
    public void non_owner_user_cannot_add_another_user_to_be_competition_owner() {

        OwnerService ownerService = new OwnerService();
        ownerService.userAuthorizer = authorizeRequest -> "userId";

        ownerService.ownerDatabase = mock(OwnerDatabase.class);
        ownerService.competitionDatabase = mock(CompetitionDatabase.class);

        AddOwnerRightsRequest request = new AddOwnerRightsRequest();
        request.authorizeRequest = new AuthorizeRequest();
        request.competitionId = UUID.randomUUID().toString();
        request.userIdExistingOwner = UUID.randomUUID().toString();
        request.authorizeRequest.username = request.userIdExistingOwner;
        request.usernameOfNewOwner = UUID.randomUUID().toString();

        when(ownerService.competitionDatabase.competitionsByOwner(request.userIdExistingOwner))
                .thenReturn(Arrays.asList());

        ownerService.addOwnerRights(request);

        verify(ownerService.ownerDatabase, never()).addOwner(any());
    }

}