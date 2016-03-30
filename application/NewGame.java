package application;

import java.io.IOException;
import javax.swing.JOptionPane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class NewGame {

  public static final int BLOCK_SIZE = 40;
  public static final int APP_W = 20 * BLOCK_SIZE;
  public static final int APP_H = 15 * BLOCK_SIZE;
  public Scene sceneGame;
  private Direction direction = Direction.RIGHT;
  private boolean moved = false;
  private boolean running = false;
  private Timeline timeline = new Timeline();
  private ObservableList<Node> snake;

  public enum Direction {
    UP, DOWN, LEFT, RIGHT;
  }

  private Parent createContent() {
    Pane root = new Pane();
    root.setPrefSize(APP_W, APP_H);

    Group snakeBody = new Group();
    snake = snakeBody.getChildren();

    Rectangle food = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
    food.setFill(Color.RED);

    food.setTranslateX((int) (Math.random() * (APP_W - BLOCK_SIZE)) / BLOCK_SIZE * BLOCK_SIZE);
    food.setTranslateY((int) (Math.random() * (APP_H - BLOCK_SIZE)) / BLOCK_SIZE * BLOCK_SIZE);

    KeyFrame frame = new KeyFrame(Duration.seconds(0.2), event -> {
      if (!running)
        return;

      boolean toRemove = snake.size() > 1;
      Node tail = toRemove ? snake.remove(snake.size() - 1) : snake.get(0);

      double tailX = tail.getTranslateX();
      double tailY = tail.getTranslateY();

      switch (direction) {
        case UP:
          tail.setTranslateX(snake.get(0).getTranslateX());
          tail.setTranslateY(snake.get(0).getTranslateY() - BLOCK_SIZE);
          break;
        case DOWN:
          tail.setTranslateX(snake.get(0).getTranslateX());
          tail.setTranslateY(snake.get(0).getTranslateY() + BLOCK_SIZE);
          break;
        case LEFT:
          tail.setTranslateX(snake.get(0).getTranslateX() - BLOCK_SIZE);
          tail.setTranslateY(snake.get(0).getTranslateY());
          break;
        case RIGHT:
          tail.setTranslateX(snake.get(0).getTranslateX() + BLOCK_SIZE);
          tail.setTranslateY(snake.get(0).getTranslateY());
          break;
      }

      moved = true;
      if (toRemove)
        snake.add(0, tail);
      for (Node rect : snake) {
        if (rect != tail && tail.getTranslateX() == rect.getTranslateX()
            && tail.getTranslateY() == rect.getTranslateY()) {
          try {
            restartGame();
          } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
          break;
        }
      }

      if (tail.getTranslateX() < 0 || tail.getTranslateX() >= APP_W || tail.getTranslateY() < 0
          || tail.getTranslateY() >= APP_W) {
        try {
          restartGame();
        } catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }

      if (tail.getTranslateX() == food.getTranslateX()
          && tail.getTranslateY() == food.getTranslateY()) {
        food.setTranslateX((int) (Math.random() * (APP_W - BLOCK_SIZE)) / BLOCK_SIZE * BLOCK_SIZE);
        food.setTranslateY((int) (Math.random() * (APP_H - BLOCK_SIZE)) / BLOCK_SIZE * BLOCK_SIZE);

        Rectangle rectangle = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
        rectangle.setTranslateX(tailX);
        rectangle.setTranslateY(tailY);
        rectangle.setFill(Color.GREEN);
        snake.add(rectangle);
      }

    });

    timeline.getKeyFrames().add(frame);
    timeline.setCycleCount(Timeline.INDEFINITE);

    root.getChildren().addAll(food, snakeBody);
    return root;
  }

  private void restartGame() throws IOException {
    stopGame();
  }

  public void startGame() { // beginning of the game
    direction = Direction.RIGHT;
    Rectangle head = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
    snake.add(head);
    timeline.play();
    running = true;
    head.setFill(Color.GREEN);
  }

  private void stopGame() throws IOException { // game stop and move to the main menu
    running = false;
    timeline.stop();
    snake.clear();
    int click =
        JOptionPane.showConfirmDialog(null, "Game Over", "Game Over", JOptionPane.CLOSED_OPTION);
    GameMenu gameMenu = new GameMenu();
    if (click == JOptionPane.YES_OPTION) {
      Pane root = new Pane();
      Scene sceneNewgame = new Scene(root);
      root.setPrefSize(1000, 600);
      root.getChildren().addAll(GlobalVariables.imageView, gameMenu);
      sceneNewgame.setRoot(root);
      GlobalVariables.stage.setScene(sceneNewgame);
    }
  }

  public void setScene() { // direction of movement of the snake
    Scene sceneGame = new Scene(createContent());
    sceneGame.setOnKeyPressed(event -> {
      if (moved) {
        switch (event.getCode()) {
          case W:
            if (direction != Direction.DOWN)
              direction = Direction.UP;
            break;
          case S:
            if (direction != Direction.UP)
              direction = Direction.DOWN;
            break;
          case A:
            if (direction != Direction.RIGHT)
              direction = Direction.LEFT;
            break;
          case D:
            if (direction != Direction.LEFT)
              direction = Direction.RIGHT;
            break;
          default:
            break;
        }

      }
      moved = false;
    });

    GlobalVariables.stage.setScene(sceneGame);
    GlobalVariables.stage.show();
    startGame();
  }
}
