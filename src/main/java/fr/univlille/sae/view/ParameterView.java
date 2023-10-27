package fr.univlille.sae.view;

import fr.univlille.sae.Main;
import fr.univlille.sae.controller.SizeController;
import fr.univlille.sae.controller.NameController;
import fr.univlille.sae.controller.ValidationController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;

/**
 * Cette classe est la fenêtre où l'utilisateur peut changer les paramètres
 * @Author Nathan Desmee, Valentin Thuillier, Armand Sady, Théo Lenglart
 * @Version 1.0
 */
public class ParameterView extends Stage {
    public static final int WIDTH = 500;
    public static final int HEIGHT = 300;

    public ParameterView(){
        setTitle("S3.02_G1_Parametres");
        setParameterScene();
        setResizable(false);
        show();
    }

    public void setParameterScene(){
        VBox root = new VBox();
        Label nameMonster = new Label("Nom du Monstre");
        nameMonster.setFont(Main.loadFont("arcade_classic_2" + File.separator + "ARCADECLASSIC.TTF", 30));
        NameController monsterName = new NameController(true);
        Label nameHunter = new Label("Nom du Chasseur");
        nameHunter.setFont(Main.loadFont("arcade_classic_2" + File.separator + "ARCADECLASSIC.TTF", 30));
        NameController hunterName = new NameController(false);
        SizeController height = new SizeController();
        SizeController width = new SizeController();
        ValidationController validation = new ValidationController(monsterName, hunterName, height, width);
        Region spacer1 = new Region();
        Region spacer2 = new Region();
        Region spacer3 = new Region();
        Region spacer4 = new Region();
        spacer1.setMinHeight(10);
        spacer2.setMinHeight(10);
        spacer3.setMinHeight(10);
        spacer4.setMinHeight(10);
        root.getChildren().addAll(nameMonster, monsterName, spacer1, nameHunter, hunterName, spacer2, height, spacer3, width, spacer4, validation);
        root.setAlignment(Pos.CENTER);
        setScene(new Scene(root, WIDTH, HEIGHT));
    }
}
