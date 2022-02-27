package snake.internal;

import engine.basictypes.Image2D;
import engine.basictypes.Vector2;
import engine.input.enums.Direction;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Image2dByGameUnit extends Image2D {
    public static final int gameUnit;
    private Vector2 position;
    private Direction direction;

    static {
        gameUnit = 45;
    }

    public Image2dByGameUnit(Vector2 position, BufferedImage image) {
        super(position, image);
        this.position = new Vector2(position);
        this.setPixelStartPosition(new Vector2(this.position.getX() * gameUnit, this.position.getY() * gameUnit));
    }

    public Image2dByGameUnit(Vector2 position, Image2D image) {
        super(image);
        this.position = new Vector2(position);
        this.setPixelStartPosition(new Vector2(this.position.getX() * gameUnit, this.position.getY() * gameUnit));
    }

    public Image2dByGameUnit(Image2D image) {
        super(image);
        this.position = new Vector2(image.getPixelStartPosition().getX() * gameUnit,
                image.getPixelStartPosition().getY() * gameUnit);
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
    }

    public void setPosition(Vector2 position) {
        this.setPixelStartPosition(new Vector2(position.getX() * gameUnit, position.getY() * gameUnit));
        this.position = new Vector2(position);
    }

    public Vector2 getPosition() {
        return this.position;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
