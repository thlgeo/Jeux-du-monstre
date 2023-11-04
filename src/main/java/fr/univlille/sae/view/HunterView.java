package fr.univlille.sae.view;

import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.utils.Observer;
import fr.univlille.iutinfo.utils.Subject;
import fr.univlille.sae.Main;
import fr.univlille.sae.controller.CellController;
import fr.univlille.sae.controller.MazeController;
import fr.univlille.sae.model.Cell;
import fr.univlille.sae.model.Maze;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * Cette classe est la fenêtre de jeu du chasseur
 * @author Nathan Desmee, Valentin Thuillier, Armand Sady, Théo Lenglart
 * @version  1.0
 */
public class HunterView extends Stage implements Observer {
    public static final double WIDTH = 150.0d;
    public static final double HEIGHT = 100.0d;
    public static final int MARGIN=150;
    private final Maze maze;
    private Label titre;
    private Label tour;
    private MazeController mc;
    private Button ready;

    public HunterView(Maze maze) {
        this.maze = maze;
        setTitle("S3.02_G1_Chasseur");
        setResizable(false);
        setHunterNodes();
        setX(MainView.BOUNDS.getMaxX()/2+WIDTH);
        setY(MainView.BOUNDS.getMinY()+MARGIN);
        maze.attachHunter(this);
    }

    /**
     * Cette méthode permet de changer la scène de la fenêtre à la scène principale du chasseur
     */
    private void setHunterScene() {
        VBox root = new VBox();
        root.getChildren().addAll(titre, mc, tour);
        setScene(mc.getSize(), root);
        root.setAlignment(Pos.CENTER);
    }

    /**
     * Cette méthode permet de changer la scène de la fenêtre à la scène d'attente du chasseur
     */
    private void setWaitScene() {
        VBox root = new VBox();
        root.getChildren().addAll(tour);
        setScene(mc.getSize(), root);
        root.setAlignment(Pos.CENTER);
    }


    /**
     * Cette méthode permet de changer la scène de la fenêtre à la scène d'avant de jouer du chasseur
     */
    private void setReadyScene() {
        VBox root = new VBox();
        root.getChildren().addAll(ready);
        ready.setOnAction(e -> setHunterScene());
        setScene(mc.getSize(), root);
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
        mc = new MazeController(maze, false);
    }

    /**
     * Cette méthode permet de mettre à jour la fenêtre
     *
     * @param subject correspond au sujet observé
     */
    @Override
    public void update(Subject subject) {
        show();
        setWaitScene();
    }

    /**
     * Cette méthode permet de mettre à jour la fenêtre à partir d'une donnée
     *
      * @param subject correspond au sujet observé
     * @param o correspond à la donnée à partir de laquelle on met à jour la fenêtre
     */
    @Override
    public void update(Subject subject, Object o) {
        if(o instanceof ICellEvent cell) {
            mc.setRender(cell.getCoord().getRow(), cell.getCoord().getCol(), Cell.render(cell.getState(), cell.getTurn()));
            tour.setText("Tour du monstre !");
            setWaitScene();
        } else if(o instanceof Cell[][]) {
            mc.resize();
        } else if("endGame".equals(o)) {
            close();
        } else if(o.equals("changerTour")) {
            tour.setText("Tour du chasseur !");
            setReadyScene();
        }
    }

    /**
     * Cette méthode permet de changer la scène de la fenêtre
     *
     * @param size correspond à la taille du labyrinthe
     * @param pane correspond au panneau à afficher
     */
    private void setScene(int size, Pane pane) {
        super.setScene(new Scene(pane, size * CellController.SIZE + WIDTH, size * CellController.SIZE + HEIGHT));
    }

}
