package Main;

import Controller.Game;
import javafx.application.Application;
import javafx.stage.Stage;

public class NineMensMorris extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Game game = Game.getInstance();
        stage = game.getStage();
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}