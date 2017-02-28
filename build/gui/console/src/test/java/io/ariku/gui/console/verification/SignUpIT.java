package io.ariku.gui.console.verification;

/**
 * @author Ari Aaltonen
 */

import io.ariku.composer.Composer;
import io.ariku.gui.console.ArikuConsole;
import io.ariku.gui.console.Keyboard;
import io.ariku.verification.SignUpRequest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.contrib.java.lang.system.SystemOutRule;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class SignUpIT {

    @Rule
    public final SystemOutRule systemOut = new SystemOutRule();

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Test
    public void when_user_can_fill_unused_email_then_signup_ok() {

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

        ArikuConsole.startConsole(() -> keyboard.startTyping());
    }

    @Test
    public void when_user_can_fill_already_used_email_then_signup_fails() {

        String email = "a@a.fi";

        // SignUp using email which will be used again
        Composer.SIMPLE.userVerificationService.signUp(new SignUpRequest(email));

        systemOut.enableLog();
        exit.expectSystemExit();
        exit.checkAssertionAfterwards(() -> assertThat(systemOut.getLog(), containsString("SignUp FAIL "+email)));

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

        ArikuConsole.startConsole(() -> keyboard.startTyping());
    }

}

