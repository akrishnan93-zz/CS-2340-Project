package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * The Welcome scene.
 */
public class InitConfigScene implements View {

    private Button startGame;

    private TextField name;
    private ToggleGroup toggleDiff;
    private ToggleGroup toggleSeed;
    private ToggleGroup toggleSeason;

    private Label nameError;

    public InitConfigScene() {
        startGame = new Button("Start Game");
        name = new TextField();
        toggleDiff = new ToggleGroup();
        toggleSeed = new ToggleGroup();
        toggleSeason = new ToggleGroup();
        nameError = new Label("Please enter a valid name.");
        nameError.setVisible(false);
    }

    public Button getStartGame() {
        return startGame;
    }

    public TextField getName() {
        return name;
    }

    public ToggleGroup getToggleDiff() {
        return toggleDiff;
    }

    public ToggleGroup getToggleSeed() {
        return toggleSeed;
    }

    public ToggleGroup getToggleSeason() {
        return toggleSeason;
    }

    public Label getNameError() {
        return nameError;
    }

    @Override
    public Scene getScene() {

        name.getStyleClass().add("text-field");
        nameError.getStyleClass().add("text-error");
        startGame.getStyleClass().add("button-big");

        //Create BorderPane to contain the content
        BorderPane root = new BorderPane();
        root.getStyleClass().add("background");

        //Creates the structural components for the screen:
        HBox nameBox = new HBox();
        nameBox.getStyleClass().add("hbox");

        Label nameText = new Label("Enter Your Name:");
        nameText.getStyleClass().add("text");
        name.setEditable(true);

        //Add all of the newly created components to the HBox structure
        nameBox.getChildren().addAll(nameText, name);

        GridPane grid = new GridPane();
        grid.getStyleClass().add("grid");

        //Labels for the first section:
        Label diffText = new Label("Choose Your Difficulty:");
        diffText.getStyleClass().add("text");

        //Create radio buttons to choose difficulty
        RadioButton easy = new RadioButton("Easy");
        easy.setUserData("Easy");
        easy.getStyleClass().add("radio-text");
        RadioButton medium = new RadioButton("Medium");
        medium.setUserData("Medium");
        medium.getStyleClass().add("radio-text");
        RadioButton hard = new RadioButton("Hard");
        hard.setUserData("Hard");
        hard.getStyleClass().add("radio-text");


        easy.setToggleGroup(toggleDiff);
        medium.setToggleGroup(toggleDiff);
        hard.setToggleGroup(toggleDiff);
        easy.setSelected(true);

        grid.addRow(0, diffText, easy, medium, hard);

        //Label for the next section:
        Label seedText = new Label("Choose Your Starting Seed:");
        seedText.getStyleClass().add("text");

        //Radio buttons to choose the starting crop
        RadioButton wheat = new RadioButton("Wheat");
        wheat.setUserData("Wheat");
        wheat.getStyleClass().add("radio-text");
        RadioButton carrot = new RadioButton("Carrot");
        carrot.setUserData("Carrot");
        carrot.getStyleClass().add("radio-text");
        RadioButton potato = new RadioButton("Potato");
        potato.setUserData("Potato");
        potato.getStyleClass().add("radio-text");

        wheat.setToggleGroup(toggleSeed);
        carrot.setToggleGroup(toggleSeed);
        potato.setToggleGroup(toggleSeed);
        wheat.setSelected(true);

        grid.addRow(1, seedText, wheat, carrot, potato);

        //Labels for the last section
        Label seasonText = new Label("Choose Your Starting Season:");
        seasonText.getStyleClass().add("text");

        //Radio buttons to choose the initial season
        RadioButton winter = new RadioButton("Winter");
        winter.setUserData("Winter");
        winter.getStyleClass().add("radio-text");
        RadioButton fall = new RadioButton("Fall");
        fall.setUserData("Fall");
        fall.getStyleClass().add("radio-text");
        RadioButton summer = new RadioButton("Summer");
        summer.setUserData("Summer");
        summer.getStyleClass().add("radio-text");
        RadioButton spring = new RadioButton("Spring");
        spring.setUserData("Spring");
        spring.getStyleClass().add("radio-text");

        winter.setToggleGroup(toggleSeason);
        fall.setToggleGroup(toggleSeason);
        summer.setToggleGroup(toggleSeason);
        spring.setToggleGroup(toggleSeason);
        winter.setSelected(true);

        grid.addRow(2, seasonText, winter, fall, summer, spring);

        HBox.setHgrow(name, Priority.ALWAYS);


        VBox items = new VBox();
        items.getStyleClass().add("vbox");
        items.getChildren().addAll(nameBox, grid, startGame, nameError);

        root.setCenter(items);

        return new Scene(root, WIDTH, HEIGHT);
    }

    /**
     * Validation method for the name TextField
     * @return true if the text field passes validation
     */
    public boolean validName() {
        return !(name.getText() == null || name.getText().equals(""));
    }
}
