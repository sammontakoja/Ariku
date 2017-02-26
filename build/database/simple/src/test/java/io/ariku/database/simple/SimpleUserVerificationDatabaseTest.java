package io.ariku.database.simple;

import com.googlecode.junittoolbox.ParallelRunner;
import io.ariku.verification.UserVerification;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.UUID;

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
        SimpleDatabase userVerificationServiceSimple = new SimpleDatabase();
        assertThat(userVerificationServiceSimple.readUserVerification(""), not(nullValue()));
    }

    @Test
    public void created_UserVerification_is_signedIn() {
        SimpleDatabase userVerificationServiceSimple = new SimpleDatabase();
        userVerificationServiceSimple.createUserVerification("123");
        UserVerification readUserVerification = userVerificationServiceSimple.readUserVerification("123");
        assertThat(readUserVerification.isSignedIn, is(true));
    }

    @Test
    public void created_UserVerification_can_be_found_when_using_same_userId() {
        SimpleDatabase userVerificationServiceSimple = new SimpleDatabase();
        userVerificationServiceSimple.createUserVerification("123");
        UserVerification readUserVerification = userVerificationServiceSimple.readUserVerification("123");
        assertThat(readUserVerification.userId, is("123"));
    }

    @Test
    public void deleted_UserVerification_cannot_be_found_when_using_same_userId() {
        SimpleDatabase userVerificationServiceSimple = new SimpleDatabase();
        userVerificationServiceSimple.createUserVerification("123");
        userVerificationServiceSimple.deleteUserVerification("123");
        UserVerification readUserVerification = userVerificationServiceSimple.readUserVerification("123");
        assertThat(readUserVerification.userId, not("123"));
    }

    @Test
    public void UserVerification_values_change_when_using_update_with_same_userId() {
        SimpleDatabase userVerificationServiceSimple = new SimpleDatabase();

        UserVerification userVerification = new UserVerification();
        userVerification.userId = "123";
        userVerification.isSignedIn = true;
        userVerification.securityMessage.token = UUID.randomUUID().toString();
        userVerification.isSignedInConfirmed = true;

        userVerificationServiceSimple.createUserVerification("123");
        userVerificationServiceSimple.updateUserVerification(userVerification);

        UserVerification readUserVerification = userVerificationServiceSimple.readUserVerification("123");

        assertThat(readUserVerification.userId, is("123"));
        assertThat(readUserVerification.securityMessage, is(userVerification.securityMessage));
        assertThat(readUserVerification.isSignedIn, is(true));
        assertThat(readUserVerification.isSignedInConfirmed, is(true));
    }


}