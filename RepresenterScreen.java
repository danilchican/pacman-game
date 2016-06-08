package com.danilchican.pacman;

import javafx.animation.FadeTransition;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class RepresenterScreen extends Pane {
  private boolean isLose = true;

  private Label titleScreen = null;
  private Label score = null;
  private Label goBack = null;
  private Label nextLevel = null;

  private Rectangle rect = null;

  RepresenterScreen(boolean isLoseScreen) {
    rect = new Rectangle(Constants.screenWidth, Constants.screenHeight);
    rect.setFill(Color.BLACK);
    rect.setOpacity(0);
    isLose = isLoseScreen;
  }

  public void addTitle(String title, String color, int fontSize, int posX, int posY) {
    titleScreen = new Label(title);
    titleScreen.setOpacity(0);
    titleScreen.setTextFill(Color.web(color));
    titleScreen.setFont(new Font(fontSize));
    titleScreen.setTranslateX(posX);
    titleScreen.setTranslateY(posY);
  }

  public void addScore(String title, String color, int fontSize, int posX, int posY) {
    score = new Label(title);
    score.setOpacity(0);
    score.setTextFill(Color.web(color));
    score.setFont(new Font(fontSize));
    score.setTranslateX(posX);
    score.setTranslateY(posY);
  }

  public void addNextLevelButton(String title, String color, int fontSize, int posX, int posY) {
    nextLevel = new Label(title);
    nextLevel.setOpacity(0);
    nextLevel.setTextFill(Color.web(color));
    nextLevel.setFont(new Font(fontSize));
    nextLevel.setTranslateX(posX);
    nextLevel.setTranslateY(posY);
  }


  public void addGoBackButton(String title, String color, int fontSize, int posX, int posY) {
    goBack = new Label(title);
    goBack.setOpacity(0);
    goBack.setTextFill(Color.web(color));
    goBack.setFont(new Font(fontSize));
    goBack.setTranslateX(posX);
    if (!isLose() && GamePlay.nextLevel(Constants.currentLevel + 1)) {
      goBack.setTranslateY(posY);
    } else {
      goBack.setTranslateY(posY - 30);
    }

  }

  public void show() {
    getChildren().addAll(rect, titleScreen, score, goBack);

    if (!isLose() && GamePlay.nextLevel(Constants.currentLevel + 1)) {
      getChildren().add(nextLevel);
    }

    FadeTransition fadeBG = new FadeTransition(Duration.millis(900), rect);
    fadeBG.setFromValue(0);
    fadeBG.setToValue(0.6);
    fadeBG.setAutoReverse(true);

    FadeTransition fadeScore = new FadeTransition(Duration.millis(900), score);
    fadeScore.setFromValue(0);
    fadeScore.setToValue(1);
    fadeScore.setAutoReverse(true);

    FadeTransition fadeTitleScreen = new FadeTransition(Duration.millis(900), titleScreen);
    fadeTitleScreen.setFromValue(0);
    fadeTitleScreen.setToValue(1);
    fadeTitleScreen.setAutoReverse(true);

    FadeTransition fadeGoBack = null, fadeNextLevel = null;
    fadeGoBack = new FadeTransition(Duration.millis(900), goBack);
    fadeGoBack.setFromValue(0);
    fadeGoBack.setToValue(1);
    fadeGoBack.setAutoReverse(true);

    if (!isLose() && GamePlay.nextLevel(Constants.currentLevel + 1)) {
      fadeNextLevel = new FadeTransition(Duration.millis(900), nextLevel);
      fadeNextLevel.setFromValue(0);
      fadeNextLevel.setToValue(1);
      fadeNextLevel.setAutoReverse(true);
    }

    Constants.gameRoot.getChildren().add(this);

    fadeBG.play();
    fadeScore.play();
    fadeTitleScreen.play();
    fadeGoBack.play();

    if (!isLose() && GamePlay.nextLevel(Constants.currentLevel + 1)) {
      fadeNextLevel.play();
    }

    Constants.save.closeOutputStream();

    goBack.setOnMouseReleased(event -> {
      MapGenerator.clearGameScene();
    });

    if (!isLose() && GamePlay.nextLevel(Constants.currentLevel + 1)) {
      nextLevel.setOnMouseReleased(event -> {
        if (GamePlay.nextLevel(++Constants.currentLevel)) {
          Constants.gamePlay = new GamePlay();
          Constants.gamePlay.createThread(Constants.currentLevel);
        } else {
          Constants.currentLevel = 1;
        }

        MapGenerator.clearGameScene();
        Constants.startGame = true;
      });
    }
  }

  public boolean isLose() {
    return isLose ? true : false;
  }

  /**
   * Display finish screen
   * 
   * @see GamePlay#finishScreen()
   */
  public static void finishScreen() {
    RepresenterScreen screen = new RepresenterScreen(false);
    screen.addTitle("You Win", "#fff446", 68, Constants.screenWidth / 2 - 160,
        Constants.screenHeight / 2 - 190);
    screen.addScore("Your score: " + Constants.CurrentCountBonuses, "#e9e8d1", 28,
        Constants.screenWidth / 2 - 105, Constants.screenHeight / 2 - 60);
    screen.addNextLevelButton("Next Level", "#e9e8d1", 24, Constants.screenWidth / 2 - 83,
        Constants.screenHeight / 2 + 30);
    screen.addGoBackButton("Back to menu", "#e9e8d1", 24, Constants.screenWidth / 2 - 100,
        Constants.screenHeight / 2 + 90);

    screen.show();
  }

  /**
   * Display lose screen
   * 
   * @see GamePlay#loseScreen()
   */
  public static void loseScreen() {
    RepresenterScreen screen = new RepresenterScreen(true);
    screen.addTitle("Game Over", "#fff446", 68, Constants.screenWidth / 2 - 170,
        Constants.screenHeight / 2 - 190);
    screen.addScore("Your score: " + Constants.CurrentCountBonuses, "#e9e8d1", 28,
        Constants.screenWidth / 2 - 86, Constants.screenHeight / 2 - 60);
    screen.addGoBackButton("Back to menu", "#e9e8d1", 24, Constants.screenWidth / 2 - 75,
        Constants.screenHeight / 2 + 30);

    screen.show();

    if (Constants.startGame || Constants.startReplay) {
      Constants.player.setAlive(false);
    } else if (Constants.startBoot) {
      Constants.boot.setAlive(false);
    }

    GamePlay.freeThead();

    Constants.startGame = false;
    Constants.startBoot = false;
    Constants.startReplay = false;
    Constants.loseGame = false;

    Constants.currentLevel = 1;
  }

  public static void setLoadScreen() {
    Constants.loadScreen = new Pane();

    Constants.loadScreen.setPrefSize(Constants.screenWidth, Constants.screenHeight);
    Rectangle rect = new Rectangle(Constants.screenWidth, Constants.screenHeight);
    rect.setFill(Color.BLACK);

    // add rectangle to gameRoot
    Constants.loadScreen.getChildren().addAll(rect);

    Image blockImg = new Image(GamePlay.class.getResourceAsStream("loading_screen.gif"));
    Constants.loader = new ImageView(blockImg);
    Constants.loader.setFitHeight(Constants.screenHeight);
    Constants.loader.setFitWidth(Constants.screenWidth);
    Constants.loader
        .setViewport(new Rectangle2D(0, 0, Constants.screenWidth - 200, Constants.screenHeight));

    Constants.loadScreen.getChildren().add(Constants.loader);
    Constants.main_scene.setRoot(Constants.loadScreen); // set load screen
  }

  public static void clearLoadScreen() {
    Constants.loader = null;
    Constants.loadScreen = null;
  }

}
