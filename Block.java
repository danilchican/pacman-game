package com.danilchican.pacman;

import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Contains information about block
 * 
 * @author Vlad Danilchik
 *
 */
public class Block extends Pane {

  public static enum BlockType {
    BRICK, FOOD, CLEARED
  }

  private Image blockImg;
  private ImageView block;
  private BlockType type;

  /**
   * Class constructor
   * 
   * @param blockType set type of block
   * @param x set x position for block
   * @param y set y position for block
   * @see Block#Block(BlockType, int, int)
   */
  public Block(BlockType blockType, int x, int y) {

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
      case CLEARED:
        break;
      default:
        break;
    }

    if (type == BlockType.FOOD) {
      setTranslateX(x + 12);
      setTranslateY(y + 12);

      block.setFitWidth(Constants.BlockWidth / 4);
      block.setFitHeight(Constants.BlockHeight / 4);

      Constants.blocks.add(this);
    } else {
      setTranslateX(x);
      setTranslateY(y);

      block.setFitWidth(Constants.BlockWidth);
      block.setFitHeight(Constants.BlockHeight);

      Constants.blocks.add(this);
    }

    this.getChildren().add(block);

    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        Constants.gameRoot.getChildren().add(Block.this);
      }
    });
  }

  /**
   * Method return type of block
   * 
   * @return BlockType of block
   * @see Block#getType()
   */
  public BlockType getType() {
    return this.type;
  }

  /**
   * Eat food by user and set up new block type
   * 
   * @param type set type of block by eating food
   * @see Block#eatFood(BlockType)
   */
  public void eatFood(BlockType type) {
    this.type = type;
    this.block
        .setViewport(new Rectangle2D(0, 8, Constants.BlockWidth / 4, Constants.BlockHeight / 4));
  }

  /**
   * Checkout block by type of food
   * 
   * @param block
   * @return boolean of compare block types
   * @see Block#isFood(Block)
   */
  public static boolean isFood(Block block) {
    return (block.getType() == BlockType.FOOD);
  }

}
