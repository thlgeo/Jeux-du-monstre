package fr.univlille.sae.controller;

import fr.univlille.sae.Main;
import fr.univlille.sae.model.ModelMain;
import javafx.scene.control.Button;

public class OptionButton extends Button {

    private final ModelMain modelMain;
    public OptionButton(ModelMain modelMain) {
        super("option");
        this.modelMain = modelMain;
        setAction();
        setMinSize(200, 30);
        setFont(Main.loadFont(Main.ARCADE_FONT, 20));
    }

    private void setAction() {
        setOnAction(event -> modelMain.notify("OptionParamSHOW"));
    }
}
