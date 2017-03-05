package io.ariku.gui.console;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import io.ariku.owner.AddOwnerRightsRequest;
import io.ariku.owner.OwnerService;

import static io.ariku.composer.Composer.SIMPLE;

/**
 * @author Ari Aaltonen
 */
public class NewOwnerPage {

    public static void draw(BasicWindow window) {

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

        Button exitButton = new Button("Exit", () -> OwnerMenu.draw(window));
        exitButton.addTo(panel);

        window.setComponent(panel);
    }

    private static boolean addNewOwner(String competitionName, String anotherUsersId) {

        OwnerService ownerService = SIMPLE.ownerService;

        if (competitionName.isEmpty() || anotherUsersId.isEmpty())
            return false;

        AddOwnerRightsRequest request = new AddOwnerRightsRequest();
        request.authorizeRequest = UserCache.authorizeRequest();
        request.competitionName = competitionName;
        request.userIdExistingOwner = UserCache.userId;
        request.usernameOfNewOwner = anotherUsersId;
        ownerService.addOwnerRights(request);

        return true;
    }

}
