package fr.univlille.sae.controller.validation;

import fr.univlille.sae.Main;
import fr.univlille.sae.controller.maze.MazeController;
import fr.univlille.sae.model.ModelMain;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;

public class RessourceValidController extends Button{
    private ColorPicker wallColorPicker;
    private ColorPicker emptyColorPicker;
    private ModelMain modelMain;

    public RessourceValidController(ColorPicker wallColorPicker, ColorPicker emptyColorPicker, ModelMain modelMain) {
        this.wallColorPicker = wallColorPicker;
        this.emptyColorPicker = emptyColorPicker;
        this.modelMain = modelMain;
        setText("Enregistrer les parametres");
        setFont(Main.loadFont(Main.ARCADE_FONT, 20));
        setAction();
    }

    private void setAction() {
        setOnAction(event -> {
            if (wallColorPicker.getValue().equals(emptyColorPicker.getValue())) {
                new Alert(Alert.AlertType.ERROR, "Veuillez entrer deux couleurs différentes !").showAndWait();
                return;
            }
            MazeController.setWallColor("#" + wallColorPicker.getValue().toString().substring(2, 8));
            MazeController.setEmptyColor("#" + emptyColorPicker.getValue().toString().substring(2, 8));
            new Alert(Alert.AlertType.CONFIRMATION, "Les informations ont bien été mises à jour").showAndWait();
            modelMain.notify("ResMAJ");

        });
    }
}
