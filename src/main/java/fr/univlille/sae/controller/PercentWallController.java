package fr.univlille.sae.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;

public class PercentWallController extends Slider {
    public PercentWallController(double percent_wall)
    {
        setWidth(100);
        setValue(percent_wall);
        setMax(0.5);
        setMin(0);
        setAction();
    }

    public void setAction()
    {
        valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number oldVal, Number newVal) {
                modelMain.setPercentWall(newVal.doubleValue());
            }
            
        });
    }
}
