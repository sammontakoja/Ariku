package io.ariku.util.data;

/**
 * @author Ari Aaltonen
 */
public class RestSettings {

    // Default values
    public String protocol = "http";
    public String host = "localhost";
    public String port = "5000";

    // **** owner ****
    public String ownerPath() {
        return "/owner";
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
        return urlPrefix() + ownerPath() + competitionNewPath();
    }
    public String competitionAddOwnerUrl() {
        return urlPrefix() + ownerPath() + addOwnerPath();
    }
    public String competitionListByOwnerUrl() {
        return urlPrefix() + ownerPath() + competitionListPath();
    }

    // **** Verification ****
    public String signUpUrl() {
        return urlPrefix() + verificationPath() + signUpPath();
    }
    public String verifySignUpUrl() {
        return urlPrefix() + verificationPath() + verifySignUpPath();
    }
    public String loginUrl() {
        return urlPrefix() + verificationPath() + loginPath();
    }
    public String logoutUrl() {
        return urlPrefix() + verificationPath() + logoutPath();
    }
    private String urlPrefix() {
        return protocol + "://" + host + ":" + port;
    }
    public String verificationPath() {
        return "/verification";
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
}
