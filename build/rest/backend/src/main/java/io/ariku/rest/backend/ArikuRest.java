package io.ariku.rest.backend;

/**
 * @author Ari Aaltonen
 */

import com.google.gson.Gson;
import io.ariku.composer.Composer;
import io.ariku.util.data.ArikuSettings;
import io.ariku.util.data.RestSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.*;

public class ArikuRest {
    public static Logger logger = LoggerFactory.getLogger(ArikuRest.class);

    private static RestSettings rs = ArikuSettings.restClientWithDefaultUrlConfiguration();

    public static void main(String[] args) {
        InputParser inputParser = new InputParser();
        inputParser.parseContents(args);
        String host = inputParser.getHost();
        int port = inputParser.getPort();
        System.out.println("Starting Ariku Rest service to "+ host +":" + port);
        start(host, port);
    }

    public static void start(String host, int port) {

        ipAddress(host);
        port(port);

        Composer composer = new Composer();
        UserVerificationServiceCaller userVerificationServiceCaller = new UserVerificationServiceCaller();
        userVerificationServiceCaller.userVerificationService = composer.userVerificationService;

        OwnerServiceCaller ownerServiceCaller = new OwnerServiceCaller();
        ownerServiceCaller.ownerService = composer.ownerService;

        Gson gson = new Gson();

        path(rs.verificationPath(), () -> {
            post(rs.signUpPath(), userVerificationServiceCaller.signUp());
            post(rs.verifySignUpPath(), userVerificationServiceCaller.verifySignUp());
            post(rs.loginPath(), userVerificationServiceCaller.login());
            post(rs.logoutPath(), userVerificationServiceCaller.logout());
        });

        path(rs.ownerPath(), () -> {
            post(rs.competitionNewPath(), ownerServiceCaller.createNewCompetition());
            get(rs.competitionListPath(), "application/json", ownerServiceCaller.ownersCompetitions(), o -> gson.toJson(o));
            post(rs.addOwnerPath(), ownerServiceCaller.addUserAsCompetitionOwner());
        });



}

    public static void stop() {
        spark.Spark.stop();
    }

}

