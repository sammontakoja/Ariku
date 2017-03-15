package io.ariku.gui.console;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Panel;
import io.ariku.rest.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * @author Ari Aaltonen
 */
public class UserVerificationMenu {
    public static Logger logger = LoggerFactory.getLogger(UserVerificationMenu.class);

    public RestClient restClient;
    public VerifySignUpPage verifySignUpPage;
    public SignUpPage signUpPage;
    public LoginPage loginPage;
    public BaseMenu baseMenu;

    public void draw(BasicWindow window) {

        List<Button> buttons = Arrays.asList(
                new Button("SignUp", () -> {
                    logger.debug("Pressed SignUp");
                    signUpPage.draw(window);
                }),
                new Button("VerifySignUp", () -> {
                    logger.debug("Pressed SignUp");
                    verifySignUpPage.draw(window);
                }),
                new Button("Login", () -> {
                    logger.debug("Pressed login");
                    loginPage.draw(window);
                }),
                new Button("Logout", () -> {
                    logger.debug("Pressed logout");
                    logout();

                }),
                new Button("Menu", () -> baseMenu.draw(window))
        );

        Panel panel = new Panel();
        buttons.forEach(button -> button.addTo(panel));

        window.setComponent(panel);
    }

    private void logout() {
        String username = UserCache.authorizeRequest().username;
        String response = restClient.logoutRequest(UserCache.authorizeRequest().username, UserCache.authorizeRequest().securityToken).get();

        logger.debug("LogoutRequest response '{}' with username:{}", response, username);

        if (response.equals("OK")) {
            UserCache.clear();
        }
    }

}
