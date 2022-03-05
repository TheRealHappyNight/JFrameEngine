package snake.game;

import engine.gamepropertiessingleton.GamePropertiesSingleton;
import engine.basictypes.Image2D;
import engine.basictypes.Vector2;
import engine.rendering.interfaces.Drawable;
import snake.game.enums.SnakeGameObjects;
import snake.internal.SnakeGraphicsData;
import snake.internal.resourceloading.Resources;

import java.awt.*;
import java.time.Instant;
import java.util.Random;

public class SnakeEnvironment implements Drawable {
    private int widthUnits;
    private int heightUnits;
    private int gameUnit;

    private Image2D[][] background;
    private SnakeGameObjects[][] gameObjects;
    private Snake snake;
    private Random random;
    private Vector2 pickupPosition;

    public SnakeEnvironment(SnakeGraphicsData snakeGraphics) {
        random = new Random(Instant.now().getNano());
//        Random random = new Random(436); issue with this one

        initImages(snakeGraphics);

        int width = GamePropertiesSingleton.getInstance().getScreenWidth();
        int height = GamePropertiesSingleton.getInstance().getScreenHeight();
        this.gameUnit = GamePropertiesSingleton.getInstance().getGameUnit();
        this.widthUnits = width/this.gameUnit;
        this.heightUnits = height/this.gameUnit;

        initBackgroundWithDefaultImage();

        generateMargins();

        snake = new Snake(snakeGraphics, this);
    }

    private void initImages(SnakeGraphicsData snakeGraphics) {
        this.fenceLeft = snakeGraphics.getResource(Resources.FENCELEFT.getKey());
        this.fenceLeftRightMid = snakeGraphics.getResource(Resources.FENCELEFTRIGHTMID.getKey());
        this.fenceRight = snakeGraphics.getResource(Resources.FENCERIGHT.getKey());
        this.fenceSingle = snakeGraphics.getResource(Resources.FENCESINGLE.getKey());
        this.fenceUp = snakeGraphics.getResource(Resources.FENCEUP.getKey());
        this.fenceDown = snakeGraphics.getResource(Resources.FENCEDOWN.getKey());;
        this.fenceUpDownMid = snakeGraphics.getResource(Resources.FENCEUPDOWNMID.getKey());;
        this.fenceCornerUpLeft = snakeGraphics.getResource(Resources.CORNERUPLEFT.getKey());;
        this.fenceCornerUpRight = snakeGraphics.getResource(Resources.CORNERUPRIGHT.getKey());;
        this.fenceCornerDownLeft = snakeGraphics.getResource(Resources.CORNERDOWNLEFT.getKey());;
        this.fenceCornerDownRight = snakeGraphics.getResource(Resources.CORNERDOWNRIGHT.getKey());;
        this.road = snakeGraphics.getResource(Resources.ROAD.getKey());
        this.pickup = snakeGraphics.getResource(Resources.PICKUP.getKey());
    }

    private void initBackgroundWithDefaultImage() {
        this.background = new Image2D[this.widthUnits][this.heightUnits];
        this.gameObjects = new SnakeGameObjects[this.widthUnits][this.heightUnits];
        for(int indexX = 0; indexX < this.widthUnits; ++indexX) {
            for (int indexJ = 0; indexJ < this.heightUnits; ++indexJ) {
                Vector2 position = new Vector2(indexX * this.gameUnit, indexJ * this.gameUnit);
                this.background[indexX][indexJ] = new Image2D(position, this.road.getImage());
                this.gameObjects[indexX][indexJ] = SnakeGameObjects.NOTHING;
            }
        }
    }

    public void generateMargins() {
        int[][] margins = randomizeWalls();

        //Group walls together for a better feel
        setCorrectWallOnTopAndBottom(margins);
        setCorrectWallOnLeftAndRight(margins);

        setCorrectImageForTopLeftCorner(margins);
        setCorrectImageForTopRightCorner(margins);
        setCorrectImageForBottomLeftCorner(margins);
        setCorrectImageForBottomRightCorner(margins);
    }

    private int[][] randomizeWalls() {
        //1 means there is a wall there
        int[][] margins = new int[widthUnits][heightUnits];
        for(int index = 0; index < widthUnits; ++index) {
            margins[index][0] = random.nextInt(2);
            margins[index][heightUnits - 1] = margins[index][0];

            if (margins[index][0] == 1) {
                this.background[index][0].setImage(fenceSingle.getImage());
                this.background[index][heightUnits - 1].setImage(fenceSingle.getImage());
                this.gameObjects[index][0] = SnakeGameObjects.WALL;
                this.gameObjects[index][heightUnits - 1] = SnakeGameObjects.WALL;
            }
        }

        for(int index = 1; index < heightUnits - 1; ++index) {
            margins[0][index] = random.nextInt(2);
            margins[widthUnits - 1][index] = margins[0][index];

            if (margins[0][index] == 1) {
                this.background[0][index].setImage(fenceSingle.getImage());
                this.background[widthUnits - 1][index].setImage(fenceSingle.getImage());
                this.gameObjects[0][index] = SnakeGameObjects.WALL;
                this.gameObjects[widthUnits - 1][index] = SnakeGameObjects.WALL;
            }
        }
        return margins;
    }

    private void setCorrectWallOnTopAndBottom(int[][] margins) {
        for(int index = 0; index < widthUnits; ++index) {
            if (margins[index][0] != 1) {
                continue;
            }

            if (index + 1 < widthUnits && margins[index + 1][0] == 1) {
                if (index - 1 >= 0 && margins[index - 1][0] == 1) {
                    this.background[index][0].setImage(fenceLeftRightMid.getImage());
                    this.background[index][heightUnits - 1].setImage(fenceLeftRightMid.getImage());
                } else {
                    this.background[index][0].setImage(fenceLeft.getImage());
                    this.background[index][heightUnits - 1].setImage(fenceLeft.getImage());
                }
            } else {
                if (index - 1 >= 0 && margins[index - 1][0] == 1) {
                    this.background[index][0].setImage(fenceRight.getImage());
                    this.background[index][heightUnits - 1].setImage(fenceRight.getImage());
                } else {
                    this.background[index][0].setImage(fenceSingle.getImage());
                    this.background[index][heightUnits - 1].setImage(fenceSingle.getImage());
                }
            }
        }
    }

    private void setCorrectWallOnLeftAndRight(int[][] margins) {
        for(int index = 0; index < heightUnits; ++index) {
            if (margins[0][index] != 1) {
                continue;
            }
            if (index + 1 < heightUnits && margins[0][index + 1] == 1) {
                if (index - 1 >= 0 && margins[0][index - 1] == 1) {
                    this.background[0][index].setImage(fenceUpDownMid.getImage());
                    this.background[widthUnits - 1][index].setImage(fenceUpDownMid.getImage());
                } else {
                    this.background[0][index].setImage(fenceDown.getImage());
                    this.background[widthUnits - 1][index].setImage(fenceDown.getImage());
                }
            } else {
                if (index - 1 >= 0 && margins[0][index - 1] == 1) {
                    this.background[0][index].setImage(fenceUp.getImage());
                    this.background[widthUnits - 1][index].setImage(fenceUp.getImage());
                } else {
                    this.background[0][index].setImage(fenceSingle.getImage());
                    this.background[widthUnits - 1][index].setImage(fenceSingle.getImage());
                }
            }
        }
    }

    private void setCorrectImageForTopLeftCorner(int[][] margins) {
        if (margins[0][0] != 1) {
            return;
        }
        if (margins[1][0] == 1) {
            if (margins[0][1] == 1) {
                this.background[0][0].setImage(fenceCornerDownRight.getImage());
            } else {
                this.background[0][0].setImage(fenceLeft.getImage());
            }
        } else {
            if (margins[0][1] == 1) {
                this.background[0][0].setImage(fenceDown.getImage());
            }
        }
    }

    private void setCorrectImageForTopRightCorner(int[][] margins) {
        if (margins[widthUnits - 1][0] != 1) {
            return;
        }

        if (margins[widthUnits - 2][0] == 1) {
            if (margins[widthUnits - 1][1] == 1) {
                this.background[widthUnits - 1][0].setImage(fenceCornerDownLeft.getImage());
            } else {
                this.background[widthUnits - 1][0].setImage(fenceRight.getImage());
            }
        } else {
            if (margins[widthUnits - 1][1] == 1) {
                this.background[widthUnits - 1][0].setImage(fenceDown.getImage());
            }
        }
    }

    private void setCorrectImageForBottomLeftCorner(int[][] margins) {
        if (margins[0][heightUnits - 1] != 1) {
            return;
        }
        if (margins[0][heightUnits - 2] == 1) {
            if (margins[1][heightUnits - 1] == 1) {
                this.background[0][heightUnits - 1].setImage(fenceCornerUpRight.getImage());
            } else {
                this.background[0][heightUnits - 1].setImage(fenceUp.getImage());
            }
        } else {
            if (margins[1][heightUnits - 1] == 1) {
                this.background[0][heightUnits - 1].setImage(fenceLeft.getImage());
            }
        }
    }

    private void setCorrectImageForBottomRightCorner(int[][] margins) {
        if (margins[widthUnits - 1][heightUnits - 1] != 1) {
            return;
        }

        if (margins[widthUnits - 1][heightUnits - 2] == 1) {
            if (margins[widthUnits - 2][heightUnits - 1] == 1) {
                this.background[widthUnits - 1][heightUnits - 1].setImage(fenceCornerUpLeft.getImage());
            } else {
                this.background[widthUnits - 1][heightUnits - 1].setImage(fenceUp.getImage());
            }
        } else {
            if (margins[widthUnits - 2][heightUnits - 1] == 1) {
                this.background[widthUnits - 1][heightUnits - 1].setImage(fenceRight.getImage());
            }
        }
    }

    public void draw(Graphics2D g) {
        for(int indexX = 0; indexX < this.widthUnits; ++indexX) {
            for (int indexJ = 0; indexJ < this.heightUnits; ++indexJ) {
                Image2D image = this.background[indexX][indexJ];
                g.drawImage(image.getImage(), image.getPixelStartPosition().getX(), image.getPixelStartPosition().getY(), null);
            }
        }
        drawGrid(g);
        snake.draw(g);
        updateSnakePosition();
    }

    private Vector2 deleteLasPos;
    public void updateSnakePosition() {
//        var snake = this.snake.getSnake();
//        if (null != deleteLasPos) {
//            this.gameObjects[deleteLasPos.getX()][deleteLasPos.getY()] = SnakeGameObjects.NOTHING;
//        }
//        this.deleteLasPos = new Vector2(snake.get(snake.size() - 1).getPosition());
//        for(var el : snake) {
//            this.gameObjects[el.getPosition().getX()][el.getPosition().getY()] = SnakeGameObjects.SNAKE;
//        }
    }

    private void drawGrid(Graphics2D g2D) {
        int screenWidth = this.widthUnits * gameUnit;
        int screenHeight = this.heightUnits * gameUnit;
        for(int index = 0; index < screenWidth; index += 45) {
            g2D.drawLine(index, 0, index, screenHeight);
        }

        for(int index = 0; index < screenHeight; index += 45) {
            g2D.drawLine(0, index, screenWidth, index);
        }
    }

    public boolean gameObjectIs(Vector2 position, SnakeGameObjects object) {
        return gameObjectIs(position.getX(), position.getY(), object);
    }

    public boolean gameObjectIs(int x, int y, SnakeGameObjects object) {
        if (x < 0 || x >= this.widthUnits || y < 0 || y >= this.heightUnits) {
            return false;
        }

        return gameObjects[x][y] == object;
    }

    public boolean isBlocked(Vector2 position) {
        return isBlocked(position.getX(), position.getY());
    }

    public boolean isBlocked(int x, int y) {
        if (x < 0 || x >= this.widthUnits || y < 0 || y >= this.heightUnits) {
            return false;
        }

        return gameObjects[x][y] != SnakeGameObjects.NOTHING;
    }

    public void spawnPickup() {
        int x = random.nextInt(widthUnits);
        int y = random.nextInt(heightUnits);
        while (isBlocked(x,y) || snake.isBlocking(x,y)) {
            x = random.nextInt(widthUnits - 1);
            y = random.nextInt(heightUnits - 1);
        }

        this.gameObjects[x][y] = SnakeGameObjects.PICKUP;
        this.pickupPosition = new Vector2(x,y);
        this.background[x][y].setImage(pickup.getImage());
    }

    public void consumePickup() {
        if (null == this.pickupPosition) {
            return;
        }
        int x = this.pickupPosition.getX();
        int y = this.pickupPosition.getY();
        this.gameObjects[x][y] = SnakeGameObjects.NOTHING;
        this.background[x][y].setImage(road.getImage());
        this.pickupPosition = new Vector2(-1,-1);
    }

    public void clearPickup() {
        consumePickup();
    }

    public Snake getSnake() {
        return snake;
    }

    private Image2D fenceUp;
    private Image2D fenceDown;
    private Image2D fenceUpDownMid;
    private Image2D fenceCornerUpLeft;
    private Image2D fenceCornerUpRight;
    private Image2D fenceCornerDownLeft;
    private Image2D fenceCornerDownRight;
    private Image2D fenceLeft;
    private Image2D fenceRight;
    private Image2D fenceLeftRightMid;
    private Image2D fenceSingle;
    private Image2D road;
    private Image2D pickup;
}
