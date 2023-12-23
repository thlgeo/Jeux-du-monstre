package fr.univlille.sae.view;

import fr.univlille.iutinfo.utils.Observer;
import fr.univlille.iutinfo.utils.Subject;
import fr.univlille.sae.Main;
import fr.univlille.sae.controller.maze.MazeController;
import fr.univlille.sae.model.ModelMain;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

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

    private double[] convertHexToRGB(String wallColor) {
        double[] rgb = new double[3];
        rgb[0] = Integer.parseInt(wallColor.substring(1, 3), 16) / 255.0;
        rgb[1] = Integer.parseInt(wallColor.substring(3, 5), 16) / 255.0;
        rgb[2] = Integer.parseInt(wallColor.substring(5, 7), 16) / 255.0;
        return rgb;
    }

    @Override
    public void update(Subject subject) {

    }

    @Override
    public void update(Subject subject, Object o) {
        if(o.equals("ResSHOW")) {
            show();
        } else {
            close();
        }
    }
}
