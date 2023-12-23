package fr.univlille.sae.controller.validation;

import fr.univlille.sae.Main;
import fr.univlille.sae.controller.parameter.FogParam;
import fr.univlille.sae.controller.parameter.DepDiagParam;
import fr.univlille.sae.model.ModelMain;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

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

    private void setAction() {
        setOnAction(event -> {
            modelMain.rebuildOption(fog.isSelected(), depDiag.isSelected());
            new Alert(Alert.AlertType.CONFIRMATION, "Les informations ont bien été mises à jour").showAndWait();
        });
    }
}
