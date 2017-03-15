package io.ariku.rest.client;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.ariku.util.data.RestSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * @author Ari Aaltonen
 */
public class RestClient {
    public static Logger logger = LoggerFactory.getLogger(RestClient.class);

    public RestSettings restSettings = new RestSettings();

    public Optional<String> signUpRequest(String username) {
        return new RequestWrapper<String>().logExceptionsSilently(() -> Optional.of(
                Unirest.post(restSettings.signUpUrl())
                        .queryString("username", username).asString().getBody()));
    }

    public Optional<String> verifySignUpRequest(String username) {
        return new RequestWrapper<String>().logExceptionsSilently(() -> Optional.of(
                Unirest.post(restSettings.verifySignUpUrl())
                        .queryString("username", username).asString().getBody()));
    }

    public Optional<String> loginRequest(String username) {
        return new RequestWrapper<String>().logExceptionsSilently(() -> Optional.of(
                Unirest.post(restSettings.loginUrl())
                        .queryString("username", username).asString().getBody()));
    }

    public Optional<String> logoutRequest(String username, String securityToken) {
        return new RequestWrapper<String>().logExceptionsSilently(() -> Optional.of(
                Unirest.post(restSettings.logoutUrl())
                        .queryString("username", username)
                        .queryString("security_token", securityToken).asString().getBody()));
    }

    public Optional<JsonNode> newCompetitionRequest(String competitionName, String competitionType, String username, String securityToken) {
        return new RequestWrapper<JsonNode>().logExceptionsSilently(() -> Optional.of(
                Unirest.post(restSettings.competitionNewUrl())
                        .queryString("competition_name", competitionName)
                        .queryString("competition_type", competitionType)
                        .queryString("username", username)
                        .queryString("security_token", securityToken).asJson().getBody()));
    }

    public Optional<String> addOwnerToCompetition(String competitionId, String existingOwnerUsername, String newOwnerUsername, String securityToken) {
        return new RequestWrapper<String>().logExceptionsSilently(() -> Optional.of(
                Unirest.post(restSettings.competitionAddOwnerUrl())
                        .queryString("competition_id", competitionId)
                        .queryString("username_new_owner", newOwnerUsername)
                        .queryString("username", existingOwnerUsername)
                        .queryString("security_token", securityToken).asString().getBody()));
    }

    public Optional<JsonNode> listOwnedCompetitionsRequest(String username, String securityToken) {
        return new RequestWrapper<JsonNode>().logExceptionsSilently(() -> Optional.of(
                Unirest.get(restSettings.competitionListByOwnerUrl())
                        .queryString("username", username)
                        .queryString("security_token", securityToken).asJson().getBody()));
    }

    public class RequestWrapper<T> {
        Optional<T> logExceptionsSilently(UniRestCall<T> uniRestCall) {
            try {
                return uniRestCall.sendHttpRequest();
            } catch (UnirestException e) {
                logger.error("Failed to do REST call");
                return Optional.empty();
            }
        }
    }

    @FunctionalInterface
    public interface UniRestCall<T> {
        Optional<T> sendHttpRequest() throws UnirestException;
    }

}
