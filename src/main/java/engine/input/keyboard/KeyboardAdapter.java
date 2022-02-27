package engine.input.keyboard;

import engine.input.enums.Direction;
import engine.input.interfaces.Pressable;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static engine.input.keyboard.KeyEvent2D.Keys.*;

public class KeyboardAdapter extends KeyAdapter {
    private Direction direction = Direction.NO_DIRECTION;
    private Pressable controllable;

    public KeyboardAdapter(Pressable controllable) {
        this.controllable = controllable;
    }

    public KeyboardAdapter(Direction direction, Pressable controllable) {
        this.direction = direction;
        this.controllable = controllable;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        KeyEvent2D keyEvent2D = null;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP: {
                this.direction = Direction.UP;
                keyEvent2D = new KeyEvent2D(MOVE_KEY, this.direction);
                break;
            }
            case KeyEvent.VK_DOWN: {
                this.direction = Direction.DOWN;
                keyEvent2D = new KeyEvent2D(MOVE_KEY, this.direction);
                break;
            }
            case KeyEvent.VK_LEFT: {
                this.direction = Direction.LEFT;
                keyEvent2D = new KeyEvent2D(MOVE_KEY, this.direction);
                break;
            }
            case KeyEvent.VK_RIGHT: {
                this.direction = Direction.RIGHT;
                keyEvent2D = new KeyEvent2D(MOVE_KEY, this.direction);
                break;
            }
            case KeyEvent.VK_ENTER: {
                keyEvent2D = new KeyEvent2D(PLAY_KEY);
                break;
            }
        }

        if (null != keyEvent2D) {
            controllable.processKeyEvent(keyEvent2D);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void setControllable(Pressable controllable) {
        this.controllable = controllable;
    }
}
