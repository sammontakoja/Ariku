package io.ariku.gui.console;

/**
 * @author Ari Aaltonen
 */
public class UserSimulator {

    public UserSimulator signUp(String username) {
        new Keyboard()
                // Go to sign up page , fill username and press ok
                .typeEnter()
                .typeEnter()
                .typeText(username)
                .typeDown()
                .typeEnter()
                .startTyping();
        return this;
    }

    public UserSimulator verifySignUp(String username) {
        new Keyboard()
                // Go to sign up verification page , fill username and press ok
                .typeEnter()
                .typeDown()
                .typeEnter()
                .typeText(username)
                .typeDown()
                .typeEnter()
                .startTyping();
        return this;
    }

    public UserSimulator login(String username) {
        new Keyboard()
                // Go to login page, fill username and press ok
                .typeEnter()
                .typeDown()
                .typeDown()
                .typeEnter()
                .typeText(username)
                .typeDown()
                .typeEnter()
                .startTyping();
        return this;
    }

    public UserSimulator logout() {
        new Keyboard()
                .typeEnter()
                .typeDown()
                .typeDown()
                .typeDown()
                .typeEnter()
                .startTyping();
        return this;
    }

    public UserSimulator signUpAndLoginUser(String userId) {
        signUp(userId);
        verifySignUp(userId);
        login(userId);
        return this;
    }

    public UserSimulator createCompetition(String competitionName, String competitionType) {

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
                .typeDown()
                .typeEnter()
                // Exit to main menu
                .typeDown()
                .typeDown()
                .typeDown()
                .typeEnter()
                .startTyping();

        return this;
    }

    public UserSimulator addUserAsOwner(String usernameOfNewOwner, String competitionName) {

        new Keyboard()
                // Go to owner menu
                .typeDown()
                .typeEnter()
                // Go to add new owner page
                .typeDown()
                .typeDown()
                .typeEnter()
                // fill values
                .typeText(usernameOfNewOwner)
                .typeDown()
                .typeText(competitionName)
                .typeDown()
                .typeEnter()
                .startTyping();

        return this;
    }

    public UserSimulator printOwnedCompetitionsToConsole() {

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

        return this;
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
