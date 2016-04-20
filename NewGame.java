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

/**
 *
 * @author vikyf_000 This class generates a random appearance of food and the creation and movement of the snake.
 *
 */
public class NewGame implements Constants {
  public static final int BLOCK_SIZE = 40;
  public static final int APP_W = 20 * BLOCK_SIZE;
  public static final int APP_H = 15 * BLOCK_SIZE;
  public double speed = 0;
  public Scene sceneGame;
  private Direction direction = Direction.RIGHT;
  private boolean moved = false;
  private boolean running = false;
  private Timeline timeline = new Timeline();
  private ObservableList<Node> snake;

  public enum Direction {
    UP, DOWN, LEFT, RIGHT;
  }

  private Parent createContent(int level, boolean intellectPlay) {
    Pane root = new Pane();
    root.setPrefSize(APP_W, APP_H);

    Group snakeBody = new Group();
    snake = snakeBody.getChildren();

    Rectangle food = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
    food.setFill(Color.RED);

    food.setTranslateX((int) (Math.random() * (APP_W - BLOCK_SIZE)) / BLOCK_SIZE * BLOCK_SIZE);
    food.setTranslateY((int) (Math.random() * (APP_H - BLOCK_SIZE)) / BLOCK_SIZE * BLOCK_SIZE);

    if (level == 1) {
      speed = 0.5;
    }
    if (level == 2) {
      speed = 0.2;
    }
    if (level == 3) {
      speed = 0.1;
    }

    KeyFrame frame = new KeyFrame(Duration.seconds(speed), event -> {
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
        if (snake.size() == 100) {
          try {
            restartGame();
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }

      if (intellectPlay) {
        intellect(food.getTranslateY(), food.getTranslateX(), tail.getTranslateX(),
            tail.getTranslateY());
      }
    });

    timeline.getKeyFrames().add(frame);
    timeline.setCycleCount(Timeline.INDEFINITE);
    root.getChildren().addAll(food, snakeBody);
    return root;
  }

 /**
  *
  * @param foodPosY
  * @param foodPosX
  * @param tailPosX
  * @param tailPosY This method allows the snake to move automatically
  */
  public void intellect(double foodPosY, double foodPosX, double tailPosX, double tailPosY) {

    int lengthY = (int) Math.abs(foodPosY - tailPosY);
    int lengthX = (int) Math.abs(foodPosX - tailPosX);
    for (int i = 0; i < lengthX;) {
      if (foodPosX > tailPosX) {
        direction = Direction.RIGHT;
        break;
      }
      if (foodPosX < tailPosX) {
        direction = Direction.LEFT;
        break;
      }
    }
    for (int i = 0; i < lengthY;) {
      if (foodPosY > tailPosY) {
        direction = Direction.DOWN;
        break;
      }
      if (foodPosY < tailPosY) {
        direction = Direction.UP;
        break;
      }
    }
  }

  /**
   *
   * @throws IOException This method allows the restart of the game
   */
  private void restartGame() throws IOException {
    stopGame(true);
  }

  /**
   * This method allows the beginning of the game
   */
  public void startGame() {
    direction = Direction.RIGHT;
    Rectangle head = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
    snake.add(head);
    timeline.play();
    running = true;
    head.setFill(Color.GREEN);
  }

  /**
   *
   * @param endFlag This method allows the game stop and move to the main menu
   * @throws IOException
   */
  private void stopGame(boolean endFlag) throws IOException {
    running = false;
    timeline.stop();
    snake.clear();
    if (endFlag) {
      int click =
          JOptionPane.showConfirmDialog(null, "Game Over", "Game Over", JOptionPane.CLOSED_OPTION);
      if (click == JOptionPane.YES_OPTION) {
        changeScene();
      }
    } else {
      changeScene();
    }
  }

  /**
   * This method changes the scene
   */
  public void changeScene() throws IOException {
    GameMenu gameMenu = new GameMenu();
    Pane root = new Pane();
    Scene sceneNewgame = new Scene(root);
    root.setPrefSize(SCREEN_HEIGHT, SCREEN_WIDTH);
    root.getChildren().addAll(GlobalVariables.imageView, gameMenu);
    sceneNewgame.setRoot(root);
    GlobalVariables.stage.setScene(sceneNewgame);
  }

  /**
   *
   * @param level presents a selection of the level play
   * @param intellectPlay This method allows the snake movement of the arrows and automatically
   */
  public void setScene(int level, boolean intellectPlay) { // direction of movement of the snake
    Scene sceneGame = new Scene(createContent(level, intellectPlay));
    sceneGame.setOnKeyPressed(event -> {
      if (moved) {
        switch (event.getCode()) {
          case UP:
            if (direction != Direction.DOWN)
              direction = Direction.UP;
            break;
          case DOWN:
            if (direction != Direction.UP)
              direction = Direction.DOWN;
            break;
          case LEFT:
            if (direction != Direction.RIGHT)
              direction = Direction.LEFT;
            break;
          case RIGHT:
            if (direction != Direction.LEFT)
              direction = Direction.RIGHT;
            break;
          case ESCAPE:
            try {
              stopGame(false);
            } catch (Exception e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
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