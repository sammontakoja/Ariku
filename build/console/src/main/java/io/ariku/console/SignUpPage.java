package io.ariku.console;

import com.googlecode.lanterna.gui2.*;
import io.ariku.verification.SignUpRequest;
import io.ariku.verification.UserVerificationService;

import static io.ariku.composer.Composer.COMPOSER;

/**
 * @author Ari Aaltonen
 */
public class SignUpPage {

    public static void draw(BasicWindow window) {

        Panel panel = new Panel();

        TextBox emailAddressText = new TextBox();
        emailAddressText.addTo(panel);

        Button okButton = new Button("OK", () -> signUp(emailAddressText.getText()));
        okButton.addTo(panel);

        Button exitButton = new Button("Exit", () -> UserVerificationMenu.draw(window));
        exitButton.addTo(panel);

        window.setComponent(panel);
    }

    private static void signUp(String value) {

        UserVerificationService userVerificationService = COMPOSER.userVerificationService;

        if (value.isEmpty())
            return;

        boolean signUp = userVerificationService.signUp(new SignUpRequest(value));
        if (signUp)
            print("SignUp OK " + value);
        else
            print("SignUp FAIL " + value);
    }

    public static void print(String value) {
        System.out.println(value);
    }

}
