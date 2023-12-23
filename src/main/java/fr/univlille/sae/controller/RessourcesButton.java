package fr.univlille.sae.controller;

import fr.univlille.sae.Main;
import fr.univlille.sae.model.ModelMain;
import javafx.scene.control.Button;

public class RessourcesButton extends Button {

    ModelMain modelMain;
    public RessourcesButton(ModelMain modelMain) {
        super("Changer Ressources");
        this.modelMain = modelMain;
        setAction();
        setMinSize(200, 30);
        setFont(Main.loadFont(Main.ARCADE_FONT, 20));
    }

    /**
     * Cette méthode permet de paramétrer les actions du bouton, c'est-à-dire afficher la page des ressources
     */
    private void setAction() {
        setOnAction(event -> modelMain.notify("ResSHOW"));
    }
}
