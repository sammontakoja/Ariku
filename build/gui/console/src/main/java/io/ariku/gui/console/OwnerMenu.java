package io.ariku.gui.console;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Panel;
import io.ariku.owner.OwnerService;
import io.ariku.util.data.Competition;

import java.util.Arrays;
import java.util.List;

/**
 * @author Ari Aaltonen
 */
public class OwnerMenu {

    BaseMenu baseMenu;
    public NewCompetitionPage newCompetitionPage;
    public NewOwnerPage newOwnerPage;
    public OwnerService ownerService;

    public void draw(BasicWindow window) {

        List<Button> buttons = Arrays.asList(
                new Button("New competition", () -> newCompetitionPage.draw(window)),
                new Button("Competitions", () -> printUsersCompetitionsToConsole()),
                new Button("New owner", () -> newOwnerPage.draw(window)),
                new Button("Menu", () -> baseMenu.draw(window))
        );

        Panel panel = new Panel();
        buttons.forEach(button -> button.addTo(panel));

        window.setComponent(panel);
    }

    private void printUsersCompetitionsToConsole() {
        List<Competition> ownedCompetitions = ownerService.findOwnedCompetitions(UserCache.authorizeRequest());
        System.out.println("User '"+UserCache.userId+"' own competitions:"+ ownedCompetitions);
    }

}
