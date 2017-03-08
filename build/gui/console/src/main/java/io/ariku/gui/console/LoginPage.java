package io.ariku.gui.console;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import io.ariku.verification.LoginRequest;
import io.ariku.verification.UserVerificationService;

/**
 * @author Ari Aaltonen
 */
public class LoginPage {

    public UserVerificationMenu userVerificationMenu;
    public UserVerificationService userVerificationService;

    public void draw(BasicWindow window) {

        Panel panel = new Panel();

        TextBox emailAddressText = new TextBox();
        emailAddressText.addTo(panel);

        Button okButton = new Button("Login", () -> login(emailAddressText.getText()));
        okButton.addTo(panel);

        Button exitButton = new Button("Exit", () -> userVerificationMenu.draw(window));
        exitButton.addTo(panel);

        window.setComponent(panel);
    }

    private void login(String userId) {

        if (userId.isEmpty())
            return;

        String securityMessage = userVerificationService.login(new LoginRequest(userId));

        if (!securityMessage.isEmpty()) {
            UserCache.securityToken = securityMessage;
            UserCache.username = userId;
            System.out.println("Login OK " + userId);
        }

        else {
            System.out.println("Login FAIL " + userId);
        }

    }

}
