package com.danilchican.pacman;

import javafx.geometry.*;
import javafx.scene.image.*;
import javafx.scene.layout.Pane;

/**
 * Main class for player which contains all info about it
 * 
 * @author Vlad Danilchik
 *
 */
public class Character extends Pane {

  private boolean isAlive;

  Image pacmanImg = new Image(getClass().getResourceAsStream("pacman.png"));
  ImageView imageView = new ImageView(pacmanImg);

  public Point2D playerVelocity = new Point2D(0, 0);

  /**
   * Character constructor
   * 
   * @param characterPosition set player position
   * @see Character#Character(Position)
   */
  public Character(Position characterPosition) {
    imageView.setViewport(new Rectangle2D(0, 0, Constants.BlockWidth, Constants.BlockHeight));
    characterPosition.setPosition(this);

    imageView.setFitWidth(Constants.BlockWidth - 5);
    imageView.setFitHeight(Constants.BlockHeight - 5);

    this.isAlive = true;

    this.getChildren().add(this.imageView);
    Constants.gameRoot.getChildren().add(this);
  }

  /**
   * Check alive of player
   * 
   * @return boolean check alive of player
   * @see Character#isAlive()
   */
  public boolean isAlive() {
    return isAlive;
  }

  /**
   * Set alive of player
   * 
   * @param life set alive for player
   * @see Character#setAlive(boolean)
   */
  public void setAlive(boolean life) {
    this.isAlive = life;
  }
}
