package ariku.verification;

/**
 * @author Ari Aaltonen
 */
public class VerifySignUpRequest {

    public String username = "";

    public VerifySignUpRequest() {
    }

    public VerifySignUpRequest(String username) {
        this.username = username;
    }
}
