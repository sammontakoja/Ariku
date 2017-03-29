package ariku.verification;

import ariku.database.UserVerificationRepository;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author Ari Aaltonen
 */
public class SecurityCleaner {

    public UserVerificationRepository userVerificationRepository;

    public void wipeTokensWhichAreOlderThan(int scanInterval, int grantedNonActiveLoginTimeInSeconds) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> wipeTokensWhichAreOlderThan(grantedNonActiveLoginTimeInSeconds), 0, scanInterval, SECONDS);
    }

    public void wipeTokensWhichAreOlderThan(int grantedNonActiveLoginTimeInSeconds) {

        Instant now = Instant.now();

        userVerificationRepository.list().stream()
                .filter(userVerification -> lastActivityInSeconds(userVerification, now) > grantedNonActiveLoginTimeInSeconds)
                .forEach(userVerification -> {
                    SecurityMessage securityMessage = new SecurityMessage();
                    securityMessage.setToken("");
                    securityMessage.setLastSecurityActivity(Instant.now().toString());
                    userVerificationRepository.update(userVerification);
                });
    }

    private long lastActivityInSeconds(UserVerification a, Instant now) {
        return Duration.between(Instant.parse(a.getSecurityMessage().getLastSecurityActivity()), now).getSeconds();
    }


}
