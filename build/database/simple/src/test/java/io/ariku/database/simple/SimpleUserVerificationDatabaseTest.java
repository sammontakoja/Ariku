package io.ariku.database.simple;

import com.googlecode.junittoolbox.ParallelRunner;
import io.ariku.verification.UserVerification;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Ari Aaltonen
 */
@RunWith(ParallelRunner.class)
public class SimpleUserVerificationDatabaseTest {

    @Test
    public void userVerification_is_not_null() {
        SimpleDatabase userVerificationServiceSimple = new SimpleDatabase();
        assertThat(userVerificationServiceSimple.findByUserId(""), not(nullValue()));
    }

    @Test
    public void created_UserVerification_is_not_confirmed_signed_in() {
        SimpleDatabase userVerificationServiceSimple = new SimpleDatabase();
        userVerificationServiceSimple.createUserVerification("123");
        UserVerification userVerification = userVerificationServiceSimple.findByUsername("123").get();
        assertThat(userVerification.isSignedInConfirmed, is(false));
    }

    @Test
    public void deleted_UserVerification_cannot_be_found_when_using_same_userId() {
        SimpleDatabase userVerificationServiceSimple = new SimpleDatabase();
        userVerificationServiceSimple.createUserVerification("123");
        userVerificationServiceSimple.deleteUserVerification("123");
        assertThat(userVerificationServiceSimple.findByUserId("123").isPresent(), is(false));
    }

    @Test
    public void UserVerification_values_change_when_using_update_with_username() {
        SimpleDatabase userVerificationServiceSimple = new SimpleDatabase();

        userVerificationServiceSimple.createUserVerification("jippii");

        UserVerification readUserVerification = userVerificationServiceSimple.findByUsername("jippii").get();


        UserVerification userVerification = new UserVerification();
        userVerification.userId = readUserVerification.userId;
        userVerification.username = "jippii";
        userVerification.securityMessage.token = UUID.randomUUID().toString();
        userVerification.isSignedInConfirmed = true;

        userVerificationServiceSimple.updateUserVerification(userVerification);

        UserVerification updatedUserVerification = userVerificationServiceSimple.findByUserId(readUserVerification.userId).get();

        assertThat(updatedUserVerification.userId.isEmpty(), is(false));
        assertThat(updatedUserVerification.username, is("jippii"));
        assertThat(readUserVerification.securityMessage, is(userVerification.securityMessage));
        assertThat(readUserVerification.isSignedInConfirmed, is(true));
    }


}