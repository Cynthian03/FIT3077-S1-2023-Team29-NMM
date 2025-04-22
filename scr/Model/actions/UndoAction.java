package Model.actions;

import Controller.GameExporter;
import Model.Player;
import Model.moves.Move;
import Model.undoMoves.*;
import View.GameView;
import java.util.ArrayList;


public class UndoAction extends Action{
    private ArrayList<UndoMove> possibleUndoMoves;
    private GameExporter gameExporter;
    public UndoAction(GameView gameView, GameExporter gameExporter) {
        super(gameView);
        possibleUndoMoves = new ArrayList<>();
        possibleUndoMoves.add(new UndoPlace(gameView));
        possibleUndoMoves.add(new UndoSlide(gameView));
        possibleUndoMoves.add(new UndoHop(gameView));

        this.gameExporter = gameExporter;
    }

    @Override
    public boolean execute(Player player) {
        boolean undo = false;
        if (!player.getHistoryMove().empty()){
            Move player_previousMove = player.getHistoryMove().peek();

            for (UndoMove undoMove : possibleUndoMoves){
                if (undoMove.validPreviousMove(player_previousMove)){
                    undo = undoMove.undo(player, player.undoMove(), this.gameExporter);
                    break;
                }
            }
            // If none of the above previous move could be a Mill
            if (!undo) {
                System.out.println("Opponent Previous moves:" + player.getHistoryMove());
                UndoMill undoMill = new UndoMill(gameView);
                if (undoMill.validPreviousMove(player.getHistoryMove().peek())){
                    // The previous move was a mill
                    undoMill.undo(player, player.undoMove(), this.gameExporter);

                    // This mill could have been created by place, slide or hop
                    for (UndoMove undoMove : possibleUndoMoves){
                        if (undoMove.validPreviousMove(player.getHistoryMove().peek())){
                            System.out.println(player.getHistoryMove().peek());
                            undo = undoMove.undo(player, player.undoMove(), this.gameExporter);
                            break;
                        }
                    }
                }
            }

        }
        return undo;

    }

}
