package com.danilchican.pacman;

import javafx.geometry.*;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.scene.Parent;
import javafx.scene.effect.DropShadow;
import javafx.animation.TranslateTransition;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class MenuBox extends StackPane {
  public MenuBox(MenuItem... items) {

    DropShadow shadow = new DropShadow(7, 5, 0, Color.BLACK);
    shadow.setSpread(0.8);
    VBox vbox = new VBox();
    vbox.setAlignment(Pos.TOP_CENTER);
    vbox.setPadding(new Insets(320, 0, 0, 350));

    VBox box = new VBox();
    box.setAlignment(Pos.TOP_CENTER);

    for (MenuItem item : items) {
      box.setMargin(item, new Insets(1, 0, 2, 0));
      box.getChildren().add(item);
    }

    vbox.getChildren().addAll(items);

    setAlignment(Pos.TOP_CENTER);
    getChildren().addAll(vbox);
  }

  public void show() {
    setVisible(true);
    TranslateTransition trans = new TranslateTransition(Duration.seconds(0.5), this);
    trans.setToY(0);
    trans.play();
  }

  public void hide() {
    TranslateTransition trans = new TranslateTransition(Duration.seconds(0.5), this);
    trans.setToY(-600);
    trans.setOnFinished(event -> setVisible(false));
    trans.play();
  }

  public static void setMenuOptions() {

    Constants.main_scene.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ESCAPE) {
        if (Constants.menu.isVisible()) {
          Constants.menu.hide();
        } else {
          Constants.menu.show();
        }
      }
    });
  }

  public static Parent drawMenu() {
    Constants.mainRoot.setPrefSize(1000, 600);

    Image im = new Image(MenuBox.class.getResourceAsStream("pacman.jpg"));
    ImageView img = new ImageView(im);
    img.setFitWidth(1000);
    img.setFitHeight(600);

    Constants.mainRoot.getChildren().add(img);

    Constants.menu = new MenuBox(new MenuItem("RESUME GAME", MenuItem.RESUME_GAME),
        new MenuItem("NEW GAME", MenuItem.NEW_GAME), new MenuItem("QUIT", MenuItem.QUIT));

    Constants.mainRoot.getChildren().add(Constants.menu);

    return Constants.mainRoot;
  }

}
