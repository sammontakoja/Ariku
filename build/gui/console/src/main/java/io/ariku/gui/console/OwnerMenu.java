package io.ariku.gui.console;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Panel;
import io.ariku.owner.OwnerService;
import io.ariku.util.data.Competition;

import java.util.Arrays;
import java.util.List;

import static io.ariku.composer.Composer.SIMPLE;

/**
 * @author Ari Aaltonen
 */
public class OwnerMenu {

    public static void draw(BasicWindow window) {

        List<Button> buttons = Arrays.asList(
                new Button("New competition", () -> NewCompetitionPage.draw(window)),
                new Button("Competitions", () -> printUsersCompetitionsToConsole(SIMPLE.ownerService)),
                new Button("Menu", () -> BaseMenu.draw(window))
        );

        Panel panel = new Panel();
        buttons.forEach(button -> button.addTo(panel));

        window.setComponent(panel);
    }

    private static void printUsersCompetitionsToConsole(OwnerService ownerService) {
        List<Competition> ownedCompetitions = ownerService.findOwnedCompetitions(UserCache.authorizeRequest());
        System.out.println("User '"+UserCache.userId+"' own competitions:"+ ownedCompetitions);
    }

}
