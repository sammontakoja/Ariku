package io.ariku.owner.api;

import io.ariku.util.data.User;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * @author Ari Aaltonen
 */
public class OwnerServiceTest {

    @Test
    public void user_can_succesfully_create_new_competition() {

        OwnerService ownerService = new OwnerService();
        String competitionName = "NastaGB";
        CompetitionType competitionType = new CompetitionType();
        User user = new User();
        boolean result = ownerService.createNewCompetition(competitionName, competitionType, user);

        assertThat(result, is(true));
    }

}