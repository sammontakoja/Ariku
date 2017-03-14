package io.ariku.rest;

import io.ariku.verification.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.route.Routes;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Ari Aaltonen
 */
public class UserVerificationServiceCaller {
    public static Logger logger = LoggerFactory.getLogger(ArikuRest.class);

    public UserVerificationService userVerificationService;
    public RequestReader requestReader = new RequestReader();
    
    public Route signUp() {
        return (request, response) -> {
            String username = requestReader.username(request);
            try {
                userVerificationService.signUp(new SignUpRequest(username));
                return "OK";
            } catch (Exception e) {
                logger.error("Failed to signup with username:{}", username, e);
                response.status(500);
                return "FAIL";
            }
        };
    }

    public Route verifySignUp() {

        return (request, response) -> {
            String username = requestReader.username(request);
            try {
                userVerificationService.verifySignUp(new VerifySignUpRequest(username));
                return "OK";
            } catch (Exception e) {
                logger.error("Failed to verifySignUp with username:{}", username, e);
                response.status(500);
                return "FAIL";
            }
        };
    }

    public Route login() {
        return (request, response) -> {
            String username = requestReader.username(request);
            try {
                return userVerificationService.login(new LoginRequest(username));
            } catch (Exception e) {
                logger.error("Failed to login with username:{}", username, e);
                response.status(500);
                return "FAIL";
            }
        };
    }

    public Route logout() {
        return (request, response) -> {
            String username = requestReader.username(request);
            String securityToken = requestReader.securityToken(request);
            try {
                userVerificationService.logout(new AuthorizeRequest(username, securityToken));
                return "OK";
            } catch (Exception e) {
                logger.error("Failed to logout with username:{} securityToken:{}", username, securityToken, e);
                response.status(500);
                return "FAIL";
            }
        };
    }
    
}
