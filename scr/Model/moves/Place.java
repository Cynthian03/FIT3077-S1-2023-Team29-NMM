package Model.moves;

import Controller.Game;
import Model.MoveEnum;
import Model.Player;
import Model.Position;
import Model.Token;
import View.GameView;

public class Place extends Move {
    private Game game;
    private Token movedToken;

    public Place(GameView gameView, Game game){
        super(gameView, "Choose a position to Place your token", MoveEnum.Place);
        this.game = game;
    }

    @Override
    public boolean move(Position position, Token token, Player player){
        boolean validMove = board.isEmptyPosition(position);
        if (validMove){
            Token newToken = new Token(player.getPet(), position);
            gameView.setHighlightedToken(newToken);
            newToken.createEventHandler(game, gameView);
            player.tokenPlaced(newToken);
            player.setSelectedToken(newToken);

            // add this move to the player's history of move
            player.addMove(this);
            newToken.addPosition(position);
            this.movedToken = newToken;

            // update player view
            board.setToken(position, newToken);
            gameView.addTokenToGroup(newToken);
            // update token tracker view
            player.getPlayerView().playerTokenTracker(player);
        }
        return validMove;
    }

    public boolean validGameState(Player player) {
        // if player still has tokens to place
        return player.getTokensNotPlaced() > 0;
    }

    public Token getMovedToken(){
        return this.movedToken;
    }
}
