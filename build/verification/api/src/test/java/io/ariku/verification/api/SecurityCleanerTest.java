package io.ariku.verification.api;

import org.hamcrest.CoreMatchers;
import org.junit.Ignore;
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

    @Ignore("Until SecurityCleaner's scheduler works")
    @Test
    public void make_sure_automatic_token_cleaner_works() throws InterruptedException {

        SecurityCleaner securityCleaner = new SecurityCleaner();

        UserVerificationDatabase database = mock(UserVerificationDatabase.class);

        // Create UserVerification which is used 1 second ago
        UserVerification userVerification = new UserVerification();
        userVerification.securityMessage.token = UUID.randomUUID().toString();
        userVerification.securityMessage.lastSecurityActivity = Instant.now().minusSeconds(1).toString();

        // Make sure SecurityCleaner get created UserVerification when getting all UserVerifications from database
        when(database.userVerifications()).thenReturn(Arrays.asList(userVerification));

        // Wipe all tokens which are used over 2 seconds ago with one second interval
        securityCleaner.wipeTokensWhichAreOlderThan(1, 1);

        // Tokens are not cleared yet because token's last activity is not over 2 seconds
        verify(database, never()).updateUserVerification(any());

        // Wait 1,5 seconds, now SecurityClearer should have wiped token because 2,5 seconds have passed with created UserVerification.
        Thread.sleep(1500);

        ArgumentCaptor<UserVerification> argument = ArgumentCaptor.forClass(UserVerification.class);

        verify(database).updateUserVerification(argument.capture());

        assertThat(argument.getValue().securityMessage.token, is(""));
    }

}