package fr.univlille.sae.controller.validation;

import fr.univlille.sae.Main;
import fr.univlille.sae.controller.parameter.FogParam;
import fr.univlille.sae.controller.parameter.DepDiagParam;
import fr.univlille.sae.model.ModelMain;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

/**
 * Classe correspondante au bouton qui permet de valider les parametres du jeu
 *
 * @author Nathan DESMEE, Armand SADY, Valentin THUILLIER, Theo LENGLART
 * @version 1.0
 */
public class OptionValidController extends Button {
    private ModelMain modelMain;
    private FogParam fog;
    private DepDiagParam depDiag;
    public OptionValidController(FogParam fog, DepDiagParam depDiag, ModelMain modelMain) {
        this.modelMain = modelMain;
        this.fog = fog;
        this.depDiag = depDiag;
        setText("Enregistrer les parametres");
        setFont(Main.loadFont(Main.ARCADE_FONT, 20));
        setAction();
    }

    /**
     * Cette méthode permet de mettre en place l'action du bouton, c'est-à-dire vérifier si les paramètres sont valides et de les enregistrer
     */
    private void setAction() {
        setOnAction(event -> {
            modelMain.rebuildOption(depDiag.isSelected(), fog.isSelected());
            new Alert(Alert.AlertType.CONFIRMATION, "Les informations ont bien été mises à jour").showAndWait();
        });
    }
}
