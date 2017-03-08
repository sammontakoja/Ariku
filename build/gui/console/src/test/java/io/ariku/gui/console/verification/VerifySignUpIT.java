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
import static org.junit.Assert.assertTrue;

public class VerifySignUpIT {

    static String username = "a@a.fi";
    static Runnable signUpWithConsole = () -> ArikuConsole.startConsole(() -> new UserSimulator().signUp(username));
    static Runnable verifySignUpWithConsole = () -> ArikuConsole.startConsole(() -> new UserSimulator().verifySignUp(username));

    @BeforeClass
    public static void signUp_and_verify_SignUp() throws InterruptedException {
        doTaskWithinFiveSeconds(signUpWithConsole);
        doTaskWithinFiveSeconds(verifySignUpWithConsole);
    }

    private static void doTaskWithinFiveSeconds(Runnable runnable) throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(runnable);
        Thread.sleep(5000);
        executorService.shutdownNow();
    }

    @Test
    public void userVerification_sign_in_is_confirmed() throws InterruptedException {
        Optional<UserVerification> userVerification =
                ArikuConsole.composer.userVerificationService.userVerificationDatabase.findByUsername(username);
        assertThat(userVerification.get().isSignedInConfirmed, is(true));
    }

}

