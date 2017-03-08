package io.ariku.gui.console.verification;

/**
 * @author Ari Aaltonen
 */

import io.ariku.gui.console.ArikuConsole;
import io.ariku.gui.console.UserSimulator;
import io.ariku.verification.UserVerification;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class LogoutIT {

    static String username = "a@a.fi";
    static Runnable signUpWithConsole = () -> ArikuConsole.startConsole(() -> new UserSimulator().signUp(username));
    static Runnable verifySignUpWithConsole = () -> ArikuConsole.startConsole(() -> new UserSimulator().verifySignUp(username));
    static Runnable loginWithConsole = () -> ArikuConsole.startConsole(() -> new UserSimulator().login(username));
    static Runnable logoutWithConsole = () -> ArikuConsole.startConsole(() -> new UserSimulator().logout());

    @BeforeClass
    public static void signUp_and_verify_SignUp_login_and_logout() throws InterruptedException {
        doTaskWithinThreeSeconds(signUpWithConsole);
        doTaskWithinThreeSeconds(verifySignUpWithConsole);
        doTaskWithinThreeSeconds(loginWithConsole);
        doTaskWithinThreeSeconds(logoutWithConsole);
    }

    private static void doTaskWithinThreeSeconds(Runnable runnable) throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(runnable);
        Thread.sleep(5000);
        executorService.shutdownNow();
    }

    @Test
    public void userVerification_is_stored_with_empty_security_message() throws InterruptedException {
        Optional<UserVerification> userVerification =
                ArikuConsole.composer.userVerificationService.userVerificationDatabase.findByUsername(username);
        assertThat(userVerification.get().securityMessage.token, is(""));
    }

}

