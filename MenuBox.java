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

/**
 * The class wich contains menu box of menu items
 * 
 * @author Vlad Danilchik
 *
 */
public class MenuBox extends StackPane {

  private enum MenuStatus {
    OPENED, CLOSED
  }

  private MenuStatus status = MenuStatus.OPENED;

  /**
   * MenuBox constructor
   * 
   * @param items to display the MenuBox
   * @see MenuBox#MenuBox(MenuItem...)
   */
  @SuppressWarnings("static-access")
  public MenuBox(MenuItem... items) {
    DropShadow shadow = new DropShadow(7, 5, 0, Color.BLACK);
    shadow.setSpread(0.8);
    VBox vbox = new VBox();
    vbox.setAlignment(Pos.TOP_CENTER);
    vbox.setPadding(new Insets(280, 0, 0, 350));

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

  /**
   * Show the menu box
   * 
   * @see MenuBox#show()
   */
  public void show() {
    this.status = MenuStatus.OPENED;
    setVisible(true);
    TranslateTransition trans = new TranslateTransition(Duration.seconds(0.5), this);
    trans.setToY(0);
    trans.play();
  }

  /**
   * Hide the menu box
   * 
   * @see MenuBox#hide()
   */
  public void hide() {
    this.status = MenuStatus.CLOSED;
    TranslateTransition trans = new TranslateTransition(Duration.seconds(0.5), this);
    trans.setToY(-600);
    trans.setOnFinished(event -> setVisible(false));
    trans.play();
  }

  /**
   * Check opened menu
   * 
   * @return boolean
   * @see MenuBox#isOpened()
   */
  public boolean isOpened() {
    return (this.status == MenuStatus.OPENED);
  }

  /**
   * Set menu options
   * 
   * @see MenuBox#setMenuOptions()
   * 
   */
  public static void setMenuOptions() {
    Constants.main_scene.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ESCAPE) {
        if (!(Constants.menu.isOpened()) && Constants.subMenuLevels.isOpened()) {
          Constants.subMenuLevels.hide();
          Constants.menu.show();
        } else if (Constants.menu.isOpened() && !(Constants.subMenuLevels.isOpened())) {
          Constants.menu.hide();
          Constants.subMenuLevels.show();
        }
      }
    });
  }

  /**
   * Method wich draws menu
   * 
   * @return Parent
   * @see MenuBox#drawMenu()
   */
  public static Parent drawMenu() {
    Constants.mainRoot.setPrefSize(Constants.screenWidth, Constants.screenHeight);

    Image im = new Image(MenuBox.class.getResourceAsStream("pacman.jpg"));
    ImageView img = new ImageView(im);
    img.setFitWidth(Constants.screenWidth);
    img.setFitHeight(Constants.screenHeight);

    Constants.mainRoot.getChildren().add(img);

    Constants.menu = new MenuBox(new MenuItem("NEW GAME", MenuItem.Options.NEW_GAME),
        new MenuItem("LEVELS", MenuItem.Options.LEVELS),
        new MenuItem("PLAY SAVED", MenuItem.Options.PLAY_SAVED),
        new MenuItem("BOOT", MenuItem.Options.BOOT), new MenuItem("SAVES", MenuItem.Options.SAVES),
        new MenuItem("SORTING", MenuItem.Options.SORTING),
        new MenuItem("SORTING LIST", MenuItem.Options.SORTING_LIST),
        new MenuItem("QUIT", MenuItem.Options.QUIT));

    Constants.mainRoot.getChildren().add(Constants.menu);

    return Constants.mainRoot;
  }

  /**
   * Method wich draw submenu levels
   * 
   * @see MenuBox#drawSubMenuLevels()
   * 
   */
  public static void drawSubMenuLevels() {
    Constants.subMenuLevels = new MenuBox(new MenuItem("LEVEL 1", MenuItem.Options.LEVEL_1),
        new MenuItem("LEVEL 2", MenuItem.Options.LEVEL_2),
        new MenuItem("BACK", MenuItem.Options.BACK));

    Constants.mainRoot.getChildren().add(Constants.subMenuLevels);
    Constants.subMenuLevels.hide();
  }

}
