package io.ariku.gui.console;

import com.googlecode.lanterna.gui2.*;
import io.ariku.composer.Composer;

/**
 * @author Ari Aaltonen
 */
public class BaseMenu {

    public static void draw(BasicWindow window) {
        Panel panel = new Panel();
        panel.addComponent(new Button("User verification", () -> UserVerificationMenu.draw(window)));
        panel.addComponent(new Button("Owner", () -> OwnerMenu.draw(window)));
        panel.addComponent(new Button("Exit", () -> {
            Composer.SIMPLE.printDatabaseContentToConsole();
            UserCache.printToConsole();
            System.exit(0);
        }));
        window.setComponent(panel);
    }

}
