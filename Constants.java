package com.danilchican.pacman;

import java.util.*;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

public class Constants {

  public static int MARGIN_TOP = 100;
  public static int MARGIN_LEFT_RIGHT = 60;

  public static int screenWidth = 1000;
  public static int screenHeight = 600;

  public static final int BlockWidth = 32;
  public static final int BlockHeight = 32;

  public static final int CharacterSize = 27;

  public static ArrayList<Block> blocks = new ArrayList<>();
  public static ArrayList<Block> food = new ArrayList<>();
  public static HashMap<KeyCode, Boolean> keys = new HashMap<>();

  public static MenuBox menu;

  public static Scene main_scene;
  public static Pane mainRoot = new Pane();
  public static Pane gameRoot = new Pane();

  public static Character player;

  public static enum BlockType {
    BRICK, FOOD
  }

}
