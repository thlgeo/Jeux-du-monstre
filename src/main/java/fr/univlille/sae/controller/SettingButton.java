package fr.univlille.sae.controller;

import fr.univlille.sae.Main;
import fr.univlille.sae.model.ModelMain;
import fr.univlille.sae.model.ModelMainInterface;
import javafx.scene.control.Button;

/**
 * Cette classe correspond au bouton de lancement de la page de paramètres
 * @author Nathan Desmee, Valentin Thuillier, Armand Sady, Théo Lenglart
 * @version  1.0
 */
public class SettingButton extends Button {
    private final ModelMainInterface modelMain;

    public SettingButton(ModelMainInterface modelMain) {
        super("Changer Parametres");
        this.modelMain = modelMain;
        setAction();
        setMinSize(200, 30);
        setFont(Main.loadFont(Main.ARCADE_FONT, 20));
    }

    /**
     * Cette méthode permet de paramétrer les actions du bouton, c'est-à-dire afficher la page de paramètres
     */
    private void setAction() {
        setOnAction(event -> modelMain.notifyShowParameter());
    }
}
