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

public class SignUpIT {

    static String username = "a@a.fi";
    static Runnable signUpWithConsole = () -> ArikuConsole.startConsole(() -> new UserSimulator().signUp(username));

    @BeforeClass
    public static void signUp() throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(signUpWithConsole);
        Thread.sleep(5000);
        executorService.shutdownNow();
    }

    @Test
    public void userVerification_found() throws InterruptedException {
        Optional<UserVerification> userVerification =
                ArikuConsole.composer.userVerificationService.userVerificationRepository.findByUsername(username);
        assertTrue(userVerification.isPresent());
    }

    @Test
    public void userVerification_contains_valid_username() throws InterruptedException {
        Optional<UserVerification> userVerification =
                ArikuConsole.composer.userVerificationService.userVerificationRepository.findByUsername(username);
        assertThat(userVerification.get().username, is(username));
    }

    @Test
    public void userVerification_sign_in_is_not_confirmed() throws InterruptedException {
        Optional<UserVerification> userVerification =
                ArikuConsole.composer.userVerificationService.userVerificationRepository.findByUsername(username);
        assertThat(userVerification.get().isSignedInConfirmed, is(false));
    }

}

