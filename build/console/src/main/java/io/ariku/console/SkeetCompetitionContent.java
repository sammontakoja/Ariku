package io.ariku.console;

import com.googlecode.lanterna.gui2.*;
import io.ariku.competition.skeet.api.Competition;
import io.ariku.verification.api.UserVerificationService;

import static io.ariku.composer.Composer.COMPOSER_MEMORY;
import static io.ariku.console.ConsoleUser.CONSOLE_USER;

/**
 * @author Ari Aaltonen
 */
public class SkeetCompetitionContent {

    public static void skeetNewCompetitionPanel(Panel contentPanel) {

        UserVerificationService userVerificationService = COMPOSER_MEMORY.userVerificationService;

        contentPanel.removeAllComponents();
        contentPanel.setLayoutManager(new GridLayout(1));

        if (!userVerificationService.isUserLoggedIn(CONSOLE_USER.userId())) {
            contentPanel.addComponent(new Label("Please login"));
            return;
        }

        TextBox competitionNameTextBox = new TextBox("Competition name here");
        contentPanel.addComponent(competitionNameTextBox);

        contentPanel.addComponent(new Button("Create new competition", () -> {
            String competitionName = competitionNameTextBox.getText();
            if (!competitionName.isEmpty()) {
                COMPOSER_MEMORY.skeetCompetitionService.createNewCompetition(CONSOLE_USER.userId(), competitionName);
            }
        }));
    }

    public static void listCompetitionPanel(Panel contentPanel) {

        contentPanel.removeAllComponents();
        contentPanel.setLayoutManager(new GridLayout(1));

        UserVerificationService userVerificationService = COMPOSER_MEMORY.userVerificationService;

        if (!userVerificationService.isUserLoggedIn(CONSOLE_USER.userId())) {
            contentPanel.addComponent(new Label("Please login"));
            return;
        }

        String userId = CONSOLE_USER.userId();

        System.out.println("Listing competitionsBy userid:"+userId);

        if (!userId.isEmpty())
            for (Competition competition : COMPOSER_MEMORY.skeetCompetitionService.listCompetitions(userId))
                contentPanel.addComponent(new Label(competition.name));

    }

}
