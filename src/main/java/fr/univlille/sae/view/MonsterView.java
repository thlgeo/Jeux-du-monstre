package fr.univlille.sae.view;

import fr.univlille.sae.Main;
import fr.univlille.sae.controller.MazeController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;

public class MonsterView extends Stage {
    public static final int WIDTH = 750;
    public static final int HEIGHT = 500;

    public MonsterView(){
        setTitle("S3.02_G1_Monstre");
        setResizable(false);
        setMonsterScene();
        show();
    }

    public void setMonsterScene(){
        VBox root = new VBox();
        Label titre = new Label("Monstre");
        titre.setFont(Main.loadFont("arcade_classic_2" + File.separator + "ARCADECLASSIC.TTF", 30));
        Label tour = new Label("Tour du Monstre");
        tour.setFont(Main.loadFont("arcade_classic_2" + File.separator + "ARCADECLASSIC.TTF", 30));
        MazeController mc = new MazeController();
        root.getChildren().addAll(titre, mc, tour);
        root.setAlignment(Pos.CENTER);
        setScene(new Scene(root, WIDTH, HEIGHT));
    }

}
