package io.ariku.gui.console;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import io.ariku.verification.LoginRequest;
import io.ariku.verification.UserVerificationService;

import static io.ariku.composer.Composer.SIMPLE;

/**
 * @author Ari Aaltonen
 */
public class LoginPage {

    public static void draw(BasicWindow window) {

        Panel panel = new Panel();

        TextBox emailAddressText = new TextBox();
        emailAddressText.addTo(panel);

        Button okButton = new Button("Login", () -> login(emailAddressText.getText()));
        okButton.addTo(panel);

        Button exitButton = new Button("Exit", () -> UserVerificationMenu.draw(window));
        exitButton.addTo(panel);

        window.setComponent(panel);
    }

    private static void login(String userId) {

        UserVerificationService userVerificationService = SIMPLE.userVerificationService;

        if (userId.isEmpty())
            return;

        String securityMessage = userVerificationService.login(new LoginRequest(userId));

        if (!securityMessage.isEmpty()) {
            UserCache.securityMessage = securityMessage;
            UserCache.userId = userId;
            System.out.println("Login OK " + userId);
        }

        else {
            System.out.println("Login FAIL " + userId);
        }

    }

}
