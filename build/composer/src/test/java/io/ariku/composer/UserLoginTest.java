package io.ariku.composer;

import io.ariku.util.data.User;
import io.ariku.verification.LoginRequest;
import io.ariku.verification.SignUpRequest;
import io.ariku.verification.VerifySignUpRequest;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * @author Ari Aaltonen
 */
public class UserLoginTest {

    private Composer composer;
    private Commands commands;
    private String username;

    @Before
    public void initializeValues() {
        composer = new Composer();
        username = UUID.randomUUID().toString();
        commands = new Commands(composer);
    }

    @Test
    public void userA_fails_to_login_when_signup_is_not_done() {
        String securityToken = composer.userVerificationService.login(new LoginRequest(username));
        assertTrue(securityToken.isEmpty());
    }

    @Test
    public void userA_fails_to_login_when_verifySignUp_is_not_done() {
        String username = UUID.randomUUID().toString();
        composer.userVerificationService.signUp(new SignUpRequest(username));
        String securityToken = composer.userVerificationService.login(new LoginRequest(username));
        assertTrue(securityToken.isEmpty());
    }

    @Test
    public void after_verify_signup_user_can_found_by_username() {
        composer.userVerificationService.signUp(new SignUpRequest(username));
        composer.userVerificationService.verifySignUp(new VerifySignUpRequest(username));
        Optional<User> foundUser = composer.userService.findUserByUsername(username);
        assertTrue(foundUser.isPresent());
        assertThat(foundUser.get().getUsername(), is(username));
    }

    @Test
    public void userA_login_ok_after_signup_and_verifysignup() {
        String securityToken = commands.loginWithUsername(username);
        assertFalse(securityToken.isEmpty());
    }

}
