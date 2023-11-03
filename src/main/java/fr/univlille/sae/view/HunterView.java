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
 * @Author Nathan Desmee, Valentin Thuillier, Armand Sady, Théo Lenglart
 * @Version 1.0
 */
public class HunterView extends Stage implements Observer {
    public static int WIDTH = 150;
    public static int HEIGHT = 100;

    private Label titre;
    private Label tour;
    private MazeController mc;
    private Maze maze;
    private Button ready;

    public HunterView(Maze maze){
        this.maze = maze;
        setTitle("S3.02_G1_Chasseur");
        setResizable(false);
        setHunterNodes();
        setWaitScene();
        maze.attachHunter(this);
    }

    /**
     * Cette méthode permet de changer la scène de la fenêtre à la scène principale du chasseur
     */
    public void setHunterScene(){
        VBox root = new VBox();
        root.getChildren().addAll(titre, mc, tour);
        root.setAlignment(Pos.CENTER);
        setScene(mc.getSize(), root);
    }

    /**
     * Cette méthode permet de changer la scène de la fenêtre à la scène d'attente du chasseur
     */
    public void setWaitScene(){
        VBox root = new VBox();
        root.getChildren().addAll(tour);
        root.setAlignment(Pos.CENTER);
        setScene(mc.getSize(), root);
    }


    /**
     * Cette méthode permet de changer la scène de la fenêtre à la scène d'avant de jouer du chasseur
     */
    public void setReadyScene(){
        VBox root = new VBox();
        root.getChildren().addAll(ready);
        ready.setOnAction(e -> setHunterScene());
        root.setAlignment(Pos.CENTER);
        setScene(mc.getSize(), root);
    }

    /**
     * Cette méthode permet d'initialiser les éléments de la fenêtre du chasseur
     */
    public void setHunterNodes(){
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
     * Cette méthode permet de mettre à jour la fenêtre (non utilisée ici)
     * @param subject correspond au sujet observé
     */
    @Override
    public void update(Subject subject) {
        show();
        setWaitScene();
    }

    /**
     * Cette méthode permet de mettre à jour la fenêtre à partir d'une donnée
     * @param subject correspond au sujet observé
     * @param o correspond à la donnée à partir de laquelle on met à jour la fenêtre    -   (ICellEvent)
     */
    @Override
    public void update(Subject subject, Object o) {
        if(o instanceof ICellEvent cell) {
            mc.setRender(cell.getCoord().getRow(), cell.getCoord().getCol(), Cell.render(cell.getState(), cell.getTurn()));
            tour.setText("Tour du monstre !");
            setWaitScene();
        } else if(o instanceof Cell[][]) {
            mc.resize();
            resize();
        } else if("endGame".equals(o)) {
            close();
        } else if (o.equals("changerTour")) {
            tour.setText("Tour du chasseur !");
            setReadyScene();
        }
    }

    public void setScene(int size, Pane pane) {
        super.setScene(new Scene(pane, size * CellController.SIZE + WIDTH, size * CellController.SIZE + HEIGHT));
    }

    public void resize() {
        int width = this.mc.getSize() * CellController.SIZE + WIDTH;
        int height = this.mc.getSize() * CellController.SIZE + HEIGHT;
        if(width != getWidth()) setWidth(width);
        if(height != getHeight()) setHeight(height);
    }

}
