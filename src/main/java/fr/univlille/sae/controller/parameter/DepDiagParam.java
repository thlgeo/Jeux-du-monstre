package fr.univlille.sae.controller.parameter;

import fr.univlille.sae.Main;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class DepDiagParam extends HBox {
    protected CheckBox depDiag;
    protected Label texte;

    public DepDiagParam() {
        depDiag = new CheckBox();
        texte = new Label("Deplacement en diagonale ");
        texte.setFont(Main.loadFont(Main.ARCADE_FONT, 20));
        getChildren().addAll(texte, depDiag);
        setAlignment(Pos.CENTER);
    }

    public boolean isSelected() {
        return depDiag.isSelected();
    }
}
