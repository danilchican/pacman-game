package com.danilchican.pacman;

import javafx.animation.*;
import javafx.scene.text.*;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class MenuItem extends StackPane {

  private Rectangle menuRect;

  public static final int RESUME_GAME = 0;
  public static final int NEW_GAME = 1;
  public static final int QUIT = 2;

  public MenuItem(String name, int type) {

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

      switch (type) {
        case QUIT:
          System.exit(0);
          break;
        case NEW_GAME:
          GamePlay.startGame();
          break;
        default:
          break;
      }
    });

  }
}

