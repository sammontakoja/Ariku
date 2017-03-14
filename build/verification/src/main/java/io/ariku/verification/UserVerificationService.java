package io.ariku.verification;

import io.ariku.util.data.User;
import io.ariku.util.data.UserRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Ari Aaltonen
 */
public class UserVerificationService {

    public UserRepository userRepository;
    public UserAuthorizer userAuthorizer;
    public UserVerificationRepository userVerificationRepository;

    public void signUp(SignUpRequest signUpRequest) {
        Optional<UserVerification> found = userVerificationRepository.getByUsername(signUpRequest.username);
        if (!found.isPresent()) {
            UserVerification userVerification = new UserVerification();
            userVerification.setUsername(signUpRequest.username);
            userVerification.setUserId(userVerificationRepository.uniqueUserId());
            userVerificationRepository.store(userVerification);
        }
    }

    public void verifySignUp(VerifySignUpRequest verifySignUpRequest) {
        Optional<UserVerification> found = userVerificationRepository.getByUsername(verifySignUpRequest.username);

        if (found.isPresent()) {
            UserVerification userVerification = found.get();
            userVerification.setSignedInConfirmed(true);
            userVerificationRepository.update(userVerification);

            User user = new User();
            user.setUsername(userVerification.getUsername());
            user.setId(userVerification.getUserId());
            userRepository.store(user);
        }
    }

    public String login(LoginRequest loginRequest) {

        Optional<UserVerification> userVerificationContent = userVerificationRepository.getByUsername(loginRequest.username);

        if (userVerificationContent.isPresent()) {
            UserVerification userVerification = userVerificationContent.get();
            if (userVerification.isSignedInConfirmed()) {
                SecurityMessage securityMessage = new SecurityMessage();
                securityMessage.setToken(UUID.randomUUID().toString());
                securityMessage.setLastSecurityActivity(Instant.now().toString());
                userVerification.setSecurityMessage(securityMessage);
                userVerificationRepository.update(userVerification);
                return securityMessage.getToken();
            }
        }
        return "";
    }

    public void logout(AuthorizeRequest authorizeRequest) {
        Optional<UserVerification> userVerificationOptional = userVerificationRepository.getByUsername(authorizeRequest.username);
        if (userVerificationOptional.isPresent()) {
            UserVerification userVerification = userVerificationOptional.get();
            SecurityMessage securityMessage = userVerification.getSecurityMessage();
            if (securityMessage.getToken().equals(authorizeRequest.securityToken)) {
                securityMessage.setToken("");
                securityMessage.setLastSecurityActivity(Instant.now().toString());
                userVerificationRepository.update(userVerification);
            }
        }
    }
//
//    public Optional<String> authorizedUser(AuthorizeRequest authorizeRequest) {
//        Optional<UserVerification> userVerificationOptional = userVerificationRepository.(authorizeRequest.username);
//
//        if (userVerificationOptional.isPresent())
//            if (userVerificationOptional.get().securityMessage.token.equals(authorizeRequest.securityToken))
//                return Optional.of(userVerificationOptional.get().userId);
//
//        return Optional.empty();
//    }
//
//    public boolean isUserSignedInConfirmed(String userId) {
//        Optional<UserVerification> userVerificationOptional = userVerificationRepository.findByUserId(userId);
//        if (userVerificationOptional.isPresent()) {
//            return userVerificationOptional.get().isSignedInConfirmed;
//        }
//        return false;
//    }

}
