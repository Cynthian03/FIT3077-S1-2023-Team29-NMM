package Model;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.shape.Line;

/**
 * Class representing one board within the system
 */

public class Board {

  //protected TokenPositionsIterator tokenPositions;
  public static final int HEIGHT = 480;
  public static final int WIDTH = 480;
  public static final int POSITION = 0;
  public static final int LINE = -1;
  public static final int EMPTY = -2;
  public static final int CAT = 1;
  public static final int DOG = 2;
  private ArrayList<Position> positions;
  private ArrayList<Line> lines;
  private int [][] boardMatrix = new int [7][7];

  //public Position[] previousBoardState = boardLocationsArray;

  /*
  //DO: This is for implementing board history by storing boardStates into an array (not needed for this sprint)
  public Position[][] boardHistoryArray = new Position[#numVersions][];
  for (int i = 0; i < #numVersions; i++) {
    boardHistoryArray[i] = Arrays.copyOf(boardLocationsArray, boardLocationsArray.length);
  }
  */

  public Board(){
    for (int[] row : boardMatrix){
      Arrays.fill(row, LINE);
    }
    boardMatrix[3][3] = EMPTY; // middle of board is empty and has no line or position
    positions = new ArrayList<>();
    lines = new ArrayList<>();
    createBoard();
  }

  public void createBoard(){
    int boardSquares = 3;
    int xLength = WIDTH /boardSquares/2;
    int yLength = HEIGHT /boardSquares/2;

    int leftX = 0;
    int topY = 0;
    int rightX = WIDTH;
    int bottomY = HEIGHT;
    int topLeft = 0;
    int bottRight = 6;
    int midIndex = 3;

    //joining lines
    lines.add(new Line(rightX/2, 0, rightX/2, yLength*(boardSquares-1)));
    lines.add(new Line(rightX/2, yLength*(boardSquares+1), rightX/2, bottomY));
    lines.add(new Line(0, bottomY/2, xLength*(boardSquares-1), bottomY/2));
    lines.add(new Line(xLength*(boardSquares+1), bottomY/2, rightX, bottomY/2));
    //square lines
    for (int s = 0; s < boardSquares; s++) {
      Integer[][] coords = {{leftX,topY},{rightX, topY},{rightX, bottomY},{leftX, bottomY}};
      int lineCount = 0;
      for (int i = 0; i < coords.length; i++){
        lineCount += 1;
        Integer[] end;
        if (i== coords.length-1){
          end = coords[0];
        } else {
          end = coords [i+1];
        }
        Integer startX = coords[i][0];
        Integer startY = coords[i][1];
        Integer endX = end[0];
        Integer endY = end[1];
        Line line = new Line(startX, startY, endX, endY);
        lines.add(line);
        int row1, row2, col1, col2;
        if (lineCount == 1){ //vert line
          row1 = topLeft;
          col1 = topLeft;
          row2 = topLeft;
          col2 = midIndex;
        } else if (lineCount == 2) { //hor line
          row1 = topLeft;
          col1 = bottRight;
          row2 = midIndex;
          col2 = bottRight;
        } else if (lineCount == 3) {
          row1 = bottRight;
          col1 = bottRight;
          row2 = bottRight;
          col2 = midIndex;
        } else {
          row1 = bottRight;
          col1 = topLeft;
          row2 = midIndex;
          col2 = topLeft;
        }
        Position pos1 = new Position(startX, startY, row1, col1);
        setPosition(pos1);
        Position pos2 = new Position((startX+endX)/ 2, (startY+endY)/ 2, row2, col2);
        setPosition(pos2);
        positions.add(pos1);
        positions.add(pos2);
      }
      leftX += xLength;
      topY += yLength;
      rightX -= xLength;
      bottomY -= yLength;
      topLeft += 1;
      bottRight -= 1;
    }
  }
  public void setPosition(Position position) {
    this.boardMatrix[position.getRow()][position.getCol()] = POSITION;
  }

  public int getMatrixValue(int row, int col) {
    return boardMatrix[row][col];
  }

  public boolean isEmptyPosition(Position position) {
    return getMatrixValue(position.getRow(),position.getCol()) == POSITION;
  }
  public void setToken(Position position, Token token){
    int value;
    if (token.getPet() == Pet.CAT){
      value = CAT;
    } else{
      value = DOG;
    }
    this.boardMatrix[position.getRow()][position.getCol()] = value;
  }

  public void removeToken(Token token){
    Position position = token.getTokenPosition();
    this.boardMatrix[position.getRow()][position.getCol()] = 0;
  }

  public ArrayList<Position> getPositions() {
    return this.positions;
  }
  public ArrayList<Line> getLines() {
    return lines;
  }

  public void updateTokensCaptured() {

  }

  public void updateTokenPosition(Position newPosition, Token token){
    setPosition(token.getTokenPosition());
    setToken(newPosition, token);
    token.move(newPosition);
  }

  public String getStringRepresentation(){
    StringBuilder stringBuilder = new StringBuilder();
    for (int[] row : boardMatrix) {
      // Convert each row to a string representation
      String rowString = Arrays.toString(row);
      // Append the row string to the overall string representation
      stringBuilder.append(rowString).append(System.lineSeparator());
    }

    return stringBuilder.toString();
  }

  public void setGameBoard(int[][] b){
    this.boardMatrix = b;
  }

}
