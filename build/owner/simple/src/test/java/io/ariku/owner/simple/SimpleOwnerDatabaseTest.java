package io.ariku.owner.simple;

import com.googlecode.junittoolbox.ParallelRunner;
import io.ariku.util.data.Competition;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * @author Ari Aaltonen
 */
@RunWith(ParallelRunner.class)
public class SimpleOwnerDatabaseTest {

    @Test
    public void happy_case() {
        SimpleOwnerDatabase db = new SimpleOwnerDatabase();

        for (int i=0; i<10; i++)
            db.createCompetition(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString());

        assertThat(db.competitions().size(), is(10));

        IntStream.range(0, 5)
                .mapToObj(db.competitions()::get)
                .collect(Collectors.toList())
                .forEach(c -> db.deleteCompetition(c.id));

        assertThat(db.competitions().size(), is(5));

        assertThat(db.competitionById("notExist").isPresent(), is(false));

        Competition competition = db.createCompetition("userA", "A", "RockPaperScissors");
        assertThat(db.competitionById(competition.id).isPresent(), is(true));

        assertThat(db.competitionById(competition.id).get().name, is("A"));
        db.updateCompetitionName("B", competition.id);
        assertThat(db.competitionById(competition.id).get().name, is("B"));

        assertThat(db.competitionById(competition.id).get().type, is("RockPaperScissors"));
        db.updateCompetitionType("RockPaperScissors2", competition.id);
        assertThat(db.competitionById(competition.id).get().type, is("RockPaperScissors2"));

        List<Competition> competitionsAfterCreatingFirstCompetition = db.competitionsByOwner("userA");
        assertThat(competitionsAfterCreatingFirstCompetition.size(), is(1));

        List<String> ownersBeforeAddingNewOwner = db.ownersByCompetition(competition.id);
        assertThat(ownersBeforeAddingNewOwner, hasItem("userA"));

        db.addOwner("userB", competition.id);

        List<String> ownersAfterAddingNewOwner = db.ownersByCompetition(competition.id);
        assertThat(ownersAfterAddingNewOwner, hasItems("userA", "userB"));

        assertThat(db.competitionState(competition.id).get().attending, is(false));
        db.openAttending(competition.id);
        assertThat(db.competitionState(competition.id).get().attending, is(true));

        db.closeAttending(competition.id);
        assertThat(db.competitionState(competition.id).get().attending, is(false));

        db.openCompetition(competition.id);
        assertThat(db.competitionState(competition.id).get().open, is(true));

        db.closeCompetition(competition.id);
        assertThat(db.competitionState(competition.id).get().open, is(false));
    }

    @Test
    public void empty_list_returned_when_there_are_no_owners() {
        SimpleOwnerDatabase db = new SimpleOwnerDatabase();
        String competitinId = UUID.randomUUID().toString();
        List<String> owners = db.ownersByCompetition(competitinId);
        assertThat(owners.size(), is(0));
    }

    @Test
    public void after_adding_user_as_competition_owner_user_can_be_found_when_listing_competition_owners() {
        SimpleOwnerDatabase db = new SimpleOwnerDatabase();
        String userId = UUID.randomUUID().toString();
        String competitinId = UUID.randomUUID().toString();
        db.addOwner(userId, competitinId);
        List<String> owners = db.ownersByCompetition(competitinId);
        assertThat(owners, hasItem(userId));
    }

    @Test
    public void same_user_cannot_be_added_twice_as_competition_owner() {
        SimpleOwnerDatabase db = new SimpleOwnerDatabase();
        String userId = UUID.randomUUID().toString();
        String competitinId = UUID.randomUUID().toString();
        db.addOwner(userId, competitinId);
        db.addOwner(userId, competitinId);
        List<String> owners = db.ownersByCompetition(competitinId);
        assertThat(owners.size(), is(1));
    }

}