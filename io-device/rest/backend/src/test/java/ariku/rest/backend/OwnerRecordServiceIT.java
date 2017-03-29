package ariku.rest.backend;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import ariku.rest.client.RestClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author Ari Aaltonen
 */
public class OwnerRecordServiceIT {

    private static RestClient restClient = new RestClient();

    @BeforeClass
    public static void startArikuRestService() {
        Util.startServerAndLetClientKnowAboutTCPPort();
        restClient.restSettings = Util.restSettings;
    }

    @AfterClass
    public static void stopArikuRestService() {
        ArikuRest.stop();
    }

    @Ignore("Until fixed")
    @Test
    public void create_competition_successfully() throws UnirestException {

        String username = UUID.randomUUID().toString();
        String competitionName = "Helsinki Grand Prix";
        String competitionType = "RockPaperScissors";

        restClient.signUpRequest(username);
        restClient.verifySignUpRequest(username);
        String securityToken = restClient.loginRequest(username).get();

        Optional<JsonNode> response = restClient.newCompetitionRequest(competitionName, competitionType, username, securityToken);
        assertTrue(response.isPresent());
    }

    @Test
    public void created_competition_can_be_found_when_getting_list_of_owned_competition() throws UnirestException {

        String username = UUID.randomUUID().toString();
        String competitionName = "Helsinki Grand Prix";
        String competitionType = "RockPaperScissors";

        restClient.signUpRequest(username);
        restClient.verifySignUpRequest(username);
        String securityToken = restClient.loginRequest(username).get();
        restClient.newCompetitionRequest(competitionName, competitionType, username, securityToken);

        Optional<JsonNode> response = restClient.listOwnedCompetitionsRequest(username, securityToken);
        assertTrue(response.isPresent());

        System.out.println(response.get().toString());

        JSONArray responseArray = response.get().getArray();
        assertThat(responseArray.length(), is(1));

        String name = responseArray.getJSONObject(0).getString("name");
        String type = responseArray.getJSONObject(0).getString("type");

        assertThat(name, is(competitionName));
        assertThat(type, is(competitionType));
    }

    @Test
    public void three_competitions_found_from_list_after_creating_them() throws UnirestException, JSONException {

        String username = UUID.randomUUID().toString();

        restClient.signUpRequest(username);
        restClient.verifySignUpRequest(username);
        String securityToken = restClient.loginRequest(username).get();
        restClient.newCompetitionRequest("competitionA", "RockPaperScissors", username, securityToken);
        restClient.newCompetitionRequest("competitionB", "RockPaperScissors", username, securityToken);
        restClient.newCompetitionRequest("competitionC", "RockPaperScissors", username, securityToken);

        Optional<JsonNode> response = restClient.listOwnedCompetitionsRequest(username, securityToken);

        JSONArray competitionList = new JSONArray(response.get().toString());
        System.out.println(competitionList.toString());

        assertThat(competitionList.length(), is(3));
    }

    @Test
    public void competition_owner_can_add_new_owner() throws UnirestException, JSONException {

        // Create userA
        String usernameOfUserA = UUID.randomUUID().toString();
        restClient.signUpRequest(usernameOfUserA);
        restClient.verifySignUpRequest(usernameOfUserA);

        // Login with userA
        String userASecurityToken = restClient.loginRequest(usernameOfUserA).get();

        // Create new competition with userA who became competition owner
        String competitionName = "Helsinki Grand Prix";
        String competitionType = "RockPaperScissors";
        restClient.newCompetitionRequest(competitionName, competitionType, usernameOfUserA, userASecurityToken);

        // Find out created competition's id
        JsonNode userAOwnedCompetitions = restClient.listOwnedCompetitionsRequest(usernameOfUserA, userASecurityToken).get();
        String userACompetitionsId = userAOwnedCompetitions.getArray().getJSONObject(0).getString("id");

        System.out.println(userAOwnedCompetitions.toString());

        // Create userB
        String usernameOfUserB = UUID.randomUUID().toString();
        restClient.signUpRequest(usernameOfUserB);
        restClient.verifySignUpRequest(usernameOfUserB);

        // Add userB also to be owner of competition which userA created
        restClient.addOwnerToCompetition(userACompetitionsId, usernameOfUserA, usernameOfUserB, userASecurityToken);

        // Login with userB and fetch owned competitions
        String userBSecurityToken = restClient.loginRequest(usernameOfUserB).get();
        JsonNode userBOwnedCompetitions = restClient.listOwnedCompetitionsRequest(usernameOfUserB, userBSecurityToken).get();

        System.out.println(userBOwnedCompetitions.toString());
        String userBCompetitionsId = userBOwnedCompetitions.getArray().getJSONObject(0).getString("id");

        assertThat(userBCompetitionsId, is(userACompetitionsId));
    }

}
