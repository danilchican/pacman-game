package com.danilchican.pacman;

public class GameInfo {
  private String name = null;

  private int moveLeft = 0, moveRight = 0, moveUp = 0, moveDown = 0;
  private int enemyMoving = 0;

  private int numberOfLevel = 0;

  GameInfo(String name, int moveLeft, int moveRight, int moveUp, int moveDown, int enemyMoving,
      int level) {
    this.name = name;

    this.numberOfLevel = level;

    this.moveLeft = moveLeft;
    this.moveRight = moveRight;
    this.moveUp = moveUp;
    this.moveDown = moveDown;

    this.enemyMoving = enemyMoving;
  }

  public String getName() {
    return this.name;
  }

  public int getLeftMoving() {
    return this.moveLeft;
  }

  public int getRightMoving() {
    return this.moveRight;
  }

  public int getUpMoving() {
    return this.moveUp;
  }

  public int getDownMoving() {
    return this.moveDown;
  }

  public int getEnemyMoving() {
    return this.enemyMoving;
  }

  public int getLevelNumber() {
    return this.numberOfLevel;
  }

  public int getSteps() {
    return moveLeft + moveRight + moveUp + moveDown;
  }

  public String printInfo() {
    String info = new String(name + " Number of level: " + numberOfLevel + " Count of hero steps: "
        + getSteps() + " Count of enemies steps: " + enemyMoving);
    return info;
  }

}

