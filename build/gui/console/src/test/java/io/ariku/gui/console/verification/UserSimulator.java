package io.ariku.gui.console.verification;

import io.ariku.gui.console.Keyboard;

/**
 * @author Ari Aaltonen
 */
public class UserSimulator {

    public void signUp(String userId) {

        new Keyboard()
                // Go to sign up page and fill user id
                .typeEnter()
                .typeEnter()
                .typeText(userId)
                .typeDown()
                .typeEnter()
                // Exit to verification menu
                .typeDown()
                .typeDown()
                .typeEnter()
                // Exit to main menu
                .typeDown()
                .typeDown()
                .typeDown()
                .typeDown()
                .typeEnter()
                .startTyping();
    }

    public void verifySignUp(String userId) {

        new Keyboard()
                // Go to sign up verification page and fill user id
                .typeEnter()
                .typeDown()
                .typeEnter()
                .typeText(userId)
                .typeDown()
                .typeEnter()
                // Exit to verification menu
                .typeDown()
                .typeDown()
                .typeEnter()
                // Exit to main menu
                .typeDown()
                .typeDown()
                .typeDown()
                .typeDown()
                .typeEnter()
                .startTyping();
    }

    public void login(String userId) {

        new Keyboard()
                // Go to login page and fill user id
                .typeEnter()
                .typeDown()
                .typeDown()
                .typeEnter()
                .typeText(userId)
                .typeDown()
                .typeEnter()
                // Exit to verification menu
                .typeDown()
                .typeDown()
                .typeEnter()
                // Exit to main menu
                .typeDown()
                .typeDown()
                .typeDown()
                .typeDown()
                .typeEnter()
                .startTyping();
    }

    public void createCompetition(String competitionName, String competitionType) {

        new Keyboard()
                // Go to owner menu
                .typeDown()
                .typeEnter()
                // Go to create new competition page
                .typeEnter()
                // Fill competition values
                .typeText(competitionName)
                .typeDown()
                .typeText(competitionType)
                .typeDown()
                .typeEnter()
                // Exit to owner menu
                .typeDown()
                .typeEnter()
                // Exit to main menu
                .typeDown()
                .typeDown()
                .typeEnter()
                .startTyping();
    }

    public void printOwnedCompetitionsToConsole() {

        new Keyboard()
                // Go to owner menu
                .typeDown()
                .typeEnter()
                // Press owned competitions button
                .typeDown()
                .typeEnter()
                // Exit to owner menu
                .typeDown()
                .typeEnter()
                // Exit to main menu
                .typeDown()
                .typeDown()
                .typeEnter()
                .startTyping();
    }

    public void exit() {

        new Keyboard()
                // Exit from main menu
                .typeDown()
                .typeDown()
                .typeDown()
                .typeEnter()
                .startTyping();
    }

}
