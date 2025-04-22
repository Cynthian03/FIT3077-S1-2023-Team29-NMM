package Model.undoMoves;

import Controller.GameExporter;
import Model.Board;
import Model.Player;
import Model.Position;
import Model.Token;
import Model.moves.Move;
import Model.moves.Place;
import Model.moves.Slide;
import View.GameView;

public class UndoSlide extends UndoMove{
    Board gameBoard;
    public UndoSlide(GameView gameView) {
        super(gameView);
        gameBoard = gameView.getBoard();
    }

    @Override
    public boolean undo(Player player, Move previousMove, GameExporter gameExporter) {
        /* To undo slide get the previous position, return token to that position */

        Token tokenMoved = ((Slide) previousMove).getMovedToken();
        tokenMoved.removePosition(); // Removes the current position of the token
        Position oldPos = tokenMoved.removePosition();  // This removes the position of the previous location
        gameBoard.updateTokenPosition(oldPos, tokenMoved);

        return true;
    }

    @Override
    public boolean validPreviousMove(Move previousMove) {
        return previousMove instanceof Slide;
    }
}
