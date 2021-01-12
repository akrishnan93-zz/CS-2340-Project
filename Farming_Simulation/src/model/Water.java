package model;

public class Water implements Appliable, Reducable {

    public void applyTo(Plot plot) {
        plot.water(1);
    }

    public void reduce(Plot plot) {
        plot.water(-1);
    }
}
