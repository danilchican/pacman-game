package com.danilchican.pacman;

import javafx.scene.layout.Pane;

public class GamePlay extends Pane {


  private static Block removeBlock = null;

  public static void startGame() {
    MapGenerator.generateMap();
    Constants.player = new Character(new Position(1, 1));

    Constants.main_scene.setOnKeyPressed(event -> Constants.keys.put(event.getCode(), true));
    Constants.main_scene.setOnKeyReleased(event -> Constants.keys.put(event.getCode(), false));
  }

  public static void moveX(int value) {
    boolean moveRight = (value > 0) ? true : false;
    for (int i = 0; i < Math.abs(value); i++) {
      for (Block platform : Constants.blocks) {
        if (moveRight) {
          if (Constants.player.getBoundsInParent().intersects(platform.getBoundsInParent())) {
            if (checkRight(platform) == false)
              return;
          }
        } else {
          if (Constants.player.getBoundsInParent().intersects(platform.getBoundsInParent())) {
            if (checkLeft(platform) == false)
              return;
          }
        }
        Constants.player.isFoodEat();
      }
      Constants.player.setTranslateX(Constants.player.getTranslateX() + (moveRight ? 1 : -1));
    }
    if (Constants.food.isEmpty())
      System.out.println("your win!");
  }

  public static void moveY(int value) {
    for (int i = 0; i < Math.abs(value); i++) {
      boolean moveDown = (value > 0) ? true : false;

      for (Block platform : Constants.blocks) {
        if (moveDown) {
          if (Constants.player.getBoundsInParent().intersects(platform.getBoundsInParent())) {
            if (checkDown(platform) == false)
              return;
          }
        } else {
          if (Constants.player.getBoundsInParent().intersects(platform.getBoundsInParent())) {
            if (checkUp(platform) == false)
              return;
          }
        }
        Constants.player.isFoodEat();
      }
      Constants.player.setTranslateY(Constants.player.getTranslateY() + (moveDown ? 1 : -1));
    }
    if (Constants.food.isEmpty())
      System.out.println("your win!");
  }

  private static boolean checkRight(Block platform) {
    if ((Constants.player.getTranslateX() + Constants.CharacterSize == platform.getTranslateX())
        && (platform.getType() == Constants.BlockType.BRICK)) {
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

  private static boolean checkLeft(Block platform) {
    if ((Constants.player.getTranslateX() == platform.getTranslateX() + Constants.BlockHeight)
        && (platform.getType() == Constants.BlockType.BRICK)) {
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

  private static boolean checkDown(Block platform) {
    if ((Constants.player.getTranslateY() + Constants.CharacterSize == platform.getTranslateY())
        && (platform.getType() == Constants.BlockType.BRICK)) {
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

  private static boolean checkUp(Block platform) {
    if ((Constants.player.getTranslateY() == platform.getTranslateY() + Constants.BlockHeight)
        && (platform.getType() == Constants.BlockType.BRICK)) {
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

}
