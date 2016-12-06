package io.ariku.console;

import com.googlecode.lanterna.gui2.*;

import static io.ariku.console.SkeetNewCompetition.skeetNewCompetitionPanel;

/**
 * @author Ari Aaltonen
 */
public class SkeetCompetitionMain {

    public static void skeetCompetitionContent(Panel rootPanel) {

        rootPanel.removeAllComponents();
        rootPanel.setLayoutManager(new GridLayout(1));

        Panel menuPanel = new Panel();
        menuPanel.setLayoutManager(new GridLayout(2));
        rootPanel.addComponent(menuPanel);

        rootPanel.addComponent(new EmptySpace());

        Panel contentPanel = new Panel();
        contentPanel.addComponent(new Label("Skeet competition!"));
        rootPanel.addComponent(contentPanel);

        menuPanel.addComponent(new Button("New skeet competitions", () -> skeetNewCompetitionPanel(contentPanel)));

        menuPanel.addComponent(new Button("List current skeet competitions", () -> System.out.println("Pushed list skeet competitions")));
    }
}
