package com.danilchican.pacman;

import java.util.*;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

/**
 * Class Contstants contains main constants
 * 
 * @author Vlad Danilchik
 *
 */
public class Constants {

  public static boolean startGame = false;
  public static boolean startBoot = false;
  public static boolean startReplay = false;

  public static final int speedOfHero = 3;

  public static int countFoodLevel = 1;
  public static int CurrentCountBonuses = 0;

  public static int currentLevel = 1;
  public static int levelsCount = 2;

  public static final int MARGIN_TOP = 100;
  public static final int MARGIN_LEFT_RIGHT = 60;

  public static final int screenWidth = 1000;
  public static final int screenHeight = 600;

  public static final int BlockWidth = 32;
  public static final int BlockHeight = 32;

  public static final int CharacterSize = 27;
  public static final int EnemySize = 30;

  public static Saves save = new Saves();

  public static Scene main_scene = null; // main scene
  public static Pane mainRoot = new Pane(); // main screen
  public static Pane gameRoot = new Pane(); // game screen

  public static Character player = null; // the player
  public static Boot boot = null; // the boot

  public static ArrayList<Block> blocks = new ArrayList<>(); // game blocks
  public static ArrayList<Enemy> enemies = new ArrayList<>(); // enemies
  public static HashMap<KeyCode, Boolean> keys = new HashMap<>(); // pushing keys

  public static MenuBox menu = null; // main menu
  public static MenuBox subMenuLevels = null; // sub menu for selection levels

}
