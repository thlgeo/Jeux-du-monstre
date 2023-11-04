package fr.univlille.sae.controller;

import fr.univlille.sae.Main;
import fr.univlille.sae.model.Maze;
import javafx.scene.control.Button;

/**
 * Cette classe correspond au bouton de lancement du jeu
 * @author Nathan Desmee, Valentin Thuillier, Armand Sady, Théo Lenglart
 * @version  1.0
 */
public class LaunchButton extends Button {

    private final Maze maze;

    public LaunchButton(Maze maze) {
        super("Jouer");
        this.maze = maze;
        setAction();
        setMinSize(200, 30);
        setFont(Main.loadFont(Main.ARCADE_FONT, 20));
    }

    /**
     * Cette méthode permet de paramétrer les actions du bouton, c'est-à-dire afficher et initialiser les pages Monster et Hunter
     */
    public void setAction() {
        setOnAction(event -> {
            maze.notifyShowMH();
            maze.notifyDiscoveredMaze();
        });
    }
}
