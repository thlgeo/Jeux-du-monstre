package fr.univlille.sae.controller;

import fr.univlille.sae.Main;
import fr.univlille.sae.model.Maze;
import javafx.scene.control.Button;

/**
 * Cette classe coorespond au bouton de lancement du jeu
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
     * Cette méthode permet de paramétrer les actions du bouton càd lancer les deux fenetres hunter et monster
     */
    public void setAction() {
        setOnAction(event -> {
            maze.notifyShowMH();
            maze.notifyDiscoveredMaze();
        });
    }
}
