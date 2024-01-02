package fr.univlille.sae.controller.parameter;

import fr.univlille.sae.Main;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Classe permettant de créer une case à cocher pour activer ou non la génération automatique du labyrinthe
 *
 * @author Nathan Desmee, Valentin Thuillier, Armand Sady, Théo Lenglart
 * @version 1.0
 */
public class GenerateMazeParam extends HBox {
    protected CheckBox generateMaze;
    protected Label texte;

    public GenerateMazeParam() {
        generateMaze = new CheckBox();
        generateMaze.setSelected(true);
        texte = new Label("Generation aleatoire ");
        texte.setFont(Main.loadFont(Main.ARCADE_FONT, 20));
        getChildren().addAll(texte, generateMaze);
        setAlignment(Pos.CENTER);
    }

    /**
     * @return true si la case est cochée, false sinon
     */
    public boolean isSelected() {
        return generateMaze.isSelected();
    }
}
