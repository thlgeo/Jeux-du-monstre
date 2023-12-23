package fr.univlille.sae.controller.parameter;

import javafx.scene.control.Slider;

public class PercentWallParam extends Slider {
    public PercentWallParam(double percent_wall)
    {
        setMaxWidth(400);
        setValue(percent_wall);
        setMax(0.5);
        setMin(0);
        majorTickUnitProperty().set(0.05);
        snapToTicksProperty().set(true);
        showTickMarksProperty().set(true);
        showTickLabelsProperty().set(true);
    }
}
