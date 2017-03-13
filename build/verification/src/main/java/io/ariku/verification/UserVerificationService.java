package io.ariku.verification;

import io.ariku.util.data.User;
import io.ariku.util.data.UserDatabase;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Ari Aaltonen
 */
public class UserVerificationService {

    public UserDatabase userDatabase;
    public UserAuthorizer userAuthorizer;
    public UserVerificationDatabase userVerificationDatabase;

    public void signUp(SignUpRequest signUpRequest) {
        if (!userVerificationDatabase.findByUsername(signUpRequest.username).isPresent())
            userVerificationDatabase.createUserVerification(signUpRequest.username);
    }

    public void verifySignUp(VerifySignUpRequest verifySignUpRequest) {
        userVerificationDatabase.findByUsername(verifySignUpRequest.username)
                .ifPresent(userVerification -> {
                    userVerification.isSignedInConfirmed = true;
                    userVerificationDatabase.updateUserVerification(userVerification);
                    userDatabase.store(new User(userVerification.username, userVerification.userId));
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

    public Optional<String> authorizedUser(AuthorizeRequest authorizeRequest) {
        Optional<UserVerification> userVerificationOptional = userVerificationDatabase.findByUsername(authorizeRequest.username);

        if (userVerificationOptional.isPresent())
            if (userVerificationOptional.get().securityMessage.token.equals(authorizeRequest.securityToken))
                return Optional.of(userVerificationOptional.get().userId);

        return Optional.empty();
    }

    public boolean isUserSignedInConfirmed(String userId) {
        Optional<UserVerification> userVerificationOptional = userVerificationDatabase.findByUserId(userId);
        if (userVerificationOptional.isPresent()) {
            return userVerificationOptional.get().isSignedInConfirmed;
        }
        return false;
    }

}
