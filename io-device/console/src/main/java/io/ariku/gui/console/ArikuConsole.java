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
import io.ariku.rest.client.RestClient;
import io.ariku.util.data.ArikuSettings;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;
import org.pmw.tinylog.writers.ConsoleWriter;

import java.io.IOException;

public class ArikuConsole {

    public static final RestClient restClient = restClientWithDefaultUrlConfiguration();

    public static void main(String[] args) {
        startConsole(() -> System.out.println("Console started!"));
    }

    public interface AfterConsoleStarted {
        void doSomething();
    }

    public static void startConsole(AfterConsoleStarted afterConsoleStarted) {

        Configurator.defaultConfig()
                .writer(new ConsoleWriter())
                .level(Level.DEBUG)
                .activate();

        BasicWindow window = new BasicWindow();

        Screen screen;
        try {
            Terminal terminal = new DefaultTerminalFactory().createTerminal();
            screen = new TerminalScreen(terminal);
            screen.startScreen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        BaseMenu baseMenu = new GUIComposer().baseMenu(restClient);

        baseMenu.draw(window);

        MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLACK));

        gui.addWindow(window);

        afterConsoleStarted.doSomething();

        gui.waitForWindowToClose(window);
    }

    private static RestClient restClientWithDefaultUrlConfiguration() {
        RestClient restClient = new RestClient();
        restClient.restSettings = ArikuSettings.restClientWithDefaultUrlConfiguration();
        return restClient;
    }

}

