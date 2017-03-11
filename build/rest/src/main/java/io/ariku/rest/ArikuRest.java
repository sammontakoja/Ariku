package io.ariku.rest;

/**
 * @author Ari Aaltonen
 */

import com.google.gson.Gson;
import io.ariku.composer.Composer;
import io.ariku.util.data.ArikuSettings;
import io.ariku.util.data.RestSettings;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;
import org.pmw.tinylog.writers.ConsoleWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.*;

public class ArikuRest {
    public static Logger logger = LoggerFactory.getLogger(ArikuRest.class);

    private static RestSettings rs = ArikuSettings.restClientWithDefaultUrlConfiguration();

    public static void main(String[] args) {
        start();
    }

    public static void start() {

        Composer composer = new Composer();
        Verification verification = new Verification();
        verification.userVerificationService = composer.userVerificationService;

        Owner owner = new Owner();
        owner.ownerService = composer.ownerService;

        Configurator.defaultConfig()
                .writer(new ConsoleWriter())
                .level(Level.INFO)
                .activate();

        port(5000);
        Gson gson = new Gson();

        path(rs.verificationPath(), () -> {
            post(rs.signUpPath(), (request, response) -> verification.signUp(request.queryParams("username")));
            post(rs.verifySignUpPath(), (request, response) -> verification.verifySignUp(request.queryParams("username")));
            post(rs.loginPath(), (request, response) -> verification.login(request.queryParams("username")));
            post(rs.logoutPath(), (request, response) -> verification.logout(request.queryParams("username"), request.queryParams("security_token")));
        });

        path(rs.ownerPath(), () -> {
            post(rs.competitionNewPath(), (request, response) -> {
                String competitionName = request.queryParams("competition_name");
                String competitionType = request.queryParams("competition_type");
                String username = request.queryParams("username");
                String securityToken = request.queryParams("security_token");
                return owner.newCompetition(competitionName, competitionType, username, securityToken);
            });
        });

    }

    public static void stop() {
        spark.Spark.stop();
    }

}

