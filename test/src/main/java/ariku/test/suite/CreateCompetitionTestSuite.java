package ariku.test.suite;

import ariku.CoreServices;
import ariku.owner.NewCompetitionRequest;
import ariku.test.util.Commands;
import ariku.test.util.InputGenerator;
import ariku.test.util.TestRunner;
import ariku.util.AuthorizeRequest;
import ariku.util.Competition;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Ari Aaltonen
 */
public class CreateCompetitionTestSuite implements TestRunner {

    private CoreServices arikuServices;
    private Commands commands;

    @Override
    public void runAllTests(CoreServices arikuServices) {
        // Initialize
        this.arikuServices = arikuServices;
        this.commands = new Commands(this.arikuServices.userVerificationService());

        // Tests
        creating_new_competition_ok();
        creating_new_competition_fails_when_given_name_is_too_short();
        after_ok_creating_competition_user_who_created_competition_is_competition_owner();
        creating_new_competition_fails_when_given_security_token_is_not_valid();
        creating_new_competition_fails_when_given_type_is_too_short();
    }

    public void creating_new_competition_ok() {

        String username = InputGenerator.randomUsername();

        String validSecurityToken = commands.loginWithUsername(username);

        Optional<Competition> createdCompetition = arikuServices.ownerService().createNewCompetition(
                new NewCompetitionRequest()
                        .name("name")
                        .type("type")
                        .authorizeRequest(new AuthorizeRequest(username, validSecurityToken)));

        assertThat(createdCompetition.isPresent(), is(true));
    }

    public void creating_new_competition_fails_when_given_security_token_is_not_valid() {

        String username = InputGenerator.randomUsername();

        Optional<Competition> createdCompetition = arikuServices.ownerService().createNewCompetition(
                new NewCompetitionRequest()
                        .name("name")
                        .type("type")
                        .authorizeRequest(new AuthorizeRequest(username, "securityToken")));

        assertThat(createdCompetition.isPresent(), is(false));
    }

    public void creating_new_competition_fails_when_given_name_is_too_short() {

        String username = InputGenerator.randomUsername();

        String validSecurityToken = commands.loginWithUsername(username);

        String tooShortName = "1";

        Optional<Competition> createdCompetition = arikuServices.ownerService().createNewCompetition(
                new NewCompetitionRequest()
                        .name(tooShortName)
                        .type("type")
                        .authorizeRequest(new AuthorizeRequest(username, validSecurityToken)));

        assertThat(createdCompetition.isPresent(), is(false));
    }

    public void after_ok_creating_competition_user_who_created_competition_is_competition_owner() {

        String username = InputGenerator.randomUsername();

        String securityToken = commands.loginWithUsername(username);

        arikuServices.ownerService().createNewCompetition(
                new NewCompetitionRequest()
                        .name("name")
                        .type("type")
                        .authorizeRequest(new AuthorizeRequest(username, securityToken)));

        List<Competition> ownedCompetitions = arikuServices.ownerService().findOwnedCompetitions(new AuthorizeRequest(username, securityToken));

        assertThat(ownedCompetitions.size(), is(1));
    }

    public void creating_new_competition_fails_when_given_type_is_too_short() {

        String username = InputGenerator.randomUsername();

        String validSecurityToken = commands.loginWithUsername(username);

        String tooShortType = "1";

        Optional<Competition> createdCompetition = arikuServices.ownerService().createNewCompetition(
                new NewCompetitionRequest()
                        .name("name")
                        .type(tooShortType)
                        .authorizeRequest(new AuthorizeRequest(username, validSecurityToken)));

        assertThat(createdCompetition.isPresent(), is(false));
    }

}
