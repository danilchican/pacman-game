package com.danilchican.pacman;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.*;
import javafx.scene.layout.Pane;

public class Enemy extends Pane {

  private boolean ready = true;
  private int speedX;
  private int speedY;

  Image pacmanImg = new Image(getClass().getResourceAsStream("pacman.png"));
  ImageView imgView = new ImageView(pacmanImg);

  /**
   * Enemy class constructor
   * 
   * @param characterPosition set position enemy
   * @see Enemy#Enemy(Position)
   */
  public Enemy(Position characterPosition) {
    ready = true;
    imgView.setFitHeight(Constants.EnemySize);
    imgView.setFitWidth(Constants.EnemySize);
    imgView.setViewport(new Rectangle2D(0, 0, Constants.BlockHeight, Constants.BlockHeight));
    imgView.setVisible(true);
    characterPosition.setPosition(this);
    speedX = 0;
    speedY = 1;
    this.getChildren().add(imgView);
    Constants.gameRoot.getChildren().add(this);
  }

  /**
   * Checkout eating player
   * 
   * @return boolean if can eat player
   * @see Enemy#isEatCharacter()
   */
  private boolean isEatCharacter() {
    if (Constants.startBoot) {
      if (this.getBoundsInParent().intersects(Constants.boot.getBoundsInParent())) {
        GamePlay.loseScreen();
        return true;
      }
    } else if (Constants.startGame) {
      if (this.getBoundsInParent().intersects(Constants.player.getBoundsInParent())) {
        GamePlay.loseScreen();
        return true;
      }
    }

    return false;
  }

  /**
   * Moving enemy by X
   * 
   * @param value set speed for emeny
   * @see Enemy#moveX(int)
   */
  public void moveX(int value) {
    boolean movingRight = value > 0;
    for (int i = 0; i < Math.abs(value); i++) {
      for (Block platform : Constants.blocks) {
        if (this.isEatCharacter())
          return;
        if (this.getBoundsInParent().intersects(platform.getBoundsInParent())) {
          if (movingRight) {
            if (checkRight(platform) == false)
              return;
          } else {
            if (checkLeft(platform) == false)
              return;
          }
        }
      }
      this.setTranslateX(this.getTranslateX() + (movingRight ? 1 : -1));
      if ((this.getTranslateX() - Constants.MARGIN_LEFT_RIGHT - 1) % Constants.BlockHeight == 0
          && (this.getTranslateY() - Constants.MARGIN_TOP - 1) % Constants.BlockHeight == 0) {
        double rand = Math.random();
        if (rand >= 0 && rand <= 0.33) {
          if (Constants.blocks.get(
              (int) ((((this.getTranslateY() - 1 - Constants.BlockHeight - Constants.MARGIN_TOP)
                  / Constants.BlockHeight) * 27)
                  + ((this.getTranslateX() - 1 - Constants.MARGIN_LEFT_RIGHT)
                      / Constants.BlockHeight)))
              .getType() != Block.BlockType.BRICK) {
            speedY = -Math.abs(speedX);
            speedX = 0;
            return;
          }
        }
        if (rand > 0.33 && rand <= 0.66) {
          if (Constants.blocks.get(
              (int) ((((this.getTranslateY() - 1 + Constants.BlockHeight - Constants.MARGIN_TOP)
                  / Constants.BlockHeight) * 27)
                  + ((this.getTranslateX() - 1 - Constants.MARGIN_LEFT_RIGHT)
                      / Constants.BlockHeight)))
              .getType() != Block.BlockType.BRICK) {
            speedY = Math.abs(speedX);
            speedX = 0;
            return;
          }
        }
      }
    }
  }

  /**
   * Moving enemy by Y
   * 
   * @param value set speed for emeny
   * @see Enemy#moveY(int)
   */
  public void moveY(int value) {
    boolean movingDown = value > 0;
    for (int i = 0; i < Math.abs(value); i++) {
      for (Block platform : Constants.blocks) {
        if (this.isEatCharacter())
          return;
        if (this.getBoundsInParent().intersects(platform.getBoundsInParent())) {
          if (movingDown) {
            if (checkDown(platform) == false)
              return;
          } else {
            if (checkUp(platform) == false)
              return;
          }
        }
      }
      this.setTranslateY(this.getTranslateY() + (movingDown ? 1 : -1));
      if ((this.getTranslateX() - Constants.MARGIN_LEFT_RIGHT - 1) % Constants.BlockHeight == 0
          && (this.getTranslateY() - Constants.MARGIN_TOP - 1) % Constants.BlockHeight == 0) {
        double rand = Math.random();
        if (rand >= 0 && rand <= 0.33) {
          if (Constants.blocks
              .get((int) ((((this.getTranslateY() - 1 - Constants.MARGIN_TOP)
                  / Constants.BlockHeight) * 27)
                  + ((this.getTranslateX() - 1 - Constants.BlockHeight
                      - Constants.MARGIN_LEFT_RIGHT) / Constants.BlockHeight)))
              .getType() != Block.BlockType.BRICK) {
            speedX = -Math.abs(speedY);
            speedY = 0;
            return;
          }
        }
        if (rand > 0.33 && rand <= 0.66) {
          if (Constants.blocks
              .get((int) ((((this.getTranslateY() - 1 - Constants.MARGIN_TOP)
                  / Constants.BlockHeight) * 27)
                  + ((this.getTranslateX() - 1 + Constants.BlockHeight
                      - Constants.MARGIN_LEFT_RIGHT) / Constants.BlockHeight)))
              .getType() != Block.BlockType.BRICK) {
            speedX = Math.abs(speedY);
            speedY = 0;
            return;
          }
        }
      }
    }
  }

  /**
   * Moving Enemy
   * 
   * @see Enemy#move()
   */
  public void move() {
    if (ready == true) {
      ready = false;
      Constants.save.saveMoves(speedX, speedY);
      if (speedX != 0)
        moveX(speedX);
      if (speedY != 0)
        moveY(speedY);
      ready = true;
    }
  }

  public void moveReplay() {
    if (ready == true) {
      ready = false;
      if (Constants.save.getIntFromFile() == 1) {
        moveX(2);
        Constants.player.setScaleX(1);
      }
      if (Constants.save.getIntFromFile() == 1) {
        moveX(-2);
        Constants.player.setScaleX(-1);
      }
      if (Constants.save.getIntFromFile() == 1) {
        moveY(2);
      }
      if (Constants.save.getIntFromFile() == 1) {
        moveY(-2);
      }
      ready = true;
    }
  }

  /**
   * Set enemy speed
   * 
   * @param x set speedX
   * @param y set speedY
   * @see Enemy#setSpeed(int, int)
   */
  public void setSpeed(int x, int y) {
    speedX = x;
    speedY = y;
  }

  /**
   * Check right way
   * 
   * @param platform Block to checkRight
   * @return boolean value if check
   * @see Enemy#checkRight(Block)
   */
  private boolean checkRight(Block platform) {
    if ((this.getTranslateX() + Constants.EnemySize == platform.getTranslateX())
        && platform.getType() == Block.BlockType.BRICK) {
      this.setTranslateX(this.getTranslateX() - 1);
      speedX = -speedX;
      return false;
    }
    return true;
  }

  /**
   * Check left way
   * 
   * @param platform Block to checkLeft
   * @return boolean value if check
   * @see Enemy#checkLeft(Block)
   */
  private boolean checkLeft(Block platform) {
    if ((this.getTranslateX() == platform.getTranslateX() + Constants.BlockHeight)
        && platform.getType() == Block.BlockType.BRICK) {
      this.setTranslateX(this.getTranslateX() + 1);
      speedX = -speedX;
      return false;
    }
    return true;
  }

  /**
   * Check down way
   * 
   * @param platform Block to checkDown
   * @return boolean value if check
   * @see Enemy#checkDown(Block)
   */
  private boolean checkDown(Block platform) {
    if ((this.getTranslateY() + Constants.EnemySize == platform.getTranslateY())
        && platform.getType() == Block.BlockType.BRICK) {
      this.setTranslateY(this.getTranslateY() - 1);
      speedY = -speedY;
      return false;
    }
    return true;
  }

  /**
   * Check up way
   * 
   * @param platform Block to checkUp
   * @return boolean value if check
   * @see Enemy#checkUp(Block)
   */
  private boolean checkUp(Block platform) {
    if ((this.getTranslateY() == platform.getTranslateY() + Constants.BlockHeight)
        && platform.getType() == Block.BlockType.BRICK) {
      this.setTranslateY(this.getTranslateY() + 1);
      speedY = -speedY;
      return false;
    }
    return true;
  }
}
