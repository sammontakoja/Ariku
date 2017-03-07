package io.ariku.gui.console;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import io.ariku.verification.AuthorizeRequest;
import io.ariku.verification.UserVerificationService;

/**
 * @author Ari Aaltonen
 */
public class LogoutPage {

    public UserVerificationMenu userVerificationMenu;
    UserVerificationService userVerificationService;

    public void draw(BasicWindow window) {

        Panel panel = new Panel();

        TextBox emailAddressText = new TextBox();
        emailAddressText.addTo(panel);

        Button okButton = new Button("Logout", () -> logout(emailAddressText.getText(), UserCache.securityMessage));
        okButton.addTo(panel);

        Button exitButton = new Button("Exit", () -> userVerificationMenu.draw(window));
        exitButton.addTo(panel);

        window.setComponent(panel);
    }

    private void logout(String email, String securityMessage) {

        if (email.isEmpty())
            return;

        userVerificationService.logout(new AuthorizeRequest(email, securityMessage));
    }

}
