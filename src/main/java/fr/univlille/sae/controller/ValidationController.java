package fr.univlille.sae.controller;

import fr.univlille.sae.Main;
import fr.univlille.sae.model.Maze;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

/**
 * Cette classe correspond au bouton de validation des paramètres
 * @author Nathan Desmee, Valentin Thuillier, Armand Sady, Théo Lenglart
 * @version  1.0
 */
public class ValidationController extends Button {
    private final NameController nameMonster;
    private final NameController nameHunter;
    private final SizeController height;
    private final Maze maze;

    public ValidationController(NameController nameMonster, NameController nameHunter, SizeController height, Maze maze) {
        this.maze = maze;
        this.nameMonster = nameMonster;
        this.nameHunter = nameHunter;
        this.height = height;
        setText("Enregistrer les parametres");
        setFont(Main.loadFont(Main.ARCADE_FONT, 20));
        setAction();
    }

    /**
     * Cette méthode permet de paramétrer les actions du bouton, c'est-à-dire signaler les changements de paramètres
     */
    public void setAction() {
        setOnAction(event -> {
            if(nameMonster.getText() == null || nameHunter.getText() == null) {
                new Alert(Alert.AlertType.ERROR, "Veuillez entrer un nom !").showAndWait();
                return;
            }
            if(!height.isValid()) {
                new Alert(Alert.AlertType.ERROR, "Veuillez entrer une taille entre " + SizeController.MIN_SIZE + " et " + SizeController.MAX_SIZE + "  !").showAndWait();
                return;
            }
            maze.changerParam(nameHunter.getText(), nameMonster.getText(), height.getValue());
        });
    }
}
