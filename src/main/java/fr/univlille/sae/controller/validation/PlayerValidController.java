package fr.univlille.sae.controller.validation;

import fr.univlille.sae.Main;
import fr.univlille.sae.controller.parameter.*;
import fr.univlille.sae.model.ModelMain;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

/**
 * Classe correspondante au bouton qui permet de valider les parametres des joueurs
 *
 * @author Nathan DESMEE, Armand SADY, Valentin THUILLIER, Theo LENGLART
 * @version 1.0
 */
public class PlayerValidController extends Button {
    private final NameParam hunterName;
    private final NameParam monsterName;
    private IAHunterParam iaHunter;
    private IAMonsterParam iaMonstre;
    private MonsterChoiceParam monsterChoice;
    private HunterChoiceParam hunterChoice;
    private ModelMain modelMain;

    public PlayerValidController(NameParam monsterName, NameParam hunterName, IAMonsterParam iaMonstre, IAHunterParam iaHunter, MonsterChoiceParam monsterChoice, HunterChoiceParam hunterChoice, ModelMain modelMain) {
        this.modelMain = modelMain;
        this.monsterName = monsterName;
        this.hunterName = hunterName;
        this.iaHunter = iaHunter;
        this.iaMonstre = iaMonstre;
        this.monsterChoice = monsterChoice;
        this.hunterChoice = hunterChoice;
        setText("Enregistrer les parametres");
        setFont(Main.loadFont(Main.ARCADE_FONT, 20));
        setAction();
    }

    /**
     * Cette méthode permet de mettre en place l'action du bouton, c'est-à-dire vérifier si les paramètres sont valides et de les enregistrer
     */
    private void setAction() {
        setOnAction(event -> {
            if (monsterName.getText() == null|| hunterName.getText() == null) {
                new Alert(Alert.AlertType.ERROR, "Veuillez entrer un nom valide !").showAndWait();
                return;
            }
            modelMain.rebuildPlayers(hunterName.getText(), monsterName.getText(), iaMonstre.isSelected(), iaHunter.isSelected(), monsterChoice.getChoice(), hunterChoice.getChoice());
            new Alert(Alert.AlertType.CONFIRMATION, "Les informations ont bien été mises à jour").showAndWait();
        });
    }
}
