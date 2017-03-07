package io.ariku.gui.console;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import io.ariku.verification.SignUpRequest;
import io.ariku.verification.UserVerificationService;

/**
 * @author Ari Aaltonen
 */
public class SignUpPage {

    public UserVerificationMenu userVerificationMenu;
    public UserVerificationService userVerificationService;

    public void draw(BasicWindow window) {

        Panel panel = new Panel();

        TextBox emailAddressText = new TextBox();
        emailAddressText.addTo(panel);

        Button okButton = new Button("OK", () -> {
            String username = emailAddressText.getText();
            userVerificationService.signUp(new SignUpRequest(username));
        });
        okButton.addTo(panel);

        Button exitButton = new Button("Exit", () -> userVerificationMenu.draw(window));
        exitButton.addTo(panel);

        window.setComponent(panel);
    }

    private void signUp(String value) {

        if (value.isEmpty())
            return;

        ;
    }

}
