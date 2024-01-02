package fr.univlille.sae.view.parameter;

import fr.univlille.iutinfo.utils.Observer;
import fr.univlille.iutinfo.utils.Subject;
import fr.univlille.sae.Main;
import fr.univlille.sae.controller.maze.MazeController;
import fr.univlille.sae.model.ModelMain;
import fr.univlille.sae.view.Spacer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Cette classe est la fenêtre où l'utilisateur peut changer les paramètres des ressources
 *
 * @author Nathan Desmee, Valentin Thuillier, Armand Sady, Théo Lenglart
 * @version 1.0
 */
public class RessourcesView extends Stage implements Observer {
    public static final int WIDTH_VIEW = 500;
    public static final int HEIGHT_VIEW = 200;
    private final ModelMain modelMain;

    private Label wallLabel;
    private ColorPicker wallColorPicker;
    private Label emptyLabel;
    private ColorPicker emptyColorPicker;
    private Button getBack;
    private Font font = Main.loadFont(Main.ARCADE_FONT, 20);

    public RessourcesView(ModelMain modelMain) {
        this.modelMain = modelMain;
        setTitle("S3.02_G1_Ressources");
        setParameterNodes();
        setParameterScene();
        setResizable(false);
        getIcons().add(Main.loadImage(Main.ICON_URL));
        modelMain.attach(this);
    }

    /**
     * Cette méthode permet de changer la scène de la fenêtre à la scène principale de la page de paramétrage
     */
    private void setParameterScene() {
        VBox root = new VBox();
        VBox wallBox = new VBox();
        VBox emptyBox = new VBox();
        wallBox.getChildren().addAll(wallLabel, wallColorPicker);
        wallBox.setAlignment(Pos.CENTER);
        emptyBox.getChildren().addAll(emptyLabel, emptyColorPicker);
        emptyBox.setAlignment(Pos.CENTER);
        root.getChildren().addAll(wallBox, new Spacer(), emptyBox, new Spacer(), getBack);
        root.setAlignment(Pos.CENTER);
        setScene(new Scene(root, WIDTH_VIEW, HEIGHT_VIEW));

    }

    /**
     * Cette méthode permet de créer les différents éléments de la fenêtre
     */
    private void setParameterNodes() {
        wallLabel = new Label("Texture des murs ");
        wallLabel.setFont(font);
        emptyLabel = new Label("Texture des cases vides ");
        emptyLabel.setFont(font);
        double[] rgbWall = convertHexToRGB(MazeController.wallColor);
        double[] rgbEmpty = convertHexToRGB(MazeController.emptyColor);
        wallColorPicker = new ColorPicker(new Color(rgbWall[0], rgbWall[1], rgbWall[2], 1));
        wallColorPicker.setOnAction(e-> {
            MazeController.setWallColor("#" + wallColorPicker.getValue().toString().substring(2, 8));
        });
        emptyColorPicker = new ColorPicker(new Color(rgbEmpty[0], rgbEmpty[1], rgbEmpty[2], 1));
        emptyColorPicker.setOnAction(e-> {
            MazeController.setEmptyColor("#" + emptyColorPicker.getValue().toString().substring(2, 8));
        });
        getBack = new Button("Retour");
        getBack.setFont(font);
        getBack.setOnAction(e-> modelMain.notify("ResMAJ"));
    }

    /**
     * Cette méthode permet de convertir une couleur hexadécimale en un tableau de couleur RGB
     * @param wallColor Couleur hexadécimale
     * @return double[] Tableau de couleur RGB
     */
    private double[] convertHexToRGB(String wallColor) {
        double[] rgb = new double[3];
        rgb[0] = Integer.parseInt(wallColor.substring(1, 3), 16) / 255.0;
        rgb[1] = Integer.parseInt(wallColor.substring(3, 5), 16) / 255.0;
        rgb[2] = Integer.parseInt(wallColor.substring(5, 7), 16) / 255.0;
        return rgb;
    }

    /**
     * Cette méthode permet de mettre à jour la fenêtre
     * @param subject correspond au sujet observé
     */
    @Override
    public void update(Subject subject) {

    }

    /**
     * Cette méthode permet de mettre à jour la fenêtre en fonction de l'objet passé en paramètre
     * @param subject correspond au sujet observé
     * @param o Object correspond à la donnée à partir de laquelle on met à jour la fenêtre
     */
    @Override
    public void update(Subject subject, Object o) {
        if(o.equals("ResSHOW")) {
            show();
        } else {
            close();
        }
    }
}
