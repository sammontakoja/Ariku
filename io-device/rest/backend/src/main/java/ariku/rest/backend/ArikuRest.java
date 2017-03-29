package ariku.rest.backend;

/**
 * @author Ari Aaltonen
 */

import ariku.CoreServices;
import ariku.settings.ArikuSettings;
import ariku.settings.RestSettings;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.ServiceLoader;

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

        CoreServices coreServices = findImplementationFromClassPath();
        UserVerificationServiceCaller userVerificationServiceCaller = new UserVerificationServiceCaller(coreServices.userVerificationService());
        OwnerServiceCaller ownerServiceCaller = new OwnerServiceCaller(coreServices.ownerService());

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

    public static CoreServices findImplementationFromClassPath() {

        Iterator<? extends CoreServices> arikuServicesIterator = ServiceLoader.load(CoreServices.class).iterator();

        if (arikuServicesIterator.hasNext()) {
            CoreServices found = arikuServicesIterator.next();
            System.out.println(String.format("Using CoreServices implementation:'%s'", found.getClass().getName()));
            return found;
        } else {
            throw new RuntimeException("Did not find CoreServices implementation from CLASSPATH");
        }
    }

    public static void stop() {
        spark.Spark.stop();
    }

}

