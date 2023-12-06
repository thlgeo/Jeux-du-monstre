package fr.univlille.sae.view;

import fr.univlille.iutinfo.utils.Observer;
import fr.univlille.iutinfo.utils.Subject;
import fr.univlille.sae.Main;
import fr.univlille.sae.controller.*;
import fr.univlille.sae.model.ModelMain;
import fr.univlille.sae.model.ModelMainInterface;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Cette classe est la fenêtre où l'utilisateur peut changer les paramètres
 * @author Nathan Desmee, Valentin Thuillier, Armand Sady, Théo Lenglart
 * @version  1.0
 */
public class ParameterView extends Stage implements Observer {
    public static final int WIDTH = 500;
    public static final int HEIGHT = 400;
    private final ModelMainInterface modelMain;
    private Label nameMonster;
    private Label nameHunter;
    private Label titreHeight;
    private Label titreWidth;
    private NameController monsterName;
    private NameController hunterName;
    private SizeController heigth;
    private SizeController width;
    private ValidationController validation;
    private DepDiagController depDiag;
    private FogController fog;

    public ParameterView(ModelMainInterface modelMain) {
        this.modelMain = modelMain;
        setTitle("S3.02_G1_Parametres");
        setParameterNodes();
        setParameterScene();
        setResizable(false);
        modelMain.attach(this);
    }


    /**
     * Cette méthode permet de changer la scène de la fenêtre à la scène principale de la page de paramétrage
     */
    private void setParameterScene() {
        VBox root = new VBox();
        root.getChildren().addAll(nameMonster, monsterName);
        root.getChildren().addAll(new Spacer(), nameHunter, hunterName);
        root.getChildren().addAll(new Spacer(), titreHeight, heigth);
        root.getChildren().addAll(new Spacer(), titreWidth, width);
        root.getChildren().addAll(new Spacer(), depDiag);
        root.getChildren().addAll(new Spacer(), fog);
        root.getChildren().addAll(new Spacer(), validation);
        root.setAlignment(Pos.CENTER);
        setScene(new Scene(root, WIDTH, HEIGHT));
    }


    /**
     * Cette méthode permet d'initialiser les éléments de la scène principale de la page de paramétrage
     */
    private void setParameterNodes() {
        nameMonster = new Label("Nom du Monstre");
        nameMonster.setFont(Main.loadFont(Main.ARCADE_FONT, 20));
        nameHunter = new Label("Nom du Chasseur");
        nameHunter.setFont(Main.loadFont(Main.ARCADE_FONT, 20));
        titreHeight = new Label("hauteur du labyrinthe");
        titreHeight.setFont(Main.loadFont(Main.ARCADE_FONT, 20));
        titreWidth = new Label("largeur du labyrinthe");
        titreWidth.setFont(Main.loadFont(Main.ARCADE_FONT, 20));
        monsterName = new NameController(true);
        hunterName = new NameController(false);
        heigth = new SizeController();
        width = new SizeController();
        depDiag = new DepDiagController();
        fog = new FogController();
        validation = new ValidationController(monsterName, hunterName, heigth, width, depDiag, fog, modelMain);
    }

    /**
     * Cette méthode permet de mettre à jour la fenêtre
     *
     * @param subject correspond au sujet observé
     */
    @Override
    public void update(Subject subject) {
        show();
    }

    /**
     * Cette méthode permet de mettre à jour la fenêtre à partir d'une donnée
     *
     * @param subject correspond au sujet observé
     * @param o correspond à la donnée à partir de laquelle on met à jour la fenêtre
     */
    @Override
    public void update(Subject subject, Object o) {
        close();
    }
}
