package fr.univlille.sae.controller.parameter;

import javafx.scene.control.Slider;

/**
 * Classe permettant de créer un slider pour choisir le pourcentage de "perfection" du labyrinth
 *
 * @author Nathan Desmee, Valentin Thuillier, Armand Sady, Théo Lenglart
 * @version 1.0
 */
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
