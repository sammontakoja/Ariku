package io.ariku.console.verification;

/**
 * @author Ari Aaltonen
 */

import io.ariku.composer.Composer;
import io.ariku.console.ArikuConsole;
import io.ariku.console.Keyboard;
import io.ariku.verification.api.SignUpRequest;
import io.ariku.verification.api.VerifySignUpRequest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.contrib.java.lang.system.SystemOutRule;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class LoginIT {

    @Rule
    public final SystemOutRule systemOut = new SystemOutRule();

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Test
    public void when_user_login_with_email_which_is_verified_then_login_succeed() {

        String email = "b@b.fi";

        // SignUp and VerifySignUp before login attempt
        Composer.COMPOSER.userVerificationService.signUp(new SignUpRequest(email));
        Composer.COMPOSER.userVerificationService.verifySignUp(new VerifySignUpRequest(email));

        systemOut.enableLog();
        exit.expectSystemExit();
        exit.checkAssertionAfterwards(() -> assertThat(systemOut.getLog(), containsString("Login OK "+email)));

        Keyboard keyboard = new Keyboard();

        // Go to Login page
        keyboard.typeEnter();
        keyboard.typeDown();
        keyboard.typeDown();
        keyboard.typeEnter();

        // Fill values
        keyboard.typeText(email);
        keyboard.typeDown();
        keyboard.typeEnter();

        // Exit Login page
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
    public void when_user_login_with_email_which_is_not_verified_then_login_fails() {

        String email = "b@b.fi";

        // SignUp but skip VerifySignUp before login attempt
        Composer.COMPOSER.userVerificationService.signUp(new SignUpRequest(email));

        systemOut.enableLog();
        exit.expectSystemExit();
        exit.checkAssertionAfterwards(() -> assertThat(systemOut.getLog(), containsString("Login FAIL "+email)));

        Keyboard keyboard = new Keyboard();

        // Go to Login page
        keyboard.typeEnter();
        keyboard.typeDown();
        keyboard.typeDown();
        keyboard.typeEnter();

        // Fill values
        keyboard.typeText(email);
        keyboard.typeDown();
        keyboard.typeEnter();

        // Exit Login page
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

