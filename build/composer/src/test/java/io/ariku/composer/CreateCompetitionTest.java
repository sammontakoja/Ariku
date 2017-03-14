package io.ariku.composer;

import io.ariku.owner.NewCompetitionRequest;
import io.ariku.util.data.Competition;
import io.ariku.verification.AuthorizeRequest;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Ari Aaltonen
 */
public class CreateCompetitionTest {

    private Composer composer;
    private Commands commands;
    private String username;

    @Before
    public void initializeValues() {
        composer = new Composer();
        username = UUID.randomUUID().toString();
        commands = new Commands(composer);
    }

    @Test
    public void creating_new_competition_fails_when_given_security_token_is_not_valid() {

        Optional<Competition> createdCompetition = composer.ownerService.createNewCompetition(
                new NewCompetitionRequest()
                        .name("name")
                        .type("type")
                        .authorizeRequest(new AuthorizeRequest(username, "securityToken")));

        assertFalse(createdCompetition.isPresent());
    }

    @Test
    public void creating_new_competition_fails_when_given_name_is_too_short() {

        String validSecurityToken = commands.loginWithUsername(username);

        String tooShortName = "1";

        Optional<Competition> createdCompetition = composer.ownerService.createNewCompetition(
                new NewCompetitionRequest()
                        .name(tooShortName)
                        .type("type")
                        .authorizeRequest(new AuthorizeRequest(username, validSecurityToken)));

        assertFalse(createdCompetition.isPresent());
    }

    @Test
    public void after_ok_creating_competition_user_who_created_competition_is_competition_owner() {

        String securityToken = commands.loginWithUsername(username);

        String tooShortName = "1";

        composer.ownerService.createNewCompetition(
                new NewCompetitionRequest()
                        .name(tooShortName)
                        .type("type")
                        .authorizeRequest(new AuthorizeRequest(username, securityToken)));

        List<Competition> ownedCompetitions = composer.ownerService.findOwnedCompetitions(new AuthorizeRequest(username, securityToken));

        assertThat(ownedCompetitions.size(), is(1));
    }

    @Test
    public void creating_new_competition_fails_when_given_type_is_too_short() {

        String validSecurityToken = commands.loginWithUsername(username);

        String tooShortType = "1";

        Optional<Competition> createdCompetition = composer.ownerService.createNewCompetition(
                new NewCompetitionRequest()
                        .name("name")
                        .type(tooShortType)
                        .authorizeRequest(new AuthorizeRequest(username, validSecurityToken)));

        assertFalse(createdCompetition.isPresent());
    }

    @Test
    public void creating_new_competition_ok() {

        String validSecurityToken = commands.loginWithUsername(username);

        Optional<Competition> createdCompetition = composer.ownerService.createNewCompetition(
                new NewCompetitionRequest()
                        .name("name")
                        .type("type")
                        .authorizeRequest(new AuthorizeRequest(username, validSecurityToken)));

        assertTrue(createdCompetition.isPresent());
    }



}
