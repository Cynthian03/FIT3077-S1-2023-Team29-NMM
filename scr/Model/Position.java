package Model;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Position extends Circle{
    private ArrayList<Integer[]> neighbours;
    private int x;
    private int y;
    private int row;
    private int col;

    public Position(int x, int y, int row, int col){
    this.x = x;
    this.y = y;
    this.row = row;
    this.col = col;
    setRadius(10);
    setCenterX(x);
    setCenterY(y);
    setStroke(Color.valueOf("#000000"));
    setFill(Color.valueOf("#023047"));
    neighbours = new ArrayList<>();
    }


    public void addNeighbour(int row, int col){
        Integer[] position= {row, col};
        neighbours.add(position);
    }

    public int getXPosition() {
        return this.x;
    }

    public int getYPosition() {
        return this.y;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setNeighbours(Board board){
      // CHECK ABOVE NEIGHBOUR
      for(int i=1; i<7 ; i++){
          int above = getRow() - i;
          int col = getCol();
          if (checkIsPosition(board, above, col)) break;
      }

      // CHECK BELOW NEIGHBOUR
      for(int i=1; i<7 ; i++){
          int below = getRow() + i;
          int col = getCol();
          if (checkIsPosition(board, below, getCol())) break;
      }

      // CHECK RIGHT SIDE NEIGHBOUR
      for(int i=1; i<7 ; i++){
          int row = getRow();
          int right = getCol() + i;
          if (checkIsPosition(board, row, right)) break;
      }

      // CHECK LEFT SIDE NEIGHBOUR
      for(int i=1; i<7 ; i++) {
          int row = getRow();
          int left = getCol() - i;
          if (checkIsPosition(board, row, left)) break;
      }
    }

    private boolean checkIsPosition(Board board, int row, int col) {
        if (6 < row || row < 0 || 6 < col || col < 0 || board.getMatrixValue(row, col) == Board.EMPTY){
            return true;
        }
        else if(row >= 0 && col >= 0 && board.getMatrixValue(row, col) > Board.LINE){
            addNeighbour(row, col);
            return true;
        }
        return false;
    }

    public boolean checkIsNeighbour(Position position){
        for(Integer[] neighbour : neighbours){
            if(neighbour[0] == position.getRow() && neighbour[1] == position.getCol()) return true;
        }
        return false;
    }
}
