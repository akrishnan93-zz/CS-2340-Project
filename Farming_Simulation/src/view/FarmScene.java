package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import model.Farm;
import model.Player;
import model.Plot;
import view.component.PlotComponent;


/**
 * The Welcome scene.
 */
public class FarmScene implements View {

    private Button showMarket;
    private Button nextDay;
    private Button harvest;
    private Button water;
    private Button deadPlants;
    private Button plantSeeds;
    private Button beginMenialWork;


    private Button fertilize;
    private Button insecticide;

    private ToggleGroup plotSelect;
    private ListView<String> inventoryItems;
    private Label warningLabel;
    private Label eventLabel;

    private Player player;
    private Farm farm;

    private static final int MAXIMUM_ITEMS_IN_INVENTORY = 30;

    public FarmScene(Player player, Farm farm) {
        this.player = player;
        this.farm = farm;
        showMarket = new Button("Go to the Market");
        nextDay = new Button("Next Day");
        harvest = new Button("Harvest Selected");
        water = new Button("Water Selected");
        fertilize = new Button("Fertilize Selected");
        insecticide = new Button("Apply Insecticide");
        deadPlants = new Button("Remove Dead Plants");
        beginMenialWork = new Button("Begin Menial Work");
        plantSeeds = new Button("Plant Seeds");
        inventoryItems = new ListView<String>();
        warningLabel = new Label("Can't harvest immature crops!");
        warningLabel.setVisible(false);
        eventLabel = new Label("");
        plotSelect = new ToggleGroup();
    }

    public Button getMarketButton() {
        return showMarket;
    }

    public Button getHarvestButton() {
        return harvest;
    }

    public ToggleGroup getPlotSelect() {
        return plotSelect;
    }

    public Button getWaterButton() {
        return water;
    }

    public Button getFertilizeButton() {
        return fertilize;
    }

    public Button getInsecticideButton() {
        return insecticide;
    }

    public Button getNextDay() {
        return nextDay;
    }

    public Button getDeadPlants() {
        return deadPlants;
    }

    public Button getBeginMenialWork() {
        return beginMenialWork; }

    public Button getPlantSeeds() {
        return plantSeeds; }


    public ListView<String> getInventoryItems() {
        return inventoryItems;
    }

    public Label getEventLabel() {
        return eventLabel;
    }

    @Override
    public Scene getScene() {

        showMarket.getStyleClass().add("button-big");
        nextDay.getStyleClass().add("button-big");

        harvest.getStyleClass().add("button-small");
        harvest.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        water.getStyleClass().add("button-small");
        water.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        fertilize.getStyleClass().add("button-small");
        fertilize.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        insecticide.getStyleClass().add("button-small");
        insecticide.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        deadPlants.getStyleClass().add("button-small");
        deadPlants.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        beginMenialWork.getStyleClass().add("button-small");
        beginMenialWork.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        plantSeeds.getStyleClass().add("button-small");
        plantSeeds.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);

        warningLabel.getStyleClass().add("text-warning");
        eventLabel.getStyleClass().add("text-warning");

        //Create HBox to contain the content
        BorderPane root = new BorderPane();
        root.getStyleClass().add("background");

        //Create the labels for user details
        Label balance = new Label("Balance: \t\t\t$"
                + player.getBalance()); // Label for starting money
        Label date = new Label("Day: \t\t\t\t" + player.getDay()); // Label for date

        showMarket.setPrefWidth(WIDTH);
        showMarket.setPrefHeight(75);
        showMarket.setAlignment(Pos.BASELINE_CENTER);

        eventLabel.setMaxWidth(400);
        eventLabel.setWrapText(true);

        Label currCap = new Label("Inventory Capacity: \t" + player.getCurrentCapacity()
                + "/" + player.getMaxCapacity());

        HBox header = new HBox();
        VBox stats = new VBox();
        stats.getChildren().addAll(balance, date, currCap, eventLabel);
        stats.setSpacing(10);

        TilePane farmPlots = new TilePane();
        farmPlots.getStyleClass().add("tile-container");
        for (Plot plot: farm.getPlotList()) {
            if (plot.getHasSeedPlantedInIt()) {
                PlotComponent component = new PlotComponent(plot);
                component.getSelectButton().setToggleGroup(plotSelect);
                farmPlots.getChildren().add(component.getComponent());
            }
        }

        if (player.getInventory().containsKey("FARM EXPANSION")) {
            int farmExpansionCount = player.getInventory().get("FARM EXPANSION");
            for (int i = farmExpansionCount; i > 0; i--) {
                farm.addPlot(new Plot(null, i));
            }

            player.getInventory().put("FARM EXPANSION", 0);
        }
        // Create a list view for all of the user's current inventory
        ObservableList<String> currentInventory =
                FXCollections.observableList(player.getInventoryAsString());
        inventoryItems.setItems(currentInventory);
        inventoryItems.setPrefWidth(400);
        inventoryItems.setPrefHeight(260);

        // Create boxes for the plot and inventory with their respective labels
        Label inventoryLabel = new Label("Player Inventory");
        Label plotLabel = new Label(player.getName() + "'s Farm");
        Label farmCapacityLabel = new Label("Capacity: " + farm.getNumFilledPlots()
                + "/" + farm.getPlotList().size());
        HBox plotLabelsBox = new HBox();
        plotLabelsBox.getChildren().addAll(plotLabel, farmCapacityLabel, warningLabel);
        plotLabelsBox.setSpacing(150);
        VBox plotBox = new VBox();
        plotBox.getStyleClass().add("vbox");
        VBox inventoryBox = new VBox();
        plotBox.getStyleClass().add("vbox");

        HBox farmButtonBox = new HBox(harvest, water, fertilize,
                insecticide, deadPlants, beginMenialWork);
        farmButtonBox.getStyleClass().add("hbox");
        plotBox.getChildren().addAll(plotLabelsBox, farmPlots);
        inventoryBox.getChildren().addAll(inventoryLabel, inventoryItems, plantSeeds);

        VBox bottomContent = new VBox(10);
        bottomContent.getChildren().addAll(farmButtonBox, showMarket);
        bottomContent.setSpacing(50);

        HBox content = new HBox(10);
        HBox.setMargin(plotBox, new Insets(25, 0, 0, 50));
        content.getChildren().addAll(plotBox);
        HBox inventoryHBox = new HBox(10);
        HBox.setMargin(inventoryBox, new Insets(100, 100, 25, 0));
        inventoryHBox.getChildren().addAll(inventoryBox);

        header.setSpacing(500);
        header.setAlignment(Pos.CENTER);
        header.getChildren().addAll(stats, nextDay);
        header.setPrefWidth(1400);
        HBox.setMargin(stats, new Insets(50, 0, 0, 0));
        HBox.setMargin(nextDay, new Insets(50, 0, 0, 0));

        root.setTop(header);
        root.setRight(inventoryHBox);
        root.setLeft(content);
        root.setBottom(bottomContent);

        return new Scene(root, WIDTH, HEIGHT);
    }

    public void harvestError(boolean errorStatus) {
        warningLabel.setVisible(errorStatus);
    }
}