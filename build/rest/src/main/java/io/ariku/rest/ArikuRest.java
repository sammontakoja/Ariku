package io.ariku.rest;

/**
 * @author Ari Aaltonen
 */

import com.google.gson.Gson;
import io.ariku.composer.Composer;
import io.ariku.verification.LoginRequest;
import io.ariku.verification.SignUpRequest;
import io.ariku.verification.UserVerification;
import io.ariku.verification.VerifySignUpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static spark.Spark.*;

public class ArikuRest {
    public static Logger logger = LoggerFactory.getLogger(ArikuRest.class);

    private final static Composer composer = new Composer();

    public static void main(String[] args) {
        start();
    }

    public static void start() {

        port(5000);
        Gson gson = new Gson();

        path("/verification", () -> {
            before((q, a) -> logger.info("Received verification call"));
            get("/signup/:username", (request, response) -> signUp(request.params(":username")));
            get("/verifysignup/:username", (request, response) -> verifySignUp(request.params(":username")));
            get("/login/:username", (request, response) -> login(request.params(":username")));
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
        String login = composer.userVerificationService.login(new LoginRequest(username));
        if (login.isEmpty())
            return "FAIL";
        return login;
    }

    public static void stop() {
        spark.Spark.stop();
    }

}

