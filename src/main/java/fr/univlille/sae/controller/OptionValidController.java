package fr.univlille.sae.controller;

import fr.univlille.sae.Main;
import fr.univlille.sae.model.ModelMain;
import javafx.scene.control.Button;

public class OptionValidController extends Button {
    private ModelMain modelMain;
    private FogController fog;
    private DepDiagController depDiag;
    public OptionValidController(FogController fog, DepDiagController depDiag, ModelMain modelMain) {
        this.modelMain = modelMain;
        this.fog = fog;
        this.depDiag = depDiag;
        setText("Enregistrer les parametres");
        setFont(Main.loadFont(Main.ARCADE_FONT, 20));
        setAction();
    }

    private void setAction() {
    }
}
