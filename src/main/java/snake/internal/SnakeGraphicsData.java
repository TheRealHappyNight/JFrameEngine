package snake.internal;

import engine.basictypes.Image2D;
import engine.loading.ResourceLoader;
import snake.internal.resourceloading.Resources;

public class SnakeGraphicsData {
    private ResourceLoader resourceLoader;

    public SnakeGraphicsData() {
        startLoadingResources();
    }

    public void startLoadingResources() {
        resourceLoader = new ResourceLoader();
        for (var file : Resources.values()) {
            resourceLoader.loadSprites(file.getKey(), file.getFilePath());
        }
    }

    public Image2D getResource(String key) {
        return resourceLoader.getImage(key);
    }
}
