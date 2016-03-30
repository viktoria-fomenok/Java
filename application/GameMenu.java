package application;

import java.io.IOException;
import javafx.animation.TranslateTransition;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class GameMenu extends Parent implements Constants {
  VBox startMenu = new VBox(SIZE_MENU_BOX);
  VBox optionMenu = new VBox(SIZE_MENU_BOX);
  VBox LevelsMenu = new VBox(SIZE_MENU_BOX);
  MenuButton btnNewGame;
  MenuButton btnOption;
  MenuButton btnExit;
  MenuButton btnBackFromOptions;
  StackPane stackPane;

  public GameMenu() throws IOException {

    startMenu.setTranslateX(MENU_TRANSLATE_X);
    startMenu.setTranslateY(MENU_TRANSLATE_Y);

    optionMenu.setTranslateX(MENU_TRANSLATE_X);
    optionMenu.setTranslateY(MENU_TRANSLATE_Y);

    btnNewGame = new MenuButton("New Game");
    btnNewGame.setOnMouseClicked(event -> {
      NewGame newGame = new NewGame();
      newGame.setScene();;
    });

    btnOption = new MenuButton("Option");
    btnOption.setOnMouseClicked(event -> {
      changeMenu(optionMenu, startMenu, 1);
    });

    btnExit = new MenuButton("Exit");
    btnExit.setOnMouseClicked(event -> {
      System.exit(0);
    });

    btnBackFromOptions = new MenuButton("Back");
    btnBackFromOptions.setOnMouseClicked(event -> {
      changeMenu(startMenu, optionMenu, 0);
    });

    startMenu.getChildren().addAll(btnNewGame, btnOption, btnExit);
    optionMenu.getChildren().addAll(btnBackFromOptions);
    getChildren().addAll(startMenu);
  }

  public void changeMenu(VBox firstMenu, VBox secondMenu, int directionFlag) {
    getChildren().add(firstMenu);

    TranslateTransition translateTransition =
        new TranslateTransition(Duration.seconds(0.25), secondMenu);
    if (directionFlag == 1)
      translateTransition.setToX(secondMenu.getTranslateX() + OFFSET);
    else
      translateTransition.setToX(secondMenu.getTranslateX() - OFFSET);

    TranslateTransition translateTransition1 =
        new TranslateTransition(Duration.seconds(0.5), firstMenu);
    translateTransition1.setToX(secondMenu.getTranslateX());

    translateTransition.play();
    translateTransition1.play();

    translateTransition.setOnFinished(evt -> {
      getChildren().remove(secondMenu);
    });
  }
}

