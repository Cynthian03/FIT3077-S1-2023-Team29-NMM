package View;

import Model.Pet;
import Model.Player;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class PlayerView {
    private VBox playerBox;
    private Group tokenGroup = new Group();

    public PlayerView(Player player){
        // player set up
        playerBox = new VBox();
        playerBox.setAlignment(Pos.CENTER);
        playerBox.getChildren().addAll(tokenGroup);
        playerBox.setPadding(new Insets(15, 15, 15, 15));
        playerTokenTracker(player);
    }

    public void playerTokenTracker(Player player){

        int player_token_count = player.getTokensNotPlaced();
        int horizontal = 0;

        tokenGroup.getChildren().clear();

        ArrayList<Circle> updated_tokens = new ArrayList<>();

        for (int i = 0; i < player_token_count; i ++){

            Circle token = new Circle();
            token.setRadius(20);

            Image image1 = new Image("file:./resources/cat_icon.png");
            Image image2 = new Image("file:./resources/dog_icon.png");
            token.setFill(player.getPet() == Pet.CAT ? new ImagePattern(image1) : new ImagePattern(image2));

            token.relocate(0, horizontal);
            horizontal =  horizontal + 50;
            updated_tokens.add(token);
        }

        tokenGroup.getChildren().addAll(updated_tokens);
    }

    public void addCaptured() {
    }

    public VBox getPlayerBox() {
        return playerBox;
    }
}
