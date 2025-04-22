package Model.undoMoves;

import Controller.GameExporter;
import Model.Board;
import Model.Player;
import Model.moves.Move;
import View.GameView;

public abstract class UndoMove {
    protected GameView gameView;
    protected Board board;

    public UndoMove(GameView gameView) {
        this.gameView = gameView;
        board = gameView.getBoard();
    }

    public abstract boolean undo(Player player, Move previousMove, GameExporter gameExporter);

    public abstract boolean validPreviousMove(Move previousMove);

}
