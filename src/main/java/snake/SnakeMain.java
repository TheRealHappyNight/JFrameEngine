package snake;

import engine.Engine;
import engine.gamepropertiessingleton.GamePropertiesSingleton;
import snake.game.SnakeEnvironment;
import snake.internal.SnakeGraphicsData;

public class SnakeMain {
    public static void main(String[] args) {
        SnakeGraphicsData snakeGraphicsData = new SnakeGraphicsData();
        SnakeEnvironment snakeEnvironment = new SnakeEnvironment(snakeGraphicsData);
        GamePropertiesSingleton.getInstance().addDrawable(snakeEnvironment,1);
        GamePropertiesSingleton.getInstance().addDrawable(snakeEnvironment.getSnake(),3);
        GamePropertiesSingleton.getInstance().setKeyboardListener(snakeEnvironment.getSnake());
        new Engine();
    }
}
