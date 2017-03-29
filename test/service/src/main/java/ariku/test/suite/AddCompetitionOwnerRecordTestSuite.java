package ariku.test.suite;

import ariku.test.ArikuServices;
import ariku.test.util.Commands;
import ariku.test.util.InputGenerator;
import ariku.test.util.TestRunner;
import io.ariku.owner.NewCompetitionRequest;
import io.ariku.util.data.AuthorizeRequest;
import io.ariku.util.data.Competition;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Ari Aaltonen
 */
public class AddCompetitionOwnerRecordTestSuite implements TestRunner {

    private ArikuServices arikuServices;
    private Commands commands;

    @Override
    public void runAllTests(ArikuServices arikuServices) {
        // Initialize
        this.arikuServices = arikuServices;
        this.commands = new Commands(this.arikuServices.userVerificationService());

        // Tests
        userA_add_userB_as_competition_owner_ok();
        userA_fail_to_add_userB_as_competition_owner_when_userA_is_not_logged_in();
        userB_fail_to_add_userC_as_competition_owner_when_userB_is_not_competition_owner();
    }

    public void userA_add_userB_as_competition_owner_ok() {

        String usernameOfUserA = InputGenerator.randomUsername();
        String usernameOfUserB = InputGenerator.randomUsername();

        // userA login
        String securityTokenOfUserA = commands.loginWithUsername(usernameOfUserA);
        AuthorizeRequest authorizeRequestOfUserA = new AuthorizeRequest(usernameOfUserA, securityTokenOfUserA);

        // userB login
        String securityTokenOfUserB = commands.loginWithUsername(usernameOfUserB);
        AuthorizeRequest authorizeRequestOfUserB = new AuthorizeRequest(usernameOfUserB, securityTokenOfUserB);

        // UserA create new competition
        NewCompetitionRequest newCompetitionRequest = new NewCompetitionRequest()
                .name("name")
                .type("type")
                .authorizeRequest(authorizeRequestOfUserA);
        Optional<Competition> competitionCreatedByUserA = arikuServices.ownerService().createNewCompetition(newCompetitionRequest);

        // UserA add userB as Owner
        arikuServices.ownerService().addNewOwner(usernameOfUserB, competitionCreatedByUserA.get().id, authorizeRequestOfUserA);

        // Verify userB is owner of competition created by userA
        List<Competition> ownedCompetitionsByUserB = arikuServices.ownerService().findOwnedCompetitions(authorizeRequestOfUserB);
        assertThat(ownedCompetitionsByUserB.size(), is(1));
        assertThat(ownedCompetitionsByUserB.get(0).id, is(competitionCreatedByUserA.get().id));
    }

    public void userA_fail_to_add_userB_as_competition_owner_when_userA_is_not_logged_in() {

        String usernameOfUserA = InputGenerator.randomUsername();
        String usernameOfUserB = InputGenerator.randomUsername();

        // userA login
        String securityTokenOfUserA = commands.loginWithUsername(usernameOfUserA);
        AuthorizeRequest authorizeRequestOfUserA = new AuthorizeRequest(usernameOfUserA, securityTokenOfUserA);

        // userB login
        String securityTokenOfUserB = commands.loginWithUsername(usernameOfUserB);
        AuthorizeRequest authorizeRequestOfUserB = new AuthorizeRequest(usernameOfUserB, securityTokenOfUserB);

        // UserA create new competition
        NewCompetitionRequest newCompetitionRequest = new NewCompetitionRequest()
                .name("name")
                .type("type")
                .authorizeRequest(authorizeRequestOfUserA);
        Optional<Competition> competitionCreatedByUserA = arikuServices.ownerService().createNewCompetition(newCompetitionRequest);

        // Modify userA's authorizeRequest securityToken to be unknown
        authorizeRequestOfUserA.securityToken = "unknown by Ariku system";

        // UserA add userB as Owner
        arikuServices.ownerService().addNewOwner(usernameOfUserB, competitionCreatedByUserA.get().id, authorizeRequestOfUserA);

        // Verify userB is not owner of competition created by userA
        List<Competition> ownedCompetitionsByUserB = arikuServices.ownerService().findOwnedCompetitions(authorizeRequestOfUserB);
        assertThat(ownedCompetitionsByUserB.size(), is(0));
    }

    public void userB_fail_to_add_userC_as_competition_owner_when_userB_is_not_competition_owner() {

        String usernameOfUserA = InputGenerator.randomUsername();
        String usernameOfUserB = InputGenerator.randomUsername();
        String usernameOfUserC = InputGenerator.randomUsername();

        // userA login
        String securityTokenOfUserA = commands.loginWithUsername(usernameOfUserA);

        // userA create new competition
        Optional<Competition> competitionCreatedByUserA = arikuServices.ownerService().createNewCompetition(new NewCompetitionRequest()
                .name("name")
                .type("type")
                .authorizeRequest(new AuthorizeRequest(usernameOfUserA, securityTokenOfUserA)));

        // userB login
        String securityTokenOfUserB = commands.loginWithUsername(usernameOfUserB);
        AuthorizeRequest authorizeRequestOfUserB = new AuthorizeRequest(usernameOfUserB, securityTokenOfUserB);

        // userC login
        String securityTokenOfUserC = commands.loginWithUsername(usernameOfUserC);
        AuthorizeRequest authorizeRequestOfUserC = new AuthorizeRequest(usernameOfUserC, securityTokenOfUserC);

        // UserB add userC as Owner
        arikuServices.ownerService().addNewOwner(usernameOfUserC, competitionCreatedByUserA.get().id, authorizeRequestOfUserB);

        // Verify userC is not owner of competition created by userA
        List<Competition> ownedCompetitionsByUserB = arikuServices.ownerService().findOwnedCompetitions(authorizeRequestOfUserC);
        assertThat(ownedCompetitionsByUserB.size(), is(0));
    }

}
