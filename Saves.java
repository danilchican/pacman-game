package com.danilchican.pacman;

import java.io.*;
import java.util.UUID;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * 
 * Class which saves your current game
 * 
 * @author Vlad
 * @version 1.0
 */
public class Saves {
  BufferedWriter out = null;
  BufferedReader in = null;
  OutputStream clean;
  FileChooser tr = new FileChooser();
  File dialog = new File("SaveGames");
  File[] allFiles;

  static int countOfSaves = 0;

  /**
   * 
   * Constructor of this class
   * 
   */
  Saves() {
    allFiles = new File("SaveGames").listFiles();
    countOfSaves = allFiles.length;
    tr.setInitialDirectory(dialog);
  }

  /**
   * 
   * Method open buffer for write saving
   * 
   */
  public void openBufferForWrite() {
    if (out != null) {
      return;
    }

    countOfSaves++;
    cleanSave(countOfSaves);
    String path = new String("SaveGames\\moveplayer_number_" + UUID.randomUUID().toString() + ".c");

    try {
      out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, true)));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 
   * Method open buffer for read saved game
   * 
   */
  public void openBufferForRead() {
    if (in != null) {
      return;
    }
    try {
      in = new BufferedReader(new InputStreamReader(new FileInputStream(tr.showOpenDialog(null))));
    } catch (IOException e) {
      //e.printStackTrace();
    }
  }

  /**
   * 
   * Save the current moving way
   * 
   * @param direction
   * @return int
   */
  public int saveMove(int direction) {
    try {
      if (out == null) {
        return -2;
      }
      out.write(direction);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return direction;
  }

  /**
   * 
   * Calculate moving way and call function for write it
   * 
   * @param speedX
   * @param speedY
   * @return int
   */
  public int saveMoves(double posX, double posY) {
    if (out == null) {
      return -2;
    }

    Constants.save.saveMove((int) (posX % 10));
    posX /= 10;
    Constants.save.saveMove((int) (posX % 10));
    posX /= 10;
    Constants.save.saveMove((int) (posX % 10));

    Constants.save.saveMove((int) (posY % 10));
    posY /= 10;
    Constants.save.saveMove((int) (posY % 10));
    posY /= 10;
    Constants.save.saveMove((int) (posY % 10));

    return 1;
  }

  /**
   * 
   * Calculate moving way and call function for write it
   * 
   * @param speedX
   * @param speedY
   * @return int
   */
  public int saveMoves(int speedX, int speedY) {
    if (out == null) {
      return -2;
    }

    if (speedX > 0) {
      saveMove(1);
      saveMove(0);
    }
    if (speedX < 0) {
      saveMove(0);
      saveMove(1);
    }
    if (speedX == 0) {
      saveMove(0);
      saveMove(0);
    }
    if (speedY > 0) {
      saveMove(0);
      saveMove(1);
    }
    if (speedY < 0) {
      saveMove(1);
      saveMove(0);
    }
    if (speedY == 0) {
      saveMove(0);
      saveMove(0);
    }

    return 1;
  }

  /**
   * 
   * Chechout new line
   * 
   */
  public void newLine() {
    try {
      if (out != null) {
        out.newLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 
   * Get int from file and return it
   * 
   * @return int
   */
  public int getIntFromFile() {
    try {
      if (in == null) {
        return -2;
      }

      int temp = in.read();

      if (temp == -1) {
        closeInputStream();
      }

      return temp;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return 0;
  }

  /**
   * 
   * Close output stream
   * 
   */
  public void closeOutputStream() {
    try {
      if (out != null) {
        saveMove(-1);
        out.close();
        out = null;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 
   * Close input stream
   * 
   */
  public void closeInputStream() {
    try {
      if (in != null) {
        in.close();
        in = null;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  /**
   * 
   * Write level number to saving file
   * 
   * @param temp
   */
  public void addLevelNumber(int temp) {
    saveMove(temp);
  }

  /**
   * 
   * Clean file content before write
   * 
   * @param number
   */
  public void cleanSave(int number) {
    String path = new String("SaveGames\\moveplayer_number_" + number + ".c");
    try {
      clean = new FileOutputStream(path);
      clean.flush();
      clean.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 
   * Show all saved games
   * 
   */
  public void showSaves() {
    Stage saves;
    saves = new Stage();
    saves.setTitle("Saves");
    Pane saveRoot = new Pane();
    saveRoot.setPrefSize(300, 200);
    saveRoot.setMaxSize(300, 200);

    ObservableList<String> games = FXCollections.observableArrayList();
    allFiles = new File("SaveGames").listFiles();
    for (File temp : allFiles) {
      games.add(temp.getName());
    }
    ListView<String> listSaves = new ListView<String>();
    listSaves.setItems(games);
    listSaves.setStyle("-fx-border-width:3pt;" + "-fx-border-color:red;"
        + "-fx-font:bold 10pt ItalicT;" + "-fx-text-fill: red;");
    listSaves.setPrefSize(300, 200);
    listSaves.setMaxSize(300, 200);
    saveRoot.getChildren().add(listSaves);
    Scene saveScene = new Scene(saveRoot);
    saves.setScene(saveScene);
    saves.show();
  }

  private int readInt() {
    int temp = -2;
    try {
      temp = in.read();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return temp;
  }

  public void convertSavesInToInfo() {
    allFiles = new File("SaveGames").listFiles();

    Constants.games = new GameInfo[allFiles.length];

    int count = 0;
    int tmp = -2;
    int numberOfLevel;
    int stepsCountR, stepsCountL, stepsCountU, stepsCountD;
    int enemyStepsCount;

    for (File temp : allFiles) {
      stepsCountR = 0;
      stepsCountL = 0;
      stepsCountU = 0;
      stepsCountD = 0;
      enemyStepsCount = 0;

      try {
        in = new BufferedReader(new InputStreamReader(new FileInputStream(temp)));
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
      numberOfLevel = readInt();

      do {
        tmp = readInt();

        switch (tmp) {
          case 3:
            if (readInt() == 1) {
              stepsCountR++;
            }
            if (readInt() == 1) {
              stepsCountL++;
            }
            if (readInt() == 1) {
              stepsCountU++;
            }
            if (readInt() == 1) {
              stepsCountD++;
            }
            break;
          case 4:
            readInt();
            if (readInt() == -2 || readInt() == -2 || readInt() == -2 || readInt() == -2
                || readInt() == -2 || readInt() == -2) {
              return;
            } else {
              enemyStepsCount++;
            }
            break;
          case 5:
            readInt();
            if (readInt() == -2 || readInt() == -2 || readInt() == -2 || readInt() == -2
                || readInt() == -2 || readInt() == -2) {
              return;
            }
            break;
        }

      } while (tmp != -1);

      closeInputStream();

      Constants.games[count] = new GameInfo(temp.getName(), stepsCountL, stepsCountR, stepsCountU,
          stepsCountD, enemyStepsCount, numberOfLevel + 1);

      count++;
    }
  }
}
