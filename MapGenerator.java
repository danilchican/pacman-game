package com.danilchican.pacman;

import com.danilchican.pacman.Block.BlockType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * The class wich generate masks for map
 * 
 * @author Vlad Danilchik
 *
 */
public class MapGenerator {

  /**
   * Generate map
   * 
   * @param level number of level
   * @see MapGenerator#generateMap(int)
   */
  public static void generateMap(int level) {
    // set background
    Constants.gameRoot.setPrefSize(Constants.screenWidth, Constants.screenHeight);
    Rectangle rect = new Rectangle(Constants.screenWidth, Constants.screenHeight);
    rect.setFill(Color.BLACK);

    // add rectangle to gameRoot
    Constants.gameRoot.getChildren().addAll(rect);

    // generate map blocks
    for (int i = 0; i < LevelData.levels[level - 1].length; i++) {
      String line = LevelData.levels[level - 1][i];
      for (int j = 0; j < line.length(); j++) {
        switch (line.charAt(j)) {
          case '0':
            new Block(BlockType.BRICK, Constants.MARGIN_LEFT_RIGHT + j * Constants.BlockWidth,
                Constants.MARGIN_TOP + i * Constants.BlockHeight);
            break;
          case '1':
            new Block(BlockType.FOOD, Constants.MARGIN_LEFT_RIGHT + j * Constants.BlockWidth,
                Constants.MARGIN_TOP + i * Constants.BlockHeight);
            break;
        }
      }
    }
  }

  /**
   * Clear game screen after win or lose
   * 
   * @see MapGenerator#clearGameScene()
   */
  public static void clearGameScene() {
    Constants.main_scene.setRoot(Constants.mainRoot);
    Constants.enemies.clear();
    Constants.blocks.clear();
    Constants.countFoodLevel = 0;
    Constants.CurrentCountBonuses = 0;
    Constants.keys.clear();
  }
}
