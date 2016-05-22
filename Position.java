package com.danilchican.pacman;

/**
 * Info about position
 * 
 * @author Vlad Danilchik
 *
 */
public class Position {

  private int column = 1;
  private int row = 1;

  private int x;
  private int y;

  /**
   * Set info about new position
   * 
   * @param column
   * @param row
   * @see Position#Position(int, int)
   */
  Position(int column, int row) {
    this.column = column;
    this.row = row;

    this.y = Constants.MARGIN_TOP + this.row * Constants.BlockWidth;
    this.x = Constants.MARGIN_LEFT_RIGHT + this.column * Constants.BlockHeight;
  }

  /**
   * Set position of player
   * 
   * @param player of the game
   * @see Position#setPosition(Character)
   */
  public void setPosition(Character player) {
    player.setTranslateX(x);
    player.setTranslateY(y);
  }

  /**
   * Set position of enemy
   * 
   * @param enemy of the game
   * @see Position#setPosition(Enemy)
   */
  public void setPosition(Enemy enemy) {
    enemy.setTranslateX(x + 1);
    enemy.setTranslateY(y + 1);
  }

  /**
   * Set position of boot
   * 
   * @param boot of the game
   * @see Position#setPosition(Boot)
   */
  public void setPosition(Boot boot) {
    boot.setTranslateX(x + 1);
    boot.setTranslateY(y + 1);
  }
}
