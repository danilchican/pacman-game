package com.danilchican.pacman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;


public class Game extends Application {

  private boolean isPressed(KeyCode key) {
    return Constants.keys.getOrDefault(key, false);
  }

  private void update() {

    if (isPressed(KeyCode.RIGHT)) {
      Constants.player.setScaleX(1);
      GamePlay.moveX(3);
    }
    if (isPressed(KeyCode.LEFT)) {
      Constants.player.setScaleX(-1);
      GamePlay.moveX(-3);
    }
    if (isPressed(KeyCode.UP)) {
      GamePlay.moveY(-3);
    }
    if (isPressed(KeyCode.DOWN)) {
      GamePlay.moveY(3);
    }
  }


  public void start(Stage stage) {
    try {
      Constants.main_scene = new Scene(MenuBox.drawMenu());
      MenuBox.setMenuOptions();

      stage.setTitle("PacmanGame");
      stage.setMaxWidth(1000);
      stage.setMaxHeight(600);
      stage.setMinWidth(1000);
      stage.setMinHeight(600);
      stage.setScene(Constants.main_scene);
      stage.show();

      AnimationTimer timer = new AnimationTimer() {

        @Override
        public void handle(long now) {
          update();
        }
      };

      timer.start();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    launch(args);
  }

}
