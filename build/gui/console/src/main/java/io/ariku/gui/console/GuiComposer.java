package io.ariku.gui.console;

import io.ariku.owner.OwnerService;
import io.ariku.util.data.RestSettings;
import io.ariku.verification.UserVerificationService;

/**
 * @author Ari Aaltonen
 */
public class GuiComposer {

    public BaseMenu baseMenu(RestClient restClient, OwnerService ownerService, UserVerificationService userVerificationService) {
        BaseMenu baseMenu = new BaseMenu();

        baseMenu.userVerificationMenu = new UserVerificationMenu();
        baseMenu.userVerificationMenu.baseMenu = baseMenu;
        baseMenu.userVerificationMenu.userVerificationService = userVerificationService;

        baseMenu.userVerificationMenu.signUpPage = new SignUpPage();
        baseMenu.userVerificationMenu.signUpPage.userVerificationMenu = baseMenu.userVerificationMenu;
        baseMenu.userVerificationMenu.signUpPage.restClient = restClient;

        baseMenu.userVerificationMenu.verifySignUpPage = new VerifySignUpPage();
        baseMenu.userVerificationMenu.verifySignUpPage.userVerificationMenu = baseMenu.userVerificationMenu;
        baseMenu.userVerificationMenu.verifySignUpPage.userVerificationService = userVerificationService;

        baseMenu.userVerificationMenu.loginPage = new LoginPage();
        baseMenu.userVerificationMenu.loginPage.userVerificationMenu = baseMenu.userVerificationMenu;
        baseMenu.userVerificationMenu.loginPage.userVerificationService = userVerificationService;

        baseMenu.ownerMenu = new OwnerMenu();
        baseMenu.ownerMenu.baseMenu = baseMenu;
        baseMenu.ownerMenu.ownerService = ownerService;

        baseMenu.ownerMenu.newOwnerPage = new NewOwnerPage();
        baseMenu.ownerMenu.newOwnerPage.ownerMenu = baseMenu.ownerMenu;
        baseMenu.ownerMenu.newOwnerPage.ownerService = ownerService;

        baseMenu.ownerMenu.newCompetitionPage = new NewCompetitionPage();
        baseMenu.ownerMenu.newCompetitionPage.ownerMenu = baseMenu.ownerMenu;
        baseMenu.ownerMenu.newCompetitionPage.ownerService = ownerService;

        return baseMenu;
    }

}
