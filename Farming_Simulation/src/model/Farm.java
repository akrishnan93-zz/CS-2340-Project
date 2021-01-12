package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Farm {

    private Player player;
    private ArrayList<Plot> plotList;
    private boolean workDoneForDay = false;

    private double[] eventProb;

    public Farm(Player player) {
        this.player = player;
        plotList = new ArrayList<>();
    }


    public Farm(Player player, ArrayList<Plot> plotList) {
        this.player = player;
        this.plotList = plotList;

        // event probabilities when day advances. [rain, drought, locusts]
        eventProb = new double[3];

        switch (player.getDifficulty()) {
        case 1:  // difficulty = easy
            eventProb[0] = 0.075;
            eventProb[1] = 0.05;
            eventProb[2] = 0.05;
            break;

        case 2: // difficulty = medium
            eventProb[0] = 0.15;
            eventProb[1] = 0.05;
            eventProb[2] = 0.05;
            break;

        case 3:  // difficulty = hard
            eventProb[0] = 0.2;
            eventProb[1] = 0.1;
            eventProb[2] = 0.1;
            break;

        default:
            eventProb[0] = 0.1;
            eventProb[1] = 0.05;
            eventProb[2] = 0.05;

        }

        switch (player.getSeason()) {
        case "Winter":
            eventProb[0] *= 1.5;
            eventProb[1] *= 0.5;
            eventProb[2] *= 0.25;
            break;
        case "Fall":
            eventProb[0] *= 1.5;
            eventProb[1] *= 1.5;
            break;
        case "Summer":
            eventProb[1] *= 1.5;
            break;
        case "Spring":
            eventProb[0] *= 1.5;
            eventProb[1] *= 0.75;
            eventProb[2] *= 0.5;
            break;
        default:
            break;
        }
    }

    public String getRandomEvent() {
        boolean isRain = Math.random() <= eventProb[0];
        boolean isDrought = Math.random() <= eventProb[1];
        boolean isLocusts = Math.random() <= eventProb[2];

        if (isRain) {
            int incAmount =  ((int) (Math.random() * 3) + 1);
            for (Plot plot: plotList) {
                plot.water(incAmount);
            }
            return "Overnight Showers increased your plot water levels by " + incAmount + ".";
        } else if (isDrought) {
            int decAmount =  -1 * ((int) (Math.random() * 3) + 1);
            for (Plot plot: plotList) {
                plot.water(decAmount);
            }
            return "A drought occurred and decreased all plot water levels by " + -decAmount + ".";
        } else if (isLocusts) {
            double killProb = 0.2 * player.getDifficulty();
            int killCount = 0;
            for (Plot plot: plotList) {
                if (Math.random() > killProb && plot.getHasSeedPlantedInIt()
                        && !plot.getHasInsecticide()) {
                    plot.dead();
                    killCount++;
                }
            }
            return killCount > 0
                    ? "Locusts attacked your farm and killed " + killCount + " crops."
                    : "Locusts attacked your farm and didn't kill any crops.";
        }
        return "";
    }

    public ArrayList<Plot> getPlotList() {
        return plotList;
    }

    public void setWorkDoneForDay(boolean val) {
        workDoneForDay = val;
    }

    public ArrayList<String> getPlotListAsString() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < plotList.size(); i++) {
            Plot plot = plotList.get(i);
            list.add(plot.toString());
        }
        return list;
    }

    public int getNumFilledPlots() {
        int count = 0;
        for (Plot plot: plotList) {
            if (plot.getCrop() != null) {
                count++;
            }
        }
        return count;
    }

    public void addPlot(Plot plot) {
        plotList.add(plot);
    }

    public void removePlot(int plotIndex) {
        plotList.set(plotIndex, new Plot(null, plotList.get(plotIndex).getPlotId()));
        plotList.get(plotIndex).setHasSeedPlantedInIt(false);
        plotList.get(plotIndex).setHasInsecticide(false);
    }

    public String matureFarm() {
        player.nextDay();
        payWorker();
        setWorkDoneForDay(false);

        for (Plot plot: plotList) {
            if ((plot.getFertilizer() == 4 || plot.getFertilizer() == 3)
                    && plot.getCurrMaturity() < 3) {
                plot.mature(2);
            } else {
                plot.mature();
            }
        }
        return getRandomEvent();
    }

    public void removeDead() {
        for (int i = plotList.size() - 1; i >= 0; i--) {
            if (plotList.get(i).getIsDead()) {
                removePlot(i);
            }
        }
    }

    public int harvest(int plotIndex) {
        int gainedExp;
        Plot plot = plotList.get(plotIndex);
        if (plot.isMatured()) {
            gainedExp = (player.getDifficulty() + plot.getCurrMaturity()) * 5;
            if (plot.getHasInsecticide()) {
                String cropNameWithInsecticide = plot.getCrop().getName() + "_INSECTICIDE";
                plot.setCrop(Crop.valueOf(cropNameWithInsecticide));
            }
            removePlot(plotIndex);
            int fertilizer = plot.getFertilizer();
            if (fertilizer == 0) {
                player.setInventoryBuy(plot.getCrop().getName(), 1);
            }
            double rand = Math.random();
            int quantity = 0;
            if (fertilizer == 1) {
                if (rand <= 0.1) {
                    quantity = 2;
                } else {
                    quantity = 1;
                }
            } else if (fertilizer == 2) {
                if (rand <= 0.2) {
                    quantity = 2;
                } else {
                    quantity = 1;
                }
            } else if (fertilizer == 3) {
                if (rand < 0.5) {
                    quantity = 2;
                } else {
                    quantity = 1;
                }
            } else if (fertilizer == 4) {
                if (rand < 0.8) {
                    quantity = 3;
                } else {
                    quantity = 2;
                }
            }
            if (player.getCurrentCapacity() > 28 && quantity >= 2) {
                player.setInventoryBuy(plot.getCrop().getName(), 1);
            } else if (player.getCurrentCapacity()  == 28 && quantity == 3) {
                player.setInventoryBuy(plot.getCrop().getName(), 2);
            } else {
                player.setInventoryBuy(plot.getCrop().getName(), quantity);
            }
        } else {
            gainedExp = 0;
        }
        player.setCurrentNumHarvests(player.getCurrentNumHarvests() + 1);
        return gainedExp;
    }

    public void water(int plotIndex) {
        Plot plot = plotList.get(plotIndex);
        HashMap<Object, Integer> currentInventory = player.getInventory();
        if (currentInventory.get("WATER") == null) {
            return;
        }

        plot.water();

        if (currentInventory.get("WATER") == 1) {
            currentInventory.remove("WATER");
        } else {
            currentInventory.put("WATER", currentInventory.get("WATER") - 1);
        }
        player.setCurrentNumWaters(player.getCurrentNumWaters() + 1);
        player.removeCurrentCapacity(1);
    }

    public void fertilize(int plotIndex) {
        Plot plot = plotList.get(plotIndex);
        HashMap<Object, Integer> currentInventory = player.getInventory();
        if (currentInventory.get("FERTILIZER") == null) {
            return;
        }
        plot.addFertilizer();
        if (currentInventory.get("FERTILIZER") == 1) {
            currentInventory.remove("FERTILIZER");
        } else {
            currentInventory.put("FERTILIZER", currentInventory.get("FERTILIZER") - 1);
        }

        player.removeCurrentCapacity(1);
    }

    public void applyInsecticide(int plotIndex) {
        Plot plot = plotList.get(plotIndex);
        HashMap<Object, Integer> currentInventory = player.getInventory();
        if (!plot.getHasInsecticide()) {
            if (currentInventory.containsKey("INSECTICIDE")) {
                if (currentInventory.get("INSECTICIDE") == 1) {
                    currentInventory.remove("INSECTICIDE");
                } else {
                    currentInventory.put("INSECTICIDE", currentInventory.get("INSECTICIDE") - 1);
                }
                player.removeCurrentCapacity(1);
                plot.setHasInsecticide(true);
            }
        }
    }

    public void plantSeeds(int inventoryItemIndex) {
        HashMap<Object, Integer> inventory = player.getInventory();
        ArrayList<Object> inventoryKeys = new ArrayList<>(inventory.keySet());
        if (inventoryItemIndex >= 0) {
            String selectedInventoryItem = player.getInventoryAsString().get(inventoryItemIndex);
            if (selectedInventoryItem.contains("SEED")) {
                String cropName = selectedInventoryItem.substring(0,
                        selectedInventoryItem.indexOf(" SEED"));
                for (int index = 0; index < plotList.size(); index++) {
                    if (!plotList.get(index).getHasSeedPlantedInIt()) {
                        plotList.set(index, new Plot(Crop.valueOf(cropName),
                                plotList.get(index).getPlotId()));
                        plotList.get(index).setHasSeedPlantedInIt(true);
                        plotList.get(index).setHasInsecticide(false);
                        int currentNumSeeds = inventory.getOrDefault(inventoryKeys.get(
                                inventoryItemIndex), 0);
                        player.removeCurrentCapacity(1);
                        if (currentNumSeeds <= 0) {
                            inventory.remove(plotList.get(index).getCrop().getName());
                        } else {
                            inventory.put(plotList.get(index).getCrop().getName()
                                    + " SEED", currentNumSeeds - 1);
                        }
                        break;
                    }
                }
            }
        }
    }

    //Waters all of the plots to tip top shape
    //Checks to see if any of the plots have matured plants to harvest
    public void menialWork() {
        HashMap<Object, Integer> inventory = player.getInventory();

        if (workDoneForDay) {
            return;
        }

        if (!inventory.containsKey("WORKER1")
                && !inventory.containsKey("WORKER2")
                && !inventory.containsKey("WORKER3")) {
            return;
        }

        workDoneForDay = true;

        int randomness = 0;

        if (inventory.containsKey("WORKER3")) {
            randomness = 3;
        } else if (inventory.containsKey("WORKER2")) {
            randomness = 2;
        } else {
            randomness = 1;
        }

        for (int i = 0; i < plotList.size(); i++) {
            if (Math.random() * (3 - randomness) > .6) {
                continue;
            }
            Plot currentPlot = plotList.get(i);

            if (currentPlot == null || currentPlot.toString().equals("[EMPTY PLOT]")) {
                break;
            }

            if (currentPlot.isMatured()) {
                harvest(i);
            }
        }
        Market market = new Market(player);

        Iterator it = inventory.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (Math.random() * (3 - randomness) > .6) {
                continue;
            }
            if (player.setInventorySell("" + entry.getKey(),
                    (Integer) entry.getValue(), it)) {
                player.setBalance(player.getBalance()
                        + (Integer) entry.getValue()
                        * market.getSellPrices().get("" + entry.getKey()));
            }
        }

        while (inventory.getOrDefault("WATER", 0) > 0) {
            boolean isChanged = false;
            if (Math.random() * (3 - randomness) > .9) {
                break;
            }
            for (int i = 0; i < plotList.size(); i++) {
                Plot currentPlot = plotList.get(i);

                if (!currentPlot.toString().equals("[EMPTY PLOT]")
                        && currentPlot.getWaterLevel() < currentPlot.getMaxWaterlevel() - 1
                        && !currentPlot.getIsDead()) {
                    water(i);
                    isChanged = true;
                }

                if (inventory.getOrDefault("WATER", 0) == 0) {
                    break;
                }
            }

            if (!isChanged) {
                break;
            }
        }
    }

    public void payWorker() {
        Market market = new Market(player);
        int price = 0;
        if (player.getInventory().containsKey("WORKER3")) {
            price += market.getBuyPrices().get("WORKER3");
        } else if (player.getInventory().containsKey("WORKER2")) {
            price += market.getBuyPrices().get("WORKER2");
        } else if (player.getInventory().containsKey("WORKER1")) {
            price += market.getBuyPrices().get("WORKER1");
        }
        if (player.getBalance() < price) {
            HashMap<Object, Integer> inventory = player.getInventory();
            player.removeCurrentCapacity(inventory.getOrDefault("WORKER1", 0));
            player.removeCurrentCapacity(inventory.getOrDefault("WORKER2", 0));
            player.removeCurrentCapacity(inventory.getOrDefault("WORKER3", 0));
            inventory.remove("WORKER1");
            inventory.remove("WORKER2");
            inventory.remove("WORKER3");
        } else {
            player.setBalance(player.getBalance() - price);
        }
    }
}
