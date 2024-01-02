package fr.univlille.sae.controller.parameter;

import fr.univlille.sae.Main;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Classe permettant de créer une case à cocher pour activer ou non l'IA du chasseur
 *
 * @author Nathan Desmee, Valentin Thuillier, Armand Sady, Théo Lenglart
 * @version 1.0
 */
public class IAHunterParam extends HBox {
    protected CheckBox IAHunter = new CheckBox();
    protected Label texte;

    public IAHunterParam() {
        texte = new Label("Jouer avec IA chasseur ");
        texte.setFont(Main.loadFont(Main.ARCADE_FONT, 20));
        getChildren().addAll(texte, IAHunter);
        setAlignment(Pos.CENTER);
    }

    /**
     * @return true si la case est cochée, false sinon
     */
    public boolean isSelected() {
        return IAHunter.isSelected();
    }
}
