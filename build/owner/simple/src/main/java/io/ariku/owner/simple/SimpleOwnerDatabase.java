package io.ariku.owner.simple;

import io.ariku.owner.api.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Ari Aaltonen
 */
public class SimpleOwnerDatabase implements OwnerDatabase, CompetitionDatabase, CompetitionStateDatabase {

    private HashMap<String, ArrayList<String>> competitionOwners = new HashMap<>();
    private List<Competition> competitions = new ArrayList<>();
    private List<CompetitionState> competitionStates = new ArrayList<>();

    @Override
    public void addOwner(String userId, String competitionId) {
        if (competitionOwners.get(competitionId) == null)
            competitionOwners.put(competitionId, new ArrayList<>());

        ArrayList<String> owners = competitionOwners.get(competitionId);
        if (!owners.contains(userId))
            competitionOwners.get(competitionId).add(userId);
    }

    @Override
    public List<String> ownersByCompetition(String competitionId) {
        if (competitionOwners.get(competitionId) == null)
            competitionOwners.put(competitionId, new ArrayList<>());

        return competitionOwners.get(competitionId);
    }

    @Override
    public Competition createCompetition(String userId, String competitionName, String competitionType) {
        Competition competition = new Competition();
        competition.id = UUID.randomUUID().toString();
        competition.name = competitionName;
        competition.type = competitionType;
        competitions.add(competition);

        addOwner(userId, competition.id);

        CompetitionState competitionState = new CompetitionState();
        competitionState.competitionId = competition.id;
        competitionStates.add(competitionState);

        return competition;
    }

    @Override
    public void deleteCompetition(String id) {
        Optional<Competition> competition = competitions.stream()
                .filter(c -> c.id.equals(id))
                .findFirst();

        if (competition.isPresent())
            competitions.remove(competition.get());
    }

    @Override
    public void updateCompetitionName(String name, String id) {
        competitions.forEach(c -> {
            if (c.id.equals(id)) c.name = name;
        });
    }

    @Override
    public void updateCompetitionType(String type, String id) {
        competitions.forEach(c -> {
            if (c.id.equals(id)) c.type = type;
        });
    }

    @Override
    public List<Competition> competitionsByOwner(String userId) {
        return competitions.stream()
                .filter(c -> competitionOwners.get(c.id).contains(userId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Competition> competitions() {
        return new ArrayList<>(competitions);
    }

    @Override
    public Optional<Competition> competitionById(String id) {
        return competitions.stream().filter(c -> c.id.equals(id)).findFirst();
    }

    @Override
    public Optional<CompetitionState> competitionState(String competitionId) {
        return competitionStates.stream().filter(s -> s.competitionId.equals(competitionId)).findFirst();
    }

    @Override
    public void openAttending(String competitionId) {
        competitionStates.stream()
                .filter(s -> s.competitionId.equals(competitionId))
                .findFirst().get().attending = true;
    }

    @Override
    public void closeAttending(String competitionId) {
        competitionStates.stream()
                .filter(s -> s.competitionId.equals(competitionId))
                .findFirst().get().attending = false;
    }

    @Override
    public void openCompetition(String competitionId) {
        competitionStates.stream()
                .filter(s -> s.competitionId.equals(competitionId))
                .findFirst().get().open = true;
    }

    @Override
    public void closeCompetition(String competitionId) {
        competitionStates.stream()
                .filter(s -> s.competitionId.equals(competitionId))
                .findFirst().get().open = false;
    }
}
