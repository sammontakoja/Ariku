package io.ariku.console;

/**
 * @author Ari Aaltonen
 */

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import io.ariku.competition.skeet.api.SkeetCompetitionService;
import io.ariku.composer.SkeetComposer;
import io.ariku.composer.VerificationComposer;
import io.ariku.verification.api.*;

import java.io.IOException;
import java.util.Arrays;

public class ArikuConsole {

    final static Label output = new Label("");

    static VerificationComposer verificationComposer = new VerificationComposer();
    static SkeetComposer skeetComposer = new SkeetComposer();
    static SkeetCompetitionService skeetCompetitionService = skeetComposer.simpleSkeetCompetitionService();
    static UserVerificationService userVerificationService = verificationComposer.userVerificationService();

    public static void main(String[] args) throws IOException {

        if (Arrays.asList(args).contains("-v")) {
            System.out.println(skeetComposer.version);
            System.out.println(verificationComposer.version);
            System.exit(0);
        }

        // Setup terminal and screen layers
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();

        // Create panel to hold components
        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(2));

        panel.addComponent(new Label(""));
        final TextBox userIdTextBox = new TextBox().addTo(panel);

        panel.addComponent(new Label("Operation"));
        final ComboBox<String> operations = new ComboBox<String>();
        operations.addItem("SignUp");
        operations.addItem("VerifySignUp");
        operations.addItem("Login");
        operations.addItem("Logout");
        panel.addComponent(operations);

        panel.addComponent(new EmptySpace(new TerminalSize(0, 0)));

        new Button("Operate!", () -> operate(operations, userIdTextBox.getText())).addTo(panel);

        panel.addComponent(new EmptySpace(new TerminalSize(0, 0)));
        panel.addComponent(output);

        new Button("Exit", () -> System.exit(0)).addTo(panel);

        // Create window to hold the panel
        BasicWindow window = new BasicWindow();
        window.setComponent(panel);

        // Create gui and start gui
        MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLACK));
        gui.addWindowAndWait(window);
    }

    private static void operate(ComboBox<String> operations, String value) {

        if (value.isEmpty())
            return;

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

    static void print(String value) {
        output.setText(value);
    }
}

