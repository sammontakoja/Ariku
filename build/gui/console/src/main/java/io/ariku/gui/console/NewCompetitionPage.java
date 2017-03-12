package io.ariku.gui.console;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;

/**
 * @author Ari Aaltonen
 */
public class NewCompetitionPage {

    public OwnerMenu ownerMenu;
    public RestClient restClient;

    public void draw(BasicWindow window) {

        Panel panel = new Panel();

        TextBox nameText = new TextBox();
        nameText.addTo(panel);
        TextBox typeText = new TextBox();
        typeText.addTo(panel);

        panel.addComponent(new Button("OK", () -> {

            String response = restClient.newCompetition(nameText.getText(), typeText.getText(), UserCache.authorizeRequest());

            if (response.equals("OK")) {
                System.out.println("Created new competition");
                nameText.setText("");
                typeText.setText("");
            }
        }));

        panel.addComponent(new Button("Exit", () -> ownerMenu.draw(window)));

        window.setComponent(panel);
    }

}
