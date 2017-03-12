package io.ariku.gui.console;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ari Aaltonen
 */
public class VerifySignUpPage {
    public static Logger logger = LoggerFactory.getLogger(SignUpPage.class);

    public UserVerificationMenu userVerificationMenu;
    public RestClient restClient;

    public void draw(BasicWindow window) {

        Panel panel = new Panel();

        TextBox username = new TextBox();
        username.addTo(panel);

        panel.addComponent(new Button("OK", () -> {
            String response = restClient.verifySignUp(username.getText());
            logger.debug("VerifySignUpRequest response '{}' with username:{}", response, username.getText());
        }));

        panel.addComponent(new Button("Exit", () -> userVerificationMenu.draw(window)));

        window.setComponent(panel);
    }

}
