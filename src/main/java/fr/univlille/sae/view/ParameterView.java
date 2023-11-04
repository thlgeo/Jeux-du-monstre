package fr.univlille.sae.view;

import fr.univlille.iutinfo.utils.Observer;
import fr.univlille.iutinfo.utils.Subject;
import fr.univlille.sae.Main;
import fr.univlille.sae.controller.NameController;
import fr.univlille.sae.controller.SizeController;
import fr.univlille.sae.controller.ValidationController;
import fr.univlille.sae.model.Maze;
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
    public static final int HEIGHT = 300;
    private final Maze maze;
    private Label nameMonster;
    private Label nameHunter;
    private Label titreHeight;
    private NameController monsterName;
    private NameController hunterName;
    private SizeController size;
    private ValidationController validation;

    public ParameterView(Maze maze) {
        this.maze = maze;
        setTitle("S3.02_G1_Parametres");
        setParameterNodes();
        setParameterScene();
        setResizable(false);
        maze.attach(this);
    }


    /**
     * Cette méthode permet de changer la scène de la fenêtre à la scène principale de la page de paramétrage
     */
    public void setParameterScene() {
        VBox root = new VBox();
        Region spacer1 = new Region();
        Region spacer2 = new Region();
        Region spacer3 = new Region();
        spacer1.setMinHeight(10);
        spacer2.setMinHeight(10);
        spacer3.setMinHeight(10);
        root.getChildren().addAll(nameMonster, monsterName, spacer1, nameHunter, hunterName, spacer2, titreHeight, size, spacer3, validation);
        root.setAlignment(Pos.CENTER);
        setScene(new Scene(root, WIDTH, HEIGHT));
    }


    /**
     * Cette méthode permet d'initialiser les éléments de la scène principale de la page de paramétrage
     */
    public void setParameterNodes() {
        nameMonster = new Label("Nom du Monstre");
        nameMonster.setFont(Main.loadFont(Main.ARCADE_FONT, 20));
        nameHunter = new Label("Nom du Chasseur");
        nameHunter.setFont(Main.loadFont(Main.ARCADE_FONT, 20));
        titreHeight = new Label("taille du labyrinthe");
        titreHeight.setFont(Main.loadFont(Main.ARCADE_FONT, 20));
        monsterName = new NameController(true);
        hunterName = new NameController(false);
        size = new SizeController();
        validation = new ValidationController(monsterName, hunterName, size, maze);
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
