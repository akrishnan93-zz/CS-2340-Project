package model;

import java.util.HashMap;

public abstract class AbstractMarket {
    abstract void updateBasePrices();
    abstract void updateSellPrice();
    abstract void updateBuyPrice();


    public abstract HashMap<Object, Integer>  getBasePrices();
    public abstract HashMap<Object, Integer> getSellPrices();
    public abstract HashMap<Object, Integer> getBuyPrices();
}
