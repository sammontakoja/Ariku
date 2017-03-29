package ariku.user;

import ariku.database.AttendingRepository;
import ariku.database.CompetitionStateRepository;
import ariku.util.User;
import ariku.database.UserRepository;
import ariku.verification.UserAuthorizer;

import java.util.Optional;

/**
 * @author Ari Aaltonen
 */
public class UserService {

    public UserRepository userRepository;
    public UserAuthorizer userAuthorizer;
    public AttendingRepository attendingRepository;
    public CompetitionStateRepository competitionStateRepository;

    public Optional<User> findUserByUsername(String username) {
        return userRepository.getByUsername(username);
    }

//    public void attendToCompetition(ParticipateRequest request) {
//        if (userIsAuthorizedAndCompetitionIsOpenToAttending(request))
//            attendingRepository.add(new AttendingInfo(request.authorizeRequest.username, request.competitionId));
//    }
//
//    public void cancelAttendingToCompetition(ParticipateRequest request) {
//        if (userIsAuthorizedAndCompetitionIsOpenToAttending(request))
//            attendingRepository.remove(new AttendingInfo(request.authorizeRequest.username, request.competitionId));
//    }
//
//    private boolean userIsAuthorizedAndCompetitionIsOpenToAttending(ParticipateRequest request) {
//        if (!userAuthorizer.userIdOfAuthorizedUser(request.authorizeRequest).isEmpty()) {
//            Optional<CompetitionState> competitionState = competitionStateRepository.competitionState(request.competitionId);
//            return (competitionState.isPresent() && competitionState.get().attending);
//        }
//        return false;
//    }
//
//    public List<Competition> competitions(AuthorizeRequest request) {
//        if (!userAuthorizer.userIdOfAuthorizedUser(request).isEmpty())
//            return attendingRepository.competitionsByAttendingUser(request.username);
//        return new ArrayList<>();
//    }
//
//    public Optional<User> findUserByUsername(String usernameOfUserA) {
//        return userRepository.findUserByUsername(usernameOfUserA);
//    }
}
