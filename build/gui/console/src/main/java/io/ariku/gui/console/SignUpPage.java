package io.ariku.gui.console;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import io.ariku.verification.SignUpRequest;
import io.ariku.verification.UserVerificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ari Aaltonen
 */
public class SignUpPage {
    public static Logger logger = LoggerFactory.getLogger(SignUpPage.class);

    public UserVerificationMenu userVerificationMenu;
    public RestClient restClient;

    public void draw(BasicWindow window) {

        Panel panel = new Panel();

        TextBox username = new TextBox();
        username.addTo(panel);

        Button okButton = new Button("OK", () -> {
            String response = restClient.signUpRequest(username.getText());
            logger.debug("SignUpRequest with username:{} {}", username, response);
        });
        okButton.addTo(panel);

        Button exitButton = new Button("Exit", () -> userVerificationMenu.draw(window));
        exitButton.addTo(panel);

        window.setComponent(panel);
    }

}
