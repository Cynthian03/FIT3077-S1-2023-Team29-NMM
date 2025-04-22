package Model.moves;

import Model.MoveEnum;
import Model.Player;
import Model.Position;
import Model.Token;
import View.GameView;

public class Slide extends Move{
    Token movedToken;

    public Slide(GameView gameView) {
        super(gameView, "Choose a token and the position you want to Slide to", MoveEnum.Slide);
    }

    @Override
    public boolean move(Position newPosition, Token token, Player player) {
        Position oldPosition = token.getTokenPosition();
        //New position is valid if it is a neighbour of the tokens current position and if the new position is empty
        boolean validMove = oldPosition.checkIsNeighbour(newPosition) && board.isEmptyPosition(newPosition);
        if(validMove) {
            board.updateTokenPosition(newPosition, token);
            
            player.addMove(this);
            token.addPosition(newPosition);
            this.movedToken = token;
        }
        return validMove;
    }

    @Override
    public boolean validGameState(Player player) {
        //No tokens remaining that can be placed on the board
        return player.getSelectedToken() != null && player.getTokensOnBoard() > Player.NUM_TOKENS_TO_HOP && player.getTokensNotPlaced() == 0;
    }

    public Token getMovedToken(){
        return this.movedToken;
    }

}