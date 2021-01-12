package view.component;

import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;
import model.Plot;

public class PlotComponent {

    private Plot plot;
    private RadioButton selectButton;


    public PlotComponent(Plot plot) {
        this.plot = plot;
        this.selectButton = new RadioButton("");
        selectButton.setUserData(plot);
    }

    public Plot getPlot() {
        return plot;
    }

    public RadioButton getSelectButton() {
        return selectButton;
    }

    public VBox getComponent() {
        VBox container = new VBox();
        container.getStyleClass().add("container");
        container.setOnMouseClicked(e -> {
            selectButton.setSelected(true);
        });

        if (plot.getHasSeedPlantedInIt()) {
            String name = plot.getCrop().getName();
            Label nameLabel = new Label(name);
            nameLabel.getStyleClass().add("component-text");

            String maturity = plot.maturityToString();
            Label maturityLabel = new Label("Maturity: " + maturity);
            maturityLabel.getStyleClass().add("component-text");

            int waterLevel = plot.getWaterLevel();
            Label waterLabel = new Label("Water Level: " + waterLevel);
            waterLabel.getStyleClass().add("component-text");

            int fertilizerLevel = plot.getFertilizer();
            Label fertilizerLabel = new Label("Fertilizer Level: " + fertilizerLevel);
            fertilizerLabel.getStyleClass().add("component-text");

            boolean insecticideStatus = plot.getHasInsecticide();
            Label insecticideLabel = new Label("");
            if (insecticideStatus) {
                insecticideLabel.setText("Insecticide Applied");
            }
            insecticideLabel.getStyleClass().add("component-text");

            container.getChildren().addAll(selectButton, nameLabel, maturityLabel,
                    waterLabel, fertilizerLabel, insecticideLabel);
        } else {
            container.getChildren().addAll(selectButton, new Label("Empty Plot"));
        }

        return container;
    }

}
