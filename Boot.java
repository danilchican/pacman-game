package com.danilchican.pacman;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Boot class for autoplay
 * 
 * @author Vlad Danilchik
 *
 */
public class Boot extends Pane {

  private boolean isAlive = true;
  private boolean ready = true;
  private int speedX;
  private int speedY;

  Image pacmanImg = new Image(getClass().getResourceAsStream("pacman.png"));
  ImageView imgView = new ImageView(pacmanImg);

  /**
   * Boot class constructor
   * 
   * @param characterPosition set position enemy
   * @see Boot#Boot(Position)
   */
  public Boot(Position pos) {
    ready = true;
    isAlive = true;
    imgView.setFitHeight(Constants.EnemySize);
    imgView.setFitWidth(Constants.EnemySize);
    imgView.setViewport(new Rectangle2D(0, 0, Constants.BlockHeight, Constants.BlockHeight));
    imgView.setVisible(true);
    pos.setPosition(this);
    speedX = 0;
    speedY = 1;
    this.getChildren().add(imgView);
    Constants.gameRoot.getChildren().add(this);
  }

  /**
   * Moving boot by X
   * 
   * @param value set speed for emeny
   * @see Boot#moveX(int)
   */
  public void moveX(int value) {
    boolean movingRight = value > 0;
    for (int i = 0; i < Math.abs(value); i++) {
      for (Block platform : Constants.blocks) {
        if (this.getBoundsInParent().intersects(platform.getBoundsInParent())) {
          if (Block.isFood(platform)) {
            platform.eatFood(Block.BlockType.CLEARED);
            GamePlay.updateCountFood();
          }
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
   * Moving boot by Y
   * 
   * @param value set speed for emeny
   * @see Boot#moveY(int)
   */
  public void moveY(int value) {
    boolean movingDown = value > 0;
    for (int i = 0; i < Math.abs(value); i++) {
      for (Block platform : Constants.blocks) {
        if (this.getBoundsInParent().intersects(platform.getBoundsInParent())) {
          if (Block.isFood(platform)) {
            platform.eatFood(Block.BlockType.CLEARED);
            GamePlay.updateCountFood();
          }
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
   * Moving Boot
   * 
   * @see Boot#move()
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

  /**
   * Set boot speed
   * 
   * @param x set speedX
   * @param y set speedY
   * @see Boot#setSpeed(int, int)
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
   * @see Boot#checkRight(Block)
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
   * @see Boot#checkLeft(Block)
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
   * @see Boot#checkDown(Block)
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
   * @see Boot#checkUp(Block)
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

  /**
   * Check alive of boot
   * 
   * @return boolean check life of player
   * @see Boot#isAlive()
   */
  public boolean isAlive() {
    return isAlive;
  }

  /**
   * Set alive of boot
   * 
   * @param life set alive for player
   * @see Boot#setAlive(boolean)
   */
  public void setAlive(boolean life) {
    this.isAlive = life;
  }
}
