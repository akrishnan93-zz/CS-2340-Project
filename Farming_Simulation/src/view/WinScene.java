package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class WinScene implements View {
    private Button winGame;

    public WinScene() {
        winGame = new Button("Restart Game");
    }

    public Button getRestartGame() {
        return winGame;
    }

    @Override
    public Scene getScene() {
        //Create HBox to contain the content
        VBox root = new VBox();
        root.getStyleClass().addAll("hbox", "background");

        //Properties and styling for the button
        winGame.getStyleClass().add("button-big");

        //Declare and initialize the labels for the welcome screen:
        Label gameName1 = new Label("Congrats, you won! Try some "
                + "new settings and see if you can win again.");
        gameName1.getStyleClass().add("label-title");
        gameName1.getStyleClass().add("end-game-label-title");
        gameName1.setWrapText(true);
        gameName1.setTextAlignment(TextAlignment.CENTER);

        root.getChildren().addAll(winGame, gameName1);

        return new Scene(root, WIDTH, HEIGHT);
    }
}