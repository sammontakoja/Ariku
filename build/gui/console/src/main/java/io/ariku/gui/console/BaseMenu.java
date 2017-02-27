package io.ariku.gui.console;

import com.googlecode.lanterna.gui2.*;

/**
 * @author Ari Aaltonen
 */
public class BaseMenu {

    public static void draw(BasicWindow window) {
        Panel panel = new Panel();
        panel.addComponent(new Button("User verification", () -> UserVerificationMenu.draw(window)));
        panel.addComponent(new Button("Exit", () -> System.exit(0)));
        window.setComponent(panel);
    }

}
