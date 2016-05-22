package com.danilchican.pacman;

import java.util.Random;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class JavaSorting {
  static Random rand = new Random();

  public void sort(GameInfo[] array, int begin, int end) {
    int i = begin;
    int j = end;
    int x = array[begin + rand.nextInt(end - begin + 1)].getRightMoving();

    while (i <= j) {
      while (array[i].getRightMoving() > x) {
        i++;
      }
      while (array[j].getRightMoving() < x) {
        j--;
      }
      if (i <= j) {
        GameInfo temp = array[i];
        array[i] = array[j];
        array[j] = temp;
        i++;
        j--;
      }
    }

    if (begin < j) {
      sort(array, begin, j);
    }
    if (i < end) {
      sort(array, i, end);
    }
  }

  public void showSortingList() {
    if (Constants.games != null) {
      Stage saves;
      saves = new Stage();
      saves.setTitle("Sorting list");
      Pane saveRoot = new Pane();
      saveRoot.setPrefSize(600, 200);
      saveRoot.setMinSize(600, 200);
      saveRoot.setMaxSize(600, 200);

      ObservableList<String> list = FXCollections.observableArrayList();
      for (GameInfo game : Constants.games) {
        list.add(game.getRightMoving() + ".    " + game.getName());
      }
      ListView<String> listSaves = new ListView<String>();
      listSaves.setItems(list);
      listSaves.setStyle("-fx-border-width:3pt;" + "-fx-border-color:red;"
          + "-fx-font:bold 10pt ItalicT;" + "-fx-text-fill: red;");
      listSaves.setPrefSize(600, 200);
      listSaves.setMinSize(600, 200);
      listSaves.setMaxSize(600, 200);

      saveRoot.getChildren().add(listSaves);
      Scene saveScene = new Scene(saveRoot);
      saves.setScene(saveScene);
      saves.show();
    } else {
      System.out.println("games is null");
    }
  }

}
