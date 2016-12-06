package io.ariku.console;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import io.ariku.verification.api.*;

import static io.ariku.composer.Composer.COMPOSER_MEMORY;
import static io.ariku.console.ConsoleUser.CONSOLE_USER;

/**
 * @author Ari Aaltonen
 */
public class UserVerificationContent {

    public static void userVerificationContent(Panel contentPanel) {

        contentPanel.removeAllComponents();

        contentPanel.setLayoutManager(new GridLayout(2));

        contentPanel.addComponent(new Label(""));
        final TextBox userIdTextBox = new TextBox().addTo(contentPanel);

        contentPanel.addComponent(new Label("Operation"));
        final ComboBox<String> operations = new ComboBox<String>();
        operations.addItem("SignUp");
        operations.addItem("VerifySignUp");
        operations.addItem("Login");
        operations.addItem("Logout");
        contentPanel.addComponent(operations);

        contentPanel.addComponent(new EmptySpace(new TerminalSize(0, 0)));

        new Button("Operate!", () -> operate(operations, userIdTextBox.getText())).addTo(contentPanel);

        contentPanel.addComponent(new EmptySpace(new TerminalSize(0, 0)));
    }

    private static void operate(ComboBox<String> operations, String value) {

        UserVerificationService userVerificationService = COMPOSER_MEMORY.userVerificationService;

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
