package fr.univlille.sae.view.parameter;

import fr.univlille.iutinfo.utils.Observer;
import fr.univlille.iutinfo.utils.Subject;
import fr.univlille.sae.controller.parameter.GenerateMazeParam;
import fr.univlille.sae.controller.parameter.PercentWallParam;
import fr.univlille.sae.controller.parameter.SizeParam;
import fr.univlille.sae.controller.validation.MazeValidController;
import fr.univlille.sae.model.ModelMain;
import fr.univlille.sae.view.Spacer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import fr.univlille.sae.Main;

/**
 * Cette classe est la fenêtre où l'utilisateur peut changer les paramètres du labyrinthe
 *
 * @author Nathan Desmee, Valentin Thuillier, Armand Sady, Théo Lenglart
 * @version 1.0
 */
public class MazeParamView extends Stage implements Observer {
    public static final int WIDTH_VIEW = 500;
    public static final int HEIGHT_VIEW = 500;
    private Label titreHeight;
    private Label titreWidth;
    private Label titrePercent;
    private SizeParam heigth;
    private SizeParam width;
    private MazeValidController validation;
    private PercentWallParam percentWall;
    private GenerateMazeParam generateMaze;
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

    /**
     * Cette méthode permet de changer la scène de la fenêtre à la scène principale de la page de paramétrage
     */
    private void setParameterScene() {
        VBox root = new VBox();
        VBox heightParam = new VBox();
        VBox widthParam = new VBox();
        heightParam.getChildren().addAll(titreHeight, heigth);
        heightParam.setAlignment(Pos.CENTER);
        widthParam.getChildren().addAll(titreWidth, this.width);
        widthParam.setAlignment(Pos.CENTER);
        root.getChildren().addAll(heightParam, new Spacer(), widthParam);
        root.getChildren().addAll(new Spacer(), titrePercent, new Spacer(), percentWall);
        root.getChildren().addAll(new Spacer(), generateMaze);
        root.getChildren().addAll(new Spacer(), validation);
        root.setAlignment(Pos.CENTER);
        setScene(new Scene(root, WIDTH_VIEW, HEIGHT_VIEW));
    }

    /**
     * Cette méthode permet d'initialiser les différents éléments de la fenêtre
     */
    private void setParameterNodes() {
        titreHeight = new Label("Hauteur");
        titreHeight.setFont(Main.loadFont(Main.ARCADE_FONT, 20));
        titreWidth = new Label("Largeur");
        titreWidth.setFont(Main.loadFont(Main.ARCADE_FONT, 20));
        titrePercent = new Label("Pourcentage imperfection");
        titrePercent.setFont(Main.loadFont(Main.ARCADE_FONT, 20));
        heigth = new SizeParam();
        width = new SizeParam();
        percentWall = new PercentWallParam(0.35);
        generateMaze = new GenerateMazeParam();
        validation = new MazeValidController(heigth, width, percentWall, generateMaze, modelMain);
    }

    /**
     * Cette méthode permet de mettre à jour la fenêtre
     *
     * @param subject correspond au sujet observé
     */
    @Override
    public void update(Subject subject) {}

    /**
     * Cette méthode permet de mettre à jour la fenêtre à partir d'une donnée
     *
     * @param subject correspond au sujet observé
     * @param o       correspond à la donnée à partir de laquelle on met à jour la fenêtre
     */
    @Override
    public void update(Subject subject, Object o) {
        if(o.equals("MazeParamSHOW")){
            show();
        } else {
            close();
        }
    }
}
