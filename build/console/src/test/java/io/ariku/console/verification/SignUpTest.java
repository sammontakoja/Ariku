package io.ariku.console.verification;

/**
 * @author Ari Aaltonen
 */

import io.ariku.console.ArikuConsole;
import io.ariku.console.Keyboard;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.contrib.java.lang.system.SystemOutRule;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class SignUpTest {

    @Rule
    public final SystemOutRule systemOut = new SystemOutRule();

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Test
    public void user_can_fill_SignUp_values_and_exit() {

        String email = "a@a.fi";

        systemOut.enableLog();
        exit.expectSystemExit();
        exit.checkAssertionAfterwards(() -> assertThat(systemOut.getLog(), containsString("SignUp OK "+email)));

        Keyboard keyboard = new Keyboard();

        // Go to SignUp page
        keyboard.typeEnter();
        keyboard.typeEnter();

        // Fill values
        keyboard.typeText(email);
        keyboard.typeDown();
        keyboard.typeEnter();

        // Exit SignUp page
        keyboard.typeDown();
        keyboard.typeEnter();

        // Exit verification page
        keyboard.typeDown();
        keyboard.typeDown();
        keyboard.typeDown();
        keyboard.typeDown();
        keyboard.typeEnter();

        // Exit program
        keyboard.typeDown();
        keyboard.typeDown();
        keyboard.typeEnter();

        keyboard.startTypingAfterTwoSeconds();

        ArikuConsole.main(new String[0]);
    }

}

