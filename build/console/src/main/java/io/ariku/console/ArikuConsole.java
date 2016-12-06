package io.ariku.console;

/**
 * @author Ari Aaltonen
 */

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.Arrays;

import static io.ariku.composer.Composer.COMPOSER_MEMORY;
import static io.ariku.console.SkeetCompetitionMain.skeetCompetitionMain;
import static io.ariku.console.UserVerificationContent.userVerificationContent;

public class ArikuConsole {

    public static void main(String[] args) throws IOException {

        if (Arrays.asList(args).contains("-v")) {
            System.out.println(COMPOSER_MEMORY.arikuVersion);
            System.exit(0);
        }

        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();

        Panel rootPanel = new Panel();
        rootPanel.setLayoutManager(new GridLayout(1));

        Panel menuPanel = new Panel();
        menuPanel.setLayoutManager(new GridLayout(3));
        rootPanel.addComponent(menuPanel);

        rootPanel.addComponent(new Label(""));
        rootPanel.addComponent(new Label(""));

        Panel contentPanel = new Panel();
        contentPanel.addComponent(new Label("Welcome to Ariku!"));
        rootPanel.addComponent(contentPanel);

        menuPanel.addComponent(new Button("User verification", () -> userVerificationContent(contentPanel)));

        final ComboBox<String> competitions = new ComboBox<String>();
        competitions.addItem("Please select competition");
        competitions.addItem("Skeet");
        menuPanel.addComponent(competitions);

        competitions.addListener((selectedIndex, previousSelection) -> {
            if (competitions.getText().equals("Skeet"))
                skeetCompetitionMain(contentPanel);
        });

        menuPanel.addComponent(new Button("Exit", () -> System.exit(0)));

        BasicWindow window = new BasicWindow();
        window.setComponent(rootPanel);
        MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLACK));
        gui.addWindowAndWait(window);
    }
}

