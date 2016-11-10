package io.ariku.verification.api;

import com.googlecode.junittoolbox.ParallelRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Ari Aaltonen
 */
@RunWith(ParallelRunner.class)
public class UserVerificationServiceTest {

    @Test
    public void verify_sign_up_fail_when_user_is_found_but_not_signed_in() {

        UserVerification storedUserVerification = new UserVerification();
        storedUserVerification.isFound = true;
        storedUserVerification.isSignedIn = false;

        UserVerificationCRUD userVerificationCRUD = mock(UserVerificationCRUD.class);
        when(userVerificationCRUD.readUserVerification(anyString())).thenReturn(storedUserVerification);

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationCRUD = userVerificationCRUD;

        boolean successful = userVerificationService.verifySignUp(new VerifySignUpRequest());

        assertThat(successful, is(false));
    }

    @Test
    public void verify_sign_up_fail_when_user_is_not_found() {

        UserVerification storedUserVerification = new UserVerification();
        storedUserVerification.isFound = false;

        UserVerificationCRUD userVerificationCRUD = mock(UserVerificationCRUD.class);
        when(userVerificationCRUD.readUserVerification(anyString())).thenReturn(storedUserVerification);

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationCRUD = userVerificationCRUD;

        boolean successful = userVerificationService.verifySignUp(new VerifySignUpRequest());

        assertThat(successful, is(false));
    }

    @Test
    public void verify_sign_up_successful_when_user_is_found_and_signed_in() {

        UserVerification storedUserVerification = new UserVerification();
        storedUserVerification.isFound = true;
        storedUserVerification.isSignedIn = true;

        UserVerificationCRUD userVerificationCRUD = mock(UserVerificationCRUD.class);
        when(userVerificationCRUD.readUserVerification(anyString())).thenReturn(storedUserVerification);

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationCRUD = userVerificationCRUD;

        boolean successful = userVerificationService.verifySignUp(new VerifySignUpRequest());

        assertThat(successful, is(true));
    }

    @Test
    public void sign_up_fail_when_user_is_found() {

        UserVerification storedUserVerification = new UserVerification();
        storedUserVerification.isFound = true;

        UserVerificationCRUD userVerificationCRUD = mock(UserVerificationCRUD.class);
        when(userVerificationCRUD.readUserVerification(anyString())).thenReturn(storedUserVerification);

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationCRUD = userVerificationCRUD;

        boolean successful = userVerificationService.signUp(new SignUpRequest());

        assertThat(successful, is(false));
    }

    @Test
    public void sign_up_is_successful_when_user_is_not_found() {

        UserVerification storedUserVerification = new UserVerification();
        storedUserVerification.isFound = false;

        UserVerificationCRUD userVerificationCRUD = mock(UserVerificationCRUD.class);
        when(userVerificationCRUD.readUserVerification(anyString())).thenReturn(storedUserVerification);

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationCRUD = userVerificationCRUD;

        boolean successful = userVerificationService.signUp(new SignUpRequest());

        assertThat(successful, is(true));
    }

    @Test
    public void logout_is_successful_when_user_is_found_but_not_logged_in() {

        UserVerification storedUserVerification = new UserVerification();
        storedUserVerification.isFound = true;
        storedUserVerification.isLoggedIn = false;

        UserVerificationCRUD userVerificationCRUD = mock(UserVerificationCRUD.class);
        when(userVerificationCRUD.readUserVerification(anyString())).thenReturn(storedUserVerification);

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationCRUD = userVerificationCRUD;

        boolean successful = userVerificationService.logout(new LogoutRequest());

        assertThat(successful, is(false));
    }

    @Test
    public void logout_is_successful_when_user_is_not_found() {

        UserVerification storedUserVerification = new UserVerification();
        storedUserVerification.isFound = false;
        storedUserVerification.isLoggedIn = true;

        UserVerificationCRUD userVerificationCRUD = mock(UserVerificationCRUD.class);
        when(userVerificationCRUD.readUserVerification(anyString())).thenReturn(storedUserVerification);

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationCRUD = userVerificationCRUD;

        boolean successful = userVerificationService.logout(new LogoutRequest());

        assertThat(successful, is(false));
    }

    @Test
    public void logout_is_successful_when_user_is_found_and_logged_in() {

        UserVerification storedUserVerification = new UserVerification();
        storedUserVerification.isFound = true;
        storedUserVerification.isLoggedIn = true;

        UserVerificationCRUD userVerificationCRUD = mock(UserVerificationCRUD.class);
        when(userVerificationCRUD.readUserVerification(anyString())).thenReturn(storedUserVerification);

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationCRUD = userVerificationCRUD;

        boolean successful = userVerificationService.logout(new LogoutRequest());

        assertThat(successful, is(true));
    }

    @Test
    public void login_is_failed_when_user_found_but_Signed_in_not_confirmed() {

        UserVerification storedUserVerification = new UserVerification();
        storedUserVerification.isFound = true;
        storedUserVerification.isSignedIn = true;
        storedUserVerification.isSignedInConfirmed = false;

        UserVerificationCRUD userVerificationCRUD = mock(UserVerificationCRUD.class);
        when(userVerificationCRUD.readUserVerification(anyString())).thenReturn(storedUserVerification);

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationCRUD = userVerificationCRUD;

        LoginRequest loginRequest = new LoginRequest();
        boolean successful = userVerificationService.login(loginRequest);

        assertThat(successful, is(false));
    }

    @Test
    public void login_is_failed_when_user_found_but_not_SignedIn() {

        UserVerification storedUserVerification = new UserVerification();
        storedUserVerification.isFound = true;
        storedUserVerification.isSignedIn = false;
        storedUserVerification.isSignedInConfirmed = true;

        UserVerificationCRUD userVerificationCRUD = mock(UserVerificationCRUD.class);
        when(userVerificationCRUD.readUserVerification(anyString())).thenReturn(storedUserVerification);

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationCRUD = userVerificationCRUD;

        LoginRequest loginRequest = new LoginRequest();
        boolean successful = userVerificationService.login(loginRequest);

        assertThat(successful, is(false));
    }

    @Test
    public void login_is_failed_when_user_is_not_found() {

        UserVerification storedUserVerification = new UserVerification();
        storedUserVerification.isFound = false;
        storedUserVerification.isSignedIn = true;
        storedUserVerification.isSignedInConfirmed = true;

        UserVerificationCRUD userVerificationCRUD = mock(UserVerificationCRUD.class);
        when(userVerificationCRUD.readUserVerification(anyString())).thenReturn(storedUserVerification);

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationCRUD = userVerificationCRUD;

        LoginRequest loginRequest = new LoginRequest();
        boolean successful = userVerificationService.login(loginRequest);

        assertThat(successful, is(false));
    }

    @Test
    public void login_is_successful_when_user_is_found_and_signed_in() {

        UserVerification storedUserVerification = new UserVerification();
        storedUserVerification.isFound = true;
        storedUserVerification.isSignedIn = true;
        storedUserVerification.isSignedInConfirmed = true;

        UserVerificationCRUD userVerificationCRUD = mock(UserVerificationCRUD.class);
        when(userVerificationCRUD.readUserVerification(anyString())).thenReturn(storedUserVerification);

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationCRUD = userVerificationCRUD;

        LoginRequest loginRequest = new LoginRequest();
        boolean successful = userVerificationService.login(loginRequest);

        assertThat(successful, is(true));
    }

    @Test
    public void is_logged_in_if_stored_UserVerification_says_so() {

        UserVerification storedUserVerification = new UserVerification();
        storedUserVerification.isLoggedIn = true;

        UserVerificationCRUD userVerificationCRUD = mock(UserVerificationCRUD.class);
        when(userVerificationCRUD.readUserVerification(anyString())).thenReturn(storedUserVerification);

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationCRUD = userVerificationCRUD;

        assertThat(userVerificationService.isUserLoggedIn(""), is(true));
    }

    @Test
    public void is_logged_out_if_stored_UserVerification_says_so() {

        UserVerification storedUserVerification = new UserVerification();
        storedUserVerification.isLoggedIn = false;

        UserVerificationCRUD userVerificationCRUD = mock(UserVerificationCRUD.class);
        when(userVerificationCRUD.readUserVerification(anyString())).thenReturn(storedUserVerification);

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationCRUD = userVerificationCRUD;

        assertThat(userVerificationService.isUserLoggedIn(""), is(false));
    }

    @Test
    public void is_signed_in_if_stored_UserVerification_says_so() {

        UserVerification storedUserVerification = new UserVerification();
        storedUserVerification.isSignedIn = true;

        UserVerificationCRUD userVerificationCRUD = mock(UserVerificationCRUD.class);
        when(userVerificationCRUD.readUserVerification(anyString())).thenReturn(storedUserVerification);

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationCRUD = userVerificationCRUD;

        assertThat(userVerificationService.isUserSignedIn(""), is(true));
    }

    @Test
    public void is_not_signed_in_if_stored_UserVerification_says_so() {

        UserVerification storedUserVerification = new UserVerification();
        storedUserVerification.isSignedIn = false;

        UserVerificationCRUD userVerificationCRUD = mock(UserVerificationCRUD.class);
        when(userVerificationCRUD.readUserVerification(anyString())).thenReturn(storedUserVerification);

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationCRUD = userVerificationCRUD;

        assertThat(userVerificationService.isUserSignedIn(""), is(false));
    }

    @Test
    public void is_signed_in_and_confirmed_if_User_found_by_UserVerification() {

        UserVerification storedUserVerification = new UserVerification();
        storedUserVerification.isSignedInConfirmed = true;

        UserVerificationCRUD userVerificationCRUD = mock(UserVerificationCRUD.class);
        when(userVerificationCRUD.readUserVerification(anyString())).thenReturn(storedUserVerification);

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationCRUD = userVerificationCRUD;

        assertThat(userVerificationService.isUserSignedInConfirmed(""), is(true));
    }

    @Test
    public void is_signed_in_but_not_confirmed_if_UserVerification_says_so() {

        UserVerification storedUserVerification = new UserVerification();
        storedUserVerification.isSignedInConfirmed = false;

        UserVerificationCRUD userVerificationCRUD = mock(UserVerificationCRUD.class);
        when(userVerificationCRUD.readUserVerification(anyString())).thenReturn(storedUserVerification);

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationCRUD = userVerificationCRUD;

        assertThat(userVerificationService.isUserSignedInConfirmed(""), is(false));
    }

}