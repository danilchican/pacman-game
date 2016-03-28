package com.danilchican.pacman;

public class Position {

  int column = 1;
  int row = 1;

  int x;
  int y;

  Position(int column, int row) {
    this.column = column;
    this.row = row;

    this.y = Constants.MARGIN_TOP + row * Constants.BlockWidth;
    this.x = Constants.MARGIN_LEFT_RIGHT + column * Constants.BlockHeight;
  }

  public void setPosition(Character player) {
    player.setTranslateX(x);
    player.setTranslateY(y);
  }

}
