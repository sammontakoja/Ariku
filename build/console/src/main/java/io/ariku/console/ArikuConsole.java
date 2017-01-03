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

public class ArikuConsole {

    public static final BasicWindow window = new BasicWindow();

    public static void main(String[] args) throws IOException {

        if (Arrays.asList(args).contains("-v")) {
            System.out.println(COMPOSER_MEMORY.arikuVersion);
            System.exit(0);
        }

        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();

        BaseMenu.draw(window);

        MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLACK));
        gui.addWindowAndWait(window);
    }

}

