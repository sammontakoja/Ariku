package io.ariku.console;

/**
 * @author Ari Aaltonen
 */

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.contrib.java.lang.system.SystemOutRule;

import java.awt.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.awt.event.KeyEvent.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserVerificationTest {

    @Rule
    public final SystemOutRule systemOut = new SystemOutRule();

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Test
    public void user_can_SignIn_and_exit() {

        systemOut.enableLog();

        exit.expectSystemExit();

        exit.checkAssertionAfterwards(() -> assertThat(systemOut.getLog(), containsString("Pressed SignUp")));

        List<Integer> signInKeys = Arrays.asList(VK_ENTER, VK_ENTER);
        List<Integer> exitKeys = Arrays.asList(VK_DOWN, VK_DOWN, VK_DOWN, VK_DOWN, VK_ENTER, VK_DOWN, VK_ENTER);

        List<Integer> keyEvents = Stream.concat(signInKeys.stream(), exitKeys.stream()).collect(Collectors.toList());

        pressButtonsWithinTwoSeconds(keyEvents);

        ArikuConsole.main(new String[0]);
    }

    @Test
    public void user_can_signIn_verifySignIn_and_exit() {

        systemOut.enableLog();

        exit.expectSystemExit();

        exit.checkAssertionAfterwards(() -> {
            assertThat(systemOut.getLog(), containsString("Pressed SignUp"));
            assertThat(systemOut.getLog(), containsString("Pressed verifySignUp"));
        });

        List<Integer> signInKeys = Arrays.asList(VK_ENTER, VK_ENTER);
        List<Integer> verifySignInKeys = Arrays.asList(VK_DOWN, VK_ENTER);
        List<Integer> exitKeys = Arrays.asList(VK_DOWN, VK_DOWN, VK_DOWN, VK_ENTER, VK_DOWN, VK_ENTER);

        List<Integer> keyEvents = Stream.of(signInKeys, verifySignInKeys, exitKeys)
                .flatMap(Collection::stream).collect(Collectors.toList());

        pressButtonsWithinTwoSeconds(keyEvents);

        ArikuConsole.main(new String[0]);
    }

    @Test
    public void user_can_signIn_verifySignIn_login_and_exit() {

        systemOut.enableLog();

        exit.expectSystemExit();

        exit.checkAssertionAfterwards(() -> {
            assertThat(systemOut.getLog(), containsString("Pressed SignUp"));
            assertThat(systemOut.getLog(), containsString("Pressed verifySignUp"));
            assertThat(systemOut.getLog(), containsString("Pressed login"));
        });

        List<Integer> signInKeys = Arrays.asList(VK_ENTER, VK_ENTER);
        List<Integer> verifySignInKeys = Arrays.asList(VK_DOWN, VK_ENTER);
        List<Integer> loginKeys = Arrays.asList(VK_DOWN, VK_ENTER);
        List<Integer> exitKeys = Arrays.asList(VK_DOWN, VK_DOWN, VK_ENTER, VK_DOWN, VK_ENTER);

        List<Integer> keyEvents = Stream.of(signInKeys, verifySignInKeys, loginKeys, exitKeys)
                .flatMap(Collection::stream).collect(Collectors.toList());

        pressButtonsWithinTwoSeconds(keyEvents);

        ArikuConsole.main(new String[0]);
    }

    @Test
    public void user_can_signIn_verifySignIn_login_logout_and_exit() {

        systemOut.enableLog();

        exit.expectSystemExit();

        exit.checkAssertionAfterwards(() -> {
            assertThat(systemOut.getLog(), containsString("Pressed SignUp"));
            assertThat(systemOut.getLog(), containsString("Pressed verifySignUp"));
            assertThat(systemOut.getLog(), containsString("Pressed login"));
            assertThat(systemOut.getLog(), containsString("Pressed logout"));
        });

        List<Integer> signInKeys = Arrays.asList(VK_ENTER, VK_ENTER);
        List<Integer> verifySignInKeys = Arrays.asList(VK_DOWN, VK_ENTER);
        List<Integer> loginKeys = Arrays.asList(VK_DOWN, VK_ENTER);
        List<Integer> logoutKeys = Arrays.asList(VK_DOWN, VK_ENTER);
        List<Integer> exitKeys = Arrays.asList(VK_DOWN, VK_ENTER, VK_DOWN, VK_ENTER);

        List<Integer> keyEvents = Stream.of(signInKeys, verifySignInKeys, loginKeys, logoutKeys, exitKeys)
                .flatMap(Collection::stream).collect(Collectors.toList());

        pressButtonsWithinTwoSeconds(keyEvents);

        ArikuConsole.main(new String[0]);
    }

    private void pressButtonsWithinTwoSeconds(List<Integer> keyEvents) {
        Executors.newScheduledThreadPool(1).schedule(() -> {

            try {
                Robot robot = new Robot();

                for (int keyEvent : keyEvents) {
                    System.out.println("Pressed key" + getKeyText(keyEvent));
                    robot.keyPress(keyEvent);
                    robot.keyRelease(keyEvent);
                }

            } catch (AWTException e) {
                throw new RuntimeException(e);
            }

        }, 2, TimeUnit.SECONDS);
    }

}

