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
public class LoginPage {
    public static Logger logger = LoggerFactory.getLogger(LoginPage.class);

    public UserVerificationMenu userVerificationMenu;
    public RestClient restClient;

    public void draw(BasicWindow window) {

        Panel panel = new Panel();

        TextBox username = new TextBox();
        username.addTo(panel);

        panel.addComponent(new Button("Login", () -> {

            String securityToken = restClient.login(username.getText());

            if (!securityToken.isEmpty()) {
                UserCache.securityToken = securityToken;
                UserCache.username = username.getText();
                logger.debug("LoginRequest response '{}' with username:{}", securityToken, username.getText());
            }
        }));

        panel.addComponent(new Button("Exit", () -> userVerificationMenu.draw(window)));

        window.setComponent(panel);
    }

}
