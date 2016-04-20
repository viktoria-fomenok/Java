package application;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author vikyf_000 This class set the background image to the main menu.
 *
 */
public class Main extends Application implements Constants {
  @Override
  public void start(Stage primaryStage) throws IOException {
    GlobalVariables.stage = primaryStage;
    GlobalVariables.stage.setTitle("Snake");
    GlobalVariables.stage.setResizable(false);

    Pane root = new Pane();
    root.setPrefSize(SCREEN_HEIGHT, SCREEN_WIDTH);

    InputStream is = Files.newInputStream(Paths.get("snake.jpg"));
    Image image = new Image(is);
    is.close();
    ImageView imageView = new ImageView(image);
    GlobalVariables.imageView = imageView;
    imageView.setFitWidth(IMAGE_HEIGHT);
    imageView.setFitHeight(IMAGE_WIDTH);

    GameMenu gameMenu = new GameMenu();
    root.getChildren().addAll(imageView, gameMenu);
    Scene scene = new Scene(root);

    GlobalVariables.stage.setScene(scene);
    GlobalVariables.stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}

