package engine.input.keyboard;


import engine.input.enums.Direction;

public class KeyEvent2D {
    public enum Keys {
        PLAY_KEY,
        MOVE_KEY;
    }

    private Keys key;
    private Direction direction;

    public KeyEvent2D(Keys key) {
        this.key = key;
    }

    public KeyEvent2D(Keys key, Direction direction) {
        this.key = key;
        this.direction = direction;
    }

    public void setKey(Keys key) {
        this.key = key;
    }

    public Keys getKey() {
        return key;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }
}
