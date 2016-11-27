package io.ariku.console;

/**
 * @author Ari Aaltonen
 */

import com.googlecode.lanterna.TerminalPosition;
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
import io.ariku.verification.api.SignUpRequest;
import io.ariku.verification.api.UserVerificationService;

import java.io.IOException;
import java.time.temporal.Temporal;
import java.util.Arrays;
import java.util.regex.Pattern;

public class ArikuConsole {
    public static void main(String[] args) throws IOException {

        VerificationComposer verificationComposer = new VerificationComposer();
        SkeetComposer skeetComposer = new SkeetComposer();

        SkeetCompetitionService skeetCompetitionService = skeetComposer.simpleSkeetCompetitionService();
        UserVerificationService userVerificationService = verificationComposer.userVerificationService();

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

        final Label lblOutput = new Label("");

        panel.addComponent(new Label("User id"));
        final TextBox userIdTextBox = new TextBox().addTo(panel);

        panel.addComponent(new Label("Operation"));
        final ComboBox<String> operations = new ComboBox<String>();
        operations.addItem("SignUp");
        operations.addItem("VerifySignUp");
        operations.addItem("Login");
        operations.addItem("Logout");
        panel.addComponent(operations);

        panel.addComponent(new EmptySpace(new TerminalSize(0, 0)));

        new Button("Operate!", () -> {
            String userId = userIdTextBox.getText();
            if (userId.isEmpty())
                return;

            if (operations.getSelectedItem().equals("SignUp")) {
                lblOutput.setText("SignUp");
            }

            if (operations.getSelectedItem().equals("VerifySignUp")) {
                lblOutput.setText("VerifySignUp");
            }

            if (operations.getSelectedItem().equals("Login")) {
                lblOutput.setText("Login");
            }

            if (operations.getSelectedItem().equals("Logout")) {
                lblOutput.setText("Logout");
            }

        }).addTo(panel);

        new Button("Exit", () -> System.exit(0)).addTo(panel);

        panel.addComponent(new EmptySpace(new TerminalSize(0, 0)));
        panel.addComponent(lblOutput);

        // Create window to hold the panel
        BasicWindow window = new BasicWindow();
        window.setComponent(panel);

        // Create gui and start gui
        MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLACK));
        gui.addWindowAndWait(window);
    }
}

