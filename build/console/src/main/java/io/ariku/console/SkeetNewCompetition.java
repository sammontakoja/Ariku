package io.ariku.console;

import com.googlecode.lanterna.gui2.*;
import io.ariku.verification.api.*;

import static io.ariku.composer.Composer.COMPOSER;
import static io.ariku.console.ConsoleUser.CONSOLE_USER;

/**
 * @author Ari Aaltonen
 */
public class SkeetNewCompetition {

    public static void skeetNewCompetitionPanel(Panel contentPanel) {

        UserVerificationService userVerificationService = COMPOSER.userVerificationService;

        contentPanel.removeAllComponents();
        contentPanel.setLayoutManager(new GridLayout(1));

        if (userVerificationService.isUserLoggedIn(CONSOLE_USER.userId())) {
            contentPanel.addComponent(new Label("New SkeetCompetition content"));
        } else {
            contentPanel.addComponent(new Label("Please login"));
        }

    }

}
