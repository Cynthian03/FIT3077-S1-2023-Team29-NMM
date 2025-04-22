package Model.moves;

import Model.MoveEnum;
import Model.Player;
import Model.Position;
import Model.Token;
import View.GameView;

public class Hop extends Move{
    Token movedToken;
    public Hop(GameView gameView) {
        super(gameView, "Choose a token and the position anywhere to Hop", MoveEnum.Hop);
    }

    @Override
    public boolean move(Position newPosition, Token token, Player player) {
        boolean validMove = board.isEmptyPosition(newPosition);
        if (validMove) {
            board.updateTokenPosition(newPosition, player.getSelectedToken());

            player.addMove(this);
            token.addPosition(newPosition);
            this.movedToken = token;
        }
        return validMove;
    }

    @Override
    public boolean validGameState(Player player) {
        // if no tokens to place and 3 tokens remaining onBoard
        return player.getSelectedToken() != null && player.getTokensNotPlaced() == 0 && (player.getTokensOnBoard() == Player.NUM_TOKENS_TO_HOP);
    }

    public Token getMovedToken(){
        return this.movedToken;
    }

}
