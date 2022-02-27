package engine.input.enums;

import engine.basictypes.Vector2;

public enum Direction {
    NO_DIRECTION(),
    UP(new Vector2(0,-1)),
    DOWN(new Vector2(0,1)),
    LEFT(new Vector2(-1,0)),
    RIGHT(new Vector2(1,0));

    private final Vector2 direction;

    Direction() {
        direction = new Vector2(0,0);
    }

    Direction(Vector2 direction) {
        this.direction = direction;
    }

    public Vector2 getDirection() {
        return direction;
    }
}
