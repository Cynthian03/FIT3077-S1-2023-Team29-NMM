package Model.actions;

import Controller.Game;
import Model.Player;
import Model.Position;
import Model.moves.*;
import View.GameView;

import java.util.ArrayList;

/*
* Move Action Class ( Player moves a token )
*/
//Need to extend Actions abstract class in the future and override methods
public class MoveAction extends Action{

    protected Game game;
    private Position position;

    private ArrayList<Move> possibleMoves;


    public MoveAction(Game game, Position position, GameView gameView){
        super(gameView);
        this.game = game;
        this.position = position;
        possibleMoves = new ArrayList<>();
        possibleMoves.add(new Place(gameView, game));
        possibleMoves.add(new Slide(gameView));
        possibleMoves.add(new Hop(gameView));
    }

    public boolean execute(Player player) {
        boolean moved = false;
        for (Move move : possibleMoves){
            if(move.validGameState(player)){
                moved = move.move(position, player.getSelectedToken(), player);
                break;
            }
        }
        if (!moved) {
            gameView.setGameMessage("Invalid Move");
        } else if (player.getSelectedToken().checkMill(gameView.getBoard())) {
            player.setMilling(true);
            gameView.setGameMessage("You've created a Mill, choose an opponent piece to remove");
            moved = false;
        } else {
            gameView.setGameMessage(null);
        }
        return moved;
    }

    public Move getValidMove(Player player) {
        Move validMove = null;
        for (Move move : possibleMoves){
            if(move.validGameState(player)){
                validMove = move;
            }
        }
        return validMove;
    }
}
