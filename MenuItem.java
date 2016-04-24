package com.danilchican.pacman;

import javafx.animation.*;
import javafx.scene.text.*;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * Contains info about item menu
 * 
 * @author Vlad Danilchik
 *
 */
public class MenuItem extends StackPane {

  private Rectangle menuRect;

  public static enum Options {
    NEW_GAME, LEVELS, BOOT, BACK, QUIT, LEVEL_1, LEVEL_2, SAVES, PLAY_SAVED
  }

  /**
   * MenuItem constructor
   * 
   * @param name for menu item
   * @param type of menu
   * @see MenuItem#MenuItem(String, Options)
   */
  public MenuItem(String name, Options type) {
    menuRect = new Rectangle(300, 30);

    menuRect.setFill(Color.WHITE);
    menuRect.setOpacity(0.1);

    Text text = new Text(name);
    text.setFill(Color.ALICEBLUE);
    text.setFont(Font.font(20));

    setAlignment(Pos.TOP_CENTER);
    getChildren().addAll(menuRect, text);

    setOnMouseEntered(event -> {

      ScaleTransition st = new ScaleTransition(Duration.millis(400), menuRect);
      st.setByX(0.1f);
      st.setByY(0.1f);

      FadeTransition ft = new FadeTransition(Duration.millis(400), menuRect);
      ft.setFromValue(0.1);
      ft.setToValue(0.3);
      ft.setAutoReverse(true);

      ft.play();
      st.play();
    });

    setOnMouseExited(event -> {
      ScaleTransition st = new ScaleTransition(Duration.millis(400), menuRect);
      st.setToX(1f);
      st.setToY(1f);

      FadeTransition ft = new FadeTransition(Duration.millis(400), menuRect);
      ft.setToValue(0.1);
      ft.setAutoReverse(true);

      ft.play();
      st.play();
    });

    setOnMousePressed(event -> {
      text.setFill(Color.AZURE);
    });

    setOnMouseReleased(event -> {
      text.setFill(Color.WHITE);
      setActionMenu(type);
    });

  }

  /**
   * Set action on menu item
   * 
   * @param type for setting action menu
   * @see MenuItem#setActionMenu(Options)
   */
  private void setActionMenu(Options type) {
    switch (type) {
      case QUIT:
        System.exit(0);
        break;
      case NEW_GAME:
        GamePlay.startGame(1);
        Constants.save.openBufferForWrite();
        Constants.save.addLevelNumber(1);
        Constants.save.saveMove(3);
        break;
      case BOOT:
        GamePlay.startBoot(Constants.currentLevel);
        Constants.save.openBufferForWrite();
        Constants.save.addLevelNumber(1);
        Constants.save.saveMove(2);
        break;
      case LEVELS:
        Constants.menu.hide();
        Constants.subMenuLevels.show();
        break;
      case LEVEL_1:
        GamePlay.startGame(1);
        Constants.save.openBufferForWrite();
        Constants.save.addLevelNumber(1);
        Constants.save.saveMove(3);
        break;
      case LEVEL_2:
        GamePlay.startGame(2);
        Constants.save.openBufferForWrite();
        Constants.save.addLevelNumber(2);
        Constants.save.saveMove(3);
        break;
      case PLAY_SAVED:
        Constants.save.openBufferForRead();
        GamePlay.startGame(Constants.save.getIntFromFile(), Constants.save.getIntFromFile());
        break;
      case SAVES:
        Constants.save.showSaves();
        break;
      case BACK:
        Constants.subMenuLevels.hide();
        Constants.menu.show();
      default:
        break;
    }
  }

}

