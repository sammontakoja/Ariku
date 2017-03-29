package ariku.test.suite;

import ariku.test.ArikuServices;
import ariku.test.util.Commands;
import ariku.test.util.InputGenerator;
import ariku.test.util.TestRunner;
import io.ariku.util.data.User;
import io.ariku.verification.LoginRequest;
import io.ariku.verification.SignUpRequest;
import io.ariku.verification.VerifySignUpRequest;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Ari Aaltonen
 */
public class UserLoginTestSuite implements TestRunner {

    private ArikuServices arikuServices;
    private Commands commands;

    @Override
    public void runAllTests(ArikuServices arikuServices) {
        // Initialize
        this.arikuServices = arikuServices;
        this.commands = new Commands(this.arikuServices.userVerificationService());

        // Tests
        userA_fails_to_login_when_signup_is_not_done();
        userA_fails_to_login_when_verifySignUp_is_not_done();
        userA_login_ok_after_signup_and_verifysignup();
        after_verify_signup_user_can_found_by_username();
    }

    public void userA_fails_to_login_when_signup_is_not_done() {
        String username = InputGenerator.randomUsername();
        String securityToken = arikuServices.userVerificationService().login(new LoginRequest(username));
        assertThat(securityToken.isEmpty(), is(true));
    }

    public void userA_fails_to_login_when_verifySignUp_is_not_done() {
        String username = InputGenerator.randomUsername();
        arikuServices.userVerificationService().signUp(new SignUpRequest(username));
        String securityToken = arikuServices.userVerificationService().login(new LoginRequest(username));
        assertThat(securityToken.isEmpty(), is(true));
    }

    public void after_verify_signup_user_can_found_by_username() {
        String username = InputGenerator.randomUsername();
        arikuServices.userVerificationService().signUp(new SignUpRequest(username));
        arikuServices.userVerificationService().verifySignUp(new VerifySignUpRequest(username));
        Optional<User> foundUser = arikuServices.userService().findUserByUsername(username);
        assertThat(foundUser.isPresent(), is(true));
        assertThat(foundUser.get().getUsername(), is(username));
    }

    public void userA_login_ok_after_signup_and_verifysignup() {
        String username = InputGenerator.randomUsername();
        String securityToken = commands.loginWithUsername(username);
        assertThat(securityToken.isEmpty(), is(false));
    }


}
