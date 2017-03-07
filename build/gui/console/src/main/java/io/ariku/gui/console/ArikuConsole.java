package io.ariku.gui.console;

/**
 * @author Ari Aaltonen
 */

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.DefaultWindowManager;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class ArikuConsole {

    static {
        System.setProperty("java.awt.headless", "true");
    }

    public static void main(String[] args) {
        startConsole(() -> System.out.println("Console started!"));
    }

    public interface AfterConsoleStarted {
        void doSomething();
    }

    public static void startConsole(AfterConsoleStarted afterConsoleStarted) {

        BasicWindow window = new BasicWindow();

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

