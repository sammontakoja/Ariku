package io.ariku.owner;

import io.ariku.user.UserService;
import io.ariku.util.data.Competition;
import io.ariku.util.data.CompetitionStateRepository;
import io.ariku.verification.AuthorizeRequest;
import io.ariku.verification.UserVerificationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Ari Aaltonen
 */
public class OwnerService {

    public CompetitionRepository competitionRepository;
    public OwnerRecordRepository ownerRecordRepository;
    public UserService userService;
    public UserVerificationService userVerificationService;
    public CompetitionStateRepository competitionStateRepository;

    public Optional<Competition> createNewCompetition(NewCompetitionRequest newCompetitionRequest) {

        String userIdOfAuthorizedUser = userVerificationService.userIdOfAuthorizedUser(newCompetitionRequest.authorizeRequest);
        if (userIdOfAuthorizedUser.isEmpty())
            return Optional.empty();

        if (newCompetitionRequest.competitionName.length() < 2 || newCompetitionRequest.competitionType.length() < 2)
            return Optional.empty();

        Competition competition = new Competition();
        competition.setId(competitionRepository.uniqueId());
        competition.setName(newCompetitionRequest.competitionName);
        competition.setType(newCompetitionRequest.competitionType);
        competitionRepository.store(competition);

        OwnerRecord ownerRecord = new OwnerRecord();
        ownerRecord.setUserId(userIdOfAuthorizedUser);
        ownerRecord.setCompetitionId(competition.getId());
        ownerRecordRepository.store(ownerRecord);

        return Optional.of(competition);
    }

    public List<Competition> findOwnedCompetitions(AuthorizeRequest request) {

        String userIdOfAuthorizedUser = userVerificationService.userIdOfAuthorizedUser(request);

        if (!userIdOfAuthorizedUser.isEmpty()) {

            List<OwnerRecord> usersOwnerRecords = ownerRecordRepository.listByUserId(userIdOfAuthorizedUser);

            return usersOwnerRecords.stream()
                    .map(ownerRecord -> competitionRepository.get(ownerRecord.getCompetitionId()).get())
                    .collect(Collectors.toList());
        }

        return new ArrayList<>();
    }
}
