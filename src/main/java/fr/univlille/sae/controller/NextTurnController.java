package fr.univlille.sae.controller;

import fr.univlille.sae.Main;
import fr.univlille.sae.model.ModelMain;
import javafx.scene.control.Button;

public class NextTurnController extends Button {

    private final ModelMain modelMain;

    public NextTurnController(ModelMain modelMain){
        super("Tour 1");
        this.modelMain = modelMain;
        setAction();
        setMinSize(200, 30);
        setFont(Main.loadFont(Main.ARCADE_FONT, 20));
    }

    private void setAction(){
        setOnAction(event -> modelMain.lancerTourMonstre());
    }
}
