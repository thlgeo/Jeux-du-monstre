package fr.univlille.sae.view;

import fr.univlille.sae.Main;
import fr.univlille.sae.controller.MazeController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;

public class HunterView extends Stage {
    public static final int HEIGHT = 500;
    public static final int WIDTH = 300;

    public HunterView(){
        setTitle("S3.02_G1_Chasseur");
        setResizable(false);
        setHunterScene();
        show();
    }

    public void setHunterScene(){
        VBox root = new VBox();
        Label titre = new Label("Chasseur");
        titre.setFont(Main.loadFont("arcade_classic_2" + File.separator + "ARCADECLASSIC.TTF", 30));
        Label tour = new Label("Tour du Monstre");
        tour.setFont(Main.loadFont("arcade_classic_2" + File.separator + "ARCADECLASSIC.TTF", 30));
        MazeController mc = new MazeController();
        root.getChildren().addAll(titre, mc, tour);
        root.setAlignment(Pos.CENTER);
        setScene(new Scene(root, HEIGHT, WIDTH));
    }
}
