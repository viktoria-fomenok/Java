package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import entity.Step;
import enums.Direction;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.util.Duration;


/**
 *
 * @author vikyf_000 This class generates a random appearance of food and the
 *         creation and movement of the snake.
 *
 */
public class NewGame implements Constants {

    static List<String> tileList = new ArrayList<String>();

    public static final int BLOCK_SIZE = 40;
    public static String mode;

    public static final int APP_W = 20 * BLOCK_SIZE;
    public static final int APP_H = 15 * BLOCK_SIZE;
    public double speed = 0;
    public Scene sceneGame;
    private Direction direction = Direction.RIGHT;
    private boolean moved = false;
    private boolean running = false;
    File gameReplayFile;
    private Timeline timeline = new Timeline();
    public ObservableList<Node> snake;
    public Rectangle food = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
    public Node tail = null;
    public int length = 0;
    public int start = 0;
    int i = 0;

    boolean pause = true;
    public ArrayList<Step> listOfSteps = null;


    private Parent createContent(int level, boolean intellectPlay) {

        Pane root = new Pane();
        root.setPrefSize(APP_W, APP_H);
        root.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));

        Group snakeBody = new Group();
        snake = snakeBody.getChildren();

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
            if (start == 0) {
                writeInFile(length, food.getTranslateX(), food.getTranslateY(), 0, 0);
                start++;
            }

            boolean toRemove = snake.size() > 1;
            tail = toRemove ? snake.remove(snake.size() - 1) : snake.get(0);

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

            if (tail.getTranslateX() == food.getTranslateX() && tail.getTranslateY() == food.getTranslateY()) {
                length++;
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
            writeInFile(length, food.getTranslateX(), food.getTranslateY(), tail.getTranslateX(), tail.getTranslateY());
            if (intellectPlay) {
                intellect(food.getTranslateX(), food.getTranslateY(), tail.getTranslateX(), tail.getTranslateY());
            }
        });

        if (pause) {
            timeline.getKeyFrames().add(frame);
            timeline.setCycleCount(Timeline.INDEFINITE);
        }

        root.getChildren().addAll( food, snakeBody);
        return root;
    }


    public void writeInFile(int length, double tailPosX, double tailPosY, double foodPosX, double foodPosY) {
        listOfSteps.add(new Step(length, foodPosX, foodPosY, tailPosX, tailPosY));
    }

    /**
     *
     * @param foodPosY
     * @param foodPosX
     * @param tailPosX
     * @param tailPosY
     *            This method allows the snake to move automatically
     */
    public void intellect(double foodPosX, double foodPosY, double tailPosX, double tailPosY) {
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

    class ThreadForReplay extends Thread {
        ArrayList<Step> list;
        int count = 0;
        int start = 0;
        ArrayList<Node> tails = new ArrayList<>();

        public ThreadForReplay(ArrayList<Step> list) {
            this.list = new ArrayList<>(list);
        }

        @Override
        public void run() {
            if (list.isEmpty()) {
                System.out.println("Enpty list");
            }
            System.out.println(list.size());
            for (Step step : list) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                boolean toRemove = snake.size() > 1;
                if (toRemove) {
                    Platform.runLater(() -> {
                        System.out.println(snake.size());
                        tail = snake.remove(snake.size() - 1);
                    });
                } else {
                    tail = snake.get(0);
                }
                if (step.getLength() > count) {
                    double tailX = food.getTranslateX();
                    double tailY = food.getTranslateY();

                    double tail1X = tail.getTranslateX();
                    double tail1Y = tail.getTranslateY();

                    Rectangle rectangle = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
                    rectangle.setTranslateX(tail1X);
                    rectangle.setTranslateY(tail1Y);
                    rectangle.setFill(Color.GREEN);

                    tail.setTranslateX(tailX);
                    tail.setTranslateY(tailY);
                    if (count == 0) {
                        tails.add(tail);
                        tails.add(rectangle);
                    } else {
                        tails.add(tails.size()-1, rectangle);
                        tails.add(0, tail);
                    }
                    count++;
                } else {
                    tail.setTranslateX(step.getAppleX());
                    tail.setTranslateY(step.getAppleY());
                    food.setTranslateX(step.getSnakeXBody());
                    food.setTranslateY(step.getSnakeYBody());
                    if (step.getLength() > 0) {
                        Rectangle rectangle = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
                        rectangle.setTranslateX(step.getAppleX());
                        rectangle.setTranslateY(step.getAppleY());
                        rectangle.setFill(Color.GREEN);
                        tails.add(0, rectangle);
                    }
                }
                if (step.getLength() < (tails.size() - 1)) {
                    for (int i = 0; i < (tails.size() - step.getLength()); i++) {
                        tails.remove(tails.size() - 1);
                    }
                }
                if (step.getLength() > length) {
                    Platform.runLater(() -> snake.clear());
                }
                for (Node we : tails) {
                    Platform.runLater(() -> snake.add(we));
                }
            }
            int click = JOptionPane.showConfirmDialog(null, "The End", "The End", JOptionPane.CLOSED_OPTION);
            if (click == JOptionPane.YES_OPTION) {
                Platform.runLater(() -> changeScene());
            }
            Platform.runLater(() -> snake.clear());
        }
    }

    public void replayFromFile() {
        if (listOfSteps != null) {
            listOfSteps.clear();
        }
        final FileChooser fileChooser = new FileChooser();
        File gameReplayFile = fileChooser.showOpenDialog(null);
        listOfSteps = FileStream.readFromFile(gameReplayFile);
        ThreadForReplay thread = new ThreadForReplay(listOfSteps);
        thread.start();
        try {
        } catch (IllegalThreadStateException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @throws IOException
     *             This method allows the restart of the game
     */
    private void restartGame() throws IOException {
        stopGame(true);
    }

    public void startGame() {
        listOfSteps = new ArrayList<Step>();
        direction = Direction.RIGHT;
        Rectangle head = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
        snake.add(head);
        timeline.play();
        running = true;
        head.setFill(Color.GREEN);
    }

    /**
     *
     * @param endFlag
     * This method allows the game stop and move to the main menu
     * @throws IOException
     */
    private void stopGame(boolean endFlag) throws IOException {
        final FileChooser fileChooser;
        running = false;
        timeline.stop();
        if (endFlag) {
            int click = JOptionPane.showConfirmDialog(null, "Game Over", "Game Over", JOptionPane.CLOSED_OPTION);
            fileChooser = new FileChooser();
            gameReplayFile = fileChooser.showSaveDialog(null);
            FileStream.writeUsingFiles(listOfSteps, gameReplayFile, false);
            if (click == JOptionPane.YES_OPTION) {
                changeScene();
            }
            snake.clear();
        } else {
            changeScene();
        }
    }


    private void pauseGame() {
        timeline.stop();
        running = false;
    }

    public void changeScene() {
        GameMenu gameMenu = null;
        try {
            gameMenu = new GameMenu();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Pane root = new Pane();
        Scene sceneNewgame = new Scene(root);
        root.setPrefSize(SCREEN_HEIGHT, SCREEN_WIDTH);
        root.getChildren().addAll(GlobalVariables.imageView, gameMenu);
        sceneNewgame.setRoot(root);
        GlobalVariables.stage.setScene(sceneNewgame);
    }

    /**
     *
     * @param level
     *            presents a selection of the level play
     * @param intellectPlay
     *            This method allows the snake movement of the arrows and
     *            automatically
     */
    public void setScene(int level, boolean intellectPlay) {
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
                        e.printStackTrace();
                    }
                    break;
                case R: {
                    pause = false;
                    try {
                        pauseGame();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    replayFromFile();
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

    public void setReplayScene(int level, boolean intellectPlay) {
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
                        e.printStackTrace();
                    }
                    break;
                case R: {
                    pause = false;
                    try {
                        pauseGame();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    replayFromFile();
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