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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Cette classe est la fenêtre de jeu du monstre
 */
public class MonsterView extends Stage implements Observer {
    public static final double WIDTH = 150.0d;
    public static final double HEIGHT = 100.0d;
    private final Maze maze;
    private Label titre;
    private Label tour;
    private MazeController mc;
    private Button ready;

    public MonsterView(Maze maze) {
        this.maze = maze;
        setTitle("S3.02_G1_Monstre");
        setResizable(false);
        setMonsterNodes();
        setMonsterScene();
        maze.attachMonster(this);
    }


    /**
     * Cette méthode permet de changer la scène de la fenêtre à la scène principale du monstre
     */
    public void setMonsterScene() {
        VBox root = new VBox();
        root.getChildren().addAll(titre, mc, tour);
        root.setAlignment(Pos.CENTER);
        setScene(mc.getSize(), root);
    }


    /**
     * Cette méthode permet de changer la scène de la fenêtre à la scène d'attente du monstre
     */
    public void setWaitScene() {
        VBox root = new VBox();
        root.getChildren().addAll(tour);
        root.setAlignment(Pos.CENTER);
        setScene(mc.getSize(), root);
    }


    /**
     * Cette méthode permet de changer la scène de la fenêtre à la scène d'avant de jouer du monstre
     */
    public void setReadyScene() {
        VBox root = new VBox();
        root.getChildren().addAll(ready);
        ready.setOnAction(e -> setMonsterScene());
        root.setAlignment(Pos.CENTER);
        setScene(mc.getSize(), root);
    }


    /**
     * Cette méthode permet d'initialiser les éléments de la fenêtre du monstre
     */
    public void setMonsterNodes() {
        ready = new Button("Pret !");
        ready.setFont(Main.loadFont(Main.ARCADE_FONT, 30));
        ready.setMinSize(200, 50);
        titre = new Label("Monstre");
        titre.setFont(Main.loadFont(Main.ARCADE_FONT, 30));
        tour = new Label("Cliquez sur une case pour commencer");
        tour.setFont(Main.loadFont(Main.ARCADE_FONT, 30));
        mc = new MazeController(maze, true);
    }

    /**
     * Cette méthode permet de mettre à jour la fenêtre (non utilisée ici)
     *
     * @param subject correspond au sujet observé
     */
    @Override
    public void update(Subject subject) {
        show();
        tour = new Label("Cliquez sur une case pour commencer");
        tour.setFont(Main.loadFont(Main.ARCADE_FONT, 30));
        setMonsterScene();
    }

    /**
     * Cette méthode permet de mettre à jour la fenêtre à partir d'une donnée
     *
     * @param subject correspond au sujet observé
     * @param o       correspond à la donnée à partir de laquelle on met à jour la fenêtre   -   (ICellEvent)
     */
    @Override
    public void update(Subject subject, Object o) {
        if(o instanceof ICellEvent cell) {
            mc.setRender(cell.getCoord().getRow(), cell.getCoord().getCol(), Cell.render(cell.getState(), cell.getTurn()));
            tour.setText("Tour du chasseur !");
            setWaitScene();
        } else if(o instanceof Cell[][] discoveredMaze) {
            mc.resize();
            resize();
            mc.initMaze(discoveredMaze);
        } else if("cantMove".equals(o)) {
            new Alert(Alert.AlertType.ERROR, "Impossible de vous déplacer sur cette case !").showAndWait();
        } else if("endGame".equals(o)) {
            close();
        } else if("changerTour".equals(o)) {
            tour.setText("Tour du monstre !");
            setReadyScene();
        }
    }

    /**
     * Cette méthode permet de changer la scène de la fenêtre
     *
     * @param size (int)   correspond à la taille du labyrinthe
     * @param pane (Pane)  correspond au panneau à afficher
     */
    public void setScene(int size, Pane pane) {
        super.setScene(new Scene(pane, size * CellController.SIZE + WIDTH, size * CellController.SIZE + HEIGHT));
    }

    /**
     * Cette méthode permet de redimensionner la fenêtre
     */
    public void resize() {
        setWidth(this.mc.getSize() * CellController.SIZE + WIDTH);
        setHeight(this.mc.getSize() * CellController.SIZE + HEIGHT);
    }

}
