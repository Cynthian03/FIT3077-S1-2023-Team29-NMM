package View;

import Controller.GameExporter;
import Model.Board;
import Model.Player;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.util.ArrayList;

public class SaveGameButton extends Rectangle {
    public SaveGameButton(GameView gameView, ArrayList<Player> players){
        javafx.scene.image.Image icon = new Image("file:./resources/save_game.png");
        ImagePattern view = new ImagePattern(icon);
        setWidth(55);
        setHeight(55);
        setArcWidth(10);
        setArcHeight(10);
        setFill(view);

        setOnMouseClicked(event -> {
            DropShadow ds = new DropShadow(25, javafx.scene.paint.Color.BLACK);
            setEffect(ds);
            GameExporter gameExporter = new GameExporter(false);
            gameExporter.exportState(players);
            Label label = new Label();
            gameView.getBoardPane().getChildren().add(label);
            label.setStyle(" -fx-background-color: lightpink; -fx-border-color: palevioletred; -fx-border-width: 3px");
            Font font = Font.loadFont("file:./resources/fonts/DiloWorld.ttf", 20);
            label.setFont(font);
            label.setText("Your Game has been Saved. You may exit the window OR continue your game.");
            int LABEL_WIDTH = 250;
            int LABEL_HEIGHT = 200;
            label.setPadding(new Insets(15, 15, 15, 15));
            label.setPrefSize(LABEL_WIDTH,LABEL_HEIGHT);
            label.setAlignment(Pos.CENTER);
            label.setTextAlignment(TextAlignment.CENTER);
            label.setWrapText(true);
            label.setLayoutX((Board.WIDTH-LABEL_WIDTH)/2);
            label.setLayoutY((Board.HEIGHT-LABEL_HEIGHT)/2);
            PauseTransition hideLabel = new PauseTransition(Duration.seconds(4));
            hideLabel.setOnFinished(e -> label.setVisible(false));
            hideLabel.play();
        });

        setOnMouseEntered(event -> {
            DropShadow ds = new DropShadow(25, Color.BLUE);
            setEffect(ds);
        });

        setOnMouseExited(event -> {
            setEffect(null);
        });
    }
}
