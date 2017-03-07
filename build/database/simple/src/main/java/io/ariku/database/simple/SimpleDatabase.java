package io.ariku.database.simple;

import io.ariku.owner.CompetitionDatabase;
import io.ariku.owner.Owner;
import io.ariku.owner.OwnerDatabase;
import io.ariku.owner.UserDatabase;
import io.ariku.user.AttendingDatabase;
import io.ariku.user.AttendingInfo;
import io.ariku.util.data.Competition;
import io.ariku.util.data.CompetitionState;
import io.ariku.util.data.CompetitionStateDatabase;
import io.ariku.util.data.User;
import io.ariku.verification.UserVerification;
import io.ariku.verification.UserVerificationDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Ari Aaltonen
 */
public class SimpleDatabase implements OwnerDatabase, CompetitionDatabase, CompetitionStateDatabase, UserVerificationDatabase, AttendingDatabase, UserDatabase {

    private List<User> users = new ArrayList<>();
    private List<UserVerification> userVerifications = new ArrayList<>();
    private List<Owner> competitionOwners = new ArrayList<>();
    private List<Competition> competitions = new ArrayList<>();
    private List<CompetitionState> competitionStates = new ArrayList<>();
    private List<AttendingInfo> attendingInfos = new ArrayList<>();

    @Override
    public void addOwner(Owner owner) {
        if (!competitionOwners.stream().anyMatch(o -> o.equals(owner)))
            competitionOwners.add(owner);
    }

    @Override
    public List<Owner> ownersByCompetition(String competitionId) {
        return competitionOwners.stream()
                .filter(o -> o.competitionId.equals(competitionId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Owner> owners() {
        return competitionOwners;
    }

    @Override
    public Competition createCompetition(String userId, String competitionName, String competitionType) {
        Competition competition = new Competition();
        competition.id = UUID.randomUUID().toString();
        competition.name = competitionName;
        competition.type = competitionType;
        competitions.add(competition);

        addOwner(new Owner(userId, competition.id));

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

        List<Owner> ownersWithWantedUserId = competitionOwners.stream()
                .filter(owner -> owner.userId.equals(userId))
                .collect(Collectors.toList());

        return competitions.stream()
                .filter(c -> ownersWithWantedUserId.stream().anyMatch(owner -> owner.competitionId.equals(c.id)))
                .collect(Collectors.toList());
    }

    @Override
    public List<Competition> competitions() {
        return competitions;
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
    public void createUserVerification(String username) {
        if (!usernameTaken(username)) {
            UserVerification userVerification = new UserVerification();
            userVerification.userId = createUniqueUserId();
            userVerification.username = username;
            userVerifications.add(userVerification);
        }
    }

    private String createUniqueUserId() {
        while (true) {
            String id = UUID.randomUUID().toString();
            boolean sameIdFound = userVerifications.stream().anyMatch(userVerification -> userVerification.userId.equals(id));
            if (!sameIdFound)
                return id;
        }
    }

    private boolean usernameTaken(String username) {
        return userVerifications.stream().anyMatch(userVerification -> userVerification.username.equals(username));
    }

    @Override
    public Optional<UserVerification> findByUserId(String userId) {
        return userVerifications.stream().filter(u -> u.userId.equals(userId)).findFirst();
    }

    @Override
    public Optional<UserVerification> findByUsername(String username) {
        return userVerifications.stream().filter(u -> u.username.equals(username)).findFirst();
    }

    @Override
    public void deleteUserVerification(String userId) {
        userVerifications.remove(userId);
    }

    @Override
    public void updateUserVerification(UserVerification userVerification) {

        Optional<UserVerification> found = userVerifications.stream().filter(u -> u.userId.equals(userVerification.userId)).findFirst();

        found.ifPresent(u -> {
            u.isSignedInConfirmed = userVerification.isSignedInConfirmed;
            u.securityMessage = userVerification.securityMessage;
        });
    }

    @Override
    public List<UserVerification> userVerifications() {
        return new ArrayList<>(userVerifications);
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

    @Override
    public Optional<User> findUserByUsername(String username) {
        return users.stream()
                .filter(user -> user.username.equals(username))
                .findFirst();
    }
}
