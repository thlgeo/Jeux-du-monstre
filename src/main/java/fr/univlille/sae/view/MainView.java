package fr.univlille.sae.view;

import fr.univlille.sae.Main;
import fr.univlille.sae.controller.menuButton.LaunchButton;
import fr.univlille.sae.controller.menuButton.SettingButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;


public class MainView extends Stage {

    public MainView(){
        setTitle("S3.02_G1_ChasseAuMonstre");
        setResizable(false);
        setMainScene();
        show();
    }

    public void setMainScene(){
        VBox root = new VBox();
        Label title = new Label("Chasse Au Monstre");
        title.setFont(Main.loadFont("arcade_classic_2" + File.separator + "ARCADECLASSIC.TTF", 30));
        VBox.setMargin(title, new Insets(10, 0, 10, 0));
        SettingButton settingButton = new SettingButton();
        VBox.setMargin(settingButton, new Insets(10, 0, 10, 0));
        LaunchButton launchButton = new LaunchButton();
        VBox.setMargin(launchButton, new Insets(10, 0, 10, 0));
        root.getChildren().addAll(title, settingButton, launchButton);
        root.setAlignment(Pos.CENTER);
        setScene(new javafx.scene.Scene(root, 500, 300));
    }
}
