package Model.actions;

import Controller.Game;
import Model.Player;
import View.GameView;

public abstract class Action {
  protected GameView gameView;

  public Action(GameView gameView){
    this.gameView = gameView;

  }
  public abstract boolean execute(Player player);
}
