package fr.univlille.sae.controller;

import fr.univlille.sae.Main;
import fr.univlille.sae.model.ModelMain;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class PlayerValidController extends Button {
    private final NameController hunterName;
    private final NameController monsterName;
    private IAHunterController iaHunter;
    private IAMonsterController iaMonstre;
    private ModelMain modelMain;

    public PlayerValidController(NameController monsterName, NameController hunterName, IAMonsterController iaMonstre, IAHunterController iaHunter, ModelMain modelMain) {
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
        });
    }
}
