package fr.univlille.sae.controller;

import fr.univlille.sae.Main;
import fr.univlille.sae.model.ModelMainInterface;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

/**
 * Cette classe correspond au bouton de validation des paramètres
 *
 * @author Nathan Desmee, Valentin Thuillier, Armand Sady, Théo Lenglart
 * @version 1.0
 */
public class ValidationController extends Button {
    private final NameController nameMonster;
    private final NameController nameHunter;
    private final SizeController height;
    private final SizeController width;
    private final DepDiagController depDiag;
    private final FogController fog;
    private final ModelMainInterface modelMain;
    private final GenerateMazeController generateMaze;
    private final IAMonsterController IAMonstre;
    private final IAHunterController IAHunter;

    public ValidationController(NameController nameMonster, NameController nameHunter, SizeController height, SizeController width, DepDiagController depDiag, FogController fog, GenerateMazeController generateMaze, IAMonsterController IAMonstre, IAHunterController IAHunter, ModelMainInterface modelMain) {
        this.modelMain = modelMain;
        this.nameMonster = nameMonster;
        this.nameHunter = nameHunter;
        this.height = height;
        this.width = width;
        this.depDiag = depDiag;
        this.fog = fog;
        this.generateMaze = generateMaze;
        this.IAMonstre = IAMonstre;
        this.IAHunter = IAHunter;
        setText("Enregistrer les parametres");
        setFont(Main.loadFont(Main.ARCADE_FONT, 20));
        setAction();
    }

    /**
     * Cette méthode permet de paramétrer les actions du bouton, c'est-à-dire signaler les changements de paramètres
     */
    private void setAction() {
        setOnAction(event -> {
            if(nameMonster.getText() == null || nameHunter.getText() == null) {
                new Alert(Alert.AlertType.ERROR, "Veuillez entrer un nom !").showAndWait();
                return;
            }
            if(!height.isValid() || !width.isValid()) {
                new Alert(Alert.AlertType.ERROR, "Veuillez entrer une taille entre " + SizeController.MIN_SIZE + " et " + SizeController.MAX_SIZE + "  !").showAndWait();
                return;
            }
            modelMain.changerParam(nameHunter.getText(), nameMonster.getText(), height.getValue(), width.getValue(), depDiag.isSelected(), fog.isSelected(), generateMaze.isSelected(), IAMonstre.isSelected(), IAHunter.isSelected());
            new Alert(Alert.AlertType.CONFIRMATION, "Les informations ont bien été mises à jour").showAndWait();
        });
    }
}
