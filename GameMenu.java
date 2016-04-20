package application;

import java.io.IOException;
import javafx.animation.TranslateTransition;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 *
 * @author vikyf_000 This class describes buttons and that used in menu.
 *
 */
public class GameMenu extends Parent implements Constants {
  VBox startMenu = new VBox(SIZE_MENU_BOX);
  VBox optionMenu = new VBox(SIZE_MENU_BOX);
  VBox playGameMenu = new VBox(SIZE_MENU_BOX);
  MenuButton btnPlayGame;
  MenuButton btnOption;
  MenuButton btnExit;
  MenuButton btnBackFromOptions;
  StackPane stackPane;
  MenuButton btnSoundOn;
  MenuButton btnSoundOff;
  MenuButton btnLight;
  MenuButton btnNormal;
  MenuButton btnHard;
  MenuButton btnBackFromNewGame;
  MenuButton btnAutomatic;
  int level = 0;

  public GameMenu() throws IOException {

    startMenu.setTranslateX(MENU_TRANSLATE_X);
    startMenu.setTranslateY(MENU_TRANSLATE_Y);

    optionMenu.setTranslateX(MENU_TRANSLATE_X);
    optionMenu.setTranslateY(MENU_TRANSLATE_Y);

    playGameMenu.setTranslateX(MENU_TRANSLATE_X);
    playGameMenu.setTranslateY(MENU_TRANSLATE_Y);

    btnPlayGame = new MenuButton("Play Game");
    btnPlayGame.setOnMouseClicked(event -> {
      changeMenu(playGameMenu, startMenu, 1);
    });

    btnLight = new MenuButton("Light");
    btnLight.setOnMouseClicked(event -> {
      level = 1;
      NewGame newGame = new NewGame();
      newGame.setScene(level, false);
    });

    btnNormal = new MenuButton("Normal");
    btnNormal.setOnMouseClicked(event -> {
      level = 2;
      NewGame newGame = new NewGame();
      newGame.setScene(level, false);
    });

    btnHard = new MenuButton("Hard");
    btnHard.setOnMouseClicked(event -> {
      level = 3;
      NewGame newGame = new NewGame();
      newGame.setScene(level, false);
    });

    btnAutomatic = new MenuButton("Automatic");
    btnAutomatic.setOnMouseClicked(event -> {
      level = 3;
      NewGame newGame = new NewGame();
      newGame.setScene(level, true);
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

    btnBackFromNewGame = new MenuButton("Back");
    btnBackFromNewGame.setOnMouseClicked(event -> {
      changeMenu(startMenu, playGameMenu, 0);
    });

    btnSoundOn = new MenuButton("Sound on");
    btnSoundOn.setOnMouseClicked(event -> {

    });

    btnSoundOff = new MenuButton("Sound off");
    btnSoundOff.setOnMouseClicked(event -> {
    });

    startMenu.getChildren().addAll(btnPlayGame, btnAutomatic, btnOption, btnExit);
    optionMenu.getChildren().addAll(btnSoundOn, btnSoundOff, btnBackFromOptions);
    playGameMenu.getChildren().addAll(btnLight, btnNormal, btnHard, btnBackFromNewGame);
    btnPlayGame.getChildren().addAll();
    getChildren().addAll(startMenu);
  }

  public void changeMenu(VBox firstMenu, VBox secondMenu, int directionFlag) {
    getChildren().add(firstMenu);

    TranslateTransition translateTransition =
        new TranslateTransition(Duration.seconds(0.25), secondMenu);
    if (directionFlag == 1) {
      translateTransition.setToX(secondMenu.getTranslateX() + OFFSET);
    } else {
      translateTransition.setToX(secondMenu.getTranslateX() - OFFSET);
    }

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

