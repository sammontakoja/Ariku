package io.ariku.console;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.awt.AWTKeyStroke.getAWTKeyStroke;
import static java.awt.event.InputEvent.SHIFT_DOWN_MASK;

/**
 * Simulate keyboard typing.
 *
 * @author Ari Aaltonen
 */
public class Keyboard {

    enum DirectionButton {
        UP(KeyEvent.VK_UP), DOWN(KeyEvent.VK_DOWN), ENTER(KeyEvent.VK_ENTER);
        public final int keyCode;

        DirectionButton(int keyCode) {
            this.keyCode = keyCode;
        }
    }

    class TypeInput {
        public DirectionButton directionButton;
        public String types;

        public TypeInput(DirectionButton directionButton) {
            this.directionButton = directionButton;
        }

        public TypeInput(String types) {
            this.types = types;
        }
    }

    private final Robot robot;
    private final List<TypeInput> charStorage = new ArrayList<>();

    public Keyboard() {
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    public void type(String chars) {
        charStorage.add(new TypeInput(chars));
    }

    public void typeDown() {
        charStorage.add(new TypeInput(DirectionButton.DOWN));
    }

    public void typeEnter() {
        charStorage.add(new TypeInput(DirectionButton.ENTER));
    }

    public void startTypingAfterTwoSeconds() {

        Executors.newScheduledThreadPool(1).schedule(() -> {

            for (TypeInput typeInput : charStorage) {

                if (typeInput.directionButton != null)
                    pressKey(typeInput.directionButton.keyCode);
                else if (typeInput.types != null)
                    pressChars(typeInput.types);

            }

        }, 2, TimeUnit.SECONDS);

    }

    private void pressChars(String chars) {
        for (int i = 0, len = chars.length(); i < len; i++) {
            char c = chars.charAt(i);
            AWTKeyStroke keyStroke = getKeyStroke(c);
            int keyCode = keyStroke.getKeyCode();

            boolean shift = Character.isUpperCase(c) || keyStroke.getModifiers() == (SHIFT_DOWN_MASK + 1);
            if (shift) {
                robot.keyPress(KeyEvent.VK_SHIFT);
            }

            pressKey(keyCode);

            if (shift) {
                robot.keyRelease(KeyEvent.VK_SHIFT);
            }
        }
    }

    private void pressKey(int keyCode) {
        robot.keyPress(keyCode);
        robot.keyRelease(keyCode);
    }

    private static AWTKeyStroke getKeyStroke(char c) {
        String upper = "`~'\"!@#$%^&*()_+{}|:<>?";
        String lower = "`~'\"1234567890-=[]\\;,./";

        int index = upper.indexOf(c);
        if (index != -1) {
            int keyCode;
            boolean shift = false;
            switch (c) {

                case '~':
                    shift = true;
                case '`':
                    keyCode = KeyEvent.VK_BACK_QUOTE;
                    break;
                case '\"':
                    shift = true;
                case '\'':
                    keyCode = KeyEvent.VK_QUOTE;
                    break;
                default:
                    keyCode = (int) Character.toUpperCase(lower.charAt(index));
                    shift = true;
            }
            return getAWTKeyStroke(keyCode, shift ? SHIFT_DOWN_MASK : 0);
        }
        return getAWTKeyStroke((int) Character.toUpperCase(c), 0);
    }

}