package fr.univlille.sae.controller.parameter;

import fr.univlille.sae.Main;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Classe permettant de créer une case à cocher pour activer ou non l'IA du monstre
 *
 * @author Nathan Desmee, Valentin Thuillier, Armand Sady, Théo Lenglart
 * @version 1.0
 */
public class IAMonsterParam extends HBox {
    protected CheckBox IAMonster = new CheckBox();
    protected Label texte;

    public IAMonsterParam() {
        texte = new Label("Jouer contre IA monstre ");
        texte.setFont(Main.loadFont(Main.ARCADE_FONT, 20));
        getChildren().addAll(texte, IAMonster);
        setAlignment(Pos.CENTER);
    }

    /**
     * @return true si la case est cochée, false sinon
     */
    public boolean isSelected() {
        return IAMonster.isSelected();
    }
}
