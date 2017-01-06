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

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;

import static java.awt.event.KeyEvent.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class SignInTest {

    @Rule
    public final SystemOutRule systemOut = new SystemOutRule();

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Test
    public void user_can_fill_SignIn_values_and_exit() {

        systemOut.enableLog();

        exit.expectSystemExit();
        exit.checkAssertionAfterwards(() -> assertThat(systemOut.getLog(), containsString("Pressed SignUp")));

        Keyboard keyboard = new Keyboard();

        // go to sign in page
        keyboard.typeEnter();
        keyboard.typeEnter();

        // exit sign in page
        keyboard.typeDown();
        keyboard.typeDown();
        keyboard.typeDown();
        keyboard.typeDown();
        keyboard.typeEnter();

        // exit program
        keyboard.typeDown();
        keyboard.typeDown();
        keyboard.typeEnter();

        keyboard.startTypingAfterTwoSeconds();

        ArikuConsole.main(new String[0]);
    }

}

