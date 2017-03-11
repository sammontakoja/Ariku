package io.ariku.gui.console;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import io.ariku.owner.NewCompetitionRequest;
import io.ariku.owner.OwnerService;

/**
 * @author Ari Aaltonen
 */
public class NewCompetitionPage {

    OwnerMenu ownerMenu;
    public OwnerService ownerService;

    public void draw(BasicWindow window) {

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

        Button exitButton = new Button("Exit", () -> ownerMenu.draw(window));
        exitButton.addTo(panel);

        window.setComponent(panel);
    }

    private boolean createNewCompetition(String name, String type) {

        NewCompetitionRequest newCompetitionRequest = new NewCompetitionRequest();
        newCompetitionRequest.competitionName = name;
        newCompetitionRequest.competitionType = type;
        newCompetitionRequest.authorizeRequest = UserCache.authorizeRequest();

        ownerService.createNewCompetition(newCompetitionRequest);

        return true;
    }

}
