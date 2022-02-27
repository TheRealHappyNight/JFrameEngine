package engine.loading;

import engine.basictypes.Image2D;
import engine.basictypes.Vector2;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ResourceLoader {
    private final static String PREFIX = "src/main/resources/";
    private final Map<String, Image2D> sprites;

    public ResourceLoader() {
        this.sprites = new HashMap<>();
    }

    public void loadSprites(String[] keyNames, String[] filePaths) {
        if (keyNames.length != filePaths.length) {
            throw new IllegalArgumentException();
        }

        for(int index = 0; index < keyNames.length; ++index) {
            File file = new File(PREFIX + filePaths[index]);
            Image2D image = null;
            try {
                image = new Image2D(new Vector2(), ImageIO.read(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.sprites.put(keyNames[index], image);
        }
    }

    public void loadSprites(String keyNames, String filePaths) {
        File file = new File(PREFIX + filePaths);
        Image2D image = null;
        try {
            image = new Image2D(new Vector2(), ImageIO.read(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.sprites.put(keyNames, image);
    }

    public Image2D getImage(String key) {
        return this.sprites.get(key);
    }
}
