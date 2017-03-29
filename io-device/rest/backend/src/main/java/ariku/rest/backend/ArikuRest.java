package ariku.rest.backend;

/**
 * @author Ari Aaltonen
 */

import ariku.simple.SimpleArikuServices;
import ariku.test.ArikuServices;
import com.google.gson.Gson;
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
        boolean inputsAreUsable = inputParser.areInputUsable(args);

        if (inputsAreUsable)
            start(inputParser.getHost(), inputParser.getPort());
        else
            start("localhost", 5000);
    }

    public static void start(String host, int port) {

        logger.debug("Running Ariku REST service in {}:{}", host, port);

        ipAddress(host);
        port(port);

        ArikuServices arikuServices = findImplementationFromClassPath();
        UserVerificationServiceCaller userVerificationServiceCaller = new UserVerificationServiceCaller(arikuServices.userVerificationService());
        OwnerServiceCaller ownerServiceCaller = new OwnerServiceCaller(arikuServices.ownerService());

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

    public static ArikuServices findImplementationFromClassPath() {
        return new SimpleArikuServices();
    }

    public static void stop() {
        spark.Spark.stop();
    }

}

