package io.ariku.console;

/**
 * @author Ari Aaltonen
 */

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainMenuNavigationTests {

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Test
    public void program_terminates_after_giving_v_parameter_to_main() throws InterruptedException {

        exit.expectSystemExit();

        String[] params = {"-v"};
        ArikuConsole.main(params);
    }

    @Test
    public void program_terminates_after_pressing_exit_button() throws InterruptedException {

        exit.expectSystemExit();

        Executors.newScheduledThreadPool(1).schedule(() -> {

            try {
                Robot robot = new Robot();

                pressOnce(robot, KeyEvent.VK_DOWN);
                pressOnce(robot, KeyEvent.VK_ENTER);

            } catch (AWTException e) {
                throw new RuntimeException(e);
            }

        }, 2, TimeUnit.SECONDS);

        ArikuConsole.main(new String[0]);

        // Give program 4 seconds to call System.exit()
        Thread.sleep(4000);
    }

    private void pressOnce(Robot robot, int key) {
        System.out.println("Pressed key" + KeyEvent.getKeyText(key));
        robot.keyPress(key);
        robot.keyRelease(key);
    }

}

