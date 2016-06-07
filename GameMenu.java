package application;

import java.io.IOException;
import java.util.ArrayList;

import javafx.animation.TranslateTransition;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import entity.Step;
import sort.Sorting;


import java.io.File;

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
  MenuButton btnReplay;
  MenuButton btnGameStatistics;
  MenuButton btnJavaSort;
  MenuButton btnJavaSortLenght;
  MenuButton btnScalaSortLenght;
  MenuButton btnScalaSort;
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

    btnReplay = new MenuButton("Replay");
    btnReplay.setOnMouseClicked(event -> {
        NewGame newGame = new NewGame();
        newGame.replayFromFile();
        newGame.setScene(level, false);
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

    btnJavaSort = new MenuButton("JavaSort-Turns");
    btnJavaSort.setOnMouseClicked(event -> {
      long startTime = System.currentTimeMillis();
      String[] string = FileStream.sorting(false);
      long timeSpent = System.currentTimeMillis() - startTime;
      SortTable sortTable = new SortTable("JavaSortTurns time: " + timeSpent, string);
    });

    btnScalaSort = new MenuButton("ScalaSort-Turns");
    btnScalaSort.setOnMouseClicked(event -> {

      Sorting sorter = new Sorting();

      long startTime = System.currentTimeMillis();

      sorter.sort(NUMBER, false);
      long timeSpent = System.currentTimeMillis() - startTime;
      String string = sorter.getBestGame();

      File fileBest = new File(string);
      ArrayList<Step> listOfSteps = new ArrayList<Step>();
      listOfSteps = FileStream.readFromFile(fileBest);
      File bestGame = new File("BestGameScalaTurn");
      FileStream.writeUsingFiles(listOfSteps, bestGame, false);

      listOfSteps.clear();
      string = sorter.getWorstGame();
      File fileWorst = new File(string);
      listOfSteps = FileStream.readFromFile(fileWorst);
      File worstGame = new File("WorstGameScalaTurn");
      FileStream.writeUsingFiles(listOfSteps, worstGame, false);
    });

    btnGameStatistics = new MenuButton("Game Statistics");
    btnGameStatistics.setOnMouseClicked(event -> {
      Sorting sorter = new Sorting();
      sorter.sort(NUMBER, false);
      int length = sorter.getLength();
      int numberOfTurns = sorter.statistic(NUMBER);
      int bestLength = sorter.statistic(NUMBER);
      StatisticGame table = new StatisticGame(length, numberOfTurns, bestLength);
    });


    btnJavaSortLenght = new MenuButton("JavaSort-Lenght");
    btnJavaSortLenght.setOnMouseClicked(event -> {
      long startTime = System.currentTimeMillis();
      String[] string = FileStream.sorting(true);
      long timeSpent = System.currentTimeMillis() - startTime;
      SortTable sortTable = new SortTable("JavaSortLength time: " + timeSpent, string);
    });

    btnScalaSortLenght = new MenuButton("ScalaSort-Lenght");
    btnScalaSortLenght.setOnMouseClicked(event -> {
      Sorting sorter = new Sorting();

      long startTime = System.currentTimeMillis();

      sorter.sort(NUMBER, true);
      long timeSpent = System.currentTimeMillis() - startTime;
      String string = sorter.getBestGame();

      File fileBest = new File(string);
      ArrayList<Step> listOfSteps = new ArrayList<Step>();
      listOfSteps = FileStream.readFromFile(fileBest);
      File bestGame = new File("BestGameScalaLength");
      FileStream.writeUsingFiles(listOfSteps, bestGame, false);

      listOfSteps.clear();
      string = sorter.getWorstGame();
      File fileWorst = new File(string);
      listOfSteps = FileStream.readFromFile(fileWorst);
      File worstGame = new File("WorstGameScalaLength");
      FileStream.writeUsingFiles(listOfSteps, worstGame, false);
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

    startMenu.getChildren().addAll(btnPlayGame, btnReplay, btnAutomatic, btnGameStatistics, btnJavaSort, btnScalaSort,
        btnJavaSortLenght, btnScalaSortLenght, btnOption, btnExit);
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

