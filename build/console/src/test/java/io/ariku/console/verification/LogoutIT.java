package io.ariku.console.verification;

/**
 * @author Ari Aaltonen
 */

import io.ariku.composer.Composer;
import io.ariku.console.ArikuConsole;
import io.ariku.console.ConsoleCache;
import io.ariku.console.Keyboard;
import io.ariku.verification.LoginRequest;
import io.ariku.verification.SignUpRequest;
import io.ariku.verification.VerifySignUpRequest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.contrib.java.lang.system.SystemOutRule;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class LogoutIT {

    @Rule
    public final SystemOutRule systemOut = new SystemOutRule();

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Test
    public void when_user_logout_with_email_which_is_logged_in_then_logout_succeed() {

        String email = "d@d.fi";

        // sign up, verify sign up and login before logout attempt
        Composer.COMPOSER.userVerificationService.signUp(new SignUpRequest(email));
        Composer.COMPOSER.userVerificationService.verifySignUp(new VerifySignUpRequest(email));
        ConsoleCache.securityMessage = Composer.COMPOSER.userVerificationService.login(new LoginRequest(email));

        systemOut.enableLog();
        exit.expectSystemExit();
        exit.checkAssertionAfterwards(() -> assertThat(systemOut.getLog(), containsString("Logout OK " + email)));

        Keyboard keyboard = new Keyboard();

        // Go to Logout page
        keyboard.typeEnter();
        keyboard.typeDown();
        keyboard.typeDown();
        keyboard.typeDown();
        keyboard.typeEnter();

        // Fill values
        keyboard.typeText(email);
        keyboard.typeDown();
        keyboard.typeEnter();

        // Exit Logout page
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
    public void when_user_logout_with_email_which_is_not_logged_in_then_logout_fails() {

        String email = "d@d.fi";

        // sign up, verify sign up and skip login before logout attempt
        Composer.COMPOSER.userVerificationService.signUp(new SignUpRequest(email));
        Composer.COMPOSER.userVerificationService.verifySignUp(new VerifySignUpRequest(email));

        systemOut.enableLog();
        exit.expectSystemExit();
        exit.checkAssertionAfterwards(() -> assertThat(systemOut.getLog(), containsString("Logout FAIL " + email)));

        Keyboard keyboard = new Keyboard();

        // Go to Logout page
        keyboard.typeEnter();
        keyboard.typeDown();
        keyboard.typeDown();
        keyboard.typeDown();
        keyboard.typeEnter();

        // Fill values
        keyboard.typeText(email);
        keyboard.typeDown();
        keyboard.typeEnter();

        // Exit Logout page
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

