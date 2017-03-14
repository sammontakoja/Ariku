package io.ariku.rest;

import io.ariku.owner.NewCompetitionRequest;
import io.ariku.owner.OwnerService;
import io.ariku.util.data.Competition;
import io.ariku.verification.AuthorizeRequest;
import io.ariku.verification.SignUpRequest;
import spark.Route;

import java.util.ArrayList;
import java.util.Optional;

import static io.ariku.rest.UserVerificationServiceCaller.logger;

/**
 * @author Ari Aaltonen
 */
public class OwnerServiceCaller {

    private RequestReader requestReader = new RequestReader();
    public OwnerService ownerService;

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
                logger.error("Failure with request:{}", request, e);
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
                logger.error("Failure url:{} request:{}", request.url(), request.queryParams(), e);
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
                logger.error("Failure url:{} request:{}", request.url(), request.queryParams(), e);
                response.status(500);
                return "FAIL";
            }
        };
    }

}
