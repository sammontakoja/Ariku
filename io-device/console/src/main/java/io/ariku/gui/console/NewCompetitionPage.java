package io.ariku.gui.console;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import com.mashape.unirest.http.JsonNode;
import ariku.rest.client.RestClient;

import java.util.Optional;

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

            Optional<JsonNode> response = restClient.newCompetitionRequest(nameText.getText(), typeText.getText(), UserCache.authorizeRequest().username, UserCache.authorizeRequest().securityToken);
            System.out.println("Created new competition response\n"+response.toString());
        }));

        panel.addComponent(new Button("Exit", () -> ownerMenu.draw(window)));

        window.setComponent(panel);
    }

}
