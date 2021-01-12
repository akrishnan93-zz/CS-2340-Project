package model;

/**
 * The type Plot.
 */
public class Plot {

    /**
     * The Crop.
     */
    private Crop crop;
    /**
     * The Infested.
     */
    private boolean infested;
    private int currFertilizerLevel;
    private int currMaturity;
    private int plotID;
    private boolean isDead;
    private boolean hasSeedPlantedInIt;
    private boolean hasInsecticide;

    private final int minWaterLevel = 0;
    private final int maxWaterLevel = 5;
    private final int maxFertilizerLevel = 5;
    private int currentWaterLevel;


    /**
     * Instantiates a new Plot.
     * @param crop the plot's crop
     * @param plotID the plots id
     */
    public Plot(Crop crop, int plotID) {
        this.currFertilizerLevel = 0;
        this.infested = false;
        this.crop = crop;
        this.currMaturity = 0;
        this.currentWaterLevel = minWaterLevel;
        this.plotID = plotID;
        this.isDead = false;
        this.hasSeedPlantedInIt = false;
        this.hasInsecticide = false;
    }

    /**
     * Gets current water level.
     *
     * @return the current water level
     */
    public int getWaterLevel() {
        return currentWaterLevel; }

    /**
     * Gets max water level.
     *
     * @return the max water level
     */
    public int getMaxWaterlevel() {
        return maxWaterLevel; }

    /**
     * Gets fertilizer.
     *
     * @return the fertilizer
     */
    public int getFertilizer() {
        return currFertilizerLevel;
    }

    /**
     * Add fertilizer.
     *
     * @param amount the fertilizer
     */
    public void addFertilizer(int amount) {
        if (!isDead) {
            if (currFertilizerLevel + amount < maxFertilizerLevel) {
                this.currFertilizerLevel += amount;
            }
        }
    }

    public void addFertilizer() {
        addFertilizer(1);
    }

    public void water(int amount) {
        if (!isDead) {
            if (currentWaterLevel + amount >= maxWaterLevel) {
                dead();
            } else if (amount < 0 && currentWaterLevel + amount < 0) {
                dead();
            } else {
                currentWaterLevel += amount;
            }
        }
    }

    public void water() {
        water(1);
    }

    /**
     * Gets health.
     *
     * @return the health
     */
    public int getHealth() {
        return Math.min(this.currentWaterLevel, this.currFertilizerLevel);
    }

    /**
     * Gets crop.
     *
     * @return the crop
     */
    public Crop getCrop() {
        return crop;
    }

    /**
     * Sets crop.
     *
     * @param crop the crop
     */
    public void setCrop(Crop crop) {
        this.crop = crop;
    }

    /**
     * Is infested boolean.
     *
     * @return the boolean
     */
    public boolean isInfested() {
        return infested;
    }

    /**
     * Sets infested.
     *
     * @param difficulty the difficulty
     */
    public void setInfested(int difficulty) {
        switch (difficulty) {
        case 1:
            this.infested = Math.random() > 0.9;
            break;
        case 2:
            this.infested = Math.random() > 0.7;
            break;
        case 3:
            this.infested = Math.random() > 0.5;
            break;
        default:
            throw new RuntimeException();
        }

    }

    public int getCurrMaturity() {
        return currMaturity;
    }

    public void setCurrMaturity(int currMaturity) {
        this.currMaturity = currMaturity;
    }

    public int getPlotId() {
        return plotID;
    }

    public void setPlotId(int plotId) {
        this.plotID = plotID; }

    public void mature() {
        mature(1);
    }

    public boolean getIsDead() {
        return isDead;
    }

    public boolean getHasSeedPlantedInIt() {
        return hasSeedPlantedInIt;
    }

    public void setHasSeedPlantedInIt(boolean seed) {
        this.hasSeedPlantedInIt = seed;
    }

    public boolean getHasInsecticide() {
        return hasInsecticide;
    }

    public void setHasInsecticide(boolean insecticide) {
        this.hasInsecticide = insecticide;
    }

    public void mature(int days) {
        currMaturity += days;
        currentWaterLevel -= 1;
        if (currFertilizerLevel != 0) {
            currFertilizerLevel -= 1;
        }
        if (currentWaterLevel < minWaterLevel) {
            dead();
        }

    }

    public void dead() {
        this.currentWaterLevel = 0;
        this.currMaturity = 0;
        this.isDead = true;
    }

    public boolean isMatured() {
        return currMaturity >= crop.getMaxMaturity();
    }

    public String maturityToString() {
        if (isDead) {
            return "Dead Plant";
        }
        if (currMaturity == 0) {
            return "Seed";
        } else if (currMaturity < crop.getMaxMaturity() / 2) {
            return "Sprout";
        } else if (currMaturity < crop.getMaxMaturity()) {
            return "Immature";
        } else {
            return "Mature";
        }
    }

    public String toString() {
        if (hasSeedPlantedInIt) {
            if (hasInsecticide) {
                return "Plot " + this.plotID + ": " + this.getCrop().getName()
                        + "     \tMaturity: " + this.maturityToString()
                        + "     \tWater Level: " + this.getWaterLevel()
                        + "     \tInsecticide Applied";
            } else {
                return "Plot " + this.plotID + ": " + this.getCrop().getName()
                        + "     \tMaturity: " + this.maturityToString()
                        + "     \tWater Level: " + this.getWaterLevel();
            }
        } else {
            return "[EMPTY PLOT]";
        }
    }
}
