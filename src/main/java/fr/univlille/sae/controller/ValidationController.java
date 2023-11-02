package fr.univlille.sae.controller;

import fr.univlille.sae.Main;
import fr.univlille.sae.model.Maze;
import fr.univlille.sae.view.HunterView;
import fr.univlille.sae.view.MonsterView;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.io.File;

/**
 * Cette classe correspond au bouton de validation des paramètres
 * @Author Nathan Desmee, Valentin Thuillier, Armand Sady, Théo Lenglart
 * @Version 1.0
 */
public class ValidationController extends Button {
    private NameController nameMonster;
    private NameController nameHunter;
    private SizeController height;
    private SizeController width;
    private Maze maze;

    public ValidationController(NameController nameMonster, NameController nameHunter, SizeController height, SizeController width, Maze maze) {
        this.maze = maze;
        this.nameMonster = nameMonster;
        this.nameHunter = nameHunter;
        this.height = height;
        this.width = width;
        setText("Enregistrer les parametres");
        setFont(Main.loadFont(Main.ARCADE_FONT, 20));
        setAction();
    }

    /**
     * Cette méthode permet de paramétrer les actions du bouton cad signaler les changements de paramètres
     */
    public void setAction(){
        setOnAction(event -> {
            if(nameMonster.getText() == null || nameHunter.getText() == null) {
                new Alert(Alert.AlertType.ERROR, "Veuillez entrer un nom !").showAndWait();
                return;
            }
            if(!height.isValid() || !width.isValid()) {
                new Alert(Alert.AlertType.ERROR, "Veuillez entrer une taille entre "  + SizeController.MIN_SIZE + " et " + SizeController.MAX_SIZE + "  !").showAndWait();
                return;
            }
            maze.changerParam(nameHunter.getText(), nameMonster.getText(), height.getValue(), width.getValue());
        });
    }
}
