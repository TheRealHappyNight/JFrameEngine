package engine;

import engine.input.interfaces.Clickable;
import engine.input.interfaces.Pressable;

public class GameProperties {
    private int screenWidth;
    private int screenHeight;
    private int gameSpeed;
    private int gameUnit;
    private Pressable keyboardListener;
    private Clickable mouseListener;

    public GameProperties() {
        screenWidth = 1080;
        screenHeight = 720;
        gameSpeed = 75;
        gameUnit = 45;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public int getGameSpeed() {
        return gameSpeed;
    }

    public void setGameSpeed(int gameSpeed) {
        this.gameSpeed = gameSpeed;
    }

    public int getGameUnit() {
        return gameUnit;
    }

    public void setGameUnit(int gameUnit) {
        this.gameUnit = gameUnit;
    }

    public Pressable getKeyboardListener() {
        return keyboardListener;
    }

    public void setKeyboardListener(Pressable keyboardListener) {
        this.keyboardListener = keyboardListener;
    }

    public Clickable getMouseListener() {
        return mouseListener;
    }

    public void setMouseListener(Clickable mouseListener) {
        this.mouseListener = mouseListener;
    }
}
