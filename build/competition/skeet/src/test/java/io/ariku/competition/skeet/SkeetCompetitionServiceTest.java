package io.ariku.competition.skeet;

import com.googlecode.junittoolbox.ParallelRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Ari Aaltonen
 */
@RunWith(ParallelRunner.class)
public class SkeetCompetitionServiceTest {

    @Test
    public void user_can_create_competition_can_be_created() {
        SkeetCompetitionService skeetCompetitionService = new SkeetCompetitionService();
        skeetCompetitionService.competitionDatabase = mock(CompetitionDatabase.class);
        skeetCompetitionService.createNewCompetition("userId", "NastaGB");
    }

    @Test
    public void user_can_list_competitions() {
        SkeetCompetitionService skeetCompetitionService = new SkeetCompetitionService();

        CompetitionDatabase competitionDatabase = mock(CompetitionDatabase.class);
        skeetCompetitionService.competitionDatabase = competitionDatabase;

        Competition nastaGB = new Competition("NastaGB");

        when(competitionDatabase.competitionsByUser("userId")).thenReturn(Arrays.asList(nastaGB));

        assertThat(skeetCompetitionService.listCompetitions("userId"), hasItem(nastaGB));
    }

}