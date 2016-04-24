package com.danilchican.pacman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

/**
 * The main class of game
 * 
 * @author Vlad Danilchik
 * @version 1.0
 */
public class Game extends Application {

  /**
   * The method return booleand of keycode press
   * 
   * @param key keycode
   * @return boolean key pressed
   * @see Game#isPressed(KeyCode)
   */
  private boolean isPressed(KeyCode key) {
    return Constants.keys.getOrDefault(key, false);
  }

  /**
   * Method update() by timer
   * 
   * @see Game#update()
   */
  private void update() {
    if (Constants.startGame) {
      Constants.save.saveMove(3);
      if (isPressed(KeyCode.RIGHT)) {
        Constants.player.setScaleX(1);
        GamePlay.moveX(3);
        Constants.save.saveMove(1);
      } else {
        Constants.save.saveMove(0);
      }
      if (isPressed(KeyCode.LEFT)) {
        Constants.player.setScaleX(-1);
        GamePlay.moveX(-3);
        Constants.save.saveMove(1);
      } else {
        Constants.save.saveMove(0);
      }
      if (isPressed(KeyCode.UP)) {
        GamePlay.moveY(-3);
        Constants.save.saveMove(1);
      } else {
        Constants.save.saveMove(0);
      }
      if (isPressed(KeyCode.DOWN)) {
        GamePlay.moveY(3);
        Constants.save.saveMove(1);
      } else {
        Constants.save.saveMove(0);
      }
      if (Constants.enemies.isEmpty())
        return;
      else {
        Constants.save.saveMove(4);
        for (Enemy enemy : Constants.enemies) {
          enemy.move();
        }
      }

      if (!(Constants.player.isAlive())) {
        MapGenerator.clearGameScene();
        Constants.save.closeOutputStream();
      }

      if ((Constants.CurrentCountBonuses == Constants.countFoodLevel)
          && Constants.player.isAlive()) {
        Constants.save.closeOutputStream();
        GamePlay.finishScreen();
        MapGenerator.clearGameScene();

        if (GamePlay.nextLevel(++Constants.currentLevel)) {
          GamePlay.startGame(Constants.currentLevel);
        } else
          Constants.currentLevel = 1;
      }
    } else if (Constants.startBoot) {

      Constants.boot.move();

      if (Constants.enemies.isEmpty())
        return;
      else {
        for (Enemy enemy : Constants.enemies) {
          enemy.move();
        }
      }

      if (!(Constants.boot.isAlive())) {
        MapGenerator.clearGameScene();
        Constants.save.closeOutputStream();
      }

      if ((Constants.CurrentCountBonuses == Constants.countFoodLevel) && Constants.boot.isAlive()) {

        Constants.save.closeOutputStream();
        GamePlay.finishScreen();
        MapGenerator.clearGameScene();

        if (GamePlay.nextLevel(Constants.currentLevel++)) {
          GamePlay.startGame(Constants.currentLevel++);
        } else
          Constants.currentLevel = 1;

      }
    } else if (Constants.startReplay) {

      int temp;
      temp = Constants.save.getIntFromFile();
      switch (temp) {
        case 3:
          GamePlay.moveReplay();
          break;
        case 4:
          for (Enemy enemy : Constants.enemies) {
            enemy.moveReplay();
          }
          break;
      }

      if (!(Constants.player.isAlive())) {
        MapGenerator.clearGameScene();
        Constants.save.closeOutputStream();
      }

      if ((Constants.CurrentCountBonuses == Constants.countFoodLevel)
          && Constants.player.isAlive()) {
        Constants.save.closeOutputStream();
        GamePlay.finishScreen();
        MapGenerator.clearGameScene();

        if (GamePlay.nextLevel(++Constants.currentLevel)) {
          GamePlay.startGame(Constants.currentLevel);
        } else
          Constants.currentLevel = 1;
      }
    }
  }

  /**
   * The submain method of the game
   * 
   * @param stage main stage
   * @see Game#start(Stage)
   */
  public void start(Stage stage) {
    try {
      Constants.main_scene = new Scene(MenuBox.drawMenu());
      MenuBox.drawSubMenuLevels();

      MenuBox.setMenuOptions();
      GamePlay.setLevelsCount();

      stage.setTitle("PacmanGame");
      stage.setMaxWidth(1000);
      stage.setMaxHeight(600);
      stage.setMinWidth(1000);
      stage.setMinHeight(600);
      stage.setScene(Constants.main_scene);
      stage.show();

      AnimationTimer timer = new AnimationTimer() {

        @Override
        public void handle(long now) {
          update();
        }
      };

      timer.start();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * The main game method
   * 
   * @param args
   * @see Game#main(String[])
   */
  public static void main(String[] args) {
    launch(args);
  }
}
