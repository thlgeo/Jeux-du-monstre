package fr.univlille.sae.controller.validation;

import fr.univlille.sae.Main;
import fr.univlille.sae.controller.parameter.GenerateMazeParam;
import fr.univlille.sae.controller.parameter.PercentWallParam;
import fr.univlille.sae.controller.parameter.SizeParam;
import fr.univlille.sae.model.ModelMain;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class MazeValidController extends Button {
    private final SizeParam height;
    private final SizeParam width;
    private PercentWallParam percentWall;
    private GenerateMazeParam generateMaze;
    private ModelMain modelMain;

    public MazeValidController(SizeParam height, SizeParam width, PercentWallParam percentWall, GenerateMazeParam generateMaze, ModelMain modelMain) {
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
                new Alert(Alert.AlertType.ERROR, "Veuillez entrer une taille entre " + SizeParam.MIN_SIZE + " et " + SizeParam.MAX_SIZE + "  !").showAndWait();
                return;
            }
            modelMain.rebuildMaze(height.getValue(), width.getValue(), generateMaze.isSelected(), percentWall.getValue());
            new Alert(Alert.AlertType.CONFIRMATION, "Les informations ont bien été mises à jour").showAndWait();
        });
    }
}
