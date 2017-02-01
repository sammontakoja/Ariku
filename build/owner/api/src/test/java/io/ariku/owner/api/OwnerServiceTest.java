package io.ariku.owner.api;

import io.ariku.util.data.User;
import io.ariku.verification.api.AuthorizeRequest;
import io.ariku.verification.api.UserAuthorizer;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Ari Aaltonen
 */
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

        verify(ownerService.competitionDatabase).createCompetition(anyString(),anyString(),anyString());}

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

        when(ownerService.competitionDatabase.ownersCompetitions("userId"))
                .thenReturn(Arrays.asList(new Competition("1"), new Competition("2")));

        List<Competition> ownedCompetitions = ownerService.findOwnedCompetitions(new AuthorizeRequest("userId", ""));

        assertThat(ownedCompetitions.size(), is(2));
    }

    @Test
    public void non_authorized_user_cannot_list_own_competitions() {

        OwnerService ownerService = new OwnerService();
        ownerService.userAuthorizer = authorizeRequest -> false;

        ownerService.competitionDatabase = mock(CompetitionDatabase.class);

        when(ownerService.competitionDatabase.ownersCompetitions("userId"))
                .thenReturn(Arrays.asList(new Competition("1"), new Competition("2")));

        List<Competition> ownedCompetitions = ownerService.findOwnedCompetitions(new AuthorizeRequest("userId", ""));

        assertThat(ownedCompetitions.size(), is(0));
    }

}