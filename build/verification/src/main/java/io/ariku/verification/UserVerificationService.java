package io.ariku.verification;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Ari Aaltonen
 */
public class UserVerificationService implements UserAuthorizer {

    public UserVerificationDatabase userVerificationDatabase;

    public boolean signUp(SignUpRequest signUpRequest) {
        if (userVerificationDatabase.findByUsername(signUpRequest.username).isPresent()) {
            userVerificationDatabase.createUserVerification(signUpRequest.username);
            return true;
        }
        return false;
    }

    public void verifySignUp(VerifySignUpRequest verifySignUpRequest) {
        Optional<UserVerification> userVerification = userVerificationDatabase.findByUserId(verifySignUpRequest.username);

        userVerification.ifPresent(user -> {
            user.isSignedInConfirmed = true;
            userVerificationDatabase.updateUserVerification(user);
        });
    }

    public String login(LoginRequest loginRequest) {

        Optional<UserVerification> userVerificationContent = userVerificationDatabase.findByUsername(loginRequest.username);

        if (userVerificationContent.isPresent()) {
            UserVerification userVerification = userVerificationContent.get();
            if (userVerification.isSignedInConfirmed) {
                String securityMessage = UUID.randomUUID().toString();
                userVerification.securityMessage.token = securityMessage;
                userVerification.securityMessage.lastSecurityActivity = Instant.now().toString();
                userVerificationDatabase.updateUserVerification(userVerification);
                return securityMessage;
            }
        }
        return "";
    }

    public void logout(AuthorizeRequest authorizeRequest) {
        Optional<UserVerification> userVerificationOptional = userVerificationDatabase.findByUserId(authorizeRequest.userId);
        if (userVerificationOptional.isPresent()) {
            UserVerification userVerification = userVerificationOptional.get();
            if (userVerification.securityMessage.token.equals(authorizeRequest.securityMessage)) {
                userVerification.securityMessage.token = "";
                userVerification.securityMessage.lastSecurityActivity = Instant.now().toString();
                userVerificationDatabase.updateUserVerification(userVerification);
            }
        }
    }

    public boolean isAuthorized(AuthorizeRequest authorizeRequest) {
        Optional<UserVerification> userVerificationOptional = userVerificationDatabase.findByUserId(authorizeRequest.userId);

        if (userVerificationOptional.isPresent()) {
            UserVerification userVerification = userVerificationOptional.get();
            boolean authorizedCall = userVerification.securityMessage.token.equals(authorizeRequest.securityMessage);
            System.out.println(authorizeRequest + " authorizedCall => " + authorizedCall);
            return authorizedCall;
        }
        return false;
    }

    public boolean isUserSignedInConfirmed(String userId) {
        Optional<UserVerification> userVerificationOptional = userVerificationDatabase.findByUserId(userId);
        if (userVerificationOptional.isPresent()) {
            return userVerificationOptional.get().isSignedInConfirmed;
        }
        return false;
    }

}
