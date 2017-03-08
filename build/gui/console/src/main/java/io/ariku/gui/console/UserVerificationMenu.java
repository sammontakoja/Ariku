package io.ariku.gui.console;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Panel;
import io.ariku.verification.UserVerificationService;

import java.util.Arrays;
import java.util.List;

/**
 * @author Ari Aaltonen
 */
public class UserVerificationMenu {

    public UserVerificationService userVerificationService;
    public VerifySignUpPage verifySignUpPage;
    public SignUpPage signUpPage;
    public LoginPage loginPage;
    public BaseMenu baseMenu;

    public void draw(BasicWindow window) {

        List<Button> buttons = Arrays.asList(
                new Button("SignUp", () -> {
                    System.out.println("Pressed SignUp");
                    signUpPage.draw(window);
                }),
                new Button("VerifySignUp", () -> {
                    System.out.println("Pressed verifySignUp");
                    verifySignUpPage.draw(window);
                }),
                new Button("Login", () -> {
                    System.out.println("Pressed login");
                    loginPage.draw(window);
                }),
                new Button("Logout", () -> {
                    System.out.println("Pressed logout");
                    userVerificationService.logout(UserCache.authorizeRequest());
                }),
                new Button("Menu", () -> baseMenu.draw(window))
        );

        Panel panel = new Panel();
        buttons.forEach(button -> button.addTo(panel));

        window.setComponent(panel);
    }

}
