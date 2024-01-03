package fr.univlille.sae.controller.parameter;

import fr.univlille.sae.Main;
import javafx.scene.control.ChoiceBox;

/**
 * Classe permettant de choisir le type d'IA du chasseur
 *
 * @author Nathan Desmee, Valentin Thuillier, Armand Sady, Théo Lenglart
 * @version 1.0
 */
public class HunterChoiceParam extends ChoiceBox<String> {

    public HunterChoiceParam() {
        this.getItems().addAll("IAHunter", "IAHunterRandom");
        this.setValue("IAHunter");
        setMinWidth(200);
    }

    /**
     * Retourne la classe de l'IA du chasseur
     * @return String : le nom de la classe de l'IA du chasseur
     */
    public String getChoice() {
        return Main.IA_PACKAGE + this.getValue();
    }

}
