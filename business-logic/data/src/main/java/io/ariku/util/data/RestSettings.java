package io.ariku.util.data;

/**
 * @author Ari Aaltonen
 */
public class RestSettings {

    public String protocol = "http";
    public String host = "localhost";
    public String port = "5000";
    public String rootPath = "/rest";

    // **** owner ****
    public String ownerPath() {
        return rootPath + "/owner";
    }
    public String competitionListPath() {
        return "/competitions";
    }
    public String competitionNewPath() {
        return "/newcompetition";
    }
    public String addOwnerPath() {
        return "/addowner";
    }
    public String competitionNewUrl() {
        return serviceUrl() + ownerPath() + competitionNewPath();
    }
    public String competitionAddOwnerUrl() {
        return serviceUrl() + ownerPath() + addOwnerPath();
    }
    public String competitionListByOwnerUrl() {
        return serviceUrl() + ownerPath() + competitionListPath();
    }

    // **** Verification ****
    public String verificationPath() {
        return rootPath + "/verification";
    }
    public String signUpUrl() {
        return serviceUrl() + verificationPath() + signUpPath();
    }
    public String verifySignUpUrl() {
        return serviceUrl() + verificationPath() + verifySignUpPath();
    }
    public String loginUrl() {
        return serviceUrl() + verificationPath() + loginPath();
    }
    public String logoutUrl() {
        return serviceUrl() + verificationPath() + logoutPath();
    }
    public String signUpPath() {
        return "/signup";
    }
    public String loginPath() {
        return "/login";
    }
    public String logoutPath() {
        return "/logout";
    }
    public String verifySignUpPath() {
        return "/verifysignup";
    }

    private String serviceUrl() {
        return protocol + "://" + host + ":" + port;
    }
}
