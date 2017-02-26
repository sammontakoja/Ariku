package io.ariku.verification;

import io.ariku.verification.SecurityCleaner;
import io.ariku.verification.UserVerification;
import io.ariku.verification.UserVerificationDatabase;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Ari Aaltonen
 */
public class SecurityCleanerTest {

    @Test
    public void when_securityMessages_lastSecurityActivity_is_over_given_threshold_then_token_updated_as_empty() {

        SecurityCleaner securityCleaner = new SecurityCleaner();

        UserVerificationDatabase database = mock(UserVerificationDatabase.class);
        securityCleaner.userVerificationDatabase = database;

        // Create UserVerification which is used 10 seconds ago
        UserVerification userVerification = new UserVerification();
        userVerification.securityMessage.token = UUID.randomUUID().toString();
        userVerification.securityMessage.lastSecurityActivity = Instant.now().minusSeconds(10).toString();

        // Make sure SecurityCleaner get created UserVerification when getting all UserVerifications from database
        when(database.userVerifications()).thenReturn(Arrays.asList(userVerification));

        // Wipe all tokens which are used over 5 seconds ago
        securityCleaner.wipeTokensWhichAreOlderThan(5);

        ArgumentCaptor<UserVerification> argument = ArgumentCaptor.forClass(UserVerification.class);
        verify(database).updateUserVerification(argument.capture());

        assertThat(argument.getValue().securityMessage.token, is(""));
    }

    @Test
    public void when_securityMessages_lastSecurityActivity_is_under_given_threshold_then_token_is_not_updated() {

        SecurityCleaner securityCleaner = new SecurityCleaner();

        UserVerificationDatabase database = mock(UserVerificationDatabase.class);
        securityCleaner.userVerificationDatabase = database;

        // Create UserVerification which is used 10 seconds ago
        UserVerification userVerification = new UserVerification();
        userVerification.securityMessage.token = UUID.randomUUID().toString();
        userVerification.securityMessage.lastSecurityActivity = Instant.now().minusSeconds(10).toString();

        // Make sure SecurityCleaner get created UserVerification when getting all UserVerifications from database
        when(database.userVerifications()).thenReturn(Arrays.asList(userVerification));

        // Wipe all tokens which are used over 15 seconds ago
        securityCleaner.wipeTokensWhichAreOlderThan(15);

        verify(database, never()).updateUserVerification(any());
    }

    @Test
    public void make_sure_automatic_token_cleaner_works() throws InterruptedException {

        SecurityCleaner securityCleaner = new SecurityCleaner();

        // Create UserVerification
        UserVerification userVerification = new UserVerification();
        userVerification.securityMessage.token = UUID.randomUUID().toString();
        userVerification.securityMessage.lastSecurityActivity = Instant.now().toString();

        class UpdateUserVerification {
            public boolean called;
        }

        UpdateUserVerification updateUserVerification = new UpdateUserVerification();

        securityCleaner.userVerificationDatabase = new UserVerificationDatabase() {
            @Override
            public void createUserVerification(String userId) {

            }

            @Override
            public UserVerification readUserVerification(String userId) {
                return null;
            }

            @Override
            public void deleteUserVerification(String userId) {

            }

            @Override
            public void updateUserVerification(UserVerification userVerification) {
                updateUserVerification.called = true;
                assertThat(userVerification.securityMessage.token, is(""));
            }

            @Override
            public List<UserVerification> userVerifications() {
                return Arrays.asList(userVerification);
            }
        };

        // Wipe all tokens which are older than 1 second with one second interval
        securityCleaner.wipeTokensWhichAreOlderThan(1, 1);

        // UserVerification is not old enough to be updated by SecurityCleaner
        assertThat(updateUserVerification.called, is(false));

        // After 4 seconds UserVerification should be updated by SecurityCleaner
        Thread.sleep(4000);
        assertThat(updateUserVerification.called, is(true));
    }

}