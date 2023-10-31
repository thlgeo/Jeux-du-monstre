package fr.univlille.sae.view;

import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.utils.Observer;
import fr.univlille.iutinfo.utils.Subject;
import fr.univlille.sae.Main;
import fr.univlille.sae.controller.MazeController;
import fr.univlille.sae.model.Maze;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Cette classe est la fenêtre de jeu du monstre
 * @Author Nathan Desmee, Valentin Thuillier, Armand Sady, Théo Lenglart
 * @Version 1.0
 */
public class MonsterView extends Stage implements Observer {
    public static final int WIDTH = 750;
    public static final int HEIGHT = 500;
    private Label titre;
    private Label tour;
    private MazeController mc;
    private Maze maze;
    private Button ready;

    public MonsterView(Maze maze){
        this.maze = maze;
        setTitle("S3.02_G1_Monstre");
        setResizable(false);
        setMonsterNodes();
        setMonsterScene();
        show();
    }


    /**
     * Cette méthode permet de changer la scène de la fenêtre à la scène principale du monstre
     */
    public void setMonsterScene(){
        VBox root = new VBox();
        root.getChildren().addAll(titre, mc, tour);
        root.setAlignment(Pos.CENTER);
        setScene(new Scene(root, WIDTH, HEIGHT));
    }


    /**
     * Cette méthode permet de changer la scène de la fenêtre à la scène d'attente du monstre
     */
    public void setWaitScene(){
        VBox root = new VBox();
        root.getChildren().addAll(tour);
        root.setAlignment(Pos.CENTER);
        setScene(new Scene(root, WIDTH, HEIGHT));
    }


    /**
     * Cette méthode permet de changer la scène de la fenêtre à la scène d'avant de jouer du monstre
     */
    public void setReadyScene(){
        VBox root = new VBox();
        root.getChildren().addAll(ready);
        ready.setOnAction(e -> setMonsterScene());
        root.setAlignment(Pos.CENTER);
        setScene(new Scene(root, WIDTH, HEIGHT));
    }


    /**
     * Cette méthode permet d'initialiser les éléments de la fenêtre du monstre
     */
    public void setMonsterNodes(){
        ready = new Button("Prêt ?");
        ready.setFont(Main.loadFont(Main.ARCADE_FONT, 30));
        ready.setMinSize(200, 50);
        titre = new Label("Monstre");
        titre.setFont(Main.loadFont(Main.ARCADE_FONT, 30));
        tour = new Label("Tour du Monstre");
        tour.setFont(Main.loadFont(Main.ARCADE_FONT, 30));
        mc = new MazeController(maze, true);
    }

    /**
     * Cette méthode permet de mettre à jour la fenêtre (non utilisée ici)
     * @param subject correspond au sujet observé
     */
    @Override
    public void update(Subject subject) {
        return;
    }

    /**
     * Cette méthode permet de mettre à jour la fenêtre à partir d'une donnée
     * @param subject correspond au sujet observé
     * @param o correspond à la donnée à partir de laquelle on met à jour la fenêtre   -   (ICellEvent)
     */
    @Override
    public void update(Subject subject, Object o) {
        if(o instanceof ICellEvent cell){ mc.setText(cell.getCoord().getRow(), cell.getCoord().getCol(), cell.getState().toString()); }
    }
}
