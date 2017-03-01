package io.ariku.gui.console;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
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
            this.robot.setAutoDelay(20);

        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    public Keyboard typeDown() {
        charStorage.add(new TypeInput(DirectionButton.DOWN));
        return this;
    }

    public Keyboard typeEnter() {
        charStorage.add(new TypeInput(DirectionButton.ENTER));
        return this;
    }

    public Keyboard typeText(String text) {
        charStorage.add(new TypeInput(text));
        return this;
    }

    public void startTyping() {
        for (TypeInput typeInput : charStorage) {

            if (typeInput.directionButton != null)
                pressKey(typeInput.directionButton.keyCode);
            else if (typeInput.types != null)
                pressKeys(typeInput.types);

        }
    }

    protected void pressKeys(String chars) {

        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        StringSelection selection = new StringSelection(chars);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, null);

        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.keyPress(KeyEvent.VK_V);

        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_SHIFT);
    }

    private void pressKey(int keyCode) {
        robot.keyPress(keyCode);
        robot.keyRelease(keyCode);
    }

}