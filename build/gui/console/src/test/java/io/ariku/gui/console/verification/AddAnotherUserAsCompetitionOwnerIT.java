package io.ariku.gui.console.verification;

/**
 * @author Ari Aaltonen
 */

import io.ariku.gui.console.ArikuConsole;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.contrib.java.lang.system.SystemOutRule;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class AddAnotherUserAsCompetitionOwnerIT {

    @Rule
    public final SystemOutRule systemOut = new SystemOutRule();

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Test
    public void userB_can_add_userA_as_competition_owner() {

        systemOut.enableLog();
        exit.expectSystemExit();
        exit.checkAssertionAfterwards(() -> assertThat(systemOut.getLog(), containsString("Added user 'userA' as new owner of competition 'Ultimate.'")));

        ArikuConsole.startConsole(() -> new UserSimulator()
                .signUpAndLoginUser("userA")
                .signUpAndLoginUser("userB")
                .createCompetition("Ultimate", "RockPaperAndScissors")
                .addUserAsOwner("userA", "Ultimate")
        );
    }

    @Test
    public void user_can_see_created_competition_when_listing_owned_competitions() {

        String userId = "a@a.fi";
        String competitionName = "Uulalaa";
        String competitionType = "RockPaperAndScissors";

        systemOut.enableLog();
        exit.expectSystemExit();
        String expectedOutput = String.format("Competitions:[Competition{name='%s', type='%s'", competitionName, competitionType);
        exit.checkAssertionAfterwards(() -> assertThat(systemOut.getLog(), containsString(expectedOutput)));

        ArikuConsole.startConsole(() -> {
            UserSimulator userSimulator = new UserSimulator();
            userSimulator.signUp(userId);
            userSimulator.verifySignUp(userId);
            userSimulator.login(userId);
            userSimulator.createCompetition(competitionName, competitionType);
            userSimulator.printOwnedCompetitionsToConsole();
            userSimulator.exit();
        });
    }

}

