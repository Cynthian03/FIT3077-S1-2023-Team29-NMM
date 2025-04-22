package View;

import Controller.Game;
import Controller.GameExporter;
import Model.actions.UndoAction;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class UndoButton extends Rectangle {

    public UndoButton(GameView gameView, GameExporter gameExporter){
        Image icon = new Image("file:./resources/undo_icon.png");
        ImagePattern view = new ImagePattern(icon);
        setWidth(55);
        setHeight(55);
        setArcWidth(10);
        setArcHeight(10);
        setFill(view);

        setOnMouseClicked(event -> {
            DropShadow ds = new DropShadow(25, Color.BLACK);
            setEffect(ds);
            UndoAction undoAction = new UndoAction(gameView, gameExporter);
            undoAction.execute(Game.getInstance().getOpponentPlayer());  // undo opponent player's previous move first
            undoAction.execute(Game.getInstance().getCurrPlayer());           // undo current player's previous move next
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
