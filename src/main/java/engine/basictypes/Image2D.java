package engine.basictypes;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Image2D {
    private Vector2 pixelStartPosition;
    private AffineTransform transform;
    private BufferedImage image;

    public Image2D(Vector2 position, BufferedImage image) {
        this.pixelStartPosition = new Vector2(position);
        this.transform = new AffineTransform();
        this.transform.translate(position.getX(), position.getY());
        this.image = image;
    }

    public Image2D(Image2D image) {
        this.pixelStartPosition = new Vector2(image.getPixelStartPosition());
        this.transform = new AffineTransform();
        this.transform.translate(this.pixelStartPosition.getX(), this.pixelStartPosition.getY());
        this.image = image.getImage();
    }

    public void draw(Graphics2D g) {
        g.drawImage(image, transform, null);
    }

    public BufferedImage getImage() {
        return image;
    }

    public Vector2 getPixelStartPosition() {
        return pixelStartPosition;
    }

    public void setPixelStartPosition(Vector2 pixelStartPosition) {
        this.pixelStartPosition.set(pixelStartPosition);
        this.transform.setToIdentity();
        this.transform.translate(this.pixelStartPosition.getX(),this.pixelStartPosition.getY());
    }

    public final void rotateBy90(int count) {
        if (count % 4 == 0)
            return;

        double rads = Math.toRadians(90 * count);
        double anchorX = (double)image.getWidth()/2;
        double anchorY = (double)image.getHeight()/2;
        transform.rotate(rads, anchorX, anchorY);
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
