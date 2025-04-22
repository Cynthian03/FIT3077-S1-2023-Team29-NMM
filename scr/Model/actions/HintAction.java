package Model.actions;

import Controller.Game;
import Model.Board;
import Model.MoveEnum;
import Model.Player;
import Model.Position;
import Model.moves.*;
import View.GameView;

import java.util.ArrayList;

/*
 * Hint Action Class ( Player Click on Hint button Shows all available moves )
 */
public class HintAction extends Action{
    private ArrayList<Position> possibleMoves;

    public HintAction(GameView gameView){
        super(gameView);

        //Setting possible moves array depending on current game state
        possibleMoves = new ArrayList<>();

    }

    @Override
    public boolean execute(Player player) {
        MoveAction moveAction = new MoveAction(null, null, gameView);
        boolean hinted = false;
        Move move = moveAction.getValidMove(player);

        if(move == null){
            return hinted;
        }
        else if(player.getSelectedToken() == null && move.getMoveName() != MoveEnum.Place){
            gameView.setGameMessage("You need to choose one of your tokens to get a hint");
        }
        else if(move.getMoveName() == MoveEnum.Hop){
            for(Position position :gameView.getBoard().getPositions()) {
                boolean validMove = gameView.getBoard().isEmptyPosition(position);
                if (validMove){
                    possibleMoves.add(position);
                    gameView.setHintHighlightPosition(position);
                }
            }
        }
        else if(move.getMoveName() == MoveEnum.Slide){
            for(Position position :gameView.getBoard().getPositions()) {
                boolean validMove = gameView.getBoard().isEmptyPosition(position) && gameView.getHighlightedToken().getTokenPosition().checkIsNeighbour(position);
                if (validMove){
                        possibleMoves.add(position);
                        gameView.setHintHighlightPosition(position);
                }
            }
        }
        else if(move.getMoveName() == MoveEnum.Place){
            for(Position position :gameView.getBoard().getPositions()) {
                boolean validMove = gameView.getBoard().isEmptyPosition(position);
                if (validMove){
                    possibleMoves.add(position);
                    gameView.setHintHighlightPosition(position);
                }
            }
        }
        return hinted;
    }


}
