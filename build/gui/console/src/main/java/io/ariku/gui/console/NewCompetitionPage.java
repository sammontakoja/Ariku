package io.ariku.gui.console;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import io.ariku.owner.NewCompetitionRequest;
import io.ariku.owner.OwnerService;
import io.ariku.verification.AuthorizeRequest;
import io.ariku.verification.SignUpRequest;
import io.ariku.verification.UserVerificationService;

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
        nameText.addTo(panel);

        Button okButton = new Button("OK", () -> createNewCompetition(nameText.getText(), typeText.getText()));
        okButton.addTo(panel);

        Button exitButton = new Button("Exit", () -> UserVerificationMenu.draw(window));
        exitButton.addTo(panel);

        window.setComponent(panel);
    }

    private static void createNewCompetition(String name, String type) {

        OwnerService ownerService = SIMPLE.ownerService;

        if (name.isEmpty())
            return;

        NewCompetitionRequest newCompetitionRequest = new NewCompetitionRequest();
        newCompetitionRequest.competitionName = name;
        newCompetitionRequest.competitionType = type;
        newCompetitionRequest.authorizeRequest = new AuthorizeRequest("", "token");

        // TODO
    }

    public static void print(String value) {
        System.out.println(value);
    }

}
