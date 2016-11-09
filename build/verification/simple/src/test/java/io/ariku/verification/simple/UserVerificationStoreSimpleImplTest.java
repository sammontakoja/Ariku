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
public class UserVerificationStoreSimpleImplTest {

    @Test
    public void userVerification_is_not_null() {
        UserVerificationStoreSimpleImpl userVerificationServiceSimple = new UserVerificationStoreSimpleImpl();
        assertThat(userVerificationServiceSimple.readUserVerification(""), not(nullValue()));
    }

    @Test
    public void created_UserVerification_can_be_found_when_using_same_userId() {
        UserVerificationStoreSimpleImpl userVerificationServiceSimple = new UserVerificationStoreSimpleImpl();
        userVerificationServiceSimple.createUserVerification("123");
        UserVerification readUserVerification = userVerificationServiceSimple.readUserVerification("123");
        assertThat(readUserVerification.isFound, is(true));
    }

    @Test
    public void deleted_UserVerification_cannot_be_found_when_using_same_userId() {
        UserVerificationStoreSimpleImpl userVerificationServiceSimple = new UserVerificationStoreSimpleImpl();
        userVerificationServiceSimple.createUserVerification("123");
        userVerificationServiceSimple.deleteUserVerification("123");
        UserVerification readUserVerification = userVerificationServiceSimple.readUserVerification("123");
        assertThat(readUserVerification.isFound, is(false));
    }

    @Test
    public void UserVerification_values_change_when_using_update_with_same_userId() {
        UserVerificationStoreSimpleImpl userVerificationServiceSimple = new UserVerificationStoreSimpleImpl();

        UserVerification userVerification = new UserVerification();
        userVerification.isFound = true;
        userVerification.isSignedIn = true;
        userVerification.isLoggedIn = true;
        userVerification.isSignedInConfirmed = true;

        userVerificationServiceSimple.createUserVerification("123");
        userVerificationServiceSimple.updateUserVerification("123", userVerification);

        UserVerification readUserVerification = userVerificationServiceSimple.readUserVerification("123");

        assertThat(readUserVerification.isFound, is(true));
        assertThat(readUserVerification.isLoggedIn, is(true));
        assertThat(readUserVerification.isSignedIn, is(true));
        assertThat(readUserVerification.isSignedInConfirmed, is(true));
    }


}