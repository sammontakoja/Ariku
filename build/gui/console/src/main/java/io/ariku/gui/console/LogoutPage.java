package io.ariku.gui.console;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import io.ariku.verification.AuthorizeRequest;
import io.ariku.verification.UserVerificationService;

import static io.ariku.composer.Composer.SIMPLE;

/**
 * @author Ari Aaltonen
 */
public class LogoutPage {

    public static void draw(BasicWindow window) {

        Panel panel = new Panel();

        TextBox emailAddressText = new TextBox();
        emailAddressText.addTo(panel);

        Button okButton = new Button("Logout", () -> logout(emailAddressText.getText(), UserCache.securityMessage));
        okButton.addTo(panel);

        Button exitButton = new Button("Exit", () -> UserVerificationMenu.draw(window));
        exitButton.addTo(panel);

        window.setComponent(panel);
    }

    private static void logout(String email, String securityMessage) {

        UserVerificationService userVerificationService = SIMPLE.userVerificationService;

        if (email.isEmpty())
            return;

        userVerificationService.logout(new AuthorizeRequest(email, securityMessage));
    }

    public static void print(String value) {
        System.out.println(value);
    }

}
