package com.danilchican.pacman;

import javafx.scene.control.Label;
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

  /**
   * Start the game by boot
   * 
   * @param level generate map by level value
   * @see GamePlay#startGame(int)
   */
  public static void startGame(int level) {
    Constants.currentLevel = level;
    setFoodCount(level);
    MapGenerator.generateMap(level);

    lblScore = new Label("¬аш счЄт: " + Constants.CurrentCountBonuses);
    lblScore.setFont(Font.font(22));
    lblScore.setTextFill(Color.WHITE);
    lblScore.setTranslateX(700);
    lblScore.setTranslateY(35);
    Constants.gameRoot.getChildren().add(lblScore);

    Constants.player = new Character(new Position(1, 1));

    for (int i = 0; i < 3; i++) {
      localEnemy = new Enemy(new Position(13, 2));
      Constants.enemies.add(localEnemy);
    }

    for (Enemy enemy : Constants.enemies) {
      enemy.setSpeed(0, 2);
    }

    localEnemy = null;

    Constants.main_scene.setOnKeyPressed(event -> Constants.keys.put(event.getCode(), true));
    Constants.main_scene.setOnKeyReleased(event -> Constants.keys.put(event.getCode(), false));

    Constants.startGame = true;
  }

  public static void startGame(int level, int countOfEnemies) {
    System.out.println(level);
    Constants.currentLevel = level;
    setFoodCount(level);
    MapGenerator.generateMap(level);

    lblScore = new Label("¬аш счЄт: " + Constants.CurrentCountBonuses);
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

    lblScore = new Label("ђаш сч™т: " + Constants.CurrentCountBonuses);
    lblScore.setFont(Font.font(22));
    lblScore.setTextFill(Color.WHITE);
    lblScore.setTranslateX(700);
    lblScore.setTranslateY(35);
    Constants.gameRoot.getChildren().add(lblScore);

    Constants.boot = new Boot(new Position(1, 1));
    Constants.boot.setSpeed(0, 2);

    for (int i = 0; i < 2; i++) {
      localEnemy = new Enemy(new Position(13, 2));
      Constants.enemies.add(localEnemy);
    }

    for (Enemy enemy : Constants.enemies) {
      enemy.setSpeed(0, 2);
    }

    localEnemy = null;

    Constants.main_scene.setOnKeyPressed(event -> Constants.keys.put(event.getCode(), true));
    Constants.main_scene.setOnKeyReleased(event -> Constants.keys.put(event.getCode(), false));

    Constants.startBoot = true;
  }

  /**
   * Moving player by X
   * 
   * @param value for speed player
   * @see GamePlay#moveX(int)
   */
  public static void moveX(int value) {
    boolean moveRight = (value > 0) ? true : false;
    for (int i = 0; i < Math.abs(value); i++) {
      for (Block platform : Constants.blocks) {
        if (moveRight) {
          if (Constants.player.getBoundsInParent().intersects(platform.getBoundsInParent())) {
            if (Block.isFood(platform)) {
              platform.eatFood(Block.BlockType.CLEARED);
              updateCountFood();
            }
            if (checkRight(platform) == false)
              return;
          }
        } else {
          if (Constants.player.getBoundsInParent().intersects(platform.getBoundsInParent())) {
            if (Block.isFood(platform)) {
              platform.eatFood(Block.BlockType.CLEARED);
              updateCountFood();
            }
            if (checkLeft(platform) == false)
              return;
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
              platform.eatFood(Block.BlockType.CLEARED);
              updateCountFood();
            }
            if (checkDown(platform) == false)
              return;
          }
        } else {
          if (Constants.player.getBoundsInParent().intersects(platform.getBoundsInParent())) {
            if (Block.isFood(platform)) {
              platform.eatFood(Block.BlockType.CLEARED);
              updateCountFood();
            }
            if (checkUp(platform) == false)
              return;
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
          .getTranslateY())
        Constants.player.setTranslateY(Constants.player.getTranslateY() - 1);
      if (Constants.player.getTranslateY() >= platform.getTranslateY() + Constants.CharacterSize)
        Constants.player.setTranslateY(Constants.player.getTranslateY() + 1);
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
          .getTranslateY())
        Constants.player.setTranslateY(Constants.player.getTranslateY() - 1);
      if (Constants.player.getTranslateY() >= platform.getTranslateY() + Constants.CharacterSize)
        Constants.player.setTranslateY(Constants.player.getTranslateY() + 1);
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
      if (Constants.player.getTranslateX() >= platform.getTranslateX() + Constants.BlockHeight - 5)
        Constants.player.setTranslateX(Constants.player.getTranslateX() + 1);
      if (Constants.player.getTranslateX() + Constants.CharacterSize - 5 <= platform
          .getTranslateX())
        Constants.player.setTranslateX(Constants.player.getTranslateX() - 1);
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
      if (Constants.player.getTranslateX() >= platform.getTranslateX() + Constants.BlockHeight - 5)
        Constants.player.setTranslateX(Constants.player.getTranslateX() + 1);
      if (Constants.player.getTranslateX() + Constants.CharacterSize - 5 <= platform
          .getTranslateX())
        Constants.player.setTranslateX(Constants.player.getTranslateX() - 1);
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
    lblScore.setText("¬аш счЄт: " + Constants.CurrentCountBonuses);
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
   * Display finish screen
   * 
   * @see GamePlay#finishScreen()
   */
  public static void finishScreen() {
    System.out.println("You win!");
  }

  /**
   * Display lose screen
   * 
   * @see GamePlay#loseScreen()
   */
  public static void loseScreen() {
    System.out.println("Game over");

    if (Constants.startGame || Constants.startReplay)
      Constants.player.setAlive(false);
    else if (Constants.startBoot)
      Constants.boot.setAlive(false);

    Constants.startGame = false;
    Constants.startBoot = false;
    Constants.startReplay = false;

    Constants.currentLevel = 1;
  }

  public static void moveReplay() {
    if (Constants.save.getIntFromFile() == 1) {
      moveX(Constants.speedOfHero);
      Constants.player.setScaleX(1);
    }
    if (Constants.save.getIntFromFile() == 1) {
      moveX(-Constants.speedOfHero);
      Constants.player.setScaleX(-1);
    }
    if (Constants.save.getIntFromFile() == 1) {
      moveY(-Constants.speedOfHero);
    }
    if (Constants.save.getIntFromFile() == 1) {
      moveY(Constants.speedOfHero);
    }
  }

}
