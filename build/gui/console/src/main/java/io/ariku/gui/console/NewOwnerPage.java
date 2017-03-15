package io.ariku.gui.console;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import io.ariku.owner.OwnerService;

import java.util.Optional;

/**
 * @author Ari Aaltonen
 */
public class NewOwnerPage {

    public OwnerMenu ownerMenu;
    public OwnerService ownerService;

    public void draw(BasicWindow window) {

        System.out.println("Entered NewOwnerPage");

        Panel panel = new Panel();

        TextBox competitionIdText = new TextBox();
        competitionIdText.addTo(panel);

        TextBox typeText = new TextBox();
        typeText.addTo(panel);

        Button okButton = new Button("OK", () -> {
            boolean addedNewOwner = addNewOwner(competitionIdText.getText(), typeText.getText());
            if (addedNewOwner) {
                System.out.println("Added new owner");
                competitionIdText.setText("");
                typeText.setText("");
            }
        });
        okButton.addTo(panel);

        Button exitButton = new Button("Exit", () -> ownerMenu.draw(window));
        exitButton.addTo(panel);

        window.setComponent(panel);
    }

    private boolean addNewOwner(String competitionName, String usernameOfNewOwner) {

        if (competitionName.isEmpty() || usernameOfNewOwner.isEmpty())
            return false;

        findOwnedCompetitionAndGetItsId(competitionName)
                .ifPresent(competitionId -> ownerService.addNewOwner(usernameOfNewOwner, competitionId, UserCache.authorizeRequest()));

        return true;
    }

    private Optional<String> findOwnedCompetitionAndGetItsId(String competitionName) {
        return ownerService.findOwnedCompetitions(UserCache.authorizeRequest()).stream()
                .filter(c -> c.name.equals(competitionName))
                .findFirst()
                .map(competition -> competition.id);
    }

}
