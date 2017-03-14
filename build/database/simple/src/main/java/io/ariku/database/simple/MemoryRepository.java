package io.ariku.database.simple;

import io.ariku.owner.CompetitionRepository;
import io.ariku.owner.OwnerRecord;
import io.ariku.owner.OwnerRecordRepository;
import io.ariku.user.AttendingInfo;
import io.ariku.util.data.*;
import io.ariku.verification.UserVerification;
import io.ariku.verification.UserVerificationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Ari Aaltonen
 */
public class MemoryRepository {

    private List<User> users = new ArrayList<>();
    private List<UserVerification> userVerifications = new ArrayList<>();
    private List<OwnerRecord> ownerRecords = new ArrayList<>();
    private List<Competition> competitions = new ArrayList<>();
    private List<CompetitionState> competitionStates = new ArrayList<>();
    private List<AttendingInfo> attendingInfos = new ArrayList<>();
    private List<String> uniqueIds = new ArrayList<>();
    
    public final OwnerRecordRepository ownerRecordRepository = new OwnerRecordRepository() {
        public void store(OwnerRecord ownerRecord) {
            ownerRecords.add(ownerRecord);
        }
        public Optional<OwnerRecord> get(String owner) {
            throw new RuntimeException("not implemented");
        }
        public List<OwnerRecord> list(OwnerRecord ownerRecord) {
            throw new RuntimeException("not implemented");
        }

        @Override
        public List<OwnerRecord> listByUserId(String userId) {
            return ownerRecords.stream().filter(o -> o.getUserId().equals(userId)).collect(Collectors.toList());
        }

        @Override
        public List<OwnerRecord> listByCompetitionId(String competitionId) {
            return ownerRecords.stream().filter(o -> o.getCompetitionId().equals(competitionId)).collect(Collectors.toList());
        }

        public void update(OwnerRecord ownerRecord) {
            throw new RuntimeException("not implemented");
        }
        public void delete(String owner) {
            throw new RuntimeException("not implemented");
        }
    };

    public final CompetitionRepository competitionRepository = new CompetitionRepository() {
        public void store(Competition competition) {
            competitions.add(competition);
        }
        public Optional<Competition> get(String competitionId) {
            return competitions.stream().filter(c -> c.id.equals(competitionId)).findFirst();
        }
        public List<Competition> list(Competition competition) {
            throw new RuntimeException("not implemented");
        }
        public void update(Competition competition) {
            throw new RuntimeException("not implemented");
        }
        public void delete(String competitionId) {
            throw new RuntimeException("not implemented");
        }
        public String uniqueId() {
            while(true) {
                String uniqueUserId = UUID.randomUUID().toString();
                if (!uniqueIds.contains(uniqueUserId))
                    return uniqueUserId;
            }
        }
    };

    public final CompetitionStateRepository competitionStateRepository = new CompetitionStateRepository() {
        public void store(CompetitionState competition) {
            throw new RuntimeException("not implemented");
        }
        public Optional<CompetitionState> get(String competitionId) {
            throw new RuntimeException("not implemented");
        }
        public List<CompetitionState> list(Competition competition) {
            throw new RuntimeException("not implemented");
        }
        public void update(CompetitionState competition) {
            throw new RuntimeException("not implemented");
        }
        public void delete(CompetitionState competitionId) {
            throw new RuntimeException("not implemented");
        }
    };

    public final UserVerificationRepository userVerificationRepository = new UserVerificationRepository() {
        public void store(UserVerification x) {
            userVerifications.add(x);
        }
        public Optional<UserVerification> getByUsername(String username) {
            return userVerifications.stream().filter(u -> u.getUsername().equals(username)).findFirst();
        }
        public Optional<UserVerification> get(String id) {
            throw new RuntimeException("not implemented");
        }
        public List<UserVerification> list(UserVerification x) {
            throw new RuntimeException("not implemented");
        }
        public List<UserVerification> list() {
            throw new RuntimeException("not implemented");
        }
        public void update(UserVerification x) {
            Optional<UserVerification> found = userVerifications.stream().filter(u -> u.getUserId().equals(x.getUserId())).findFirst();
            found.ifPresent(userVerification -> {
                userVerifications.remove(found.get());
                userVerifications.add(x);
            });
        }
        public void delete(UserVerification x) {
            throw new RuntimeException("not implemented");
        }
        public String uniqueUserId() {
            while(true) {
                String uniqueUserId = UUID.randomUUID().toString();
                if (!uniqueIds.contains(uniqueUserId))
                    return uniqueUserId;
            }
        }
    };

    public final UserRepository userRepository = new UserRepository() {
        public void store(User user) {
            boolean userStoredAlready = users.stream().anyMatch(u -> u.getId().equals(user.getId()));
            if (!userStoredAlready)
                users.add(user);
        }
        public Optional<User> getById(String id) {
            throw new RuntimeException("not implemented");
        }
        public Optional<User> getByUsername(String username) {
            return users.stream().filter(u -> u.getUsername().equals(username)).findFirst();
        }
        public List<User> list(User owner) {
            throw new RuntimeException("not implemented");
        }
        public void update(User owner) {
            throw new RuntimeException("not implemented");
        }
        public void delete(User owner) {
            throw new RuntimeException("not implemented");
        }
    };

}
