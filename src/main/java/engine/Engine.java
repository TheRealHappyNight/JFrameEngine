package engine;

import engine.input.keyboard.KeyboardAdapter;
import engine.input.mouse.MouseAdapter;
import engine.rendering.Window;

public class Engine {
    public Engine(GameProperties gameProperties) {
        new Window(gameProperties);
    }
}
