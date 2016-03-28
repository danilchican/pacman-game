package com.danilchican.pacman;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Block extends Pane {

  private Image blockImg;
  private ImageView block;
  private Constants.BlockType type;

  public Block(Constants.BlockType blockType, int x, int y) {
    this.type = blockType;

    switch (type) {
      case BRICK:
        blockImg = new Image(getClass().getResourceAsStream("brick.jpg"));
        block = new ImageView(blockImg);
        block.setViewport(new Rectangle2D(0, 0, Constants.BlockWidth, Constants.BlockHeight));
        break;
      case FOOD:
        blockImg = new Image(getClass().getResourceAsStream("food.png"));
        block = new ImageView(blockImg);
        block.setViewport(
            new Rectangle2D(0, 0, Constants.BlockWidth / 4, Constants.BlockHeight / 4));
        break;
    }

    if (type == Constants.BlockType.FOOD) {
      setTranslateX(x + 12);
      setTranslateY(y + 12);

      block.setFitWidth(Constants.BlockWidth / 4);
      block.setFitHeight(Constants.BlockHeight / 4);
      
      Constants.food.add(this);
    } else {
      setTranslateX(x);
      setTranslateY(y);

      block.setFitWidth(Constants.BlockWidth);
      block.setFitHeight(Constants.BlockHeight);
      
      Constants.blocks.add(this);
    }

    this.getChildren().add(block);
    Constants.gameRoot.getChildren().add(this);
  }
  
  public Constants.BlockType getType() {
    return this.type;
  }

}
