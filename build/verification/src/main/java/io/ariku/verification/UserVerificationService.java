package io.ariku.verification;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Ari Aaltonen
 */
public class UserVerificationService implements UserAuthorizer {

    public UserVerificationDatabase userVerificationDatabase;

    public void signUp(SignUpRequest signUpRequest) {
        if (!userVerificationDatabase.findByUsername(signUpRequest.username).isPresent())
            userVerificationDatabase.createUserVerification(signUpRequest.username);
    }

    public void verifySignUp(VerifySignUpRequest verifySignUpRequest) {
        Optional<UserVerification> userVerification = userVerificationDatabase.findByUsername(verifySignUpRequest.username);

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
        Optional<UserVerification> userVerificationOptional = userVerificationDatabase.findByUsername(authorizeRequest.username);
        if (userVerificationOptional.isPresent()) {
            UserVerification userVerification = userVerificationOptional.get();
            if (userVerification.securityMessage.token.equals(authorizeRequest.securityToken)) {
                userVerification.securityMessage.token = "";
                userVerification.securityMessage.lastSecurityActivity = Instant.now().toString();
                userVerificationDatabase.updateUserVerification(userVerification);
            }
        }
    }

    public String authorizedUserId(AuthorizeRequest authorizeRequest) {
        Optional<UserVerification> userVerificationOptional = userVerificationDatabase.findByUsername(authorizeRequest.username);

        if (userVerificationOptional.isPresent())
            if (userVerificationOptional.get().securityMessage.token.equals(authorizeRequest.securityToken))
                return userVerificationOptional.get().userId;

        return "";
    }

    public boolean isUserSignedInConfirmed(String userId) {
        Optional<UserVerification> userVerificationOptional = userVerificationDatabase.findByUserId(userId);
        if (userVerificationOptional.isPresent()) {
            return userVerificationOptional.get().isSignedInConfirmed;
        }
        return false;
    }

}
