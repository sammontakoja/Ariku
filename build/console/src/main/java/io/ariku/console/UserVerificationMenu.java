package io.ariku.console;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Panel;

import java.util.Arrays;
import java.util.List;

/**
 * @author Ari Aaltonen
 */
public class UserVerificationMenu {

    public static void draw(BasicWindow window) {

        List<Button> buttons = Arrays.asList(
                new Button("SignUp", () -> {
                    System.out.println("Pressed SignUp");
                    SignUpPage.draw(window);
                }),
                new Button("VerifySignUp", () -> {
                    System.out.println("Pressed verifySignUp");
                    VerifySignUpPage.draw(window);
                }),
                new Button("Login", () -> {
                    System.out.println("Pressed login");
                    LoginPage.draw(window);
                }),
                new Button("Logout", () -> {
                    System.out.println("Pressed logout");
                    LogoutPage.draw(window);
                }),
                new Button("Menu", () -> BaseMenu.draw(window))
        );

        Panel panel = new Panel();
        buttons.forEach(button -> button.addTo(panel));

        window.setComponent(panel);
    }

}
