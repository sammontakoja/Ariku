package io.ariku.gui.console;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Panel;
import io.ariku.composer.Composer;

/**
 * @author Ari Aaltonen
 */
public class BaseMenu {

    UserVerificationMenu userVerificationMenu;
    OwnerMenu ownerMenu;

    public void draw(BasicWindow window) {
        Panel panel = new Panel();
        panel.addComponent(new Button("User verification", () -> userVerificationMenu.draw(window)));
        panel.addComponent(new Button("Owner", () -> ownerMenu.draw(window)));
        panel.addComponent(new Button("Exit", () -> {
            UserCache.printToConsole();
            System.exit(0);
        }));
        window.setComponent(panel);
    }

}
