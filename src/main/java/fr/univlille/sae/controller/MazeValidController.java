package fr.univlille.sae.controller;

import fr.univlille.sae.Main;
import fr.univlille.sae.model.ModelMain;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class MazeValidController extends Button {
    private final SizeController height;
    private final SizeController width;
    private PercentWallController percentWall;
    private GenerateMazeController generateMaze;
    private ModelMain modelMain;
    public MazeValidController(SizeController height, SizeController width, PercentWallController percentWall, GenerateMazeController generateMaze, ModelMain modelMain) {
        this.modelMain = modelMain;
        this.height = height;
        this.width = width;
        this.percentWall = percentWall;
        this.generateMaze = generateMaze;
        setText("Enregistrer les parametres");
        setFont(Main.loadFont(Main.ARCADE_FONT, 20));
        setAction();

    }

    private void setAction() {
        setOnAction(event -> {
            if (!height.isValid() || !width.isValid()) {
                new Alert(Alert.AlertType.ERROR, "Veuillez entrer une taille entre " + SizeController.MIN_SIZE + " et " + SizeController.MAX_SIZE + "  !").showAndWait();
                return;
            }
            modelMain.rebuildMaze(height.getValue(), width.getValue(), true, 0.35);
            new Alert(Alert.AlertType.CONFIRMATION, "Les informations ont bien été mises à jour").showAndWait();
        });
    }
}
