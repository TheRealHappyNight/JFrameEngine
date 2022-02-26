package engine.input.enums;

public enum Direction {
    NO_DIRECTION(-1),
    UP(0),
    DOWN(1),
    LEFT(2),
    RIGHT(3);

    private final int direction;

    Direction() {
        direction = -1;
    }

    Direction(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }
}
