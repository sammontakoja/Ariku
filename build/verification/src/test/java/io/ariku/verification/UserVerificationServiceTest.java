package io.ariku.verification;

import com.googlecode.junittoolbox.ParallelRunner;
import io.ariku.verification.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.UUID;

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
        storedUserVerification.isSignedIn = false;

        UserVerificationDatabase userVerificationDatabase = mock(UserVerificationDatabase.class);
        when(userVerificationDatabase.readUserVerification(anyString())).thenReturn(storedUserVerification);

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationDatabase = userVerificationDatabase;

        boolean successful = userVerificationService.verifySignUp(new VerifySignUpRequest());

        assertThat(successful, is(false));
    }

    @Test
    public void verify_sign_up_fail_when_UserVerification_lack_userId() {

        UserVerification storedUserVerification = new UserVerification();
        storedUserVerification.userId = "";

        UserVerificationDatabase userVerificationDatabase = mock(UserVerificationDatabase.class);
        when(userVerificationDatabase.readUserVerification(anyString())).thenReturn(storedUserVerification);

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationDatabase = userVerificationDatabase;

        boolean successful = userVerificationService.verifySignUp(new VerifySignUpRequest());

        assertThat(successful, is(false));
    }

    @Test
    public void verify_sign_up_successful_when_user_is_found_and_signed_in() {

        UserVerification userVerification = new UserVerification();
        userVerification.userId = "X";
        userVerification.isSignedIn = true;

        UserVerificationDatabase userVerificationDatabase = mock(UserVerificationDatabase.class);
        when(userVerificationDatabase.readUserVerification(anyString())).thenReturn(userVerification);

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationDatabase = userVerificationDatabase;

        boolean successful = userVerificationService.verifySignUp(new VerifySignUpRequest());

        assertThat(successful, is(true));
    }

    @Test
    public void sign_up_fail_when_userId_already_taken() {

        UserVerification userVerification = new UserVerification();
        userVerification.userId = "x";

        UserVerificationDatabase userVerificationDatabase = mock(UserVerificationDatabase.class);
        when(userVerificationDatabase.readUserVerification(anyString())).thenReturn(userVerification);

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationDatabase = userVerificationDatabase;

        boolean successful = userVerificationService.signUp(new SignUpRequest("x"));

        assertThat(successful, is(false));
    }

    @Test
    public void sign_up_is_successful_when_user_is_not_found() {

        UserVerification storedUserVerification = new UserVerification();

        UserVerificationDatabase userVerificationDatabase = mock(UserVerificationDatabase.class);
        when(userVerificationDatabase.readUserVerification(anyString())).thenReturn(storedUserVerification);

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationDatabase = userVerificationDatabase;

        boolean successful = userVerificationService.signUp(new SignUpRequest());

        assertThat(successful, is(true));
    }

    @Test
    public void logout_is_successful_when_user_is_found_but_not_logged_in() {

        UserVerification storedUserVerification = new UserVerification();

        UserVerificationDatabase userVerificationDatabase = mock(UserVerificationDatabase.class);
        when(userVerificationDatabase.readUserVerification(anyString())).thenReturn(storedUserVerification);

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationDatabase = userVerificationDatabase;

        boolean successful = userVerificationService.logout(new LogoutRequest("a"));

        assertThat(successful, is(false));
    }

    @Test
    public void logout_fail_when_user_is_not_found() {

        UserVerification storedUserVerification = new UserVerification();

        UserVerificationDatabase userVerificationDatabase = mock(UserVerificationDatabase.class);
        when(userVerificationDatabase.readUserVerification(anyString())).thenReturn(storedUserVerification);

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationDatabase = userVerificationDatabase;

        boolean successful = userVerificationService.logout(new LogoutRequest());

        assertThat(successful, is(false));
    }

    @Test
    public void logout_is_successful_when_user_is_found_and_logged_in_with_given_securityMessage() {

        UserVerification userVerification = new UserVerification();
        userVerification.userId = "a";
        userVerification.isSignedInConfirmed = true;
        userVerification.isSignedIn = true;
        userVerification.securityMessage.token = UUID.randomUUID().toString();

        UserVerificationDatabase userVerificationDatabase = mock(UserVerificationDatabase.class);
        when(userVerificationDatabase.readUserVerification(anyString())).thenReturn(userVerification);

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationDatabase = userVerificationDatabase;

        boolean successful = userVerificationService.logout(new LogoutRequest(userVerification.userId, userVerification.securityMessage.token));

        assertThat(successful, is(true));
    }

    @Test
    public void logout_fails_when_user_is_found_but_logged_in_with_different_securityMessage() {

        UserVerification userVerification = new UserVerification();
        userVerification.userId = "a";
        userVerification.isSignedInConfirmed = true;
        userVerification.isSignedIn = true;
        userVerification.securityMessage.token = UUID.randomUUID().toString();

        UserVerificationDatabase userVerificationDatabase = mock(UserVerificationDatabase.class);
        when(userVerificationDatabase.readUserVerification(anyString())).thenReturn(userVerification);

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationDatabase = userVerificationDatabase;

        boolean successful = userVerificationService.logout(new LogoutRequest(userVerification.userId, "notSameSecurityMessage"));

        assertThat(successful, is(false));
    }

    @Test
    public void login_is_failed_when_user_found_but_Signed_in_not_confirmed() {

        UserVerification storedUserVerification = new UserVerification();
        storedUserVerification.isSignedIn = true;
        storedUserVerification.isSignedInConfirmed = false;

        UserVerificationDatabase userVerificationDatabase = mock(UserVerificationDatabase.class);
        when(userVerificationDatabase.readUserVerification(anyString())).thenReturn(storedUserVerification);

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationDatabase = userVerificationDatabase;

        LoginRequest loginRequest = new LoginRequest();

        String securityMessage = userVerificationService.login(loginRequest);

        assertThat(securityMessage.isEmpty(), is(true));
    }

    @Test
    public void login_is_failed_when_user_found_but_not_SignedIn() {

        UserVerification storedUserVerification = new UserVerification();
        storedUserVerification.isSignedIn = false;
        storedUserVerification.isSignedInConfirmed = true;

        UserVerificationDatabase userVerificationDatabase = mock(UserVerificationDatabase.class);
        when(userVerificationDatabase.readUserVerification(anyString())).thenReturn(storedUserVerification);

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationDatabase = userVerificationDatabase;

        LoginRequest loginRequest = new LoginRequest();

        String securityMessage = userVerificationService.login(loginRequest);

        assertThat(securityMessage.isEmpty(), is(true));
    }

    @Test
    public void login_is_failed_when_user_is_not_found() {

        UserVerification storedUserVerification = new UserVerification();
        storedUserVerification.isSignedIn = true;
        storedUserVerification.isSignedInConfirmed = true;

        UserVerificationDatabase userVerificationDatabase = mock(UserVerificationDatabase.class);
        when(userVerificationDatabase.readUserVerification(anyString())).thenReturn(storedUserVerification);

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationDatabase = userVerificationDatabase;

        LoginRequest loginRequest = new LoginRequest();

        String securityMessage = userVerificationService.login(loginRequest);

        assertThat(securityMessage.isEmpty(), is(true));
    }

    @Test
    public void login_is_successful_when_user_found_and_signed_in_and_signed_in_confirmed() {

        UserVerification storedUserVerification = new UserVerification();
        storedUserVerification.userId = "a";
        storedUserVerification.isSignedIn = true;
        storedUserVerification.isSignedInConfirmed = true;

        UserVerificationDatabase userVerificationDatabase = mock(UserVerificationDatabase.class);
        when(userVerificationDatabase.readUserVerification(anyString())).thenReturn(storedUserVerification);

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationDatabase = userVerificationDatabase;

        String securityMessage = userVerificationService.login(new LoginRequest("a"));

        assertThat(securityMessage.isEmpty(), is(false));
    }

    @Test
    public void is_signed_in_if_stored_UserVerification_says_so() {

        UserVerification storedUserVerification = new UserVerification();
        storedUserVerification.isSignedIn = true;

        UserVerificationDatabase userVerificationDatabase = mock(UserVerificationDatabase.class);
        when(userVerificationDatabase.readUserVerification(anyString())).thenReturn(storedUserVerification);

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationDatabase = userVerificationDatabase;

        assertThat(userVerificationService.isUserSignedIn(""), is(true));
    }

    @Test
    public void is_not_signed_in_if_stored_UserVerification_says_so() {

        UserVerification storedUserVerification = new UserVerification();
        storedUserVerification.isSignedIn = false;

        UserVerificationDatabase userVerificationDatabase = mock(UserVerificationDatabase.class);
        when(userVerificationDatabase.readUserVerification(anyString())).thenReturn(storedUserVerification);

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationDatabase = userVerificationDatabase;

        assertThat(userVerificationService.isUserSignedIn(""), is(false));
    }

    @Test
    public void is_signed_in_and_confirmed_if_User_found_by_UserVerification() {

        UserVerification storedUserVerification = new UserVerification();
        storedUserVerification.isSignedInConfirmed = true;

        UserVerificationDatabase userVerificationDatabase = mock(UserVerificationDatabase.class);
        when(userVerificationDatabase.readUserVerification(anyString())).thenReturn(storedUserVerification);

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationDatabase = userVerificationDatabase;

        assertThat(userVerificationService.isUserSignedInConfirmed(""), is(true));
    }

    @Test
    public void is_signed_in_but_not_confirmed_if_UserVerification_says_so() {

        UserVerification storedUserVerification = new UserVerification();
        storedUserVerification.isSignedInConfirmed = false;

        UserVerificationDatabase userVerificationDatabase = mock(UserVerificationDatabase.class);
        when(userVerificationDatabase.readUserVerification(anyString())).thenReturn(storedUserVerification);

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
        when(userVerificationDatabase.readUserVerification(anyString())).thenReturn(userVerification);

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
        when(userVerificationDatabase.readUserVerification(anyString())).thenReturn(userVerification);

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationDatabase = userVerificationDatabase;

        AuthorizeRequest authorizeRequest = new AuthorizeRequest();
        authorizeRequest.securityMessage = "securityToken";
        authorizeRequest.userId = "userId";

        boolean userAuthorized = userVerificationService.isAuthorized(authorizeRequest);

        assertThat(userAuthorized, is(false));
    }

    @Test
    public void user_is_not_authorized_when_UserVerification_not_found_with_given_userId() {

        UserVerificationDatabase userVerificationDatabase = mock(UserVerificationDatabase.class);
        when(userVerificationDatabase.readUserVerification(anyString())).thenReturn(null);

        UserVerificationService userVerificationService = new UserVerificationService();
        userVerificationService.userVerificationDatabase = userVerificationDatabase;

        AuthorizeRequest authorizeRequest = new AuthorizeRequest();
        authorizeRequest.securityMessage = "securityToken";
        authorizeRequest.userId = "userId";

        boolean userAuthorized = userVerificationService.isAuthorized(authorizeRequest);

        assertThat(userAuthorized, is(false));
    }

}