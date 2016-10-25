package io.ariku.verification.api;

import io.ariku.util.data.User;

import static org.junit.Assert.*;

/**
 * @author Ari Aaltonen
 */
public class UserVerificationTest {

    public void user_can_signUp() {
        new UserVerification().signUp(new User());
    }

    public void user_can_verifySignUp() {
        new UserVerification().verifySignUp();
    }

    public void user_can_ogin() {
        new UserVerification().login();
    }

    public void user_can_logout() {
        new UserVerification().logout();
    }

}