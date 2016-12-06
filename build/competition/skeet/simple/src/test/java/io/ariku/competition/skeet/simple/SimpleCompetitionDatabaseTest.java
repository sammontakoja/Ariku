package io.ariku.competition.skeet.simple;

import com.googlecode.junittoolbox.ParallelRunner;
import io.ariku.competition.skeet.api.Competition;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Ari Aaltonen
 */
@RunWith(ParallelRunner.class)
public class SimpleCompetitionDatabaseTest {

    @Test
    public void list_contains_zero_competitions() {
        List<Competition> competitions = new SimpleCompetitionDatabase().competitions();
        assertThat(competitions.size(), is(0));
    }

    @Test
    public void list_contains_one_competition_after_creating_one() {
        SimpleCompetitionDatabase db = new SimpleCompetitionDatabase();
        db.create("userId", new Competition("NastaGB"));
        List<Competition> competitions = db.competitions();
        assertThat(competitions.size(), is(1));
    }

    @Test
    public void when_user_create_two_competitions_they_are_found_from_competition_list() {
        SimpleCompetitionDatabase db = new SimpleCompetitionDatabase();
        db.create("userId", new Competition("competition1"));
        db.create("userId", new Competition("competition2"));
        List<Competition> competitions = db.competitions();
        assertThat(competitions.size(), is(2));
    }

    @Test
    public void when_two_users_create_two_competitions_they_are_found_from_competition_list() {
        SimpleCompetitionDatabase db = new SimpleCompetitionDatabase();
        db.create("userId1", new Competition("competition1"));
        db.create("userId1", new Competition("competition2"));
        db.create("userId2", new Competition("competition3"));
        db.create("userId2", new Competition("competition4"));
        List<Competition> competitions = db.competitions();
        assertThat(competitions.size(), is(4));
    }

    @Test
    public void when_userid_not_created_any_competition_then_empty_list_returned() {
        SimpleCompetitionDatabase db = new SimpleCompetitionDatabase();
        List<Competition> competitions = db.competitionsByUser("user");
        assertThat(competitions.size(), is(0));
    }

    @Test
    public void when_user_create_two_competitions_they_are_found_from_competition_by_user_list() {
        SimpleCompetitionDatabase db = new SimpleCompetitionDatabase();
        db.create("userId1", new Competition("competition1"));
        db.create("userId1", new Competition("competition2"));
        List<Competition> competitions = db.competitionsByUser("userId1");
        assertThat(competitions.size(), is(2));
    }

    @Test
    public void when_user1_create_two_and_user2_create_one_then_user1_find_two_competition_by_user_list() {
        SimpleCompetitionDatabase db = new SimpleCompetitionDatabase();
        db.create("user1", new Competition("competition1"));
        db.create("user1", new Competition("competition2"));
        db.create("user2", new Competition("competition3"));
        List<Competition> competitions = db.competitionsByUser("user1");
        assertThat(competitions.size(), is(2));
    }

}