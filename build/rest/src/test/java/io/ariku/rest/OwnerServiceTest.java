package io.ariku.rest;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.UUID;

import static io.ariku.rest.Util.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;

/**
 * @author Ari Aaltonen
 */
public class OwnerServiceTest {

    @BeforeClass
    public static void startArikuRestService() {
        ArikuRest.start();
    }

    @AfterClass
    public static void stopArikuRestService() {
        ArikuRest.stop();
    }

    @Test
    public void create_competition_successfully() throws UnirestException {

        String username = UUID.randomUUID().toString();
        String competitionName = "Helsinki Grand Prix";
        String competitionType = "RockPaperScissors";

        signUpRequest(username).asString();
        verifySignUpRequest(username).asString();
        String securityToken = loginRequest(username).asString().getBody();

        String response = newCompetitionRequest(competitionName, competitionType, username, securityToken).asString().getBody();

        assertThat(response, is("OK"));
    }

    @Test
    public void create_competition_fail_when_security_token_not_used() throws UnirestException {

        String username = UUID.randomUUID().toString();
        String competitionName = "Helsinki Grand Prix";
        String competitionType = "RockPaperScissors";

        signUpRequest(username).asString();
        verifySignUpRequest(username).asString();
        loginRequest(username).asString().getBody();

        String response = newCompetitionRequest(competitionName, competitionType, username, "notSecurityToken").asString().getBody();

        assertThat(response, is("FAIL"));
    }

    @Test
    public void create_competitions_can_be_seen_when_getting_list_of_owned_competition() throws UnirestException {

        String username = UUID.randomUUID().toString();
        String competitionName = "Helsinki Grand Prix";
        String competitionType = "RockPaperScissors";

        signUpRequest(username).asString();
        verifySignUpRequest(username).asString();
        String securityToken = loginRequest(username).asString().getBody();
        newCompetitionRequest(competitionName, competitionType, username, securityToken).asString().getBody();

        String response = listOwnedCompetitionsRequest(username, securityToken).asString().getBody();

        System.out.println(response);

        assertThat(response, not("PLAA"));
    }

}
