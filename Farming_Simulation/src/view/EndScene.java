package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class EndScene implements View {
    private Button restartGame;

    public EndScene() {
        restartGame = new Button("Restart Game");
    }

    public Button getRestartGame() {
        return restartGame;
    }

    @Override
    public Scene getScene() {
        //Create HBox to contain the content
        VBox root = new VBox();
        root.getStyleClass().addAll("hbox", "background");

        //Properties and styling for the button
        restartGame.getStyleClass().add("button-big");

        //Declare and initialize the labels for the welcome screen:
        Label gameName1 = new Label("Game Over! You ran out of money and "
                + " have no growing crops. You can quit or restart!");
        gameName1.getStyleClass().add("end-game-label-title");
        gameName1.setWrapText(true);
        gameName1.setTextAlignment(TextAlignment.CENTER);

        root.getChildren().addAll(restartGame, gameName1);

        return new Scene(root, WIDTH, HEIGHT);
    }
}
