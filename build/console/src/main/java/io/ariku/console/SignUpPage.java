package io.ariku.console;

import com.googlecode.lanterna.gui2.*;
import io.ariku.verification.api.*;

import java.util.Arrays;
import java.util.List;

import static io.ariku.composer.Composer.COMPOSER_MEMORY;
import static io.ariku.console.ConsoleUser.CONSOLE_USER;

/**
 * @author Ari Aaltonen
 */
public class SignUpPage {

    public static void draw(BasicWindow window) {

        Panel panel = new Panel();

        TextBox emailAddressText = new TextBox();
        emailAddressText.addTo(panel);

        Button okButton = new Button("OK", () -> operate(emailAddressText.getText()));
        okButton.addTo(panel);

        Button exitButton = new Button("Exit", () -> UserVerificationMenu.draw(window));
        exitButton.addTo(panel);

        window.setComponent(panel);
    }

    private static void operate(String value) {

        UserVerificationService userVerificationService = COMPOSER_MEMORY.userVerificationService;

        if (value.isEmpty())
            return;

        CONSOLE_USER.userId(value);

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
