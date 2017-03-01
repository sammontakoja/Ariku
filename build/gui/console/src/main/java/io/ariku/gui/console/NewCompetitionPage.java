package io.ariku.gui.console;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import io.ariku.owner.NewCompetitionRequest;
import io.ariku.owner.OwnerService;

import static io.ariku.composer.Composer.SIMPLE;

/**
 * @author Ari Aaltonen
 */
public class NewCompetitionPage {

    public static void draw(BasicWindow window) {

        Panel panel = new Panel();

        TextBox nameText = new TextBox();
        nameText.addTo(panel);

        TextBox typeText = new TextBox();
        typeText.addTo(panel);

        Button okButton = new Button("OK", () -> {
            boolean createdNewCompetition = createNewCompetition(nameText.getText(), typeText.getText());
            if (createdNewCompetition) {
                System.out.println("Created new competition");
                nameText.setText("");
                typeText.setText("");
            }
        });
        okButton.addTo(panel);

        Button exitButton = new Button("Exit", () -> OwnerMenu.draw(window));
        exitButton.addTo(panel);

        window.setComponent(panel);
    }

    private static boolean createNewCompetition(String name, String type) {

        OwnerService ownerService = SIMPLE.ownerService;

        if (name.isEmpty() || type.isEmpty())
            return false;

        NewCompetitionRequest newCompetitionRequest = new NewCompetitionRequest();
        newCompetitionRequest.competitionName = name;
        newCompetitionRequest.competitionType = type;
        newCompetitionRequest.authorizeRequest = UserCache.authorizeRequest();

        ownerService.createNewCompetition(newCompetitionRequest);

        return true;
    }

}
