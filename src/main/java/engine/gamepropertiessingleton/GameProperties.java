package engine.gamepropertiessingleton;

import engine.input.interfaces.Clickable;
import engine.input.interfaces.Pressable;
import engine.rendering.interfaces.Drawable;
import engine.rendering.interfaces.Updateable;

import java.util.*;

public class GameProperties {
    private int screenWidth;
    private int screenHeight;
    private int gameSpeed;
    private int gameUnit;
    private Pressable keyboardListener;
    private Clickable mouseListener;
    private final SortedMap<Integer, ArrayList<Updateable>> updateables;
    private final SortedMap<Integer, ArrayList<Drawable>> drawables;

    public GameProperties() {
        screenWidth = 1080;
        screenHeight = 720;
        gameSpeed = 75;
        gameUnit = 45;
        this.updateables = new TreeMap<>();
        this.drawables = new TreeMap<>();
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

    public int getUpdateablesKeySize() {
        return this.updateables.keySet().size();
    }

    public ArrayList<Updateable> getUpdateable(int priority) {
        if (priority < 0 || priority >= this.updateables.keySet().size())
            throw new IllegalArgumentException();


        return this.updateables.get(priority);
    }

    public void addUpdateable(Updateable updateable, int priority) {
        var updateableList = this.updateables.get(priority);
        if (null == updateableList) {
            updateableList = new ArrayList<>();
        }
        updateableList.add(updateable);
        this.updateables.put(priority, (ArrayList<Updateable>) updateableList);
    }

    public void addUpdateable(List<Updateable> updateable, int priority) {
        var updateableList = this.updateables.get(priority);
        if (null == updateableList) {
            updateableList = new ArrayList<>();
        }
        updateableList.addAll(updateable);
        this.updateables.put(priority, updateableList);
    }

    public int getDrawableKeySize() {
        return this.drawables.keySet().size();
    }

    public SortedMap<Integer, ArrayList<Drawable>> getDrawable() {
        return this.drawables;
    }

    public ArrayList<Drawable> getDrawable(int priority) {
        return this.drawables.get(priority);
    }

    public void addDrawable(Drawable drawable, int priority) {
        ArrayList<Drawable> drawableList = this.drawables.get(priority);
        if (null == drawableList) {
            drawableList = new ArrayList<>();
        }
        drawableList.add(drawable);
        this.drawables.put(priority, drawableList);
    }

    public void addDrawable(List<Drawable> drawable, int priority) {
        ArrayList<Drawable> drawableList = this.drawables.get(priority);
        if (null == drawableList) {
            drawableList = new ArrayList<>();
        }
        drawableList.addAll(drawable);
        this.drawables.put(priority, drawableList);
    }
}
