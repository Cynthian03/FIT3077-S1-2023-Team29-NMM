package Controller;

import Model.*;
import Model.actions.MoveAction;
import Model.moves.Place;
import View.GameView;
import View.HintButton;
import View.UndoButton;
import View.SaveGameButton;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class representing the game state, including the position of tokens on the board.
 */
public class Game {
  private static Game instance;
  public static GameState state = GameState.Menu;
  protected GameView gameView;
  protected Board board;
  protected ArrayList<Player> players = new ArrayList<>();
  protected Player currPlayer;

  protected GameExporter gameExporter;

  private Game() {
    gameView = new GameView(this);
    board = gameView.getBoard();
    Player player1 = new Player(Pet.DOG);
    Player player2 = new Player(Pet.CAT);
    //this.gameExporter = new GameExporter();
    addPlayer(player1);
    addPlayer(player2);
    currPlayer = players.get(1);
    gameView.setPlayers(player1, player2);
    gameView.playerTurnView(currPlayer);
    createEventHandlers();
  }

  private void createEventHandlers() {
    UndoButton undoButton = new UndoButton(gameView, this.gameExporter);
    HintButton hintButton = new HintButton(gameView);
    SaveGameButton saveButton = new SaveGameButton(gameView, players);
    gameView.createHeader(undoButton, hintButton, saveButton);

        // Add event listeners to position components
    for (Position position: gameView.getBoard().getPositions()){
      position.setNeighbours(board); //add neighbours
      position.setOnMouseClicked(mouseEvent -> {
        gameView.setHighlightedPosition(position);
        MoveAction moveAction = new MoveAction(instance, position, gameView);
        boolean moved = moveAction.execute(currPlayer);
        continueGame(moved);
        currPlayer.setSelectedToken(null);
        gameView.unhighlightPositions(gameView.getBoard().getPositions());

      });
    }
  }

  public void continueGame(boolean moved) {
    if (moved) {
      checkGameOver();
      swapPlayer();
      gameView.playerTurnView(currPlayer);
      gameView.setGameMessage(gameMove());
    }
  }

  public String gameMove(){
    MoveAction moveAction = new MoveAction(instance, null, gameView);
    return moveAction.getValidMove(currPlayer).getMoveDesc();
  }

  public static Game getInstance(){
    if (instance == null) {
      instance = new Game();
    }
    return instance;
  }

  /**
   * Checks if game is over and updates view if Over.
   * The game is considered to still be running if both players have more than 3 tokens
   *
   */
  protected void checkGameOver() {
    Player winningPlayer = null;
    boolean gameOver = false;
    for (Player player : players){
      if (player.getTokensNotPlaced() + player.getTokensOnBoard()<3){
        gameOver = true;
        continue;
      }
      winningPlayer = player;
    }
    if (gameOver) {
      gameView.whenFinished(winningPlayer);
    }
  }

  public void addPlayer(Player player) {
    this.players.add(player);
  }
  public Player getCurrPlayer() {
    return this.currPlayer;
  }
  public Player getOpponentPlayer() {
    Player opponent = null;
    for (Player player : players){
      if (currPlayer == player){
        continue;
      }
      opponent = player;
    }
    return opponent;
  }
  public void swapPlayer() {
    for (Player player : players) {
      if (currPlayer != player) {
        this.currPlayer = player;
        break;
      }
    }
  }
  public static GameState getState() {
    return state;
  }

  public Stage getStage() {
    return this.gameView.getStage();
  }

  public GameExporter getGameExporter(){
    return this.gameExporter;
  }

  public void loadGameBoard(int[][] gBoard, GameExporter gameExporter){
    Board oldBoard = new Board();
    oldBoard.setGameBoard(gBoard);

    // board.setGameBoard(gBoard);

    Pet pet = gameExporter.getLastPlayer();
    if (pet == getCurrPlayer().getPet()){
      swapPlayer();
      gameView.playerTurnView(currPlayer);
    }
    ArrayList<Position> allPositions = board.getPositions();
    for (Position pos : allPositions) {
      int petNum = oldBoard.getMatrixValue(pos.getRow(), pos.getCol());
      if (petNum == 1 || petNum == 2){
        Token token = petNum == 1 ? new Token(Pet.CAT, pos) : new Token(Pet.DOG, pos);
        Player player = petNum == 1 ? players.get(1) : players.get(0);

        Place place = new Place(gameView, this);
        place.move(pos, token, player);
      }
    }

    ArrayList<String> catStringMove = gameExporter.getPlayerStringMove(Pet.CAT);
    ArrayList<String> dogStringMove = gameExporter.getPlayerStringMove(Pet.DOG);

    for (String line: catStringMove){
      String millPattern = "\\bMill\\b";
      Pattern millRegex = Pattern.compile(millPattern);
      Matcher millMatcher = millRegex.matcher(line);
      while (millMatcher.find()){
        players.get(0).decreaseTokenNotPlaced();
        players.get(0).getPlayerView().playerTokenTracker(players.get(0));
      }
    }

    for (String line: dogStringMove){
      System.out.println(line);
      String millPattern = "\\bMill\\b";
      Pattern millRegex = Pattern.compile(millPattern);

      Matcher millMatcher = millRegex.matcher(line);
      while (millMatcher.find()){
        players.get(1).decreaseTokenNotPlaced();
        players.get(1).getPlayerView().playerTokenTracker(players.get(1));
      }
    }
    gameExporter = new GameExporter(false);
  }

}
