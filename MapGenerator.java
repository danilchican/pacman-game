package com.danilchican.pacman;

import com.danilchican.pacman.Constants.BlockType;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MapGenerator {


  public static void generateMap() {

    // set background
    Constants.gameRoot.setPrefSize(Constants.screenWidth, Constants.screenHeight);
    Rectangle rect = new Rectangle(Constants.screenWidth, Constants.screenHeight);
    rect.setFill(Color.BLACK);

    // add rectangle to gameRoot
    Constants.gameRoot.getChildren().addAll(rect);
    Constants.main_scene.setRoot(Constants.gameRoot);

    // generate map blocks
    for (int i = 0; i < LevelData.levels[0].length; i++) {

      String line = LevelData.levels[0][i];

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
}
