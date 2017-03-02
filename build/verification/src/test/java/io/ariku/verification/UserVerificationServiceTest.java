package io.ariku.verification;

import com.googlecode.junittoolbox.ParallelRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @author Ari Aaltonen
 */
@RunWith(ParallelRunner.class)
public class UserVerificationServiceTest {

    @Test
    public void sign_up_verification_ok_when_found_stored_UserVerification_with_same_username() {

        UserVerification userVerification = new UserVerification();
        userVerification.username = UUID.randomUUID().toString();

        UserVerificationDatabase userVerificationDatabase = mock(UserVerificationDatabase.class);
        when(userVerificationDatabase.findByUserId(anyString())).thenReturn(Optional.of(userVerification));

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationDatabase = userVerificationDatabase;

        VerifySignUpRequest request = new VerifySignUpRequest();
        request.username = userVerification.username;

        userVerificationService.verifySignUp(request);

        verify(userVerificationDatabase, atLeastOnce()).updateUserVerification(any());
    }

    @Test
    public void sign_up_fail_when_username_already_taken() {

        UserVerification userVerification = new UserVerification();
        userVerification.username = "x";

        UserVerificationDatabase userVerificationDatabase = mock(UserVerificationDatabase.class);
        when(userVerificationDatabase.findByUserId(anyString())).thenReturn(Optional.of(userVerification));

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationDatabase = userVerificationDatabase;

        boolean signUpSuccessful = userVerificationService.signUp(new SignUpRequest("x"));

        assertThat(signUpSuccessful, is(false));
    }

    @Test
    public void sign_up_is_successful_when_username_is_not_used() {

        UserVerification userVerification = new UserVerification();

        UserVerificationDatabase userVerificationDatabase = mock(UserVerificationDatabase.class);
        when(userVerificationDatabase.findByUsername(anyString())).thenReturn(Optional.of(userVerification));

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationDatabase = userVerificationDatabase;

        boolean successful = userVerificationService.signUp(new SignUpRequest());

        assertThat(successful, is(true));
    }

    @Test
    public void logout_is_successful_when_given_securityMessage_match_with_stored_one() {

        UserVerification userVerification = new UserVerification();
        userVerification.securityMessage.token = UUID.randomUUID().toString();

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationDatabase = mock(UserVerificationDatabase.class);

        String userId = UUID.randomUUID().toString();

        when(userVerificationService.userVerificationDatabase.findByUserId(userId)).thenReturn(Optional.of(userVerification));

        userVerificationService.logout(new AuthorizeRequest(userId, userVerification.securityMessage.token));

        verify(userVerificationService.userVerificationDatabase, atLeastOnce()).updateUserVerification(any());
    }

    @Test
    public void logout_is_not_done_when_UserVerification_is_not_found() {

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationDatabase = mock(UserVerificationDatabase.class);

        when(userVerificationService.userVerificationDatabase.findByUserId(anyString())).thenReturn(Optional.empty());

        userVerificationService.logout(new AuthorizeRequest());

        verify(userVerificationService.userVerificationDatabase, never()).updateUserVerification(any());
    }

    @Test
    public void logout_fails_when_user_is_found_but_logged_in_with_different_securityMessage() {

        UserVerification userVerification = new UserVerification();
        userVerification.userId = "a";
        userVerification.isSignedInConfirmed = true;
        userVerification.securityMessage.token = UUID.randomUUID().toString();

        UserVerificationDatabase userVerificationDatabase = mock(UserVerificationDatabase.class);
        when(userVerificationDatabase.findByUserId(anyString())).thenReturn(Optional.of(userVerification));

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationDatabase = userVerificationDatabase;

        userVerificationService.logout(new AuthorizeRequest(userVerification.userId, "notSameSecurityMessage"));

        verify(userVerificationService.userVerificationDatabase, never()).updateUserVerification(any());
    }

    @Test
    public void login_is_failed_when_user_found_but_Signed_in_not_confirmed() {

        UserVerification userVerification = new UserVerification();
        userVerification.isSignedInConfirmed = false;

        UserVerificationDatabase userVerificationDatabase = mock(UserVerificationDatabase.class);
        when(userVerificationDatabase.findByUserId(anyString())).thenReturn(Optional.of(userVerification));

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationDatabase = userVerificationDatabase;

        LoginRequest loginRequest = new LoginRequest();

        String securityMessage = userVerificationService.login(loginRequest);

        assertThat(securityMessage.isEmpty(), is(true));
    }

    @Test
    public void login_is_failed_when_user_found_but_not_SignedIn() {

        UserVerification userVerification = new UserVerification();
        userVerification.isSignedInConfirmed = true;

        UserVerificationDatabase userVerificationDatabase = mock(UserVerificationDatabase.class);
        when(userVerificationDatabase.findByUserId(anyString())).thenReturn(Optional.of(userVerification));

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationDatabase = userVerificationDatabase;

        LoginRequest loginRequest = new LoginRequest();

        String securityMessage = userVerificationService.login(loginRequest);

        assertThat(securityMessage.isEmpty(), is(true));
    }

    @Test
    public void login_is_failed_when_user_is_not_found() {

        UserVerification userVerification = new UserVerification();
        userVerification.isSignedInConfirmed = true;

        UserVerificationDatabase userVerificationDatabase = mock(UserVerificationDatabase.class);
        when(userVerificationDatabase.findByUserId(anyString())).thenReturn(Optional.of(userVerification));

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationDatabase = userVerificationDatabase;

        LoginRequest loginRequest = new LoginRequest();

        String securityMessage = userVerificationService.login(loginRequest);

        assertThat(securityMessage.isEmpty(), is(true));
    }

    @Test
    public void login_is_successful_when_user_found_by_username_and_which_has_signed_in_confirmed() {

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationDatabase = mock(UserVerificationDatabase.class);

        UserVerification userVerification = new UserVerification();
        userVerification.username = UUID.randomUUID().toString();
        userVerification.userId = UUID.randomUUID().toString();
        userVerification.isSignedInConfirmed = true;

        when(userVerificationService.userVerificationDatabase.findByUsername(userVerification.username))
                .thenReturn(Optional.of(userVerification));

        String securityMessage = userVerificationService.login(new LoginRequest(userVerification.username));

        verify(userVerificationService.userVerificationDatabase, atLeastOnce()).updateUserVerification(any());

        assertThat(securityMessage.isEmpty(), is(false));
    }

    @Test
    public void is_signed_in_and_confirmed_if_User_found_by_UserVerification() {

        UserVerification userVerification = new UserVerification();
        userVerification.isSignedInConfirmed = true;

        UserVerificationDatabase userVerificationDatabase = mock(UserVerificationDatabase.class);
        when(userVerificationDatabase.findByUserId(anyString())).thenReturn(Optional.of(userVerification));

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationDatabase = userVerificationDatabase;

        assertThat(userVerificationService.isUserSignedInConfirmed(""), is(true));
    }

    @Test
    public void is_signed_in_but_not_confirmed_if_UserVerification_says_so() {

        UserVerification userVerification = new UserVerification();
        userVerification.isSignedInConfirmed = false;

        UserVerificationDatabase userVerificationDatabase = mock(UserVerificationDatabase.class);
        when(userVerificationDatabase.findByUserId(anyString())).thenReturn(Optional.of(userVerification));

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationDatabase = userVerificationDatabase;

        assertThat(userVerificationService.isUserSignedInConfirmed(""), is(false));
    }

    @Test
    public void user_is_authorized_when_given_userId_and_security_token_match() {

        UserVerification userVerification = new UserVerification();
        userVerification.userId = "a@a.com";
        userVerification.securityMessage.token = UUID.randomUUID().toString();

        UserVerificationDatabase userVerificationDatabase = mock(UserVerificationDatabase.class);
        when(userVerificationDatabase.findByUserId(anyString())).thenReturn(Optional.of(userVerification));

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationDatabase = userVerificationDatabase;

        AuthorizeRequest authorizeRequest = new AuthorizeRequest();
        authorizeRequest.securityMessage = userVerification.securityMessage.token;
        authorizeRequest.userId = userVerification.userId;

        boolean userAuthorized = userVerificationService.isAuthorized(authorizeRequest);

        assertThat(userAuthorized, is(true));
    }

    @Test
    public void user_is_not_authorized_when_given_security_token_do_not_match() {

        UserVerification userVerification = new UserVerification();
        userVerification.userId = "a@a.com";
        userVerification.securityMessage.token = UUID.randomUUID().toString();

        UserVerificationDatabase userVerificationDatabase = mock(UserVerificationDatabase.class);
        when(userVerificationDatabase.findByUserId(anyString())).thenReturn(Optional.of(userVerification));

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationDatabase = userVerificationDatabase;

        AuthorizeRequest authorizeRequest = new AuthorizeRequest();
        authorizeRequest.securityMessage = "securityToken";
        authorizeRequest.userId = "username";

        boolean userAuthorized = userVerificationService.isAuthorized(authorizeRequest);

        assertThat(userAuthorized, is(false));
    }

    @Test
    public void user_is_not_authorized_when_UserVerification_not_found_with_given_userId() {

        UserVerificationDatabase userVerificationDatabase = mock(UserVerificationDatabase.class);
        when(userVerificationDatabase.findByUserId(anyString())).thenReturn(Optional.empty());

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationDatabase = userVerificationDatabase;

        AuthorizeRequest authorizeRequest = new AuthorizeRequest();
        authorizeRequest.securityMessage = "securityToken";
        authorizeRequest.userId = "username";

        assertThat(userVerificationService.isAuthorized(authorizeRequest), is(false));
    }

}