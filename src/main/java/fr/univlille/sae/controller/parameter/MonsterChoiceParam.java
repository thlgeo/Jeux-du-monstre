package fr.univlille.sae.controller.parameter;

import fr.univlille.sae.Main;
import javafx.scene.control.ChoiceBox;

/**
 * Classe permettant de choisir le type d'IA du monstre
 *
 * @author Nathan Desmee, Valentin Thuillier, Armand Sady, Théo Lenglart
 * @version 1.0
 */
public class MonsterChoiceParam extends ChoiceBox<String> {

    public MonsterChoiceParam() {
        this.getItems().addAll("IAMonster", "RightWallIAMonster");
        this.setValue("IAMonster");
        setMinWidth(200);
    }

    /**
     * Retourne la classe de l'IA du monstre
     * @return String : le nom de la classe de l'IA du monstre
     */
    public String getChoice() {
    	return Main.IA_PACKAGE + this.getValue();
    }

}
