package io.ariku.verification.api;

import com.googlecode.junittoolbox.ParallelRunner;
import io.ariku.util.data.User;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Ari Aaltonen
 */
@RunWith(ParallelRunner.class)
public class UserVerificationServiceTest {

    @Test
    public void user_is_logged_in_if_stored_UserVerification_says_so() {

        UserVerification storedUserVerification = new UserVerification();
        storedUserVerification.isLoggedIn = true;

        UserVerificationStore userVerificationStore = mock(UserVerificationStore.class);
        when(userVerificationStore.findUserVerification(any(User.class))).thenReturn(storedUserVerification);

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationStore = userVerificationStore;

        assertThat(userVerificationService.isUserLoggedIn(new User()), is(true));
    }

    @Test
    public void user_is_logged_out_if_stored_UserVerification_says_so() {

        UserVerification storedUserVerification = new UserVerification();
        storedUserVerification.isLoggedIn = false;

        UserVerificationStore userVerificationStore = mock(UserVerificationStore.class);
        when(userVerificationStore.findUserVerification(any(User.class))).thenReturn(storedUserVerification);

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationStore = userVerificationStore;

        assertThat(userVerificationService.isUserLoggedIn(new User()), is(false));
    }

    @Test
    public void user_is_signed_in_if_stored_UserVerification_says_so() {

        UserVerification storedUserVerification = new UserVerification();
        storedUserVerification.isSignedIn = true;

        UserVerificationStore userVerificationStore = mock(UserVerificationStore.class);
        when(userVerificationStore.findUserVerification(any(User.class))).thenReturn(storedUserVerification);

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationStore = userVerificationStore;

        assertThat(userVerificationService.isUserSignedIn(new User()), is(true));
    }

    @Test
    public void user_is_not_signed_in_if_stored_UserVerification_says_so() {

        UserVerification storedUserVerification = new UserVerification();
        storedUserVerification.isSignedIn = false;

        UserVerificationStore userVerificationStore = mock(UserVerificationStore.class);
        when(userVerificationStore.findUserVerification(any(User.class))).thenReturn(storedUserVerification);

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationStore = userVerificationStore;

        assertThat(userVerificationService.isUserSignedIn(new User()), is(false));
    }

    @Test
    public void user_is_signed_in_and_confirmed_if_UserVerification_says_so() {

        UserVerification storedUserVerification = new UserVerification();
        storedUserVerification.isSignedInConfirmed = true;

        UserVerificationStore userVerificationStore = mock(UserVerificationStore.class);
        when(userVerificationStore.findUserVerification(any(User.class))).thenReturn(storedUserVerification);

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationStore = userVerificationStore;

        assertThat(userVerificationService.isUserSignedInConfirmed(new User()), is(true));
    }

    @Test
    public void user_is_signed_in_but_not_confirmed_if_UserVerification_says_so() {

        UserVerification storedUserVerification = new UserVerification();
        storedUserVerification.isSignedInConfirmed = false;

        UserVerificationStore userVerificationStore = mock(UserVerificationStore.class);
        when(userVerificationStore.findUserVerification(any(User.class))).thenReturn(storedUserVerification);

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationStore = userVerificationStore;

        assertThat(userVerificationService.isUserSignedInConfirmed(new User()), is(false));
    }

    @Test
    public void user_can_signUp() {
        new UserVerificationService().signUp(new User());
    }

    @Test
    public void user_can_verifySignUp() {
        new UserVerificationService().verifySignUp();
    }

    @Test
    public void user_can_login() {
        new UserVerificationService().login(new User());
    }

    @Test
    public void user_can_logout() {
        new UserVerificationService().logout();
    }

}