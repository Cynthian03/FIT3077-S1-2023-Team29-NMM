package Model.moves;

import Model.*;
import View.GameView;

/**
 * Create class
 */
public abstract class Move {
    protected GameView gameView;
    protected Board board;
    protected String moveDesc;
    private final MoveEnum moveName;

    public Move(GameView gameView, String moveDesc, MoveEnum name) {
        this.gameView = gameView;
        this.board = gameView.getBoard();
        this.moveDesc = moveDesc;
        this.moveName = name;
    }

    public abstract boolean move(Position newPosition, Token token, Player player);

    public abstract boolean validGameState(Player player);
    public String getMoveDesc() {
        return moveDesc;
    }

    public Board getUpdateBoard(){
        return board;
    }

    public MoveEnum getMoveName(){
        return moveName;
    }

    @Override
    public String toString(){
        return moveName.toString();
    }

}
