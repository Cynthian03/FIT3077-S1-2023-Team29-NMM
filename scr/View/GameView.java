package View;

import Controller.Game;
import Controller.GameExporter;
import Model.*;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class that sets up the game and board UI for the user
 */
public class GameView {
  /**
   * Width of game Display
   */
  public static final int WIDTH = Board.WIDTH * 2;
  /**
   * Height of game Display
   */
  public static final int HEIGHT = Board.HEIGHT + Board.HEIGHT*3/4;
  public final static String SCENE_COLOR = "#8ECAE6";
  /**
   * Object to hold the actual scene
   */
  private Scene scene;
  /**
   * The stage
   */
  private Stage stage;
  /**
   * Actual state of the game
   */
  private GameState state;
  /**
   * Board object
   */
  private Board board;
  private ArrayList<PlayerView> playerViews = new ArrayList<>();
  private Label turnLabel = new Label();

  private Pane boardPane;
  private VBox turnBox;
  private Label  messageLabel = new Label();
  private BorderPane canvas;
  private Group lineGroup = new Group();
  private Group positionGroup = new Group();
  private Group tokenGroup = new Group();
  private Position highlightedPosition;
  private Token highlightedToken;
  private Game game;

  public GameView(Game game){
    board = new Board();

    stage = new Stage();
    stage.setTitle("Nine Men's Morris");

    // game canvas set up
    canvas = new BorderPane();
    canvas.setStyle("-fx-background-color: "+SCENE_COLOR);
    canvas.setPrefSize(WIDTH, HEIGHT);

    scene = new Scene(canvas);
    stage.setScene(scene);
    this.game = game;
    render();
}

  /**
   * The render method, that displays the graphics
   */
  public void render() {

    this.state = Game.getState();
    switch (state) {
      case Menu ->    // if game state is Menu display the menu screen
              whenMenu();
      case Running, Loaded ->    // if game state is Started display the starting screen
              whenRunning();
      case Tutorial -> whenTutorial();
      default -> {
      }
    }
  }

  private void whenMenu() {
    BorderPane menu = new BorderPane();
    menu.setStyle("-fx-background-color: "+SCENE_COLOR);
    menu.setPrefSize(WIDTH, HEIGHT);
    VBox desc = new VBox();
    desc.setAlignment(Pos.CENTER);
    desc.setStyle("-fx-background-color: "+SCENE_COLOR);
    Label gameRules = new Label();
    Font font = Font.loadFont("file:./resources/fonts/DiloWorld.ttf", 30);
    gameRules.setFont(font);
    gameRules.setText("""
                    Welcome to
                    Nine Men's Morris
                    Press "Start Game" to play a New Game.
                    Press "Tutorial" to learn the game rules and how to play.
                    Press "Load Game" to continue a Previous Game save.
                    """);

    Button startGameButton = new Button("Start Game");
    startGameButton.setOnMouseClicked(event -> {
        Game.state = GameState.Running;
        render();
    });
    Button tutorialButton = new Button("Tutorial");
    tutorialButton.setOnMouseClicked(event -> {
      Game.state = GameState.Tutorial;
      render();
    });

    Button loadGameButton = new Button("Load Game");
    loadGameButton.setOnAction(actionEvent -> {
      try {
        BufferedReader br = new BufferedReader(new FileReader("scr/Save_Files/game_state.txt"));
        if (br.readLine() == null || br.readLine().trim().length() == 0) {
          Label loadErrorMsg = new Label();
          menu.setBottom(loadErrorMsg);
          loadErrorMsg.setStyle(" -fx-background-color: lightpink; -fx-border-color: palevioletred; -fx-border-width: 3px");
          Font msgFont = Font.loadFont("file:./resources/fonts/DiloWorld.ttf", 20);
          loadErrorMsg.setFont(msgFont);
          loadErrorMsg.setText("You have no Previous Game to Load. Click 'Start Game' to create a new game.");
          int LABEL_WIDTH = WIDTH-100;
          int LABEL_HEIGHT = 80;
          loadErrorMsg.setPadding(new Insets(15, 15, 15, 15));
          loadErrorMsg.setPrefSize(LABEL_WIDTH,LABEL_HEIGHT);
          loadErrorMsg.setAlignment(Pos.CENTER);
          loadErrorMsg.setTextAlignment(TextAlignment.CENTER);
          loadErrorMsg.setWrapText(true);
          loadErrorMsg.setTranslateX((WIDTH-LABEL_WIDTH)/2);
          PauseTransition hideLabel = new PauseTransition(Duration.seconds(4));
          hideLabel.setOnFinished(event -> loadErrorMsg.setVisible(false));
          hideLabel.play();
          System.out.println("No Save Slots Available");
        } else {
          GameExporter gameExporter = new GameExporter(true);
          int[][] board= gameExporter.loadGameState();
          game.loadGameBoard(board, gameExporter);
          Game.state = GameState.Loaded;
          render();
        }
      } catch (IOException e) {
        System.out.println("File Does not Exist");
      }
    });
    desc.getChildren().add(gameRules);
    desc.getChildren().addAll(startGameButton, tutorialButton, loadGameButton);
    menu.setCenter(desc);
    scene.setRoot(menu);
  }

  private void whenTutorial() {
    whenRunning();
    // label introducing the game preface
    Label label = new Label();
    boardPane.getChildren().add(label);
    label.setStyle(" -fx-background-color: lightpink; -fx-border-color: palevioletred; -fx-border-width: 3px");
    Font font = Font.loadFont("file:./resources/fonts/OpenDyslexic-Bold.otf", 15);
    label.setFont(font);
    label.setText("Nine Men's Morris is played on a board with 24 positions or intersections where tokens "+
            "may be placed. Both players start with nine tokens each. On the right is player 1 (CAT) tokens "+
            "and the left is player 2 (DOG) tokens. The players' turn is indicated below the board. " +
            "Follow the tutorial instructions to be given at the bottom to learn to play.");
    int LABEL_WIDTH = 450;
    int LABEL_HEIGHT = 300;
    label.setPadding(new Insets(15, 15, 15, 15));
    label.setPrefSize(LABEL_WIDTH,LABEL_HEIGHT);
    label.setAlignment(Pos.CENTER);
    label.setTextAlignment(TextAlignment.CENTER);
    label.setWrapText(true);
    label.setLayoutX((Board.WIDTH-LABEL_WIDTH)/2);
    label.setLayoutY((Board.HEIGHT-LABEL_HEIGHT)/2);
    // create a label to give tutorial messages
    Label message = new Label();
    message.setFont(font);
    message.setTextFill(Color.web("023047"));
    message.setWrapText(true);
    message.setTextAlignment(TextAlignment.CENTER);
    message.setPadding(new Insets(15, 15, 15, 15));
    turnBox.getChildren().add(message);

    Button closeBut = new Button("Begin Tutorial");
    closeBut.setOnAction(event -> {
      label.setVisible(false);
      new TutorialView(message, this);
    });
    label.setGraphic(closeBut);
    label.setContentDisplay(ContentDisplay.BOTTOM);
  }

  private void whenRunning(){
    // reset Groups and players
    positionGroup.getChildren().clear();
    lineGroup.getChildren().clear();

    // board set up
    boardPane = new Pane();
    boardPane.setStyle("-fx-background-color: "+SCENE_COLOR);
    boardPane.setMaxSize(Board.WIDTH, Board.HEIGHT);
    positionGroup.getChildren().addAll(board.getPositions());
    lineGroup.getChildren().addAll(board.getLines());
    boardPane.getChildren().addAll(lineGroup, positionGroup, tokenGroup);
    canvas.setCenter(boardPane);

    //set up turn box
    turnBox = new VBox();
    turnBox.setAlignment(Pos.CENTER);
    turnBox.setPadding(new Insets(15, 15, 15, 15));
    Font font = Font.loadFont("file:./resources/fonts/DiloWorld.ttf", 30);
    turnLabel.setFont(font);
    messageLabel.setFont(font);
    messageLabel.setTextAlignment(TextAlignment.CENTER);
    messageLabel.setText("Choose a position to Place your token");
    turnBox.getChildren().addAll(turnLabel, messageLabel);
    canvas.setBottom(turnBox);

    // set left and right players
    canvas.setLeft(playerViews.get(0).getPlayerBox());
    canvas.setRight(playerViews.get(1).getPlayerBox());

    scene.setRoot(canvas);
  }

  public void createHeader(UndoButton undo, HintButton hint, SaveGameButton save) {
    // set up UndoButton
    HBox controls = new HBox();
    controls.getChildren().addAll(undo, hint, save);
    controls.setAlignment(Pos.TOP_RIGHT);
    controls.setPadding(new Insets(15, 15, 15, 15));
    canvas.setTop(controls);
  }

  public void whenFinished(Player player) {
    Label label;
    if (player == null){
      label = new Label("Game Draw");
    } else {
      label = new Label("Game Over: " + player.getPet() + " wins!");
    }
    label.setStyle(" -fx-background-color: lightpink; -fx-border-color: palevioletred; -fx-border-width: 3px");
    Font font = Font.loadFont("file:./resources/fonts/DiloWorld.ttf", 45); // import custom font
    label.setFont(font);
    int LABEL_WIDTH = 250;
    int LABEL_HEIGHT = 140;
    label.setPrefSize(LABEL_WIDTH,LABEL_HEIGHT);
    label.setAlignment(Pos.CENTER);
    label.setTextAlignment(TextAlignment.CENTER);
    label.setWrapText(true);
    label.setLayoutX((boardPane.getWidth()-LABEL_WIDTH)/2);
    label.setLayoutY((boardPane.getHeight()-LABEL_HEIGHT)/2);
    boardPane.getChildren().add(label);
  }

  public Board getBoard() {
      return board;
    }

  public Stage getStage() {
    return stage;
  }

  public void setPlayers(Player player1, Player player2) {
    playerViews.add(player1.getPlayerView());
    playerViews.add(player2.getPlayerView());
  }

  public void addTokenToGroup(Token token) {
    tokenGroup.getChildren().add(token);
  }
  public void removeTokenFromGroup(Token token){
    tokenGroup.getChildren().remove(token);
  }

  public Position getHighlightedPosition() {
    return highlightedPosition;
  }

  public void setHighlightedPosition(Position nextPosition) {
    if (highlightedPosition != null) {
      highlightedPosition.setEffect(null);
    }
    DropShadow ds = new DropShadow();
    ds.setRadius(25.0);
    ds.setColor(Color.BLACK);
    nextPosition.setEffect(ds);
    highlightedPosition = nextPosition;
  }

  public void setHintHighlightPosition(Position position){
    DropShadow ds = new DropShadow();
    ds.setRadius(20.0);
    ds.setColor(Color.YELLOW);
    position.setEffect(ds);
  }

  public void unhighlightPositions(ArrayList <Position> positions){
    for(Position position  : positions) {
      position.setEffect(null);
    }
  }

  public Token getHighlightedToken() {
    return highlightedToken;
  }

  public void setHighlightedToken(Token nextToken) {
    if (highlightedToken != null) {
      highlightedToken.setEffect(null);
    }
    DropShadow ds = new DropShadow();
    ds.setRadius(25.0);
    ds.setColor(Color.BLACK);
    nextToken.setEffect(ds);
    highlightedToken = nextToken;
  }

  public void setGameMessage(String message){
    messageLabel.setText(message);
  }

  public void playerTurnView(Player player) {
    String animalString = player.getPet() == Pet.CAT ? "Cat" : "Dog";   // determining current player
    turnLabel.setText("Turn:  " + animalString);
  }

  public Pane getBoardPane() {
    return boardPane;
  }
}