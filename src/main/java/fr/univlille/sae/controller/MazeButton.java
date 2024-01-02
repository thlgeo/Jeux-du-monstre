package fr.univlille.sae.controller;

import fr.univlille.sae.Main;
import fr.univlille.sae.model.ModelMain;
import javafx.scene.control.Button;

/**
 * Cette classe correspond au bouton d'affichage des paramètres du labyrinthe
 *
 * @author Nathan Desmee, Valentin Thuillier, Armand Sady, Théo Lenglart
 * @version 1.0
 */
public class MazeButton extends Button {
    private final ModelMain modelMain;
    public MazeButton(ModelMain modelMain) {
        super("labyrinthe");
        this.modelMain = modelMain;
        setAction();
        setMinSize(200, 30);
        setFont(Main.loadFont(Main.ARCADE_FONT, 20));
    }

    /**
     * Cette méthode permet de paramétrer les actions du bouton, c'est-à-dire afficher la page MazeParam et fermer les autres pages
     */
    private void setAction() {
        setOnAction(event -> modelMain.notify("MazeParamSHOW"));
    }
}
