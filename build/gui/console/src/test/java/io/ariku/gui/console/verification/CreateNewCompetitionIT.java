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

public class CreateNewCompetitionIT {

    @Rule
    public final SystemOutRule systemOut = new SystemOutRule();

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Test
    public void user_can_create_new_competition() {

        String userId = "a@a.fi";
        String competitionName = "Uulalaa";
        String competitionType = "RockPaperAndScissors";

        systemOut.enableLog();
        exit.expectSystemExit();
        exit.checkAssertionAfterwards(() -> assertThat(systemOut.getLog(), containsString("Created new competition")));

        ArikuConsole.startConsole(() -> new UserSimulator()
                .signUp(userId)
                .verifySignUp(userId)
                .login(userId)
                .createCompetition(competitionName, competitionType)
                .exit());
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

        ArikuConsole.startConsole(() -> new UserSimulator()
                .signUp(userId)
                .verifySignUp(userId)
                .login(userId)
                .createCompetition(competitionName, competitionType)
                .printOwnedCompetitionsToConsole()
                .exit());
    }

}

