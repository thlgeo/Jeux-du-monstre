package fr.univlille.sae.controller.validation;

import fr.univlille.sae.Main;
import fr.univlille.sae.controller.parameter.IAHunterParam;
import fr.univlille.sae.controller.parameter.IAMonsterParam;
import fr.univlille.sae.controller.parameter.NameParam;
import fr.univlille.sae.model.ModelMain;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class PlayerValidController extends Button {
    private final NameParam hunterName;
    private final NameParam monsterName;
    private IAHunterParam iaHunter;
    private IAMonsterParam iaMonstre;
    private ModelMain modelMain;

    public PlayerValidController(NameParam monsterName, NameParam hunterName, IAMonsterParam iaMonstre, IAHunterParam iaHunter, ModelMain modelMain) {
        this.modelMain = modelMain;
        this.monsterName = monsterName;
        this.hunterName = hunterName;
        this.iaHunter = iaHunter;
        this.iaMonstre = iaMonstre;
        setText("Enregistrer les parametres");
        setFont(Main.loadFont(Main.ARCADE_FONT, 20));
        setAction();
    }

    private void setAction() {
        setOnAction(event -> {
            if (monsterName.getText() == null|| hunterName.getText() == null) {
                new Alert(Alert.AlertType.ERROR, "Veuillez entrer un nom valide !").showAndWait();
                return;
            }
            modelMain.rebuildPlayers(hunterName.getText(), monsterName.getText(), iaMonstre.isSelected(), iaHunter.isSelected());
            new Alert(Alert.AlertType.CONFIRMATION, "Les informations ont bien été mises à jour").showAndWait();
        });
    }
}
