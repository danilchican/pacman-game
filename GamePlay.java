package com.danilchican.pacman;

import Statistics.ScalaLog;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * The class for user gameplay
 * 
 * @author Vladislav Danilchik
 */
public class GamePlay extends Pane {

  private static Label lblScore = null;
  private static Enemy localEnemy = null;

  public void createThread(int index) {
    Constants.GameThread = new Thread(new MyThread(index));
    Constants.GameThread.start();
  }

  public static void freeThead() {
    Constants.GameThread = null;
  }

  private class MyThread implements Runnable {

    private int lvlIndex = 1;

    MyThread(int index) {
      lvlIndex = index;
    }

    public void run() {
      ScalaLog log = new ScalaLog();
      System.out.println(log.makePseudo(1));
      RepresenterScreen.setLoadScreen();
      startGame(lvlIndex);
    }
  }

  /**
   * Start the game by boot
   * 
   * @param level generate map by level value
   * @see GamePlay#startGame(int)
   */
  public static void startGame(int level) {
    RepresenterScreen.clearLoadScreen();
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    Constants.currentLevel = level;
    setFoodCount(level);
    MapGenerator.generateMap(level);

    lblScore = new Label("Your score: " + Constants.CurrentCountBonuses);
    lblScore.setFont(Font.font(22));
    lblScore.setTextFill(Color.WHITE);
    lblScore.setTranslateX(700);
    lblScore.setTranslateY(35);

    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        Constants.gameRoot.getChildren().add(lblScore);
      }
    });

    Constants.player = new Character(new Position(1, 1));

    for (int i = 0; i < 3; i++) {
      localEnemy = new Enemy(new Position(13, 2));
      Constants.enemies.add(localEnemy);
    }

    for (Enemy enemy : Constants.enemies) {
      enemy.setSpeed(0, 2);
    }

    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        Constants.main_scene.setRoot(Constants.gameRoot); // set game screen
      }
    });

    localEnemy = null;

    Constants.main_scene.setOnKeyPressed(event -> Constants.keys.put(event.getCode(), true));
    Constants.main_scene.setOnKeyReleased(event -> {
      if (event.getCode() == KeyCode.ESCAPE) {
        if (Constants.loseGame) {
          Constants.loseGame = false;
          Constants.startGame = true;
          return;
        } else {
          Constants.startGame = false;
          Constants.loseGame = true;
          GamePlay.freeThead();
          return;
        }
      }
      Constants.keys.put(event.getCode(), false);
    });

    Constants.startGame = true;
  }


  /**
   * Start the game by boot
   * 
   * @param level generate map by level value
   * @see GamePlay#startGame(int)
   */
  public static void startGame(int level, int countOfEnemies) {
    Constants.currentLevel = level;
    setFoodCount(level);
    MapGenerator.generateMap(level);

    lblScore = new Label("Your score: " + Constants.CurrentCountBonuses);
    lblScore.setFont(Font.font(22));
    lblScore.setTextFill(Color.WHITE);
    lblScore.setTranslateX(700);
    lblScore.setTranslateY(35);
    Constants.gameRoot.getChildren().add(lblScore);

    Constants.player = new Character(new Position(1, 1));

    for (int i = 0; i < countOfEnemies; i++) {
      localEnemy = new Enemy(new Position(13, 2));
      Constants.enemies.add(localEnemy);
    }

    for (Enemy enemy : Constants.enemies) {
      enemy.setSpeed(0, 2);
    }
    Constants.main_scene.setRoot(Constants.gameRoot);

    localEnemy = null;

    Constants.main_scene.setOnKeyPressed(event -> Constants.keys.put(event.getCode(), true));
    Constants.main_scene.setOnKeyReleased(event -> Constants.keys.put(event.getCode(), false));

    Constants.startReplay = true;
  }

  /**
   * Start the game by boot
   * 
   * @param level
   * @see GamePlay#startBoot(int)
   */
  public static void startBoot(int level) {
    setFoodCount(level);
    MapGenerator.generateMap(level);

    lblScore = new Label("Your score: " + Constants.CurrentCountBonuses);
    lblScore.setFont(Font.font(22));
    lblScore.setTextFill(Color.WHITE);
    lblScore.setTranslateX(700);
    lblScore.setTranslateY(35);
    Constants.gameRoot.getChildren().add(lblScore);

    for (int i = 0; i < 2; i++) {
      localEnemy = new Enemy(new Position(13, 2));
      Constants.enemies.add(localEnemy);
    }

    for (Enemy enemy : Constants.enemies) {
      enemy.setSpeed(0, 2);
    }

    Constants.boot = new Boot(new Position(1, 1));
    Constants.boot.setSpeed(0, 3);

    localEnemy = null;

    Constants.main_scene.setOnKeyPressed(event -> Constants.keys.put(event.getCode(), true));
    Constants.main_scene.setOnKeyReleased(event -> Constants.keys.put(event.getCode(), false));

    Constants.startBoot = true;

    Constants.main_scene.setRoot(Constants.gameRoot);
  }

  /**
   * Moving player by X
   * 
   * @param value for speed player
   * @see GamePlay#moveX(int)
   */
  public static void moveX(int value) {
    boolean moveRight = (value > 0) ? true : false;

    System.out.println(Constants.log.makePseudo((moveRight) ? 3 : 4));
    
    for (int i = 0; i < Math.abs(value); i++) {
      for (Block platform : Constants.blocks) {
        if (moveRight) {
          if (Constants.player.getBoundsInParent().intersects(platform.getBoundsInParent())) {
            if (Block.isFood(platform)) {
              platform.eatFood(Block.BlockType.CLEARED);

              System.out.println(Constants.log.makePseudo(4));
              updateCountFood();
            }
            if (checkRight(platform) == false) {
              return;
            }
          }
        } else {
          if (Constants.player.getBoundsInParent().intersects(platform.getBoundsInParent())) {
            if (Block.isFood(platform)) {
              platform.eatFood(Block.BlockType.CLEARED);

              System.out.println(Constants.log.makePseudo(4));
              updateCountFood();
            }
            if (checkLeft(platform) == false) {
              return;
            }
          }
        }
      }
      Constants.player.setTranslateX(Constants.player.getTranslateX() + (moveRight ? 1 : -1));
    }
  }

  /**
   * Moving player by Y
   * 
   * @param value speed of player
   * @see GamePlay#moveY(int)
   */
  public static void moveY(int value) {
    for (int i = 0; i < Math.abs(value); i++) {
      boolean moveDown = (value > 0) ? true : false;

      for (Block platform : Constants.blocks) {
        if (moveDown) {
          if (Constants.player.getBoundsInParent().intersects(platform.getBoundsInParent())) {
            if (Block.isFood(platform)) {

              System.out.println(Constants.log.makePseudo(4));
              platform.eatFood(Block.BlockType.CLEARED);
              updateCountFood();
            }
            if (!checkDown(platform)) {
              return;
            }
          }
        } else {
          if (Constants.player.getBoundsInParent().intersects(platform.getBoundsInParent())) {
            if (Block.isFood(platform)) {

              System.out.println(Constants.log.makePseudo(4));
              platform.eatFood(Block.BlockType.CLEARED);
              updateCountFood();
            }
            if (!checkUp(platform)) {
              return;
            }
          }
        }
      }
      Constants.player.setTranslateY(Constants.player.getTranslateY() + (moveDown ? 1 : -1));
    }
  }

  /**
   * Method check right way
   * 
   * @param platform Block to checkRight
   * @return boolean value if check
   * @see GamePlay#checkRight(Block)
   */
  private static boolean checkRight(Block platform) {
    if ((Constants.player.getTranslateX() + Constants.CharacterSize == platform.getTranslateX())
        && (platform.getType() == Block.BlockType.BRICK)) {
      Constants.player.setTranslateX(Constants.player.getTranslateX() - 1);
      if (Constants.player.getTranslateY() + Constants.CharacterSize - 5 <= platform
          .getTranslateY()) {
        Constants.player.setTranslateY(Constants.player.getTranslateY() - 1);
      }
      if (Constants.player.getTranslateY() >= platform.getTranslateY() + Constants.CharacterSize) {
        Constants.player.setTranslateY(Constants.player.getTranslateY() + 1);
      }
      return false;
    }
    return true;
  }

  /**
   * Method check left way
   * 
   * @param platform Block to checkLeft
   * @return boolean value if check
   * @see GamePlay#checkLeft(Block)
   */
  private static boolean checkLeft(Block platform) {
    if ((Constants.player.getTranslateX() == platform.getTranslateX() + Constants.BlockHeight)
        && (platform.getType() == Block.BlockType.BRICK)) {
      Constants.player.setTranslateX(Constants.player.getTranslateX() + 1);
      if (Constants.player.getTranslateY() + Constants.CharacterSize - 5 <= platform
          .getTranslateY()) {
        Constants.player.setTranslateY(Constants.player.getTranslateY() - 1);
      }
      if (Constants.player.getTranslateY() >= platform.getTranslateY() + Constants.CharacterSize) {
        Constants.player.setTranslateY(Constants.player.getTranslateY() + 1);
      }
      return false;
    }
    return true;
  }

  /**
   * Method check down way
   * 
   * @param platform Block to checkDown
   * @return boolean value if check
   * @see GamePlay#checkDown(Block)
   */
  private static boolean checkDown(Block platform) {
    if ((Constants.player.getTranslateY() + Constants.CharacterSize == platform.getTranslateY())
        && (platform.getType() == Block.BlockType.BRICK)) {
      Constants.player.setTranslateY(Constants.player.getTranslateY() - 1);
      if (Constants.player.getTranslateX() >= platform.getTranslateX() + Constants.BlockHeight
          - 5) {
        Constants.player.setTranslateX(Constants.player.getTranslateX() + 1);
      }
      if (Constants.player.getTranslateX() + Constants.CharacterSize - 5 <= platform
          .getTranslateX()) {
        Constants.player.setTranslateX(Constants.player.getTranslateX() - 1);
      }
      return false;
    }
    return true;
  }

  /**
   * Method check up way
   * 
   * @param platform Block to checkUp
   * @return boolean value if check
   * @see GamePlay#checkUp(Block)
   */
  private static boolean checkUp(Block platform) {
    if ((Constants.player.getTranslateY() == platform.getTranslateY() + Constants.BlockHeight)
        && (platform.getType() == Block.BlockType.BRICK)) {
      Constants.player.setTranslateY(Constants.player.getTranslateY() + 1);
      if (Constants.player.getTranslateX() >= platform.getTranslateX() + Constants.BlockHeight
          - 5) {
        Constants.player.setTranslateX(Constants.player.getTranslateX() + 1);
      }
      if (Constants.player.getTranslateX() + Constants.CharacterSize - 5 <= platform
          .getTranslateX()) {
        Constants.player.setTranslateX(Constants.player.getTranslateX() - 1);
      }
      return false;
    }
    return true;
  }

  /**
   * Set count food in current level
   * 
   * @param levelNumber consider countFoodLevel
   * @see GamePlay#setFoodCount(int)
   */
  private static void setFoodCount(int levelNumber) {
    Constants.countFoodLevel = 0;
    Constants.CurrentCountBonuses = 0;
    for (int i = 0; i < LevelData.levels[levelNumber - 1].length; i++) {
      String line = LevelData.levels[levelNumber - 1][i];
      for (int j = 0; j < line.length(); j++) {
        switch (line.charAt(j)) {
          case '1':
            Constants.countFoodLevel++;
        }
      }
    }
  }

  /**
   * Set max levels count
   * 
   * @see GamePlay#setLevelsCount()
   */
  public static void setLevelsCount() {
    Constants.levelsCount = LevelData.levels.length;
  }

  /**
   * Method update count food in level
   * 
   * @see GamePlay#updateCountFood()
   */
  public static void updateCountFood() {
    Constants.CurrentCountBonuses++;

    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        lblScore.setText("Your score: " + Constants.CurrentCountBonuses);
      }
    });
  }

  /**
   * Checkout next level
   * 
   * @param level check level for availability
   * @return boolean value if check
   * @see GamePlay#nextLevel(int)
   */
  public static boolean nextLevel(int level) {
    return (level <= Constants.levelsCount);
  }

  /**
   * 
   * Replay game
   * 
   */
  public static void moveReplay() {
    int x = Constants.save.getIntFromFile() + Constants.save.getIntFromFile() * 10
        + Constants.save.getIntFromFile() * 100;
    int y = Constants.save.getIntFromFile() + Constants.save.getIntFromFile() * 10
        + Constants.save.getIntFromFile() * 100;
    Constants.player.setTranslateX(x);
    Constants.player.setTranslateY(y);

    for (Block platform : Constants.blocks) {
      if (Constants.player.getBoundsInParent().intersects(platform.getBoundsInParent())) {
        if (Block.isFood(platform)) {
          platform.eatFood(Block.BlockType.CLEARED);
          updateCountFood();
          return;
        }
      }
    }
  }
}
