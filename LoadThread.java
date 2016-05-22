package com.danilchican.pacman;

import Statistics.ScalaSorting;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LoadThread implements Runnable {
  private Image blockImg;
  private boolean isMainScreen = true;

  public void setPosition(int x, int y) {
    Constants.loader.setTranslateX(x);
    Constants.loader.setTranslateY(y);
  }

  LoadThread(boolean mainScreen, String name, int width, int height, int fitHeight, int fitWidth,
      int posX, int posY) {
    blockImg = new Image(getClass().getResourceAsStream(name));
    Constants.loader = new ImageView(blockImg);
    Constants.loader.setFitHeight(fitHeight);
    Constants.loader.setFitWidth(fitWidth);
    Constants.loader.setViewport(new Rectangle2D(0, 0, width, height));
    setPosition(posX, posY);
  }

  public void run() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        if (isMainScreen) {
          Constants.mainRoot.getChildren().add(Constants.loader);
        } else {
          Constants.loadScreen.getChildren().add(Constants.loader);
        }
      }
    });

    Constants.save.convertSavesInToInfo();

    JavaSorting javaSort = new JavaSorting();
    long time = System.currentTimeMillis();
    javaSort.sort(Constants.games, 0, Constants.games.length - 1);
    time = System.currentTimeMillis() - time;
    System.out.println("Time for java sort: " + time);

    ScalaSorting scalaSort = new ScalaSorting();
    time = System.currentTimeMillis();
    scalaSort.sort(Constants.games);
    time = System.currentTimeMillis() - time;
    System.out.println("Time for scala sort: " + time);

    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        Constants.mainRoot.getChildren().remove(Constants.loader);
        Constants.loader = null;
        Constants.menu.setPadding(new Insets(1, 0, 0, 0));
      }
    });
  }
}
