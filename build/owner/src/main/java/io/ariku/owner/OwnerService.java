package io.ariku.owner;

import io.ariku.user.UserService;
import io.ariku.util.data.Competition;
import io.ariku.util.data.CompetitionStateRepository;
import io.ariku.verification.AuthorizeRequest;
import io.ariku.verification.UserVerificationService;

import java.util.List;
import java.util.Optional;

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
        return null;
    }

    public List<Competition> findOwnedCompetitions(AuthorizeRequest request) {
        return null;
    }
}
