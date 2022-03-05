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
    private final ArrayList<Image2dByGameUnit> snake;
    private final SnakeEnvironment snakeEnvironment;

    private Direction direction;
    private final Timer timer;
    private boolean gameRunning;
    private boolean gameOver;

    public Snake(SnakeGraphicsData snakeGraphicsData, SnakeEnvironment gameEnvironment) {
        initImages(snakeGraphicsData);
        this.snake = new ArrayList<>();
        this.snakeEnvironment = gameEnvironment;
        timer = new Timer(GamePropertiesSingleton.getInstance().getGameSpeed() * 2, this::move);
        initGame();
    }

    private void initGame() {
        int x = (int) (GamePropertiesSingleton.getInstance().getScreenWidth() /
                GamePropertiesSingleton.getInstance().getGameUnit() * 0.25);
        int y = (int) (GamePropertiesSingleton.getInstance().getScreenHeight() /
                GamePropertiesSingleton.getInstance().getGameUnit() * 0.4);
        Vector2 startingPosition = new Vector2(x,y);
        this.snake.clear();
        this.snakeEnvironment.clearPickup();
        this.snake.add(new Image2dByGameUnit(startingPosition, this.headRight.getImage()));

        this.direction = Direction.RIGHT;
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
        if (gameOver)
            return;

        moveRestOfBody();
        moveHead();

        update();
        snakeEnvironment.updateSnakePosition();
    }

    private void moveHead() {
        var head = this.snake.get(0);
        Vector2 pos = new Vector2(head.getPosition());
        pos.add(this.direction.getDirection());
        moveHeadOnTheOtherSideOfTheMapWhenOutOfBounds(pos);
        head.setPosition(pos);
        head.setDirection(this.direction);
    }

    private void moveHeadOnTheOtherSideOfTheMapWhenOutOfBounds(Vector2 pos) {
        int screenHeightInGameUnits = GamePropertiesSingleton.getInstance().getScreenHeight()  /
                GamePropertiesSingleton.getInstance().getGameUnit();
        int screenWidthInGameUnits = GamePropertiesSingleton.getInstance().getScreenWidth()  /
                GamePropertiesSingleton.getInstance().getGameUnit();

        if (pos.getY() < 0) {
            pos.setY(screenHeightInGameUnits - 1);
        }
        else if (pos.getY() >= screenHeightInGameUnits) {
            pos.setY(0);
        }
        else if (pos.getX() < 0) {
            pos.setX(screenWidthInGameUnits - 1);
        }
        else if (pos.getX() >= screenWidthInGameUnits) {
            pos.setX(0);
        }
    }

    private void moveRestOfBody() {
        for(int index = this.snake.size() - 1; index > 0; --index) {
            Vector2 positionInFront = new Vector2(this.snake.get(index - 1).getPosition());
            var currEl = this.snake.get(index);
            Vector2 pos = new Vector2(positionInFront);
            currEl.setPosition(pos);
        }
    }

    public void update() {
        Vector2 currPosition = this.snake.get(0).getPosition();
        if (this.snakeEnvironment.gameObjectIs(currPosition, SnakeGameObjects.WALL) ||
                this.snakeEnvironment.gameObjectIs(currPosition, SnakeGameObjects.SNAKE)) {
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
        if (this.gameRunning) {
            this.gameRunning = false;
            this.gameOver = true;
        }
    }

    public void draw(Graphics2D g) {
        for(Image2dByGameUnit img : snake) {
            img.draw(g);
        }

        if (gameOver) {
            g.setColor(Color.red);
            g.setFont( new Font("Ink Free",Font.BOLD, 75));
            g.drawString("Game Over", GamePropertiesSingleton.getInstance().getScreenHeight() / 2,
                    GamePropertiesSingleton.getInstance().getScreenHeight() / 2);
        }

        if (!gameRunning) {
            g.setColor(Color.red);
            g.setFont( new Font("Ink Free",Font.BOLD, 75));
            g.drawString("Press Enter to Start", GamePropertiesSingleton.getInstance().getScreenHeight() / 3,
                    GamePropertiesSingleton.getInstance().getScreenHeight() / 3);
        }
    }

    public void addElement(Direction direction) {
        Vector2 newPos = new Vector2(this.snake.get(this.snake.size() - 1).getPosition());
        Image2D newImage = null;
        Direction newDir = Direction.NO_DIRECTION;
        switch (direction) {
            case UP: {
                newPos.setY(newPos.getY() + 1);
                if (snakeEnvironment.isBlocked(newPos)) {
                    newPos = findFirstFreePosition(this.snake.get(this.snake.size() - 1).getPosition());
                }
                newImage = this.bodyVer;
                newDir = Direction.UP;
                break;
            }
            case DOWN: {
                newPos.setY(newPos.getY() - 1);
                if (snakeEnvironment.isBlocked(newPos)) {
                    newPos = findFirstFreePosition(this.snake.get(this.snake.size() - 1).getPosition());
                }
                newImage = this.bodyVer;
                newDir = Direction.DOWN;
                break;
            }
            case LEFT: {
                newPos.setX(newPos.getX() + 1);
                if (snakeEnvironment.isBlocked(newPos)) {
                    newPos = findFirstFreePosition(this.snake.get(this.snake.size() - 1).getPosition());
                }
                newImage = this.bodyHor;
                newDir = Direction.LEFT;
                break;
            }
            case RIGHT: {
                newPos.setX(newPos.getX() - 1);
                if (snakeEnvironment.isBlocked(newPos)) {
                    newPos = findFirstFreePosition(this.snake.get(this.snake.size() - 1).getPosition());
                }
                newImage = this.bodyHor;
                newDir = Direction.RIGHT;
                break;
            }
        }

        if (null == newPos || null == newImage) {
            throw new NullPointerException();
        }
        Image2dByGameUnit newEl = new Image2dByGameUnit(newPos, newImage);
        newEl.setDirection(newDir);
        this.snake.add(newEl);
    }

    private Vector2 findFirstFreePosition(Vector2 lastElementPos) {
        if (!snakeEnvironment.isBlocked(lastElementPos.getX() + 1, lastElementPos.getY())) {
            return new Vector2(lastElementPos.getX() + 1, lastElementPos.getY());
        }
        else if (!snakeEnvironment.isBlocked(lastElementPos.getX() - 1, lastElementPos.getY())) {
            return new Vector2(lastElementPos.getX() - 1, lastElementPos.getY());
        }
        else if (!snakeEnvironment.isBlocked(lastElementPos.getX(), lastElementPos.getY() + 1)) {
            return new Vector2(lastElementPos.getX(), lastElementPos.getY() + 1);
        }
        else if (!snakeEnvironment.isBlocked(lastElementPos.getX(), lastElementPos.getY() - 1)) {
            return new Vector2(lastElementPos.getX(), lastElementPos.getY() - 1);
        }
        return null;
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
        if (this.gameRunning) {
            return;
        }
        this.gameRunning = true;
        this.gameOver = false;
        this.initGame();
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

    public ArrayList<Image2dByGameUnit> getSnake() {
        return snake;
    }

    private Image2D headUp;
    private Image2D headDown;
    private Image2D headLeft;
    private Image2D headRight;
    private Image2D bodyHor;
    private Image2D bodyVer;
}
