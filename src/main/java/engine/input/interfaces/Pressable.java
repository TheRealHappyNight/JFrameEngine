package engine.input.interfaces;

import engine.input.keyboard.KeyEvent2D;

@FunctionalInterface
public interface Pressable {
    void processKeyEvent(KeyEvent2D event);
}
