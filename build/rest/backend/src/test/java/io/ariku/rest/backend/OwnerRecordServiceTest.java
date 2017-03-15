package io.ariku.rest.backend;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Ari Aaltonen
 */
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

        Util.signUpRequest(username).asString();

        Util.verifySignUpRequest(username).asString();

        String securityToken = Util.loginRequest(username).asString().getBody();

        String response = Util.newCompetitionRequest(competitionName, competitionType, username, securityToken).asString().getBody();

        assertThat(response, is("OK"));
    }

    @Test
    public void created_competition_can_be_found_when_getting_list_of_owned_competition() throws UnirestException {

        String username = UUID.randomUUID().toString();
        String competitionName = "Helsinki Grand Prix";
        String competitionType = "RockPaperScissors";

        Util.signUpRequest(username).asString();

        Util.verifySignUpRequest(username).asString();

        String securityToken = Util.loginRequest(username).asString().getBody();

        Util.newCompetitionRequest(competitionName, competitionType, username, securityToken).asString();

        JsonNode response = Util.listOwnedCompetitionsRequest(username, securityToken).asJson().getBody();

        System.out.println(response.toString());

        JSONArray responseArray = response.getArray();
        assertThat(responseArray.length(), is(1));

        String name = responseArray.getJSONObject(0).getString("name");
        String type = responseArray.getJSONObject(0).getString("type");

        assertThat(name, is(competitionName));
        assertThat(type, is(competitionType));
    }

    @Test
    public void three_competitions_found_from_list_after_creating_them() throws UnirestException, JSONException {

        String username = UUID.randomUUID().toString();

        Util.signUpRequest(username).asString();

        Util.verifySignUpRequest(username).asString();

        String securityToken = Util.loginRequest(username).asString().getBody();

        Util.newCompetitionRequest("competitionA", "RockPaperScissors", username, securityToken).asString();
        Util.newCompetitionRequest("competitionB", "RockPaperScissors", username, securityToken).asString();
        Util.newCompetitionRequest("competitionC", "RockPaperScissors", username, securityToken).asString();

        String response = Util.listOwnedCompetitionsRequest(username, securityToken).asString().getBody();

        JSONArray competitionList = new JSONArray(response);
        System.out.println(competitionList.toString());

        assertThat(competitionList.length(), is(3));
    }

    @Test
    public void competition_owner_can_add_new_owner() throws UnirestException, JSONException {

        // Create userA
        String usernameOfUserA = UUID.randomUUID().toString();
        Util.signUpRequest(usernameOfUserA).asString();
        Util.verifySignUpRequest(usernameOfUserA).asString();

        // Login with userA
        String userASecurityToken = Util.loginRequest(usernameOfUserA).asString().getBody();

        // Create new competition with userA who became competition owner
        String competitionName = "Helsinki Grand Prix";
        String competitionType = "RockPaperScissors";
        Util.newCompetitionRequest(competitionName, competitionType, usernameOfUserA, userASecurityToken).asString();

        // Find out created competition's id
        JsonNode userAOwnedCompetitions = Util.listOwnedCompetitionsRequest(usernameOfUserA, userASecurityToken).asJson().getBody();
        String userACompetitionsId = userAOwnedCompetitions.getArray().getJSONObject(0).getString("id");

        System.out.println(userAOwnedCompetitions.toString());

        // Create userB
        String usernameOfUserB = UUID.randomUUID().toString();
        Util.signUpRequest(usernameOfUserB).asString();
        Util.verifySignUpRequest(usernameOfUserB).asString();

        // Add userB also to be owner of competition which userA created
        Util.addOwnerToCompetition(userACompetitionsId, usernameOfUserA, usernameOfUserB, userASecurityToken).asString();

        // Login with userB and fetch owned competitions
        String userBSecurityToken = Util.loginRequest(usernameOfUserB).asString().getBody();
        JsonNode userBOwnedCompetitions = Util.listOwnedCompetitionsRequest(usernameOfUserB, userBSecurityToken).asJson().getBody();

        System.out.println(userBOwnedCompetitions.toString());
        String userBCompetitionsId = userBOwnedCompetitions.getArray().getJSONObject(0).getString("id");

        assertThat(userBCompetitionsId, is(userACompetitionsId));
    }

}
