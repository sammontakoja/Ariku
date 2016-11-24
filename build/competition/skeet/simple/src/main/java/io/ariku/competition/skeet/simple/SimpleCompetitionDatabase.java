package io.ariku.competition.skeet.simple;

import io.ariku.competition.skeet.api.Competition;
import io.ariku.competition.skeet.api.CompetitionDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Ari Aaltonen
 */
public class SimpleCompetitionDatabase implements CompetitionDatabase {

    private HashMap<String, ArrayList<Competition>> database = new HashMap();

    @Override
    public void create(String user, Competition competition) {
        if (!database.containsKey(user))
            database.put(user, new ArrayList<>());
        database.get(user).add(competition);
    }

    @Override
    public List<Competition> competitionsByUser(String user) {
        return new ArrayList(database.get(user));
    }

    @Override
    public List<Competition> competitions() {
        ArrayList list = new ArrayList();
        for (ArrayList<Competition> competitions : database.values())
            list.addAll(competitions);
        return list;
    }

    @Override
    public void delete(String user, String competitionId) {

    }

    @Override
    public void update(String user, String competitionId) {

    }
}
