package io.ariku.gui.console;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Panel;
import ariku.rest.client.RestClient;

import java.util.Arrays;
import java.util.List;

/**
 * @author Ari Aaltonen
 */
public class OwnerMenu {

    BaseMenu baseMenu;
    public NewCompetitionPage newCompetitionPage;
    public NewOwnerPage newOwnerPage;
    public RestClient restClient;

    public void draw(BasicWindow window) {

        List<Button> buttons = Arrays.asList(
                new Button("New competition", () -> newCompetitionPage.draw(window)),
                new Button("Competitions", () -> System.out.println(restClient.listOwnedCompetitionsRequest(UserCache.authorizeRequest().username, UserCache.authorizeRequest().securityToken))),
                new Button("New owner", () -> newOwnerPage.draw(window)),
                new Button("Menu", () -> baseMenu.draw(window))
        );

        Panel panel = new Panel();
        buttons.forEach(button -> button.addTo(panel));

        window.setComponent(panel);
    }

}
