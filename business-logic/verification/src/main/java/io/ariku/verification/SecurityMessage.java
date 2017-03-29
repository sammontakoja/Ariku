package io.ariku.verification;

/**
 * @author Ari Aaltonen
 */
public class SecurityMessage {

    private String token = "";
    private String lastSecurityActivity = "";

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLastSecurityActivity() {
        return lastSecurityActivity;
    }

    public void setLastSecurityActivity(String lastSecurityActivity) {
        this.lastSecurityActivity = lastSecurityActivity;
    }

    @Override
    public String toString() {
        return "SecurityMessage{" +
                "token='" + token + '\'' +
                ", lastSecurityActivity='" + lastSecurityActivity + '\'' +
                '}';
    }
}
