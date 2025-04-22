package Model;

import Controller.Game;
import Model.moves.Mill;
import View.GameView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.image.Image;

import java.util.Stack;

public class Token extends Circle {

    private final Pet pet;
    private Position position;

    private Stack<Position> positions;

    public Token(Pet pet, Position position) {
        this.pet = pet;
        this.positions = new Stack<>();

        setRadius(20);
        Image cat = new Image("file:./resources/cat_icon.png");
        Image dog = new Image("file:./resources/dog_icon.png");
        setFill(pet == Pet.CAT ? new ImagePattern(cat) : new ImagePattern(dog));

        move(position);
    }

    public Pet getPet(){
        return this.pet;
    }

    public void move(Position position){
        double oldX = position.getXPosition();
        double oldY = position.getYPosition();
        setCenterX(oldX);
        setCenterY(oldY);
        this.position = position;
    }

    public Position getTokenPosition(){
        return this.position;
    }

    public boolean checkMill(Board board) {
        Position position = getTokenPosition();
        int tokenNum = pet == Pet.CAT ?  Board.CAT : Board.DOG;
        int millRowCount = 0;
        int millColCount = 0;

        if (position.getRow() != 3) {
            for (int i = 0; i < 7; i++) {
                if (board.getMatrixValue(position.getRow(),i) == tokenNum){
                    millRowCount++;
                }
            }

        } else if (position.getRow() == 3) {
            int startIndex;
            int endIndex;
            if (position.getCol() < 3){
                startIndex = 0;
                endIndex = 3;
            } else {
                startIndex = 4;
                endIndex = 7;
            }
            for (int i = startIndex; i < endIndex; i++) {
                if (board.getMatrixValue(3,i) == tokenNum){
                    millRowCount++;
                }
            }
        }

        if (position.getCol() != 3) {
            for (int i = 0; i < 7; i++) {
                if (board.getMatrixValue(i, position.getCol()) == tokenNum){
                    millColCount++;
                }
            }
        } else if(position.getCol() == 3) {
            int startIndex;
            int endIndex;
            if (position.getRow() < 3){
                startIndex = 0;
                endIndex = 3;
            } else {
                startIndex = 4;
                endIndex = 7;
            }
            for (int i = startIndex; i < endIndex; i++) {
                if (board.getMatrixValue(i,3) == tokenNum){
                    millColCount++;
                }
            }
        }
        return millColCount == 3 || millRowCount == 3;
    }

    public void createEventHandler(Game game, GameView gameView) {
        setOnMouseClicked(mouseEvent -> {
            gameView.setHighlightedToken(Token.this);
            Player currPlayer = game.getCurrPlayer();
            if (currPlayer.getPet() == getPet()) {
                currPlayer.setSelectedToken(Token.this);
            }
            else if(currPlayer.isMilling() && currPlayer.getPet() != getPet()){
                currPlayer.setSelectedToken(Token.this);
                Mill mill = new Mill(game, gameView);
                boolean moved = mill.move(getTokenPosition(), Token.this, currPlayer);
                game.continueGame(moved);
            }
        });
    }

    public void addPosition(Position pos){
        // adds the most recent position to the history of positions
        this.positions.push(pos);
    }

    public Position removePosition(){
        // removes the most recent position from the history of positions
        return this.positions.pop();
    }
}
