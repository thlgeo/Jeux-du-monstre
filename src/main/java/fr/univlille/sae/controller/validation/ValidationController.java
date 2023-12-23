package fr.univlille.sae.controller.validation;

import fr.univlille.sae.Main;
import fr.univlille.sae.controller.parameter.*;
import fr.univlille.sae.model.ModelMain;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

/**
 * Cette classe correspond au bouton de validation des paramètres
 *
 * @author Nathan Desmee, Valentin Thuillier, Armand Sady, Théo Lenglart
 * @version 1.0
 */
public class ValidationController extends Button {

    private final ModelMain modelMain;

    public ValidationController(ModelMain modelMain) {
        this.modelMain = modelMain;
        setText("Enregistrer les parametres");
        setFont(Main.loadFont(Main.ARCADE_FONT, 20));
        setAction();
    }

    /**
     * Cette méthode permet de paramétrer les actions du bouton, c'est-à-dire signaler les changements de paramètres
     */
    private void setAction() {
        setOnAction(event -> modelMain.notify("ParamMAJ"));
    }
}
