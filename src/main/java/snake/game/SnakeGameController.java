package snake.game;

public class SnakeGameController {
    private Snake snake;
    private SnakeEnvironment snakeEnvironment;

    public SnakeGameController(Snake snake, SnakeEnvironment snakeEnvironment) {
        this.snake = snake;
        this.snakeEnvironment = snakeEnvironment;
    }
}
