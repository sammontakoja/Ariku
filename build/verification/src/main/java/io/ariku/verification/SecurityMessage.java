package io.ariku.verification;

/**
 * @author Ari Aaltonen
 */
public class SecurityMessage {
    public String token = "";
    public String lastSecurityActivity = "";

    @Override
    public String toString() {
        return "SecurityMessage{" +
                "token='" + token + '\'' +
                ", lastSecurityActivity='" + lastSecurityActivity + '\'' +
                '}';
    }
}
