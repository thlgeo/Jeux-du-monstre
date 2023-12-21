package fr.univlille.sae.view;

import fr.univlille.iutinfo.utils.Observer;
import fr.univlille.iutinfo.utils.Subject;
import fr.univlille.sae.controller.*;
import fr.univlille.sae.model.ModelMain;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import fr.univlille.sae.Main;

public class MazeParamView extends Stage implements Observer {
    public static final int WIDTH_VIEW = 500;
    public static final int HEIGHT_VIEW = 500;
    private Label titreHeight;
    private Label titreWidth;
    private SizeController heigth;
    private SizeController width;
    private MazeValidController validation;
    private PercentWallController percentWall;
    private GenerateMazeController generateMaze;
    private ModelMain modelMain;

    public MazeParamView(ModelMain modelMain) {
        this.modelMain = modelMain;
        setTitle("S3.02_G1_ParametresMaze");
        setParameterNodes();
        setParameterScene();
        setResizable(false);
        getIcons().add(Main.loadImage(Main.ICON_URL));
        modelMain.attach(this);
    }

    private void setParameterScene() {
        VBox root = new VBox();
        VBox heightParam = new VBox();
        VBox widthParam = new VBox();
        heightParam.getChildren().addAll(titreHeight, heigth);
        heightParam.setAlignment(Pos.CENTER);
        widthParam.getChildren().addAll(titreWidth, this.width);
        widthParam.setAlignment(Pos.CENTER);
        root.getChildren().addAll(heightParam, new Spacer(), widthParam);
        root.getChildren().addAll(new Spacer(), percentWall);
        root.getChildren().addAll(new Spacer(), generateMaze);
        root.getChildren().addAll(new Spacer(), validation);
        root.setAlignment(Pos.CENTER);
        setScene(new Scene(root, WIDTH_VIEW, HEIGHT_VIEW));
    }

    private void setParameterNodes() {
        titreHeight = new Label("Hauteur");
        titreHeight.setFont(Main.loadFont(Main.ARCADE_FONT, 20));
        titreWidth = new Label("Largeur");
        titreWidth.setFont(Main.loadFont(Main.ARCADE_FONT, 20));
        heigth = new SizeController();
        width = new SizeController();
        validation = new MazeValidController(heigth, width, percentWall, generateMaze, modelMain);
        percentWall = new PercentWallController(0.35);
        generateMaze = new GenerateMazeController();
    }

    @Override
    public void update(Subject subject) {}

    @Override
    public void update(Subject subject, Object o) {
    }
}
