package io.ariku.rest;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.UUID;

import static io.ariku.rest.Util.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Ari Aaltonen
 */
@Ignore("Until composer level modifications and tests are done")
public class OwnerRecordServiceTest {

    @BeforeClass
    public static void startArikuRestService() {
        Util.startServerAndLetClientKnowAboutTCPPort();
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
    public void created_competition_can_be_found_when_getting_list_of_owned_competition() throws UnirestException {

        String username = UUID.randomUUID().toString();
        String competitionName = "Helsinki Grand Prix";
        String competitionType = "RockPaperScissors";

        signUpRequest(username).asString();

        verifySignUpRequest(username).asString();

        String securityToken = loginRequest(username).asString().getBody();

        newCompetitionRequest(competitionName, competitionType, username, securityToken).asString();

        String response = listOwnedCompetitionsRequest(username, securityToken).asString().getBody();

        System.out.println(response);

        assertThat(response, startsWith("[\"Competition{name='"+competitionName+"', type='"+competitionType+"'"));
    }

    @Test
    public void three_competitions_found_from_list_after_creating_them() throws UnirestException, JSONException {

        String username = UUID.randomUUID().toString();

        signUpRequest(username).asString();

        verifySignUpRequest(username).asString();

        String securityToken = loginRequest(username).asString().getBody();

        newCompetitionRequest("competitionA", "RockPaperScissors", username, securityToken).asString();
        newCompetitionRequest("competitionB", "RockPaperScissors", username, securityToken).asString();
        newCompetitionRequest("competitionC", "RockPaperScissors", username, securityToken).asString();

        String response = listOwnedCompetitionsRequest(username, securityToken).asString().getBody();

        JSONArray competitionList = new JSONArray(response);
        System.out.println(competitionList.toString());

        assertThat(competitionList.length(), is(3));
    }

    @Test
    public void competition_owner_can_add_new_owner() throws UnirestException, JSONException {

        // Create userA
        String usernameOfUserA = UUID.randomUUID().toString();
        signUpRequest(usernameOfUserA).asString();
        verifySignUpRequest(usernameOfUserA).asString();

        // Login with userA
        String userASecurityToken = loginRequest(usernameOfUserA).asString().getBody();

        // Create new competition with userA who became competition owner
        String competitionName = "Helsinki Grand Prix";
        String competitionType = "RockPaperScissors";
        newCompetitionRequest(competitionName, competitionType, usernameOfUserA, userASecurityToken).asString();

        // Find out created competition's id
        JsonNode userAOwnedCompetitions = listOwnedCompetitionsRequest(usernameOfUserA, userASecurityToken).asJson().getBody();
        String userACompetitionsId = userAOwnedCompetitions.getArray().getJSONObject(0).getString("id");

        System.out.println(userAOwnedCompetitions.toString());

        // Create userB
        String usernameOfUserB = UUID.randomUUID().toString();
        signUpRequest(usernameOfUserB).asString();
        verifySignUpRequest(usernameOfUserB).asString();

        // Add userB also to be owner of competition which userA created
        addOwnerToCompetition(userACompetitionsId, usernameOfUserA, usernameOfUserB, userASecurityToken);

        // Login with userB and fetch owned competitions
        String userBSecurityToken = loginRequest(usernameOfUserB).asString().getBody();
        JsonNode userBOwnedCompetitions = listOwnedCompetitionsRequest(usernameOfUserB, userBSecurityToken).asJson().getBody();

        System.out.println(userBOwnedCompetitions.toString());
        String userBCompetitionsId = userBOwnedCompetitions.getArray().getJSONObject(0).getString("id");

        assertThat(userBCompetitionsId, is(userACompetitionsId));
    }

}
