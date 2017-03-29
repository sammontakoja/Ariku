package ariku.rest.backend;

import ariku.owner.NewCompetitionRequest;
import ariku.owner.OwnerService;
import ariku.util.AuthorizeRequest;
import ariku.util.Competition;
import spark.Route;

import java.util.ArrayList;

/**
 * @author Ari Aaltonen
 */
public class OwnerServiceCaller {

    private final RequestReader requestReader = new RequestReader();
    private final OwnerService ownerService;

    public OwnerServiceCaller(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    public Route createNewCompetition() {
        return (request, response) -> {

            String competitionName = request.queryParams("competition_name");
            String competitionType = request.queryParams("competition_type");
            String username = requestReader.username(request);
            String securityToken = requestReader.securityToken(request);

            AuthorizeRequest authorizeRequest = new AuthorizeRequest(username, securityToken);

            NewCompetitionRequest newCompetitionRequest = new NewCompetitionRequest()
                    .name(competitionName)
                    .type(competitionType)
                    .authorizeRequest(authorizeRequest);
            try {
                ownerService.createNewCompetition(newCompetitionRequest);
                return "OK";
            } catch (Exception e) {
                UserVerificationServiceCaller.logger.error("Failure with request:{}", request, e);
                response.status(500);
                return "FAIL";
            }
        };
    }

    public Route ownersCompetitions() {
        return (request, response) -> {

            String username = requestReader.username(request);
            String securityToken = requestReader.securityToken(request);
            AuthorizeRequest authorizeRequest = new AuthorizeRequest(username, securityToken);

            try {
                return ownerService.findOwnedCompetitions(authorizeRequest);
            } catch (Exception e) {
                UserVerificationServiceCaller.logger.error("Failure url:{} request:{}", request.url(), request.queryParams(), e);
                response.status(500);
                return new ArrayList<Competition>();
            }
        };
    }

    public Route addUserAsCompetitionOwner() {
        return (request, response) -> {

            String username = requestReader.username(request);
            String securityToken = requestReader.securityToken(request);
            AuthorizeRequest authorizeRequest = new AuthorizeRequest(username, securityToken);

            String competitionId = request.queryParams("competition_id");
            String newOwner = request.queryParams("username_new_owner");

            try {
                ownerService.addNewOwner(newOwner, competitionId, authorizeRequest);
                return "OK";
            } catch (Exception e) {
                UserVerificationServiceCaller.logger.error("Failure url:{} request:{}", request.url(), request.queryParams(), e);
                response.status(500);
                return "FAIL";
            }
        };
    }

}
