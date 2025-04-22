package Model;

import Model.moves.Move;
import View.GameView;
import View.PlayerView;

import java.util.ArrayList;
import java.util.Stack;

public class Player {
  public static final int MAX_TOKENS = 9;
  public static final int NUM_TOKENS_TO_HOP = 3;
  private final Pet pet;
  private ArrayList<Token> tokens;
  private Token selectedToken;
  private int tokensOnBoard;
  private int tokensNotPlaced;
  private boolean milling;
  private final PlayerView playerView;

  private Stack<Move> moves;
  private Stack<Move> allMoves;

  public Player(Pet pet) {
    this.pet = pet;
    this.tokens = new ArrayList<>();
    tokensNotPlaced = MAX_TOKENS;
    playerView = new PlayerView(this);

    this.moves = new Stack<>(); // create a list of moves player has created throughout the game
  }
  public ArrayList<Token> getTokens(){
    return this.tokens;
  }
  public void tokenPlaced(Token token){
    this.tokens.add(token);
    this.tokensNotPlaced --;
    this.tokensOnBoard ++;
  }
  public void tokenRemoved(Token token){
    tokens.remove(token);
    this.tokensOnBoard --;
  }

  public void tokenAdded(Token token){
    tokens.add(token);
    this.tokensOnBoard ++;
  }

  public Pet getPet() {
    return pet;
  }

  public int getTokensOnBoard() {
    return tokensOnBoard;
  }
  public void setTokensOnBoard(int num){
    this.tokensOnBoard = num;
  }

  public Token getSelectedToken() {
    return selectedToken;
  }

  public void setSelectedToken(Token selectedToken) {
    this.selectedToken = selectedToken;
  }

  public int getTokensNotPlaced(){
    return tokensNotPlaced;
  }

  public void setTokensNotPlaced(int tokensNotPlaced) {
    this.tokensNotPlaced = tokensNotPlaced;
  }

  public PlayerView getPlayerView() {
    return playerView;
  }

  public boolean isMilling() {
    return milling;
  }

  public void setMilling(boolean milling) {
    this.milling = milling;
  }

  public void addMove(Move move){
    // When player moves, add the most recent move to list of moves
    this.moves.push(move);
  }

  public Move undoMove(){
    // pops the most recent moves from the list of moves
    return this.moves.pop();
  }

  public Stack<Move> getHistoryMove(){
    return this.moves;
  }

  public void unPlaceToken(Token token){
    this.tokens.remove(token);
    this.tokensNotPlaced ++;
    this.tokensOnBoard --;
  }

  public void translateAllMoves(){
    Stack<Move> allMoves = new Stack<>();
    while (!this.moves.empty()){
      Move m = this.moves.pop();
      allMoves.push(m);
    }
    this.allMoves = allMoves;
  }

  public Stack<Move> getAllMoves(){
    return this.allMoves;
  }
  public void resetPlayer(GameView gameView){
    ArrayList<Token> tokens = getTokens();
    while (!tokens.isEmpty()) {
      Token token = tokens.get(0);
      tokenRemoved(token);
      gameView.getBoard().removeToken(token);
      gameView.removeTokenFromGroup(token);
    }
    setTokensNotPlaced(9);
    setTokensOnBoard(0);
    getPlayerView().playerTokenTracker(this);
    moves.clear();
  }

  public void decreaseTokenNotPlaced(){
    this.tokensNotPlaced --;
  }
}
