package io.ariku.composer;

import io.ariku.owner.NewCompetitionRequest;
import io.ariku.util.data.Competition;
import io.ariku.verification.AuthorizeRequest;
import io.ariku.verification.LoginRequest;
import io.ariku.verification.SignUpRequest;
import io.ariku.verification.VerifySignUpRequest;
import org.junit.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Ari Aaltonen
 */
public class AddCompetitionOwnershipToAnotherUserTest {

    @Test
    public void userA_can_add_userB_as_competition_owner() {

        Composer composer = new Composer();

        // Create user A login and login with it
        String usernameA = UUID.randomUUID().toString();
        composer.userVerificationService.signUp(new SignUpRequest(usernameA));
        composer.userVerificationService.verifySignUp(new VerifySignUpRequest(usernameA));
        String securityTokenOfUserA = composer.userVerificationService.login(new LoginRequest(usernameA));

        // Create competition with user A
        Optional<Competition> createdCompetition = composer.ownerService.createNewCompetition(new NewCompetitionRequest()
                .competitionName("Helsinki GP")
                .competitionType("WRC")
                .authorizeRequest(new AuthorizeRequest(usernameA, securityTokenOfUserA)));

        assertTrue(createdCompetition.isPresent());

        // Create user B login and login with it
        String usernameB = UUID.randomUUID().toString();
        composer.userVerificationService.signUp(new SignUpRequest(usernameA));
        composer.userVerificationService.verifySignUp(new VerifySignUpRequest(usernameA));
        String securityTokenOfUserB = composer.userVerificationService.login(new LoginRequest(usernameA));
        assertFalse(securityTokenOfUserB.isEmpty());

//         Now user A should be owner of created competition
        boolean userAIsOneOfCompetitionOwners =
                composer.ownerService.findOwnedCompetitions(new AuthorizeRequest(usernameA, securityTokenOfUserA))
                .stream().anyMatch(c -> c.name.equals(createdCompetition.get().name) && c.id.equals(createdCompetition.get().id));

        assertTrue(userAIsOneOfCompetitionOwners);

        // User A should NOT be owner of created competition
//
//        // UserA add userB as owner
//        composer.ownerService.addOwnerRights(new AddOwnerRightsRequest()
//                .competitionId(createdCompetition.get().id)
//                .usernameOfNewOwner(usernameB)
//                .authorizeRequest(new AuthorizeRequest(usernameA, securityTokenOfUserA)));
//
//        // Now competition should have two owners

    }

}
