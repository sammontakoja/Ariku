package io.ariku.composer;

import io.ariku.database.simple.MemoryRepository;
import io.ariku.owner.CompetitionRepository;
import io.ariku.owner.OwnerRecordRepository;
import io.ariku.owner.OwnerService;
import io.ariku.user.UserService;
import io.ariku.util.data.CompetitionStateRepository;
import io.ariku.util.data.UserRepository;
import io.ariku.verification.SecurityCleaner;
import io.ariku.verification.UserVerificationRepository;
import io.ariku.verification.UserVerificationService;

/**
 * @author Ari Aaltonen
 */
public class Composer {

    // Services
    public final UserVerificationService userVerificationService;
    public final OwnerService ownerService;
    public final UserService userService;

    // Database implementations
    private final UserVerificationRepository userVerificationRepository;
    private final CompetitionRepository competitionRepository;
    private final OwnerRecordRepository ownerRecordRepository;
    private final UserRepository userRepository;
    private final CompetitionStateRepository competitionStateRepository;

    public Composer() {

        MemoryRepository memoryRepository = new MemoryRepository();
        this.userVerificationRepository = memoryRepository.userVerificationRepository;
        this.competitionRepository = memoryRepository.competitionRepository;
        this.ownerRecordRepository = memoryRepository.ownerRecordRepository;
        this.userRepository = memoryRepository.userRepository;
        this.competitionStateRepository = memoryRepository.competitionStateRepository;

        // Build services using implemented databases
        userVerificationService = new UserVerificationService();
        userVerificationService.userRepository = userRepository;
        userVerificationService.userVerificationRepository = userVerificationRepository;

        SecurityCleaner securityCleaner = new SecurityCleaner();
        securityCleaner.userVerificationRepository = userVerificationRepository;
        securityCleaner.wipeTokensWhichAreOlderThan(60, 900);

        userService = new UserService();
        userService.userRepository = userRepository;

        ownerService = new OwnerService();
        ownerService.competitionRepository = competitionRepository;
        ownerService.competitionStateRepository = competitionStateRepository;
        ownerService.ownerRecordRepository = ownerRecordRepository;
        ownerService.userService = userService;
        ownerService.userVerificationService = userVerificationService;
    }

}
