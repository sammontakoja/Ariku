package io.ariku.gui.console.verification;

/**
 * @author Ari Aaltonen
 */

import io.ariku.gui.console.ArikuConsole;
import io.ariku.gui.console.UserCache;
import io.ariku.gui.console.UserSimulator;
import io.ariku.verification.UserVerification;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class LoginIT {

    static String username = "a@a.fi";
    static Runnable signUpWithConsole = () -> ArikuConsole.startConsole(() -> new UserSimulator().signUp(username));
    static Runnable verifySignUpWithConsole = () -> ArikuConsole.startConsole(() -> new UserSimulator().verifySignUp(username));
    static Runnable loginWithConsole = () -> ArikuConsole.startConsole(() -> new UserSimulator().login(username));

    @BeforeClass
    public static void signUp_and_verify_SignUp_and_login() throws InterruptedException {
        doTaskWithinThreeSeconds(signUpWithConsole);
        Thread.sleep(200);
        doTaskWithinThreeSeconds(verifySignUpWithConsole);
        Thread.sleep(200);
        doTaskWithinThreeSeconds(loginWithConsole);
    }

    private static void doTaskWithinThreeSeconds(Runnable runnable) throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(runnable);
        Thread.sleep(3000);
        executorService.shutdownNow();
    }

    @Test
    public void userVerification_is_stored_with_same_security_message_as_is_returned_after_login() throws InterruptedException {
        Optional<UserVerification> userVerification =
                ArikuConsole.composer.userVerificationService.userVerificationRepository.findByUsername(username);
        assertThat(userVerification.get().securityMessage.token, is(UserCache.securityToken));
    }

}

