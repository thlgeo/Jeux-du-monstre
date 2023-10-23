package fr.univlille.sae.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class MainView extends Stage {
    public static final String fontDir = System.getProperty("user.dir") + File.separator + "res" + File.separator + "font" + File.separator;

    public MainView(){
        setTitle("S3.02_G1_ChasseAuMonstre");
        setResizable(false);
        setMainScene();
        show();
    }

    public void setMainScene(){
        HBox root = new HBox();
        Label title = new Label("Chasse Au Monstre");
        title.setFont(loadFont("arcade_classic_2" + File.separator + "ARCADECLASSIC.TTF", 30));
        root.getChildren().add(title);
        root.setAlignment(Pos.CENTER);
        setScene(new javafx.scene.Scene(root, 500, 300));
    }

    public Font loadFont(String path, int size){
        try {
            return Font.loadFont(new FileInputStream(new File(fontDir + path)), size);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return Font.font("Arial", size);
    }
}
