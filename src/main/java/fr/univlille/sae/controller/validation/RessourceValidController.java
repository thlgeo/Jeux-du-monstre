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
    private String emptyImagePath;
    private String wallImagePath;

    public RessourceValidController(ColorPicker wallColorPicker, ColorPicker emptyColorPicker, ModelMain modelMain) {
        this.wallColorPicker = wallColorPicker;
        this.emptyColorPicker = emptyColorPicker;
        this.emptyImagePath = "";
        this.wallImagePath = "";
        this.modelMain = modelMain;
        setText("Enregistrer les parametres");
        setFont(Main.loadFont(Main.ARCADE_FONT, 20));
        setAction();
    }

    /**
     * Cette méthode permet de changer l'image des cases vides
     * @param emptyImagePath
     */
    public void setEmptyImagePath(String emptyImagePath) {
        this.emptyImagePath = emptyImagePath;
    }

    /**
     * Cette méthode permet de changer l'image des murs
     * @param wallImagePath
     */
    public void setWallImagePath(String wallImagePath) {
        this.wallImagePath = wallImagePath;
    }

    /**
     * Cette méthode check si les couleurs sont différentes et si les images sont bien chargées
     */
    private void setAction() {
        setOnAction(event -> {
            if (wallColorPicker.getValue().equals(emptyColorPicker.getValue())) {
                new Alert(Alert.AlertType.ERROR, "Veuillez entrer deux couleurs différentes !").showAndWait();
                return;
            }
            if (!emptyImagePath.equals("")) {
                MazeController.setEmptyImage("-fx-background-image: url('" + emptyImagePath + "'); -fx-background-size: cover");
            } else {
                MazeController.setEmptyColor("-fx-background-color: #" + emptyColorPicker.getValue().toString().substring(2, 8));
            }
            if (!wallImagePath.equals("")) {
                MazeController.setWallImage("-fx-background-image: url('" + wallImagePath + "'); -fx-background-size: cover");
            } else {
                MazeController.setWallColor("-fx-background-color: #" + wallColorPicker.getValue().toString().substring(2, 8));
            }
            new Alert(Alert.AlertType.CONFIRMATION, "Les informations ont bien été mises à jour").showAndWait();
            modelMain.notify("ResMAJ");

        });
    }
}
