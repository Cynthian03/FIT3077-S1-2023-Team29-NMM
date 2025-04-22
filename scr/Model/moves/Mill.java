package Model.moves;

import Controller.Game;
import Model.*;
import View.GameView;

public class Mill extends Move {
    private Game game;
    private int tokensInMill;
    private Token tokenTaken;

    public Mill(Game game, GameView gameView){
        super(gameView, "Removing opponent Token", MoveEnum.Mill);
        this.game = game;
    }

    @Override
    public boolean move(Position position, Token token, Player player){
        Player opponent = game.getOpponentPlayer();
        boolean validMove = !token.checkMill(board) || areAllTokensInMills(opponent, board);
        if (validMove){
            board.removeToken(token);
            gameView.removeTokenFromGroup(token);
            opponent.tokenRemoved(token);
            player.setMilling(false);

            player.addMove(this);
            this.tokenTaken = token;
        }
        return validMove;
    }

    @Override
    public boolean validGameState(Player player) {
        return true;
    }
    public boolean areAllTokensInMills(Player player, Board board) {
        for (Token token : player.getTokens()) {
            if (token.checkMill(board)) {
                tokensInMill++;
            }
        }
        return tokensInMill == player.getTokens().size();
    }

    public Token getTokenTaken(){
        return this.tokenTaken;
    }
}
