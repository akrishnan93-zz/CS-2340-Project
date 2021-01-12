package model;

import java.util.HashMap;

public class Market extends AbstractMarket {
    private Player player;
    private static HashMap<Object, Integer> sellPrices = new HashMap<>();
    private static HashMap<Object, Integer> buyPrices = new HashMap<>();
    private static HashMap<Object, Integer> basePrices = new HashMap<>();

    private final double iNSECTICIDEPRICEDISCOUNT = 0.8;
    private static int farmExpansionPrice = 250;

    public Market(Player player) {
        this.player = player;
        updateBasePrices();
        updateSellPrice();
        updateBuyPrice();
    }

    void updateBasePrices() {
        basePrices.put("WHEAT", 5);
        basePrices.put("POTATO", 4);
        basePrices.put("CARROT", 6);
        basePrices.put("WHEAT SEED", 2);
        basePrices.put("POTATO SEED", 3);
        basePrices.put("CARROT SEED", 5);
        basePrices.put("FERTILIZER", 2);
        basePrices.put("WATER", 1);
        basePrices.put("INSECTICIDE", 6);
        basePrices.put("WORKER1", 25);
        basePrices.put("WORKER2", 50);
        basePrices.put("WORKER3", 100);
        basePrices.put("FARM EXPANSION", farmExpansionPrice);
        basePrices.put("TRACTOR", 55);
        basePrices.put("IRRIGATION", 45);
    }

    public static void increaseFarmExpansionPrice(int amount) {
        farmExpansionPrice += amount;
        buyPrices.remove("FARM EXPANSION");
    }

    public HashMap<Object, Integer>  getBasePrices() {
        return basePrices;
    }

    void updateSellPrice() {
        sellPrices.put("WHEAT", (int) (basePrices.get("WHEAT") * (4 - player.getDifficulty())
                + (Math.random() * 5)));
        sellPrices.put("WHEAT_INSECTICIDE",
                (int) (sellPrices.get("WHEAT") * iNSECTICIDEPRICEDISCOUNT));
        sellPrices.put("POTATO", (int) (basePrices.get("POTATO") * (4 - player.getDifficulty())
                + (Math.random() * 5)));
        sellPrices.put("POTATO_INSECTICIDE",
                (int) (sellPrices.get("POTATO") * iNSECTICIDEPRICEDISCOUNT));
        sellPrices.put("CARROT", (int) (basePrices.get("CARROT") * (4 - player.getDifficulty())
                + (Math.random() * 5)));
        sellPrices.put("CARROT_INSECTICIDE",
                (int) (sellPrices.get("CARROT") * iNSECTICIDEPRICEDISCOUNT));
    }

    void updateBuyPrice() {
        buyPrices.put("WHEAT SEED", (int) ((basePrices.get("WHEAT SEED")
                * (player.getDifficulty()) + (Math.random() * 5))));
        buyPrices.put("POTATO SEED", (int) (basePrices.get("POTATO SEED")
                * (player.getDifficulty()) + (Math.random() * 5)));
        buyPrices.put("CARROT SEED", (int) (basePrices.get("CARROT SEED")
                * (player.getDifficulty()) + (Math.random() * 5)));
        buyPrices.put("FERTILIZER", (int) (basePrices.get("FERTILIZER")
                * (player.getDifficulty()) + (Math.random() * 5)));
        buyPrices.put("WATER", (int) (basePrices.get("WATER")
                * (player.getDifficulty()) + (Math.random() * 5)));
        buyPrices.put("INSECTICIDE", (int) (basePrices.get("INSECTICIDE")
                * (player.getDifficulty()) + (Math.random() * 5)));
        buyPrices.put("WORKER1", (int) (basePrices.get("WORKER1")
                * (player.getDifficulty()) + (Math.random() * 5)));
        buyPrices.put("WORKER2", (int) (basePrices.get("WORKER2")
                * (player.getDifficulty()) + (Math.random() * 5)));
        buyPrices.put("WORKER3", (int) (basePrices.get("WORKER3")
                * (player.getDifficulty()) + (Math.random() * 5)));
        buyPrices.put("FARM EXPANSION", (int) (basePrices.get("FARM EXPANSION")));
        buyPrices.put("TRACTOR", (int) (basePrices.get("TRACTOR")
                * (player.getDifficulty()) + (Math.random() * 5)));
        buyPrices.put("IRRIGATION", (int) (basePrices.get("IRRIGATION")
                * (player.getDifficulty()) + (Math.random() * 5)));
    }

    public HashMap<Object, Integer> getSellPrices() {
        return sellPrices;
    }

    public HashMap<Object, Integer> getBuyPrices() {
        return buyPrices;
    }
}
