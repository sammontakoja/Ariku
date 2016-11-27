package io.ariku.verification.simple;

import com.googlecode.junittoolbox.ParallelRunner;
import io.ariku.verification.api.UserVerification;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Ari Aaltonen
 */
@RunWith(ParallelRunner.class)
public class SimpleUserVerificationDatabaseTest {

    @Test
    public void userVerification_is_not_null() {
        SimpleUserVerificationDatabase userVerificationServiceSimple = new SimpleUserVerificationDatabase();
        assertThat(userVerificationServiceSimple.readUserVerification(""), not(nullValue()));
    }

    @Test
    public void created_UserVerification_is_signedIn() {
        SimpleUserVerificationDatabase userVerificationServiceSimple = new SimpleUserVerificationDatabase();
        userVerificationServiceSimple.createUserVerification("123");
        UserVerification readUserVerification = userVerificationServiceSimple.readUserVerification("123");
        assertThat(readUserVerification.isSignedIn, is(true));
    }

    @Test
    public void created_UserVerification_can_be_found_when_using_same_userId() {
        SimpleUserVerificationDatabase userVerificationServiceSimple = new SimpleUserVerificationDatabase();
        userVerificationServiceSimple.createUserVerification("123");
        UserVerification readUserVerification = userVerificationServiceSimple.readUserVerification("123");
        assertThat(readUserVerification.userId, is("123"));
    }

    @Test
    public void deleted_UserVerification_cannot_be_found_when_using_same_userId() {
        SimpleUserVerificationDatabase userVerificationServiceSimple = new SimpleUserVerificationDatabase();
        userVerificationServiceSimple.createUserVerification("123");
        userVerificationServiceSimple.deleteUserVerification("123");
        UserVerification readUserVerification = userVerificationServiceSimple.readUserVerification("123");
        assertThat(readUserVerification.userId, not("123"));
    }

    @Test
    public void UserVerification_values_change_when_using_update_with_same_userId() {
        SimpleUserVerificationDatabase userVerificationServiceSimple = new SimpleUserVerificationDatabase();

        UserVerification userVerification = new UserVerification();
        userVerification.userId = "123";
        userVerification.isSignedIn = true;
        userVerification.isLoggedIn = true;
        userVerification.isSignedInConfirmed = true;

        userVerificationServiceSimple.createUserVerification("123");
        userVerificationServiceSimple.updateUserVerification(userVerification);

        UserVerification readUserVerification = userVerificationServiceSimple.readUserVerification("123");

        assertThat(readUserVerification.userId, is("123"));
        assertThat(readUserVerification.isLoggedIn, is(true));
        assertThat(readUserVerification.isSignedIn, is(true));
        assertThat(readUserVerification.isSignedInConfirmed, is(true));
    }


}