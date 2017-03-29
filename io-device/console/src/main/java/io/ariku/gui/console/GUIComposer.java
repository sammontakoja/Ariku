package io.ariku.gui.console;

import ariku.rest.client.RestClient;

/**
 * @author Ari Aaltonen
 */
public class GUIComposer {

    public BaseMenu baseMenu(RestClient restClient) {
        BaseMenu baseMenu = new BaseMenu();

        baseMenu.userVerificationMenu = new UserVerificationMenu();
        baseMenu.userVerificationMenu.baseMenu = baseMenu;
        baseMenu.userVerificationMenu.restClient = restClient;

        baseMenu.userVerificationMenu.signUpPage = new SignUpPage();
        baseMenu.userVerificationMenu.signUpPage.userVerificationMenu = baseMenu.userVerificationMenu;
        baseMenu.userVerificationMenu.signUpPage.restClient = restClient;

        baseMenu.userVerificationMenu.verifySignUpPage = new VerifySignUpPage();
        baseMenu.userVerificationMenu.verifySignUpPage.userVerificationMenu = baseMenu.userVerificationMenu;
        baseMenu.userVerificationMenu.verifySignUpPage.restClient = restClient;

        baseMenu.userVerificationMenu.loginPage = new LoginPage();
        baseMenu.userVerificationMenu.loginPage.userVerificationMenu = baseMenu.userVerificationMenu;
        baseMenu.userVerificationMenu.loginPage.restClient = restClient;

        baseMenu.ownerMenu = new OwnerMenu();
        baseMenu.ownerMenu.baseMenu = baseMenu;
        baseMenu.ownerMenu.restClient = restClient;

        baseMenu.ownerMenu.newOwnerPage = new NewOwnerPage();
        baseMenu.ownerMenu.newOwnerPage.ownerMenu = baseMenu.ownerMenu;
        baseMenu.ownerMenu.newOwnerPage.restClient = restClient;

        baseMenu.ownerMenu.newCompetitionPage = new NewCompetitionPage();
        baseMenu.ownerMenu.newCompetitionPage.ownerMenu = baseMenu.ownerMenu;
        baseMenu.ownerMenu.newCompetitionPage.restClient = restClient;

        return baseMenu;
    }

}
