package engine.input.interfaces;

import engine.input.mouse.MouseEvent;

@FunctionalInterface
public interface Clickable {
    void processMouseInput(MouseEvent event);
}
