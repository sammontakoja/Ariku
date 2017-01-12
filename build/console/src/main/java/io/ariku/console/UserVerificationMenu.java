package io.ariku.console;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.ComboBox;
import com.googlecode.lanterna.gui2.Panel;
import io.ariku.verification.api.*;

import java.util.Arrays;
import java.util.List;

import static io.ariku.composer.Composer.COMPOSER;
import static io.ariku.console.ConsoleUser.CONSOLE_USER;

/**
 * @author Ari Aaltonen
 */
public class UserVerificationMenu {

    public static void draw(BasicWindow window) {

        List<Button> buttons = Arrays.asList(
                new Button("SignUp", () -> {
                    System.out.println("Pressed SignUp");
                    SignUpPage.draw(window);
                }),
                new Button("VerifySignUp", () -> {
                    System.out.println("Pressed verifySignUp");
                    VerifySignUpPage.draw(window);
                }),
                new Button("Login", () -> {
                    System.out.println("Pressed login");
                    LoginPage.draw(window);
                }),
                new Button("Logout", () -> {
                    System.out.println("Pressed logout");
                    LogoutPage.draw(window);
                }),
                new Button("Menu", () -> BaseMenu.draw(window))
        );

        Panel panel = new Panel();
        buttons.forEach(button -> button.addTo(panel));

        window.setComponent(panel);
    }

    private static void operate(ComboBox<String> operations, String value) {

        UserVerificationService userVerificationService = COMPOSER.userVerificationService;

        if (value.isEmpty())
            return;

        CONSOLE_USER.userId(value);

        if (operations.getSelectedItem().equals("SignUp")) {
            boolean signUp = userVerificationService.signUp(new SignUpRequest(value));
            if (signUp)
                print(value + " signUp ok");
            else
                print(value + " signUp failed");
        }

        if (operations.getSelectedItem().equals("VerifySignUp")) {
            boolean verifySignUp = userVerificationService.verifySignUp(new VerifySignUpRequest(value));
            if (verifySignUp)
                print(value + " verifySignUp ok");
            else
                print(value + " verifySignUp failed");
        }

        if (operations.getSelectedItem().equals("Login")) {
            boolean loggedIn = userVerificationService.login(new LoginRequest(value));
            if (loggedIn)
                print(value + " login ok");
            else
                print(value + " login failed");
        }

        if (operations.getSelectedItem().equals("Logout")) {
            boolean loggedOut = userVerificationService.logout(new LogoutRequest(value));
            if (loggedOut)
                print(value + " logout ok");
            else
                print(value + " logout failed");
        }
    }

    public static void print(String value) {
        System.out.println(value);
    }

}
