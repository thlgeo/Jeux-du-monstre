package fr.univlille.sae.controller.parameter;

import fr.univlille.sae.Main;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

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

    public boolean isSelected() {
        return generateMaze.isSelected();
    }
}
