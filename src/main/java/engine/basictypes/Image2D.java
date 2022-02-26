package engine.basictypes;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Image2D {
    private Vector2 position;
    private AffineTransform transform;
    private BufferedImage image;

    public Image2D(Vector2 position, BufferedImage image) {
        this.position = new Vector2(position);
        this.transform = new AffineTransform();
        this.transform.translate(position.getX(), position.getY());
        this.image = image;
    }

    public AffineTransform getTransform() {
        return transform;
    }

    public BufferedImage getImage() {
        return image;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position.add(position);
        this.transform.setToIdentity();
        this.transform.translate(this.position.getX(),this.position.getY());
    }

    public void rotateBy90(int count) {
        if (count % 4 == 0)
            return;

        double rads = Math.toRadians(90 * count);
        double anchorX = (double)image.getWidth()/2;
        double anchorY = (double)image.getHeight()/2;
        transform.rotate(rads, anchorX, anchorY);
    }

    public void paint(Graphics2D g2D) {
        g2D.drawImage(image, transform, null);
    }
}
