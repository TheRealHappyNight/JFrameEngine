package snake.game;

import engine.gamepropertiessingleton.GamePropertiesSingleton;
import engine.basictypes.Image2D;
import engine.basictypes.Vector2;
import engine.input.enums.Direction;
import engine.input.interfaces.Pressable;
import engine.input.keyboard.KeyEvent2D;
import engine.rendering.interfaces.Drawable;
import snake.game.enums.SnakeGameObjects;
import snake.internal.Image2dByGameUnit;
import snake.internal.SnakeGraphicsData;
import snake.internal.resourceloading.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class Snake implements Pressable, Drawable {
    private ArrayList<Image2dByGameUnit> snake;
    private SnakeEnvironment snakeEnvironment;

    private Direction direction;
    private Timer timer;

    public Snake(SnakeGraphicsData snakeGraphicsData, SnakeEnvironment gameEnvironment) {
        initImages(snakeGraphicsData);
        this.snake = new ArrayList<>();
        this.snakeEnvironment = gameEnvironment;

        int x = (int) (GamePropertiesSingleton.getInstance().getScreenWidth() /
                        GamePropertiesSingleton.getInstance().getGameUnit() * 0.25);
        int y = (int) (GamePropertiesSingleton.getInstance().getScreenHeight() /
                GamePropertiesSingleton.getInstance().getGameUnit() * 0.4);
        Vector2 startingPosition = new Vector2(x,y);
        this.snake.add(new Image2dByGameUnit(startingPosition, this.headRight.getImage()));

        this.direction = Direction.RIGHT;
        timer = new Timer(GamePropertiesSingleton.getInstance().getGameSpeed() * 2, this::move);
    }

    private void initImages(SnakeGraphicsData snakeGraphicsData) {
        this.headUp = snakeGraphicsData.getResource(Resources.HEADUP.getKey());
        this.headDown = snakeGraphicsData.getResource(Resources.HEADDOWN.getKey());
        this.headLeft = snakeGraphicsData.getResource(Resources.HEADLEFT.getKey());
        this.headRight = snakeGraphicsData.getResource(Resources.HEADRIGHT.getKey());
        this.bodyHor = snakeGraphicsData.getResource(Resources.BODYHORIZONTAL.getKey());
        this.bodyVer = snakeGraphicsData.getResource(Resources.BODYVERTICAL.getKey());
    }

    private void move(ActionEvent event) {
        moveRestOfBody();
        moveHead();

        update();
    }

    private void moveHead() {
        var head = this.snake.get(0);
        Vector2 pos = new Vector2(head.getPosition());
        pos.add(this.direction.getDirection());
        head.setPosition(pos);
        head.setDirection(this.direction);
    }

    private void moveRestOfBody() {
        for(int index = this.snake.size() - 1; index > 0; --index) {
            Vector2 positionInFront = new Vector2(this.snake.get(index - 1).getPosition());
            var currEl = this.snake.get(index);
            Vector2 pos = new Vector2(positionInFront);
            currEl.setPosition(pos);
            currEl.setDirection(this.direction);
        }
    }

    public void update() {
        Vector2 currPosition = this.snake.get(0).getPosition();
        if (this.snakeEnvironment.gameObjectIs(currPosition, SnakeGameObjects.WALL)) {
            gameOver();
        }

        if (this.snakeEnvironment.gameObjectIs(currPosition, SnakeGameObjects.PICKUP)) {
            this.snakeEnvironment.consumePickup();
            this.snakeEnvironment.spawnPickup();
            addElement(this.direction);
        }

        updateDirection();
    }

    public void updateDirection() {
        switch (this.direction) {
            case UP: {
                this.snake.get(0).setImage(headUp.getImage());
                break;
            }
            case DOWN: {
                this.snake.get(0).setImage(headDown.getImage());
                break;
            }
            case RIGHT: {
                this.snake.get(0).setImage(headRight.getImage());
                break;
            }
            case LEFT: {
                this.snake.get(0).setImage(headLeft.getImage());
                break;
            }
        }
        for(int index = this.snake.size() - 1; index > 0; --index) {
            var object = this.snake.get(index);
            var dir = this.snake.get(index - 1).getDirection();
            object.setDirection(dir);
            if (dir == Direction.UP || dir == Direction.DOWN) {
                object.setImage(bodyVer.getImage());
            } else {
                object.setImage(bodyHor.getImage());
            }
        }
    }

    public void gameOver() {
        System.out.println("GameOver");
    }

    public void draw(Graphics2D g) {
        for(Image2dByGameUnit img : snake) {
            img.draw(g);
        }
    }

    public void addElement(Direction direction) {
        Vector2 lastElementPos = new Vector2(this.snake.get(this.snake.size() - 1).getPosition());
        switch (direction) {
            case UP: {
                lastElementPos.setY(lastElementPos.getY() + 1);
                if (snakeEnvironment.isBlocked(lastElementPos)) {
                    //TODO::
                } else {
                    var newEl = new Image2dByGameUnit(lastElementPos, this.bodyVer);
                    newEl.setDirection(Direction.UP);
                    this.snake.add(newEl);
                }
                break;
            }
            case DOWN: {
                lastElementPos.setY(lastElementPos.getY() - 1);
                if (snakeEnvironment.isBlocked(lastElementPos)) {
                    //TODO::
                } else {
                    var newEl = new Image2dByGameUnit(lastElementPos, this.bodyVer);
                    newEl.setDirection(Direction.DOWN);
                    this.snake.add(newEl);
                }
                break;
            }
            case LEFT: {
                lastElementPos.setX(lastElementPos.getX() + 1);
                if (snakeEnvironment.isBlocked(lastElementPos)) {
                    //TODO::
                } else {
                    var newEl = new Image2dByGameUnit(lastElementPos, this.bodyHor);
                    newEl.setDirection(Direction.LEFT);
                    this.snake.add(newEl);
                }
                break;
            }
            case RIGHT: {
                lastElementPos.setX(lastElementPos.getX() - 1);
                if (snakeEnvironment.isBlocked(lastElementPos)) {
                    //TODO::
                } else {
                    var newEl = new Image2dByGameUnit(lastElementPos, this.bodyHor);
                    newEl.setDirection(Direction.RIGHT);
                    this.snake.add(newEl);
                }
                break;
            }
        }
    }

    @Override
    public void processKeyEvent(KeyEvent2D event) {
        switch (event.getKey()) {
            case MOVE_KEY: {
                handleMovement(event.getDirection());
                break;
            }
            case PLAY_KEY: {
                startGame();
                break;
            }
        }
    }

    public void startGame() {
        this.timer.start();
        this.snakeEnvironment.spawnPickup();
    }

    private void handleMovement(Direction direction) {
        switch (direction) {
            case UP: {
                if (this.direction == Direction.DOWN) {
                    return;
                }
                break;
            }
            case DOWN: {
                if (this.direction == Direction.UP) {
                    return;
                }
                break;
            }
            case RIGHT: {
                if (this.direction == Direction.LEFT) {
                    return;
                }
                break;
            }
            case LEFT: {
                if (this.direction == Direction.RIGHT) {
                    return;
                }
                break;
            }
        }
        this.direction = direction;
    }

    //Le cuplez cam tare aici
    public boolean isBlocking(int x, int y) {
        return isBlocking(new Vector2(x,y));
    }
    public boolean isBlocking(Vector2 position) {
        for (var element : this.snake) {
            if(element.getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }

    private Image2D headUp;
    private Image2D headDown;
    private Image2D headLeft;
    private Image2D headRight;
    private Image2D bodyHor;
    private Image2D bodyVer;
}
