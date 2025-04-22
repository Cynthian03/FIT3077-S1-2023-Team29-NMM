package View;

import Controller.Game;
import Model.actions.HintAction;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class HintButton extends Rectangle {

    public HintButton(GameView gameView){
        Image icon = new Image("file:./resources/hint_icon.png");
        ImagePattern view = new ImagePattern(icon);
        setWidth(55);
        setHeight(55);
        setArcWidth(10);
        setArcHeight(10);
        setFill(view);

        setOnMouseClicked(event -> {
            DropShadow ds = new DropShadow(25, Color.BLACK);
            setEffect(ds);
            HintAction hintAction = new HintAction(gameView);
            hintAction.execute(Game.getInstance().getCurrPlayer());
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
