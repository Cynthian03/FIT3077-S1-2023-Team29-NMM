package View;

import Controller.Game;
import Model.*;
import Model.moves.Hop;
import Model.moves.Place;
import Model.moves.Slide;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Random;

public class TutorialView {

    private GameView gameView;

    public TutorialView(Label message, GameView gameView){
        this.gameView = gameView;
        placingPhase(message);
    }

    private void placingPhase(Label message) {
        message.setText("The game begins with players alternately placing tokens on an empty position.\n" +
                "Try clicking on a position to place your token.\n" +
                "When you have done this click 'Next' below.");

        Button nextBut = new Button("Next");
        nextBut.setOnAction(event -> {
            slidingPhase(message);
        });
        message.setGraphic(nextBut);
        message.setContentDisplay(ContentDisplay.BOTTOM);
    }

    private void slidingPhase(Label message) {
        // placing all tokens
        ArrayList<Position> positions = gameView.getBoard().getPositions();
        Random rand = new Random();
        // get
        Player player = Game.getInstance().getCurrPlayer();
        for(int i = 0; i<2; i++){
            while (player.getTokensNotPlaced() != 0){
                int randInt = rand.nextInt(positions.size());
                Position currPos = positions.get(randInt);
                Place place = new Place(gameView, Game.getInstance());
                place.move(currPos, null, player);
            }
            player = Game.getInstance().getOpponentPlayer();
        }

        gameView.setGameMessage(new Slide(gameView).getMoveDesc());
        message.setText("After all tokens are placed, players slide tokens to any adjacent vacant point.\n" +
                "Choosing a token and then choose a position that is adjacent to the token to slide.\n" +
                "Players can only choose their own tokens to slide when it is their turn.");

        Button nextBut = new Button("Next");
        nextBut.setOnAction(event -> {
            undoPhase(message);
        });
        message.setGraphic(nextBut);
        message.setContentDisplay(ContentDisplay.BOTTOM);
    }

    private void undoPhase(Label message) {
        message.setText("When we either made a mistake or wish to undo a move due to any circumstance, \n we can use the undo button." +
                "Try clicking on the undo button on the top right of the screen.\n");

        Button nextBut = new Button("Next");
        nextBut.setOnAction(event -> {
            hoppingPhase(message);
        });
        message.setGraphic(nextBut);
        message.setContentDisplay(ContentDisplay.BOTTOM);
    }

    private void hoppingPhase(Label message){
        Board board = gameView.getBoard();
        Player player = Game.getInstance().getCurrPlayer();

        // remove 6 of tokens previously placed to leave only 3 on the board for each player.
        for(int i = 0; i<2; i++){
            ArrayList<Token> tokens = player.getTokens();
            int tokenCount = tokens.size();
            for (Token token : tokens){
                if(tokenCount == 3){
                    break;
                }
                board.removeToken(token);
                gameView.removeTokenFromGroup(token);
                tokenCount --;
                player.setTokensOnBoard(tokenCount);
            }
            player = Game.getInstance().getOpponentPlayer();
        }

        gameView.setGameMessage(new Hop(gameView).getMoveDesc());
        message.setText("When a player has only three tokens left, they may jump a tokens to any vacant point.\n" +
                "Choosing a token and then choose a position that is unoccupied by a token to hop.\n" +
                "Players can only choose their own tokens to hop when it is their turn.");

        Button nextBut = new Button("Next");
        nextBut.setOnAction(event -> {
            millingPhase(message);
        });
        message.setGraphic(nextBut);
        message.setContentDisplay(ContentDisplay.BOTTOM);
    }

    private void millingPhase(Label message){
        // getting positions to place
        ArrayList<Position> positions = gameView.getBoard().getPositions();
        Random rand = new Random();
        // get curr player
        Player player = Game.getInstance().getCurrPlayer();

        for(int i = 0; i<2; i++){
            int randInt = rand.nextInt(positions.size());
            Position currPos = positions.get(randInt);
            Place place = new Place(gameView, Game.getInstance());
            place.move(currPos, null, player);
            player.setTokensNotPlaced(0);
            player = Game.getInstance().getOpponentPlayer();
        }

        gameView.setGameMessage(new Slide(gameView).getMoveDesc());
        message.setText("The objective of the game is to create a mill (three-in-a-row) by moving your tokens.\n" +
                "When you close a mill, you can remove any of your opponent's token which are not part of a mill.\n" +
                "Created a mill by sliding your tokens and then choose the opponent's token to remove.");

        Button nextBut = new Button("Next");
        nextBut.setOnAction(event -> {
            endGamePhase(message);
        });
        message.setGraphic(nextBut);
        message.setContentDisplay(ContentDisplay.BOTTOM);
    }

    private void endGamePhase(Label message){
        message.setText("You win when your opponent has less than three tokens remaining " +
                " OR your opponent cannot make a legal move. \n" +
                "You have learned all the rules and are ready to play! If you need help press the hint button in the top right to show valid moves.");
        Button nextBut = new Button("Finish Tutorial");
        nextBut.setOnAction(event -> {
            // reset both players to original state and remove tokens on board
            Player player = Game.getInstance().getCurrPlayer();
            for(int i = 0; i<2; i++) {
                player.resetPlayer(gameView);
                player = Game.getInstance().getOpponentPlayer();
            }
            Game.state = GameState.Menu;
            gameView.render();
        });
        message.setGraphic(nextBut);
        message.setContentDisplay(ContentDisplay.BOTTOM);
    }
}
