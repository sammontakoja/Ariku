package io.ariku.database.simple;

import io.ariku.owner.CompetitionDatabase;
import io.ariku.owner.OwnerDatabase;
import io.ariku.user.AttendingDatabase;
import io.ariku.user.AttendingInfo;
import io.ariku.util.data.Competition;
import io.ariku.util.data.CompetitionState;
import io.ariku.util.data.CompetitionStateDatabase;
import io.ariku.verification.UserVerificationDatabase;
import io.ariku.verification.UserVerification;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Ari Aaltonen
 */
public class SimpleDatabase implements OwnerDatabase, CompetitionDatabase, CompetitionStateDatabase, UserVerificationDatabase, AttendingDatabase {

    private HashMap<String, UserVerification> userVerifications = new HashMap<>();
    private HashMap<String, ArrayList<String>> competitionOwners = new HashMap<>();
    private List<Competition> competitions = new ArrayList<>();
    private List<CompetitionState> competitionStates = new ArrayList<>();
    private List<AttendingInfo> attendingInfos = new ArrayList<>();

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

    @Override
    public void createUserVerification(String userId) {
        UserVerification userVerification = new UserVerification(userId);
        userVerification.isSignedIn = true;
        userVerifications.put(userId, userVerification);
    }

    @Override
    public UserVerification readUserVerification(String userId) {
        UserVerification foundUserVerification = userVerifications.get(userId);
        if (foundUserVerification != null)
            return foundUserVerification;
        else
            return new UserVerification();
    }

    @Override
    public void deleteUserVerification(String userId) {
        userVerifications.remove(userId);
    }

    @Override
    public void updateUserVerification(UserVerification userVerification) {
        UserVerification foundVerification = userVerifications.get(userVerification.userId);
        if (foundVerification != null) {
            foundVerification.isSignedInConfirmed = userVerification.isSignedInConfirmed;
            foundVerification.isSignedIn = userVerification.isSignedIn;
            foundVerification.securityMessage = userVerification.securityMessage;
        }
    }

    @Override
    public List<UserVerification> userVerifications() {
        return new ArrayList<>(userVerifications.values());
    }

    @Override
    public void add(AttendingInfo a) {
        attendingInfos.add(a);
    }

    @Override
    public void remove(AttendingInfo a) {
        Optional<AttendingInfo> attendingInfo = attendingInfos.stream()
                .filter(x -> x.userId.equals(a.userId) && x.competitionId.equals(a.competitionId))
                .findFirst();

        if (attendingInfo.isPresent())
            attendingInfos.remove(attendingInfo.get());
    }

    @Override
    public List<Competition> competitionsByAttendingUser(String userId) {
        List<AttendingInfo> usersAttendingInfos = attendingInfos.stream()
                .filter(x -> x.userId.equals(userId))
                .collect(Collectors.toList());

        return competitions.stream()
                .filter(x -> usersAttendingInfos.stream().anyMatch(y -> y.competitionId.equals(x.id)))
                .collect(Collectors.toList());
    }

}
