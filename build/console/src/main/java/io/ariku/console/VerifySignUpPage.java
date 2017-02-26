package io.ariku.console;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import io.ariku.verification.UserVerificationService;
import io.ariku.verification.VerifySignUpRequest;

import static io.ariku.composer.Composer.COMPOSER;

/**
 * @author Ari Aaltonen
 */
public class VerifySignUpPage {

    public static void draw(BasicWindow window) {

        Panel panel = new Panel();

        TextBox emailAddressText = new TextBox();
        emailAddressText.addTo(panel);

        Button okButton = new Button("OK", () -> verifySignUp(emailAddressText.getText()));
        okButton.addTo(panel);

        Button exitButton = new Button("Exit", () -> UserVerificationMenu.draw(window));
        exitButton.addTo(panel);

        window.setComponent(panel);
    }

    private static void verifySignUp(String value) {

        UserVerificationService userVerificationService = COMPOSER.userVerificationService;

        if (value.isEmpty())
            return;

        boolean signUpVerified = userVerificationService.verifySignUp(new VerifySignUpRequest(value));

        if (signUpVerified)
            print("VerifySignUp OK " + value);
        else
            print("VerifySignUp FAIL " + value);
    }

    public static void print(String value) {
        System.out.println(value);
    }

}
