package fr.univlille.sae.controller;

import fr.univlille.sae.Main;
import fr.univlille.sae.model.Maze;
import javafx.scene.control.Button;

/**
 * Cette classe correspond au bouton de lancement de la page de paramètres
 * @Author Nathan Desmee, Valentin Thuillier, Armand Sady, Théo Lenglart
 * @Version 1.0
 */
public class SettingButton extends Button {
    private Maze maze;

    public SettingButton(Maze maze){
        super("Changer Parametres");
        this.maze = maze;
        setAction();
        setMinSize(200, 30);
        setFont(Main.loadFont(Main.ARCADE_FONT, 20));
    }

    /**
     * Cette méthode permet de paramétrer les actions du bouton càd lancer la fenetre de parametrage
     */
    public void setAction(){
        setOnAction(event -> {
            maze.notifyShowParameter();
        });
    }
}
