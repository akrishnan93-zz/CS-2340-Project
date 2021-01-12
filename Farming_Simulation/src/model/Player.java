package model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * The type Player.
 */
public class Player {

    public static final int EXP_TO_LEVEL = 100;

    private int balance;
    private String name;
    private int exp;
    private int level;

    private int difficulty;
    private int currentCapacity;
    private int maxCapacity;
    private int day;
    private String season;
    private HashMap<Object, Integer> inventory = new HashMap<>();
    private int maxHarvestPerDay;
    private int maxWaterPerDay;
    private int currentNumHarvests;
    private int currentNumWaters;

    /**
     * Instantiates a new Player.
     *
     * @param difficulty the difficulty
     * @param name name of player
     * @param startingSeed the starting seed
     * @param startingSeason the selected season start
     */
    public Player(String difficulty, String name, String startingSeed, 
                                        String startingSeason) {
        switch (difficulty) {
        case "Easy":
            this.difficulty = 1;
            balance = 3000;
            break;
        case "Medium":
            this.difficulty = 2;
            balance = 2000;
            break;
        case "Hard":
            this.difficulty = 3;
            balance = 1000;
            break;
        default:
            this.difficulty = 1;
            balance = 100;
        }
        this.exp = 0;
        this.level = 0;
        this.name = name;
        inventory.put(startingSeed.toUpperCase(), 10);
        //For testing BUY purposes:
        this.currentCapacity = 10;
        this.maxCapacity = 30;
        this.day = 0;
        this.season = startingSeason;
        this.maxHarvestPerDay = 2;
        this.maxWaterPerDay = 6;
        this.currentNumHarvests = 0;
        this.currentNumWaters = 0;
    }

    public String getName() {
        return name;
    }

    /**
     * Gets balance.
     *
     * @return the balance
     */
    public int getBalance() {
        return balance;
    }

    public int getDay() {
        return day;
    }

    public String getSeason() {
        return season;
    }

    /**
     * Advances to next day.
     */
    public void nextDay() {
        currentNumHarvests = 0;
        currentNumWaters = 0;
        day += 1;
    } 

    /**
     * Gets exp.
     *
     * @return the exp
     */
    public int getExp() {
        return exp;
    }

    /**
     * Gets difficulty.
     *
     * @return the difficulty
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Gets level.
     *
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets level.
     *
     * @param level the level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Sets balance.
     *
     * @param balance the balance
     */
    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getMaxHarvestPerDay() {
        return this.maxHarvestPerDay;
    }

    public void setMaxHarvestPerDay(int maxHarvestPerDay) {
        this.maxHarvestPerDay = maxHarvestPerDay;
    }

    public int getMaxWaterPerDay() {
        return this.maxWaterPerDay;
    }

    public void setMaxWaterPerDay(int maxWaterPerDay) {
        this.maxWaterPerDay = maxWaterPerDay;
    }

    public int getCurrentNumHarvests() {
        return this.currentNumHarvests;
    }

    public void setCurrentNumHarvests(int currentNumHarvests) {
        this.currentNumHarvests = currentNumHarvests;
    }

    public int getCurrentNumWaters() {
        return this.currentNumWaters;
    }

    public void setCurrentNumWaters(int currentNumWaters) {
        this.currentNumWaters = currentNumWaters;
    }

    /**
     * Sets exp.
     *
     * @param exp the exp
     */
    public void setExp(int exp) {
        this.exp = exp;
    }

    public void addExp(int exp) {
        this.exp += exp;
        if (this.exp / EXP_TO_LEVEL > 1) {
            level += this.exp / EXP_TO_LEVEL;
            this.exp = this.exp % EXP_TO_LEVEL;
        }
    }

    /**
     * Sets difficulty.
     *
     * @param difficulty the difficulty
     */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getCurrentCapacity() {
        return currentCapacity;
    }

    public boolean addCurrentCapacity(int quantity) {
        if (currentCapacity + quantity <= maxCapacity) {
            currentCapacity = currentCapacity + quantity;
            return true;
        }
        return false;
    }

    public void removeCurrentCapacity(int quantity) {
        if (currentCapacity - quantity >= 0) {
            currentCapacity = currentCapacity - quantity;
        } else {
            currentCapacity = 0;
        }
    }

    public HashMap<Object, Integer> getInventory() {
        return inventory;
    }

    public ArrayList<String> getInventoryAsString() {
        ArrayList<String> inventoryAsString = new ArrayList<>();
        ArrayList<Object> tempKeys = new ArrayList<>(inventory.keySet());
        for (Object key : tempKeys) {
            if (inventory.get(key) <= 0) {
                inventory.remove(key);
            }
        }
        ArrayList<Object> inventoryKeys = new ArrayList<>(inventory.keySet());
        for (Object key : inventoryKeys) {
            inventoryAsString.add("" + key.toString() + " : " + inventory.get(key));
        }
        return inventoryAsString;
    }

    public boolean setInventorySell(String key, int quantitySold, Iterator iterator) {
        boolean cropExists = false;
        for (Crop c : Crop.values()) {
            if (c.name().equals(key.toUpperCase())) {
                cropExists = true;
            }
        }
        if (!cropExists) {
            return false;
        }

        Crop curr = Crop.valueOf(key.toUpperCase());
        if (inventory.get(curr) - quantitySold <= 0) {
            currentCapacity -= inventory.get(curr);
            if (iterator != null) {
                iterator.remove();
            } else {
                inventory.remove(curr);
            }
        } else {
            inventory.put(curr, inventory.get(curr) - quantitySold);
            currentCapacity -= quantitySold;
        }
        return true;
    }

    public boolean setInventoryBuy(String key, int quantityBought) {
        boolean isEnum = false;
        key = key.toUpperCase();
        for (Crop c : Crop.values()) {
            if (key.equals(c.name())) {
                isEnum = true;
                break;
            }
        }
        if (key.equals("FARM EXPANSION")) {
            inventory.put(key, 1);
            return true;
        } else if (addCurrentCapacity(quantityBought)) {
            if (isEnum) {
                inventory.put(Crop.valueOf(key),
                        inventory.getOrDefault(Crop.valueOf(key), 0) + quantityBought);
            } else {
                inventory.put(key, inventory.getOrDefault(key, 0) + quantityBought);
            }
            return true;
        }
        return false;
    }

    public boolean hasWon() {
        return balance >= 5000;
    }

    public boolean hasLost() {
        return balance <= 0;
    }


}
