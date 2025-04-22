package Model.undoMoves;

import Controller.GameExporter;
import Model.Board;
import Model.Player;
import Model.Position;
import Model.Token;
import Model.moves.Hop;
import Model.moves.Mill;
import Model.moves.Move;
import View.GameView;

import java.util.PropertyResourceBundle;

public class UndoMill extends UndoMove{
    private Board gameBoard;
    public UndoMill(GameView gameView) {
        super(gameView);
        gameBoard = gameView.getBoard();
    }

    @Override
    public boolean undo(Player player, Move previousMove, GameExporter gameExporter) {
        Token tokenTaken = ((Mill) previousMove).getTokenTaken();
        Position oldPos = tokenTaken.removePosition();
        System.out.println(tokenTaken.getPet() + ": " + oldPos.getRow() + ", " + oldPos.getCol());
        board.setToken(oldPos, tokenTaken);
        tokenTaken.addPosition(oldPos);
        player.tokenAdded(tokenTaken);
        player.setMilling(false);

        gameView.addTokenToGroup(tokenTaken);
        return true;
    }

    @Override
    public boolean validPreviousMove(Move previousMove) {
        return previousMove instanceof Mill;
    }
}
