package controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import model.Farm;
import model.Plot;
import view.FarmScene;
import view.InitConfigScene;
import view.WelcomeScene;
import view.MarketScene;
import view.EndScene;
import view.WinScene;
import model.Player;
import model.Market;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.ArrayList;


/**
 * Main Controller class for the Digital Farm game
 */
public class Controller extends Application {


    private Stage window;
    private final int width = 1600;
    private final int height = 900;
    private Player player;
    private Farm farm;
    private Market marketInstance;
    private MediaPlayer mediaPlayer;

    /**
     * Starting method for the e = nulntire application
     * @param primaryStage Main stage for JavaFX application
     * @throws Exception Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        primaryStage.setTitle("Digital Farm");

        String musicFile = "resources/music/gameSong.mp3";
        // For example

        Media sound = new Media(new File(musicFile).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(999999999);
        mediaPlayer.setVolume(0.1);
        mediaPlayer.setAutoPlay(true);

        goToWelcomeScene();
    }

    /**
     * Controller method for WelcomeScene
     */
    private void goToWelcomeScene() {
        WelcomeScene welcomeScene = new WelcomeScene();
        Button startButton = welcomeScene.getStartGame();
        startButton.setOnAction(e -> goToInitConfigScene());

        Scene scene = welcomeScene.getScene();
        scene.getStylesheets().add("file:resources/styles/welcome.css");
        window.setScene(scene);
        window.show();
    }

    /**
     * Controller method for InitConfigScene
     */
    private void goToInitConfigScene() {
        InitConfigScene initConfigScene = new InitConfigScene();
        Button startButton = initConfigScene.getStartGame();
        TextField name = initConfigScene.getName();
        ToggleGroup toggleDiff = initConfigScene.getToggleDiff();
        ToggleGroup toggleSeed = initConfigScene.getToggleSeed();
        ToggleGroup toggleSeason = initConfigScene.getToggleSeason();
        Label nameError = initConfigScene.getNameError();

        startButton.setOnAction(e -> {
            //Some basic checks to ensure validity:
            if (!initConfigScene.validName()) {
                nameError.setVisible(true);
            } else {
                String difficulty = (String) toggleDiff.getSelectedToggle().getUserData();
                String startingSeed = (String) toggleSeed.getSelectedToggle().getUserData();
                String startingSeason = (String) toggleSeason.getSelectedToggle().getUserData();

                // Random initialization of the Farm object
                ArrayList<Plot> plotList = new ArrayList<>();
                for (int index = 0; index < 5; index++) {
                    Plot plotToAdd = new Plot(null, index);
                    plotList.add(plotToAdd);
                }

                player = new Player(difficulty, name.getText(), startingSeed
                        + " SEED", startingSeason);
                farm = new Farm(player, plotList);

                marketInstance = new Market(player);
                goToFarmScene();
            }
        });

        Scene scene = initConfigScene.getScene();
        scene.getStylesheets().add("file:resources/styles/initconfig.css");
        window.setScene(scene);
        window.show();
    }

    /**
     * Controller method for FarmScene
     */
    private void goToFarmScene() {
        FarmScene farmScene = new FarmScene(player, farm);
        ListView<String> inventoryItems = farmScene.getInventoryItems();
        Button showMarket = farmScene.getMarketButton();
        showMarket.setOnAction(e -> {
            goToMarketScene();
        });
        Button nextDay = farmScene.getNextDay();
        Label eventLabel = farmScene.getEventLabel();
        nextDay.setOnAction(e -> {
            String event  = farm.matureFarm();
            if (player.getBalance() >= 5000) {
                goToWinScene();
                return;
            }
            boolean emptyFarm = true; //check if farm is empty
            for (int i = farm.getPlotList().size() - 1; i >= 0; i--) {
                if (farm.getPlotList().get(i).getHasSeedPlantedInIt()) {
                    emptyFarm = false;
                }
            }
            if (!emptyFarm) {
                boolean allDead = true; //check if all plots are dead
                for (int i = farm.getPlotList().size() - 1; i >= 0; i--) {
                    if (farm.getPlotList().get(i).getHasSeedPlantedInIt()
                            && !farm.getPlotList().get(i).getIsDead()) {
                        allDead = false;
                    }
                }
                if (allDead && player.getBalance() <= 0) {
                    goToEndScene();
                    return;
                }
            }
            if (event.length() != 0) {
                eventLabel.setText("Latest News: " + event);
            } else {
                eventLabel.setText("");
            }
            Scene scene = farmScene.getScene();
            scene.getStylesheets().add("file:resources/styles/farm.css");
            window.setScene(scene);
            window.show();
        });
        Button harvest = farmScene.getHarvestButton();
        ToggleGroup plotSelect = farmScene.getPlotSelect();
        harvest.setOnAction(e -> {
            try {
                if (player.getInventory().containsKey("TRACTOR")) {
                    player.setMaxHarvestPerDay(6);
                }
                if (player.getCurrentNumHarvests() < player.getMaxHarvestPerDay()) {
                    int plotIndex = farm.getPlotList()
                            .indexOf((Plot) plotSelect.getSelectedToggle().getUserData());
                    int gainedExp = farm.harvest(plotIndex);
                    farmScene.harvestError(gainedExp == 0);
                    player.addExp(gainedExp);
                    Scene scene = farmScene.getScene();
                    scene.getStylesheets().add("file:resources/styles/farm.css");
                    window.setScene(scene);
                    window.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Exceeded the max # of times you can harvest for today.");
                    alert.show();
                }
            } catch (Exception exception) {
            }
        });
        Button water = farmScene.getWaterButton();
        water.setOnAction(e -> {
            try {
                if (player.getInventory().containsKey("IRRIGATION")) {
                    player.setMaxWaterPerDay(10);
                }
                if (player.getCurrentNumWaters() < player.getMaxWaterPerDay()) {
                    int plotIndex = farm.getPlotList()
                            .indexOf((Plot) plotSelect.getSelectedToggle().getUserData());
                    farm.water(plotIndex);
                    Scene scene = farmScene.getScene();
                    scene.getStylesheets().add("file:resources/styles/farm.css");
                    window.setScene(scene);
                    window.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Exceeded the max # of times you can water for today.");
                    alert.show();
                }
            } catch (Exception exception) {
            }
        });
        Button fertilize = farmScene.getFertilizeButton();
        fertilize.setOnAction(e -> {
            try {
                int plotIndex = farm.getPlotList()
                        .indexOf((Plot) plotSelect.getSelectedToggle().getUserData());
                farm.fertilize(plotIndex);
                Scene scene = farmScene.getScene();
                scene.getStylesheets().add("file:resources/styles/farm.css");
                window.setScene(scene);
                window.show();
            } catch (Exception exception) {
            }
        });
        Button insecticide = farmScene.getInsecticideButton();
        insecticide.setOnAction(e -> {
            try {
                int plotIndex = farm.getPlotList()
                        .indexOf(plotSelect.getSelectedToggle().getUserData());
                farm.applyInsecticide(plotIndex);
                Scene scene = farmScene.getScene();
                scene.getStylesheets().add("file:resources/styles/farm.css");
                window.setScene(scene);
                window.show();
            } catch (Exception exception) {
            }
        });
        Button deadPlants = farmScene.getDeadPlants();
        deadPlants.setOnAction(e -> {
            farm.removeDead();
            Scene scene = farmScene.getScene();
            scene.getStylesheets().add("file:resources/styles/farm.css");
            window.setScene(scene);
            window.show();
        });
        Button plantSeeds = farmScene.getPlantSeeds();
        plantSeeds.setOnAction(e -> {
            int inventoryItemIndex = inventoryItems.getSelectionModel().getSelectedIndex();
            farm.plantSeeds(inventoryItemIndex);
            Scene scene = farmScene.getScene();
            scene.getStylesheets().add("file:resources/styles/farm.css");
            window.setScene(scene);
            window.show();
        });
        Button beginMenialWork = farmScene.getBeginMenialWork();
        beginMenialWork.setOnAction(e -> {
            farm.menialWork();
            Scene scene = farmScene.getScene();
            scene.getStylesheets().add("file:resources/styles/farm.css");
            window.setScene(scene);
            window.show();
        });
        Scene scene = farmScene.getScene();
        scene.getStylesheets().add("file:resources/styles/farm.css");
        window.setScene(scene);
        window.show();
    }

    /**
     * * Controller method for MarketScene
     * */
    private void goToMarketScene() {
        MarketScene marketScene = new MarketScene(player, marketInstance);
        Button sellButton = marketScene.getSellButton();
        Button buyButton = marketScene.getBuyButton();
        Label warningLabel = marketScene.getWarningLabel();
        ListView<String> currentInventory = marketScene.getCurrentInventory();
        ListView<String> onSaleView = marketScene.getOnSaleView();

        Button goBackToFarm = marketScene.getBackToFarmButton();
        goBackToFarm.setOnAction(e -> {
            goToFarmScene();
        });

        buyButton.setOnAction(e -> {
            if (onSaleView.getSelectionModel().getSelectedItem() != null) {
                marketScene.buyTransaction();
                goToMarketScene();
            } else {
                warningLabel.setVisible(true);
            }
        });

        sellButton.setOnAction(e -> {
            if (currentInventory.getSelectionModel().getSelectedItem() != null) {
                marketScene.sellTransaction();
                goToMarketScene();
            } else {
                warningLabel.setVisible(true);
            }
        });
        
        Scene scene = marketScene.getScene();
        scene.getStylesheets().add("file:resources/styles/market.css");
        window.setScene(scene);
        window.show();
    }

    private void goToEndScene() {
        EndScene endScene = new EndScene();
        Button restartButton = endScene.getRestartGame();
        restartButton.setOnAction(e -> goToInitConfigScene());

        Scene scene = endScene.getScene();
        scene.getStylesheets().add("file:resources/styles/welcome.css");
        window.setScene(scene);
        window.show();
    }

    private void goToWinScene() {
        WinScene winScene = new WinScene();
        Button restartButton = winScene.getRestartGame();
        restartButton.setOnAction(e -> goToInitConfigScene());

        Scene scene = winScene.getScene();
        scene.getStylesheets().add("file:resources/styles/welcome.css");
        window.setScene(scene);
        window.show();
    }

    /**
     * The entry point of application
     * @param args the input arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
