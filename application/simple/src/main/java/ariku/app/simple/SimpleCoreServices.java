package ariku.app.simple;

import ariku.CoreServices;
import ariku.database.CompetitionRepository;
import ariku.database.OwnerRecordRepository;
import ariku.owner.OwnerService;
import ariku.user.UserService;
import ariku.database.CompetitionStateRepository;
import ariku.database.UserRepository;
import ariku.verification.SecurityCleaner;
import ariku.database.UserVerificationRepository;
import ariku.verification.UserVerificationService;
import com.google.auto.service.AutoService;
import ariku.database.impl.MemoryRepository;

/**
 * @author Ari Aaltonen
 */
@AutoService(CoreServices.class)
public class SimpleCoreServices implements CoreServices {

    // Services
    private final UserVerificationService userVerificationService;
    private final OwnerService ownerService;
    private final UserService userService;
    // Database implementations
    private final UserVerificationRepository userVerificationRepository;
    private final CompetitionRepository competitionRepository;
    private final OwnerRecordRepository ownerRecordRepository;
    private final UserRepository userRepository;
    private final CompetitionStateRepository competitionStateRepository;

    public SimpleCoreServices() {

        MemoryRepository memoryRepository = new MemoryRepository();
        this.userVerificationRepository = memoryRepository.userVerificationRepository;
        this.competitionRepository = memoryRepository.competitionRepository;
        this.ownerRecordRepository = memoryRepository.ownerRecordRepository;
        this.userRepository = memoryRepository.userRepository;
        this.competitionStateRepository = memoryRepository.competitionStateRepository;

        // Build services using memory based reposiories
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

    public UserVerificationService userVerificationService() {
        return userVerificationService;
    }

    public OwnerService ownerService() {
        return ownerService;
    }

    public UserService userService() {
        return userService;
    }
}
