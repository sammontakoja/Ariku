package io.ariku.rest;

/**
 * @author Ari Aaltonen
 */

import com.google.gson.Gson;
import io.ariku.composer.Composer;
import io.ariku.util.data.ArikuSettings;
import io.ariku.util.data.RestSettings;
import io.ariku.verification.*;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;
import org.pmw.tinylog.writers.ConsoleWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static spark.Spark.*;

public class ArikuRest {
    public static Logger logger = LoggerFactory.getLogger(ArikuRest.class);

    private static RestSettings rs = ArikuSettings.restClientWithDefaultUrlConfiguration();
    private final static Composer composer = new Composer();

    public static void main(String[] args) {
        start();
    }

    public static void start() {

        Configurator.defaultConfig()
                .writer(new ConsoleWriter())
                .level(Level.INFO)
                .activate();

        port(5000);
        Gson gson = new Gson();

        path(rs.verificationPath(), () -> {
            before((q, a) -> logger.info("Received verification call"));
            post(rs.signUpPath(), (request, response) -> signUp(request.queryParams("username")));
            post(rs.verifySignUpPath(), (request, response) -> verifySignUp(request.queryParams("username")));
            post(rs.loginPath(), (request, response) -> login(request.queryParams("username")));
            post(rs.logoutPath(), (request, response) -> logout(request.queryParams("username"), request.queryParams("security_token")));
        });

    }

    private static String signUp(String username) {
        composer.userVerificationService.signUp(new SignUpRequest(username));

        Optional<UserVerification> userVerification = composer.userVerificationService.userVerificationDatabase.findByUsername(username);
        if (userVerification.isPresent())
            return "OK";

        return "FAIL";
    }

    private static String verifySignUp(String username) {
        composer.userVerificationService.verifySignUp(new VerifySignUpRequest(username));

        Optional<UserVerification> userVerification = composer.userVerificationService.userVerificationDatabase.findByUsername(username);
        if (userVerification.isPresent())
            if (userVerification.get().isSignedInConfirmed)
                return "OK";

        return "FAIL";
    }

    private static String login(String username) {
        return composer.userVerificationService.login(new LoginRequest(username));
    }

    private static String logout(String username, String securityToken) {
        composer.userVerificationService.logout(new AuthorizeRequest(username, securityToken));

        Optional<UserVerification> userVerification = composer.userVerificationService.userVerificationDatabase.findByUsername(username);
        if (userVerification.isPresent())
            if (userVerification.get().securityMessage.token.isEmpty())
                return "OK";

        return "FAIL";
    }

    public static void stop() {
        spark.Spark.stop();
    }

}

