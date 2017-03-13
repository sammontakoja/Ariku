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
        start(5000);
    }

    public static void start(int port) {

        port(port);

        Composer composer = new Composer();
        Verification verification = new Verification();
        verification.userVerificationService = composer.userVerificationService;

        Owner owner = new Owner();
        owner.ownerService = composer.ownerService;

        Configurator.defaultConfig()
                .writer(new ConsoleWriter())
                .level(Level.INFO)
                .activate();

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

            get(rs.competitionListPath(), "application/json", (request, response) -> {
                String username = request.queryParams("username");
                String securityToken = request.queryParams("security_token");
                return owner.listOwnedCompetitions(username, securityToken);
            }, o -> gson.toJson(o));

            post(rs.addOwnerPath(), (request, response) -> {
                String competitionId = request.queryParams("competition_id");
                String newOwner = request.queryParams("username_new_owner");
                String existingOwner = request.queryParams("username_existing_owner");
                String securityToken = request.queryParams("security_token");
                owner.addOwnerToCompetition(competitionId, newOwner, existingOwner, securityToken);
                return "OK";
            });
        });

    }

    public static void stop() {
        spark.Spark.stop();
    }

}

