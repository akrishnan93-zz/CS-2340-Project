package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * The Welcome scene.
 */
public class WelcomeScene implements View {

    private Button startGame;

    public WelcomeScene() {
        startGame = new Button("Start Game");
    }

    public Button getStartGame() {
        return startGame;
    }

    @Override
    public Scene getScene() {
        //Create HBox to contain the content
        HBox root = new HBox();
        root.getStyleClass().addAll("hbox", "background");

        //Properties and styling for the button
        startGame.getStyleClass().add("button-big");

        //Declare and initialize the labels for the welcome screen:
        Label gameName1 = new Label("Digital Farming");
        gameName1.getStyleClass().add("label-title");

        root.getChildren().addAll(startGame, gameName1);

        return new Scene(root, WIDTH, HEIGHT);
    }
}
