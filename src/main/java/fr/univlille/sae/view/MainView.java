package fr.univlille.sae.view;

import fr.univlille.iutinfo.utils.Observer;
import fr.univlille.iutinfo.utils.Subject;
import fr.univlille.sae.Main;
import fr.univlille.sae.controller.LaunchButton;
import fr.univlille.sae.controller.SettingButton;
import fr.univlille.sae.model.Maze;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;

/**
 * Cette classe est la fenêtre principale de l'application
 * @Author Nathan Desmee, Valentin Thuillier, Armand Sady, Théo Lenglart
 * @Version 1.0
 */
public class MainView extends Stage implements Observer {

    public static final int WIDTH = 500;
    public static final int HEIGHT = 300;
    private Maze maze;
    private Label titre;
    private SettingButton settingButton;
    private LaunchButton launchButton;

    public MainView(Maze maze){
        this.maze = maze;
        setTitle("S3.02_G1_ChasseAuMonstre");
        setResizable(false);
        setMainNodes();
        setMainScene();
        show();
    }

    /**
     * Cette méthode permet de mettre en place la scène principale
     */
    public void setMainScene(){
        VBox root = new VBox();
        VBox.setMargin(titre, new Insets(10, 0, 10, 0));
        VBox.setMargin(settingButton, new Insets(10, 0, 10, 0));
        VBox.setMargin(launchButton, new Insets(10, 0, 10, 0));
        root.getChildren().addAll(titre, settingButton, launchButton);
        root.setAlignment(Pos.CENTER);
        setScene(new Scene(root, WIDTH, HEIGHT));
    }

    /**
     * Cette méthode permet d'initialiser les éléments de la fenêtre principale
     */
    public void setMainNodes(){
        titre = new Label("Chasse Au Monstre");
        titre.setFont(Main.loadFont(Main.ARCADE_FONT, 30));
        settingButton = new SettingButton(maze);
        launchButton = new LaunchButton(maze);
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
     * @param o correspond à la donnée à partir de laquelle on met à jour la fenêtre
     */
    @Override
    public void update(Subject subject, Object o) {

    }
}
