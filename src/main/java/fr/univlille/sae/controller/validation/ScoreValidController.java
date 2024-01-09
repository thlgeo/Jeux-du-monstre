package fr.univlille.sae.controller.validation;

import fr.univlille.sae.Main;
import fr.univlille.sae.model.ModelMain;
import javafx.scene.control.Button;


/**
 * Cette classe correspond au bouton de retour au menu principal
 * @author Nathan DESMEE, Armand SADY, Valentin THUILLIER, Theo LENGLART
 * @version 1.0
 */
public class ScoreValidController extends Button {

    private ModelMain modelMain;

    public ScoreValidController(ModelMain modelMain) {
        this.modelMain = modelMain;
        setText("Menu");
        setFont(Main.loadFont(Main.ARCADE_FONT, 20));
        setAction();
    }

    /**
     * Cette méthode permet de paramétrer les actions du bouton, c'est-à-dire retourner au menu principal
     */
    private void setAction() {
        setOnAction(event -> {
            modelMain.notify("ScoreHIDE");
            modelMain.notifyScoreManager("ScoreHIDE");
        });
    }
}
