package io.ariku.database.simple;

import io.ariku.user.AttendingInfo;
import io.ariku.util.data.Competition;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Ari Aaltonen
 */
public class AttendingDatabaseTest {

    @Test
    public void after_adding_attending_it_can_be_found_from_competitions_list() {

        String userId = UUID.randomUUID().toString();

        SimpleDatabase db = new SimpleDatabase();
        Competition competition = db.createCompetition(userId, "name", "type");

        db.add(new AttendingInfo(userId, competition.id));
        List<Competition> competitions = db.competitions();
        assertThat(competitions.size(), is(1));
    }

    @Test
    public void only_one_attending_can_be_stored_at_time() {

        String userId = UUID.randomUUID().toString();

        SimpleDatabase db = new SimpleDatabase();

        Competition competition = db.createCompetition(userId, "name", "type");

        db.add(new AttendingInfo(userId, competition.id));
        db.add(new AttendingInfo(userId, competition.id));

        assertThat(db.competitionsByAttendingUser(userId).size(), is(1));
    }

    @Test
    public void after_remove_attending_it_cannot_be_found_from_competitions_list() {

        String userId = UUID.randomUUID().toString();

        SimpleDatabase db = new SimpleDatabase();

        Competition competition = db.createCompetition(userId, "name", "type");

        db.add(new AttendingInfo(userId, competition.id));
        assertThat(db.competitionsByAttendingUser(userId).size(), is(1));

        db.remove(new AttendingInfo(userId, competition.id));
        assertThat(db.competitionsByAttendingUser(userId).size(), is(0));
    }

}