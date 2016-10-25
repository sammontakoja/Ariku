package io.ariku.verification.api;

import com.googlecode.junittoolbox.ParallelRunner;
import io.ariku.util.data.User;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Ari Aaltonen
 */
@RunWith(ParallelRunner.class)
public class UserVerificationTest {

    @Test
    public void user_can_signUp() {
        new UserVerification().signUp(new User());
    }

    @Test
    public void user_can_verifySignUp() {
        new UserVerification().verifySignUp();
    }

    @Test
    public void user_can_ogin() {
        new UserVerification().login();
    }

    @Test
    public void user_can_logout() {
        new UserVerification().logout();
    }

}