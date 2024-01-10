package fr.univlille.sae.view;

import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.utils.Observer;
import fr.univlille.iutinfo.utils.Subject;
import fr.univlille.sae.Main;
import fr.univlille.sae.controller.maze.CellController;
import fr.univlille.sae.controller.maze.MazeController;
import fr.univlille.sae.model.Cell;
import fr.univlille.sae.model.ModelMain;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * Cette classe est la fenêtre de jeu du chasseur
 *
 * @author Nathan Desmee, Valentin Thuillier, Armand Sady, Théo Lenglart
 * @version 1.0
 */
public class HunterView extends Stage implements Observer {
    public static final int MARGIN = 150;

    private final ModelMain modelMain;
    private Label titre;
    private Label tour;
    private MazeController mc;
    private Button ready;
    private Label nbTour;

    public HunterView(ModelMain modelMain) {
        this.modelMain = modelMain;
        setTitle("S3.02_G1_Chasseur");
        setResizable(false);
        setHunterNodes();
        getIcons().add(Main.loadImage(Main.ICON_URL));
        modelMain.attachHunter(this);
    }

    /**
     * Cette méthode permet de changer la scène de la fenêtre à la scène principale du chasseur
     */
    private void setHunterScene() {
        VBox root = new VBox();
        HBox turnBox = new HBox();
        turnBox.getChildren().addAll(tour, nbTour);
        turnBox.setAlignment(Pos.CENTER);
        root.getChildren().addAll(titre, mc, turnBox);
        setScene(modelMain.getNbCols(), modelMain.getNbRows(), root);
        root.setAlignment(Pos.CENTER);
    }

    /**
     * Cette méthode permet de changer la scène de la fenêtre à la scène d'attente du chasseur
     */
    private void setWaitScene() {
        VBox root = new VBox();
        HBox turnBox = new HBox();
        turnBox.getChildren().addAll(tour, nbTour);
        turnBox.setAlignment(Pos.CENTER);
        root.getChildren().addAll(turnBox);
        setScene(modelMain.getNbCols(), modelMain.getNbRows(), root);
        root.setAlignment(Pos.CENTER);
    }


    /**
     * Cette méthode permet de changer la scène de la fenêtre à la scène d'avant de jouer du chasseur
     */
    private void setReadyScene() {
        VBox root = new VBox();
        root.getChildren().addAll(ready);
        ready.setOnAction(e -> setHunterScene());
        setScene(modelMain.getNbCols(), modelMain.getNbRows(), root);
        root.setAlignment(Pos.CENTER);
    }

    /**
     * Cette méthode permet d'initialiser les éléments de la fenêtre du chasseur
     */
    private void setHunterNodes() {
        ready = new Button("Pret !");
        ready.setFont(Main.loadFont(Main.ARCADE_FONT, 30));
        ready.setMinSize(200, 50);
        titre = new Label("Chasseur");
        titre.setFont(Main.loadFont(Main.ARCADE_FONT, 30));
        tour = new Label("Tour du monstre !");
        tour.setFont(Main.loadFont(Main.ARCADE_FONT, 30));
        nbTour = new Label("  Tour 1");
        nbTour.setFont(Main.loadFont(Main.ARCADE_FONT, 30));
        mc = new MazeController(modelMain, false);
    }

    /**
     * Cette méthode permet de mettre à jour la fenêtre
     *
     * @param subject correspond au sujet observé
     */
    @Override
    public void update(Subject subject) {
        setPosition();
        montrer();
        tour = new Label("Tour de " + modelMain.getMonsterName() + " !");
        tour.setFont(Main.loadFont(Main.ARCADE_FONT, 30));
        nbTour = new Label("  Tour 1");
        nbTour.setFont(Main.loadFont(Main.ARCADE_FONT, 30));

    }

    /**
     * Cette méthode permet de mettre à jour la fenêtre à partir d'une donnée
     *
     * @param subject correspond au sujet observé
     * @param o       correspond à la donnée à partir de laquelle on met à jour la fenêtre
     */
    @Override
    public void update(Subject subject, Object o) {
        if(o instanceof ICellEvent cell) {
            mc.setRender(cell.getCoord().getRow(), cell.getCoord().getCol(), cell.getState(), cell.getTurn());
            tour.setText("Tour de " + modelMain.getMonsterName() + " !");
            setWaitScene();
        } else if(o instanceof Cell[][]) {
            mc.resize();
            setWaitScene();
        } else if("endGame".equals(o)) {
            close();
        } else if(o.equals("changerTour")) {
            tour.setText("Tour de " + modelMain.getHunterName() + " !");
            setReadyScene();
        } else if(o.equals("changerTourIA")) {
            tour.setText("Tour de " + modelMain.getHunterName() + " !");
            setHunterScene();
        } else if(o instanceof Integer turn) {
            nbTour.setText("  Tour " + turn);
        }
    }

    /**
     * Cette méthode permet de changer la scène de la fenêtre
     *
     * @param nbCols (int) correspond au nombre de colonnes du labyrinthe
     * @param nbRows (int) correspond au nombre de lignes du labyrinthe
     * @param pane   (Pane) correspond au panneau à afficher
     */
    private void setScene(int nbCols, int nbRows, Pane pane) {
        super.setScene(new Scene(pane, calcEffectiveSize(nbCols), calcEffectiveSize(nbRows)));
    }

    /**
     * Cette méthode permet de calculer la taille effective de la fenêtre
     * @param size (double) correspond à la taille de base
     * @return (double) la taille effective
     */
    private double calcEffectiveSize(double size) {
        return size * CellController.SIZE + MARGIN;
    }

    /**
     * Cette méthode permet de changer la position de la fenêtre
     */
    private void setPosition() {
        double effectiveWidth;
        double effectiveHeight = 0;
        if(modelMain.monsterIsIA()) {
            effectiveWidth = (MainView.BOUNDS.getMaxX() / 2) - calcEffectiveSize(modelMain.getNbCols()) / 2;
        } else if (modelMain.hunterIsIA()){
            effectiveWidth = MainView.BOUNDS.getMaxX();
        } else {
            effectiveWidth = MainView.BOUNDS.getMaxX() - calcEffectiveSize(modelMain.getNbCols());
            effectiveHeight = MainView.BOUNDS.getMinY();
        }
        setX(effectiveWidth);
        setY(effectiveHeight);
    }

    /**
     * Cette méthode permet d'afficher la fenêtre
     */
    private void montrer(){
        if(!modelMain.hunterIsIA()){
            show();
        }
    }
}
