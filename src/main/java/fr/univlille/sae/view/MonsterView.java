package fr.univlille.sae.view;

import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.utils.Observer;
import fr.univlille.iutinfo.utils.Subject;
import fr.univlille.sae.Main;
import fr.univlille.sae.controller.CellController;
import fr.univlille.sae.controller.MazeController;
import fr.univlille.sae.controller.NextTurnController;
import fr.univlille.sae.model.Cell;
import fr.univlille.sae.model.ModelMain;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Cette classe est la fenêtre de jeu du monstre
 *
 * @author Nathan Desmee, Valentin Thuillier, Armand Sady, Théo Lenglart
 * @version 1.0
 */
public class MonsterView extends Stage implements Observer {
    public static final int MARGIN = 150;
    private final ModelMain modelMain;
    private Label titre;
    private Label tour;
    private MazeController mc;
    private Button ready;
    private Label nbTour;
    private NextTurnController nextTurnController;

    public MonsterView(ModelMain modelMain) {
        this.modelMain = modelMain;
        setTitle("S3.02_G1_Monstre");
        setResizable(false);
        setMonsterNodes();
        modelMain.attachMonster(this);
        modelMain.attach(this);
    }


    /**
     * Cette méthode permet de changer la scène de la fenêtre à la scène principale du monstre
     */
    private void setMonsterScene() {
        VBox root = new VBox();
        HBox turnBox = new HBox();
        turnBox.getChildren().addAll(tour, nbTour);
        turnBox.setAlignment(Pos.CENTER);
        root.getChildren().addAll(titre, mc, turnBox);
        setScene(modelMain.getNbCols(), modelMain.getNbRows(), root);
        root.setAlignment(Pos.CENTER);
    }

    private void setMonsterIAScene() {
        VBox root = new VBox();
        root.getChildren().addAll(titre, mc, new Spacer(), nextTurnController);
        setScene(modelMain.getNbCols(), modelMain.getNbRows(), root);
        root.setAlignment(Pos.CENTER);
    }


    /**
     * Cette méthode permet de changer la scène de la fenêtre à la scène d'attente du monstre
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
     * Cette méthode permet de changer la scène de la fenêtre à la scène d'avant de jouer du monstre
     */
    private void setReadyScene() {
        VBox root = new VBox();
        root.getChildren().addAll(ready);
        ready.setOnAction(e -> setMonsterScene());
        setScene(modelMain.getNbCols(), modelMain.getNbRows(), root);
        root.setAlignment(Pos.CENTER);
    }


    /**
     * Cette méthode permet d'initialiser les éléments de la fenêtre du monstre
     */
    private void setMonsterNodes() {
        ready = new Button("Pret !");
        ready.setFont(Main.loadFont(Main.ARCADE_FONT, 30));
        ready.setMinSize(200, 50);
        titre = new Label("Monstre");
        titre.setFont(Main.loadFont(Main.ARCADE_FONT, 30));
        tour = new Label("Cliquez pour commencer !");
        tour.setFont(Main.loadFont(Main.ARCADE_FONT, 30));
        nbTour = new Label("  Tour 1");
        nbTour.setFont(Main.loadFont(Main.ARCADE_FONT, 30));
        mc = new MazeController(modelMain, true);
        nextTurnController = new NextTurnController(modelMain);
    }

    /**
     * Cette méthode permet de mettre à jour la fenêtre
     *
     * @param subject correspond au sujet observé
     */
    @Override
    public void update(Subject subject) {
        if(subject instanceof ModelMain) return;
        setPosition();
        show();
        tour = new Label("Cliquez pour commencer !");
        tour.setFont(Main.loadFont(Main.ARCADE_FONT, 30));
        nbTour = new Label("  Tour 1");
        nbTour.setFont(Main.loadFont(Main.ARCADE_FONT, 30));
        setMonsterScene();
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
            tour.setText("Tour du chasseur !");
            setWaitScene();
        } else if(o instanceof Cell[][] discoveredMaze) {
            mc.resize();
            mc.initMaze(discoveredMaze);
        } else if("cantMove".equals(o)) {
            new Alert(Alert.AlertType.ERROR, "Impossible de vous déplacer sur cette case !").showAndWait();
        } else if("endGame".equals(o)) {
            close();
        } else if("changerTour".equals(o)) {
            tour.setText("Tour du monstre !");
            setReadyScene();
        } else if ("changerTourIA".equals(o)) {
            tour.setText("Tour du monstre !");
            setMonsterScene();
        } else if(o instanceof Integer turn) {
            nbTour.setText("  Tour " + turn);
            nextTurnController.setText("Tour " + turn);
        }else if("showIA".equals(o)) {
            setMonsterIAScene();
            show();
        }
    }

    /**
     * Cette méthode permet de changer la scène de la fenêtre
     *
     * @param nbCols (int) correspond au nombre de colonnes du labyrinthe
     * @param nbRows (int) correspond au nombre de lignes du labyrinthe
     * @param pane   (Pane) correspond au panneau à afficher
     */
    private void setScene(int nbRows, int nbCols, Pane pane) {
        super.setScene(new Scene(pane, calcEffectiveSize(nbRows), calcEffectiveSize(nbCols)));
    }

    private double calcEffectiveSize(double size) {
        return size * CellController.SIZE + MARGIN;
    }

    private void setPosition() {
        double effectiveWidth;
        double effectiveHeight = 0;
        if(modelMain.hunterIsIA()) {
            effectiveWidth = (MainView.BOUNDS.getMaxX() / 2) - calcEffectiveSize(modelMain.getNbCols()) / 2;
        } else if (modelMain.monsterIsIA()){
            effectiveWidth = MainView.BOUNDS.getMinX();
        } else {
            effectiveWidth = MainView.BOUNDS.getMinX();
            effectiveHeight = MainView.BOUNDS.getMinY();
        }
        setX(effectiveWidth);
        setY(effectiveHeight);
    }
}
