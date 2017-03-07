package io.ariku.gui.console;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import io.ariku.verification.UserVerificationService;
import io.ariku.verification.VerifySignUpRequest;

/**
 * @author Ari Aaltonen
 */
public class VerifySignUpPage {

    public UserVerificationMenu userVerificationMenu;
    public UserVerificationService userVerificationService;

    public void draw(BasicWindow window) {

        Panel panel = new Panel();

        TextBox emailAddressText = new TextBox();
        emailAddressText.addTo(panel);

        Button okButton = new Button("OK", () -> verifySignUp(emailAddressText.getText()));
        okButton.addTo(panel);

        Button exitButton = new Button("Exit", () -> userVerificationMenu.draw(window));
        exitButton.addTo(panel);

        window.setComponent(panel);
    }

    private void verifySignUp(String value) {

        if (value.isEmpty())
            return;

        userVerificationService.verifySignUp(new VerifySignUpRequest(value));
    }

    public static void print(String value) {
        System.out.println(value);
    }

}
