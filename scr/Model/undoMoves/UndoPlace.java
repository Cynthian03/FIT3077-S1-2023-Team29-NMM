package Model.undoMoves;

import Controller.GameExporter;
import Model.Board;
import Model.Player;
import Model.Token;
import Model.moves.Move;
import Model.moves.Place;
import View.GameView;

public class UndoPlace extends UndoMove {

    Board gameBoard;
    public UndoPlace(GameView gameView) {
        super(gameView);
        gameBoard = gameView.getBoard();
    }

    @Override
    public boolean undo(Player player, Move previousMove, GameExporter gameExporter) {

        // If there is still previous move left
        Token tokenMoved = ((Place) previousMove).getMovedToken();

        // update undo the placing of token
        player.unPlaceToken(tokenMoved);

        // update the view model to reflect this undo action
        player.getPlayerView().playerTokenTracker(player);
        gameBoard.removeToken(tokenMoved);
        gameView.removeTokenFromGroup(tokenMoved);

        return true;
    }

    @Override
    public boolean validPreviousMove(Move previousMove) {
        return previousMove instanceof Place;
    }
}
