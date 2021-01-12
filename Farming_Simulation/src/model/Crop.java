package model;


/**
 * The enum for Crop Object.
 */
public enum Crop {

    /**
     * Wheat crop.
     */
    WHEAT("WHEAT", 4),
    WHEAT_INSECTICIDE("WHEAT_INSECTICIDE", 4),

    /**
     * Carrot crop.
     */
    CARROT("CARROT", 6),
    CARROT_INSECTICIDE("CARROT_INSECTICIDE", 6),

    /**
     * Potato crop.
     */
    POTATO("POTATO", 5),
    POTATO_INSECTICIDE("POTATO_INSECTICIDE", 5);

    private int maxMaturity;
    private String name;

    /**
     * Constructor to create crop object.
     * @param maxMaturity initial maxMaturity level
     * @param name name of crop
     */
    Crop(String name, int maxMaturity) {
        this.maxMaturity = maxMaturity;
        this.name = name;
    }

    /**
     * Gets maturation.
     *
     * @return the maturation
     */
    public int getMaxMaturity() {
        return maxMaturity;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }
}

