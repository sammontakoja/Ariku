package io.ariku.console;

import com.googlecode.lanterna.gui2.*;
import io.ariku.competition.skeet.api.Competition;
import io.ariku.verification.api.UserVerificationService;

import static io.ariku.composer.Composer.COMPOSER;
import static io.ariku.console.ConsoleUser.CONSOLE_USER;

/**
 * @author Ari Aaltonen
 */
public class SkeetCompetitionContent {

    public static void skeetNewCompetitionPanel(Panel contentPanel) {

        UserVerificationService userVerificationService = COMPOSER.userVerificationService;

        contentPanel.removeAllComponents();
        contentPanel.setLayoutManager(new GridLayout(1));

        if (!userVerificationService.isAuthorized(CONSOLE_USER.userId(), "")) {
            contentPanel.addComponent(new Label("Please login"));
            return;
        }

        TextBox competitionNameTextBox = new TextBox("Competition name here");
        contentPanel.addComponent(competitionNameTextBox);

        contentPanel.addComponent(new Button("Create new competition", () -> {
            String competitionName = competitionNameTextBox.getText();
            if (!competitionName.isEmpty()) {
                COMPOSER.skeetCompetitionService.createNewCompetition(CONSOLE_USER.userId(), competitionName);
            }
        }));
    }

    public static void listCompetitionPanel(Panel contentPanel) {

        contentPanel.removeAllComponents();
        contentPanel.setLayoutManager(new GridLayout(1));

        UserVerificationService userVerificationService = COMPOSER.userVerificationService;

        if (!userVerificationService.isAuthorized(CONSOLE_USER.userId(), "")) {
            contentPanel.addComponent(new Label("Please login"));
            return;
        }

        String userId = CONSOLE_USER.userId();

        System.out.println("Listing competitionsBy userid:"+userId);

        if (!userId.isEmpty())
            for (Competition competition : COMPOSER.skeetCompetitionService.listCompetitions(userId))
                contentPanel.addComponent(new Label(competition.name));

    }

}
