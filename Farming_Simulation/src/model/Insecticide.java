package model;

public class Insecticide implements Appliable {
    public void applyTo(Plot plot) {
        plot.setHasInsecticide(true);
    }
}
