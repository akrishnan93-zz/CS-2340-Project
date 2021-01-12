package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Crop;
import model.Market;
import model.Player;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Welcome scene.
 */
public class MarketScene implements View {

    private Button backToFarm;
    private Player player;
    private Market shop;

    private Button buyButton;
    private Button sellButton;
    private Label warningLabel;
    private ListView<String> currentInventory;
    private TextField quantityField;
    private ListView<String> onSaleView;
    private TextField amountField;

    public MarketScene(Player player, Market market) {
        backToFarm = new Button("Go Back to My Farm");
        this.player = player;
        this.shop = market;

        buyButton = new Button("Buy");
        sellButton = new Button("Sell");
        warningLabel = new Label("No item is picked! Try again!");

        currentInventory = new ListView<String>();
        quantityField = new TextField();

        onSaleView = new ListView<String>();
        amountField = new TextField();

    }

    public Button getBackToFarmButton() {
        return backToFarm;
    }

    public Button getBuyButton() {
        return buyButton;
    }

    public Button getSellButton() {
        return sellButton;
    }

    public Label getWarningLabel() {
        return warningLabel;
    }

    public ListView<String> getCurrentInventory() {
        return currentInventory;
    }

    public ListView<String> getOnSaleView() {
        return onSaleView;
    }

    @Override
    public Scene getScene() {

        backToFarm.getStyleClass().add("button-big");
        backToFarm.setStyle("-fx-font-size: 30px");
        warningLabel.getStyleClass().add("text-warning");

        //Create HBox to contain the
        BorderPane root = new BorderPane();
        root.getStyleClass().add("background");

        //Create the labels for user details on top of the screen
        Label balance = new Label("Balance: \t\t\t$"
                + player.getBalance()); // Label for starting money
        Label date = new Label("Day: \t\t\t\t" + player.getDay()); // Label for date

        backToFarm.setPrefWidth(WIDTH);
        backToFarm.setPrefHeight(75);
        backToFarm.setAlignment(Pos.BASELINE_CENTER);

        onSaleView.setPrefWidth(400);
        onSaleView.setPrefHeight(550);
        currentInventory.setPrefWidth(400);
        currentInventory.setPrefHeight(550);
        Label currCap = new Label("Inventory Capacity: \t" + player.getCurrentCapacity()
                + "/" + player.getMaxCapacity());

        //Grouping nodes for the above elements
        HBox header = new HBox();
        VBox stats = new VBox();
        stats.getChildren().addAll(balance, date, currCap);
        stats.setSpacing(10);

        HBox.setMargin(stats, new Insets(50, 0, 0, 50));

        //Preferences for the warning label
        warningLabel.setVisible(false);

        //VBox for the player inventory list view
        VBox playerInventory = new VBox();
        Label inventoryLabel = new Label("Current Player Inventory: (ITEM : AMOUNT)");

        //VBox for the current prices list view
        VBox currentPrices = new VBox();
        Label currentLabel = new Label("Current Market Prices: (ITEM : PRICE)");

        //VBox for the market list view
        VBox marketView = new VBox();
        Label marketLabel = new Label("Current Items for Sale: (ITEM : PRICE)");
        VBox.setMargin(marketView, new Insets(0, 0, 0, 100));
        VBox.setMargin(currentPrices, new Insets(0, 0, 0, 100));


        //Create a list view for all of the user's current inventory
        ObservableList<String> items = FXCollections.observableArrayList();

        items.clear();
        for (Map.Entry<Object, Integer> set : player.getInventory().entrySet()) {
            items.add("" + set.getKey() + " : " + set.getValue());
        }
        //Create a list view for all of the market's prices
        ListView<String> marketItems = new ListView<String>();
        ObservableList<String> itemsForMarket = FXCollections.observableArrayList();

        //Add all the entries to the list view from the selling prices hashmap
        for (Map.Entry<Object, Integer> set : shop.getSellPrices().entrySet()) {
            itemsForMarket.add("" + set.getKey() + ": $" + set.getValue());
        }

        //Setting up the HBox for selling items
        HBox sellGroup = new HBox();
        quantityField.setPromptText("SELL QUANTITY");
        quantityField.setPrefColumnCount(10);
        quantityField.setPrefHeight(50);
        sellGroup.getChildren().addAll(quantityField, sellButton);
        sellGroup.setAlignment(Pos.TOP_LEFT);


        //Create a list view for all of the market's sell list
        ObservableList<String> onSale = FXCollections.observableArrayList();

        //Populate the list view for all of the sale prices on the buy side
        for (Map.Entry<Object, Integer> set : shop.getBuyPrices().entrySet()) {
            onSale.add("" + set.getKey() + " : $" + set.getValue());
        }

        //Setting up the HBox for buying items
        HBox buyGroup = new HBox();
        amountField.setPromptText("BUY QUANTITY");
        amountField.setPrefColumnCount(10);
        amountField.setPrefHeight(50);

        buyGroup.getChildren().addAll(amountField, buyButton);

        //Preferences for the inventory list view
        currentInventory.setItems(items);
        currentInventory.setPrefWidth(300);
        currentInventory.setPrefHeight(500);

        //Preferences for the current market prices list view
        marketItems.setItems(itemsForMarket);
        marketItems.setPrefWidth(300);
        marketItems.setPrefHeight(500);
        marketItems.setMouseTransparent(true);
        marketItems.setFocusTraversable(false);

        //Preferences for the market-buy list view
        onSaleView.setItems(onSale);
        onSaleView.setPrefWidth(300);
        onSaleView.setPrefHeight(500);

        playerInventory.getChildren().addAll(inventoryLabel, currentInventory);
        currentPrices.getChildren().addAll(currentLabel, marketItems, sellGroup);
        marketView.getChildren().addAll(marketLabel, onSaleView, buyGroup);

        HBox sellHalf = new HBox();
        sellHalf.getChildren().addAll(playerInventory, currentPrices);
        sellHalf.setSpacing(50);
        HBox.setMargin(playerInventory, new Insets(25, 0, 0, 50));
        HBox.setMargin(currentPrices, new Insets(25, 0, 0, 0));


        HBox buyHalf = new HBox();
        buyHalf.getChildren().addAll(marketView);
        HBox.setMargin(marketView, new Insets(25, 50, 0, 0));


        header.setSpacing(600);
        header.getChildren().add(stats);
        header.setPrefSize(200, 200);
        root.setLeft(sellHalf);
        root.setRight(buyHalf);
        root.setTop(header);
        root.setCenter(warningLabel);
        root.setBottom(backToFarm);

        return new Scene(root, WIDTH, HEIGHT);
    }

    public void sellTransaction() {
        String item = currentInventory.getSelectionModel().getSelectedItem().split(" : ")[0];
        String quantity = quantityField.getText();

        if (!checkQuantity(quantity)) {
            warningLabel.setText("Quantity is invalid! Try again!");
            warningLabel.setVisible(true);
            return;
        }
        try {
            Object itemSold = Crop.valueOf(item.toUpperCase());
            int amountSold = Integer.parseInt(quantity);
            int priceSold = shop.getSellPrices().get((item));

            if (player.getInventory().get(itemSold) >= amountSold) {
                player.setBalance(player.getBalance() + amountSold * priceSold);
                player.setInventorySell(item, amountSold, null);
            } else {
                warningLabel.setText("Insufficient inventory!");
                warningLabel.setVisible(true);
                return;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            warningLabel.setText("Cannot sell that!");
            warningLabel.setVisible(true);
            return;
        }
    }

    public void buyTransaction() {
        String item = onSaleView.getSelectionModel().getSelectedItem().split(" : ")[0];
        String quantity = amountField.getText();
        if (!checkQuantity(quantity)) {
            warningLabel.setVisible(true);
            return;
        }
        try {
            Object itemBought = item.toUpperCase();
            int amountBought = Integer.parseInt(quantity);
            int priceBought = shop.getBuyPrices().get(itemBought);

            if (priceBought * amountBought <= player.getBalance()) {
                if (!((itemBought.equals("TRACTOR") || itemBought.equals("IRRIGATION"))
                        && player.getInventory().containsKey(itemBought))) {
                    if (itemBought.equals("FARM EXPANSION")) {
                        amountBought = 1;
                        Market.increaseFarmExpansionPrice(200);
                    }
                    if (player.setInventoryBuy(item, amountBought)) {
                        player.setBalance(player.getBalance() - amountBought * priceBought);
                    } else {
                        warningLabel.setText("Insufficient inventory space!");
                        warningLabel.setVisible(true);
                        return;
                    }
                }
            } else {
                warningLabel.setText("Insufficient funds!");
                warningLabel.setVisible(true);
                return;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            warningLabel.setText("Error");
            warningLabel.setVisible(true);
            return;
        }

    }

    private boolean checkQuantity(String quantity) {
        String regularExpression = "[0-9]*[0-9]+$";
        Pattern pattern = Pattern.compile(regularExpression);
        Matcher matcher = pattern.matcher(quantity);
        return matcher.matches();
    }
}
