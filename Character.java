package com.danilchican.pacman;

import javafx.geometry.*;
import javafx.scene.image.*;
import javafx.scene.layout.Pane;

public class Character extends Pane {

  private Block removeBlock = null;

  Image pacmanImg = new Image(getClass().getResourceAsStream("pacman.png"));
  ImageView imageView = new ImageView(pacmanImg);

  public Point2D playerVelocity = new Point2D(0, 0);

  public Character(Position characterPosition) {
    imageView.setViewport(new Rectangle2D(0, 0, Constants.BlockWidth, Constants.BlockHeight));
    characterPosition.setPosition(this);

    imageView.setFitWidth(Constants.BlockWidth - 5);
    imageView.setFitHeight(Constants.BlockHeight - 5);

    this.getChildren().add(this.imageView);
    Constants.gameRoot.getChildren().add(this);
  }



  public void isFoodEat() {

    Constants.food.forEach((block) -> {
      if (Constants.player.getBoundsInParent().intersects(block.getBoundsInParent())) {
        removeBlock = block;
      }
    });

    Constants.food.remove(removeBlock);
    Constants.gameRoot.getChildren().remove(removeBlock);
  }
}
