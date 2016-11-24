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
import io.ariku.competition.skeet.simple.SkeetComposer;

import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;

public class ArikuConsole {
    public static void main(String[] args) throws IOException {

        SkeetComposer skeetComposer = new SkeetComposer();

        SkeetCompetitionService skeetCompetitionService = skeetComposer.simpleSkeetCompetitionService();

        if (Arrays.asList(args).contains("-v")) {
            System.out.println(skeetComposer.version);
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

        panel.addComponent(new Label("Num 1"));
        final TextBox txtNum1 = new TextBox().setValidationPattern(Pattern.compile("[0-9]*")).addTo(panel);

        panel.addComponent(new Label("Num 2"));
        final TextBox txtNum2 = new TextBox().setValidationPattern(Pattern.compile("[0-9]*")).addTo(panel);

        panel.addComponent(new Label("Operation"));
        final ComboBox<String> operations = new ComboBox<String>();
        operations.addItem("Add");
        operations.addItem("Subtract");
        panel.addComponent(operations);

        panel.addComponent(new EmptySpace(new TerminalSize(0, 0)));
        new Button("Calculate!", new Runnable() {
            @Override
            public void run() {
                int num1 = Integer.parseInt(txtNum1.getText());
                int num2 = Integer.parseInt(txtNum2.getText());
                if (operations.getSelectedIndex() == 0) {
                    lblOutput.setText(Integer.toString(num1 + num2));
                } else if (operations.getSelectedIndex() == 1) {
                    lblOutput.setText(Integer.toString(num1 - num2));
                }
            }
        }).addTo(panel);

        panel.addComponent(new EmptySpace(new TerminalSize(0, 0)));
        panel.addComponent(lblOutput);

        // Create window to hold the panel
        BasicWindow window = new BasicWindow();
        window.setComponent(panel);

        // Create gui and start gui
        MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));
        gui.addWindowAndWait(window);
    }
}

