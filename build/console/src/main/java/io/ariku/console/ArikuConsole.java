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

import static io.ariku.composer.Composer.COMPOSER;

public class ArikuConsole {

    public static final BasicWindow window = new BasicWindow();

    public static void main(String[] args) {

        if (Arrays.asList(args).contains("-v")) {
            System.out.println(COMPOSER.arikuVersion);
            System.exit(0);
        }

        startConsole(() -> System.out.println("Console started from jar packet"));
    }

    public interface AfterConsoleStarted {
        void doSomething();
    }

    public static void startConsole(AfterConsoleStarted afterConsoleStarted) {

        Screen screen;
        try {
            Terminal terminal = new DefaultTerminalFactory().createTerminal();
            screen = new TerminalScreen(terminal);
            screen.startScreen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        BaseMenu.draw(window);

        MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLACK));

        gui.addWindow(window);

        afterConsoleStarted.doSomething();

        gui.waitForWindowToClose(window);

    }

}

