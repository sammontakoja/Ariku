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

/**
 * @author Ari Aaltonen
 */
public class AddCompetitionOwnerRecordTest {

    private Composer composer;
    private Commands commands;
    private String usernameOfUserA;
    private String usernameOfUserB;
    private String usernameOfUserC;

    @Before
    public void initializeValues() {
        composer = new Composer();
        usernameOfUserA = UUID.randomUUID().toString();
        usernameOfUserB = UUID.randomUUID().toString();
        usernameOfUserC = UUID.randomUUID().toString();
        commands = new Commands(composer);
    }

    @Test
    public void userA_add_userB_as_competition_owner_ok() {

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
        Optional<Competition> competitionCreatedByUserA = composer.ownerService.createNewCompetition(newCompetitionRequest);

        // UserA add userB as Owner
        composer.ownerService.addNewOwner(usernameOfUserB, competitionCreatedByUserA.get().id, authorizeRequestOfUserA);

        // Verify userB is owner of competition created by userA
        List<Competition> ownedCompetitionsByUserB = composer.ownerService.findOwnedCompetitions(authorizeRequestOfUserB);
        assertThat(ownedCompetitionsByUserB.size(), is(1));
        assertThat(ownedCompetitionsByUserB.get(0).id, is(competitionCreatedByUserA.get().id));
    }

    @Test
    public void userA_fail_to_add_userB_as_competition_owner_when_userA_is_not_logged_in() {

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
        Optional<Competition> competitionCreatedByUserA = composer.ownerService.createNewCompetition(newCompetitionRequest);

        // Modify userA's authorizeRequest securityToken to be unknown
        authorizeRequestOfUserA.securityToken = "unknown by Ariku system";

        // UserA add userB as Owner
        composer.ownerService.addNewOwner(usernameOfUserB, competitionCreatedByUserA.get().id, authorizeRequestOfUserA);

        // Verify userB is not owner of competition created by userA
        List<Competition> ownedCompetitionsByUserB = composer.ownerService.findOwnedCompetitions(authorizeRequestOfUserB);
        assertThat(ownedCompetitionsByUserB.size(), is(0));
    }

    @Test
    public void userB_fail_to_add_userC_as_competition_owner_when_userB_is_not_competition_owner() {

        // userA login
        String securityTokenOfUserA = commands.loginWithUsername(usernameOfUserA);

        // userA create new competition
        Optional<Competition> competitionCreatedByUserA = composer.ownerService.createNewCompetition(new NewCompetitionRequest()
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
        composer.ownerService.addNewOwner(usernameOfUserC, competitionCreatedByUserA.get().id, authorizeRequestOfUserB);

        // Verify userC is not owner of competition created by userA
        List<Competition> ownedCompetitionsByUserB = composer.ownerService.findOwnedCompetitions(authorizeRequestOfUserC);
        assertThat(ownedCompetitionsByUserB.size(), is(0));
    }

}
