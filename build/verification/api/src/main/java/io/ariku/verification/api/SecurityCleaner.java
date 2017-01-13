package io.ariku.verification.api;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author Ari Aaltonen
 */
public class SecurityCleaner {

    public UserVerificationDatabase userVerificationDatabase;

    public void wipeTokensWhichAreOlderThan(int scanInterval, int grantedNonActiveLoginTimeInSeconds) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> wipeTokensWhichAreOlderThan(grantedNonActiveLoginTimeInSeconds), 0, scanInterval, SECONDS);
    }

    public void wipeTokensWhichAreOlderThan(int grantedNonActiveLoginTimeInSeconds) {

        System.out.println("PLAA");

        Instant now = Instant.now();

        userVerificationDatabase.userVerifications().stream()
                .filter(userVerification -> lastActivityInSeconds(userVerification, now) > grantedNonActiveLoginTimeInSeconds)
                .forEach(userVerification -> {
                    System.out.println("UULALAA");
                    userVerification.securityMessage.token = "";
                    userVerificationDatabase.updateUserVerification(userVerification);
                });
    }

    private long lastActivityInSeconds(UserVerification a, Instant now) {
        return Duration.between(Instant.parse(a.securityMessage.lastSecurityActivity), now).getSeconds();
    }


}
