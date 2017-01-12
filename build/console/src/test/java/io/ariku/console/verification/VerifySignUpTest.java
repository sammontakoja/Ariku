package io.ariku.console.verification;

/**
 * @author Ari Aaltonen
 */

import io.ariku.composer.Composer;
import io.ariku.console.ArikuConsole;
import io.ariku.console.Keyboard;
import io.ariku.verification.api.SignUpRequest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.contrib.java.lang.system.SystemOutRule;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class VerifySignUpTest {

    @Rule
    public final SystemOutRule systemOut = new SystemOutRule();

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Test
    public void when_user_give_email_which_is_used_in_SignUp_then_signup_is_verified_successfully() {

        String email = "b@b.fi";

        // SignUp using email which will be verified
        Composer.COMPOSER.userVerificationService.signUp(new SignUpRequest(email));

        systemOut.enableLog();
        exit.expectSystemExit();
        exit.checkAssertionAfterwards(() -> assertThat(systemOut.getLog(), containsString("VerifySignUp OK "+email)));

        Keyboard keyboard = new Keyboard();

        // Go to VerifySignUp page
        keyboard.typeEnter();
        keyboard.typeDown();
        keyboard.typeEnter();

        // Fill values
        keyboard.typeText(email);
        keyboard.typeDown();
        keyboard.typeEnter();

        // Exit VerifySignUp page
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

    @Test
    public void when_user_give_email_which_is_not_used_in_SignUp_then_signup_verification_fails() {

        String email = "b@b.fi";

        systemOut.enableLog();
        exit.expectSystemExit();
        exit.checkAssertionAfterwards(() -> assertThat(systemOut.getLog(), containsString("VerifySignUp FAIL "+email)));

        Keyboard keyboard = new Keyboard();

        // Go to VerifySignUp page
        keyboard.typeEnter();
        keyboard.typeDown();
        keyboard.typeEnter();

        // Fill values
        keyboard.typeText(email);
        keyboard.typeDown();
        keyboard.typeEnter();

        // Exit VerifySignUp page
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

    @Test
    public void when_user_can_fill_already_used_email_then_signup_fails() {

        String email = "a@a.fi";

        // SignUp using email which will be used again
        Composer.COMPOSER.userVerificationService.signUp(new SignUpRequest(email));

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

        keyboard.startTypingAfterTwoSeconds();

        ArikuConsole.main(new String[0]);
    }

}

