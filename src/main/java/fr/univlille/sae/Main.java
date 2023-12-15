package fr.univlille.sae;

import fr.univlille.sae.model.ModelMain;
import fr.univlille.sae.view.HunterView;
import fr.univlille.sae.view.MainView;
import fr.univlille.sae.view.MonsterView;
import fr.univlille.sae.view.ParameterView;
import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Cette classe permet de lancer l'application
 *
 * @author Nathan Desmee, Valentin Thuillier, Armand Sady, Théo Lenglart
 * @version 1.0
 */
public class Main extends Application {
    public static final String FONT_DIR = System.getProperty("user.dir") + File.separator + "res" + File.separator + "font" + File.separator;
    public static final String MUSIC_DIR = System.getProperty("user.dir") + File.separator + "res" + File.separator + "music" + File.separator;
    public static final String ARCADE_FONT = "arcade_classic_2" + File.separator + "ARCADECLASSIC.TTF";

    /**
     * Cette méthode permet de lancer l'application
     *
     * @param args correspond aux arguments de lancement, mais non utilisés ici
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Cette méthode permet de charger une police d'écriture
     *
     * @param path : le chemin vers la police d'écriture depuis le dossier res/font
     * @param size : la taille de la police d'écriture
     * @return Font : la police d'écriture chargée
     */
    public static Font loadFont(String path, int size) {
        try {
            return Font.loadFont(new FileInputStream(FONT_DIR + path), size);
        } catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return Font.font("Arial", size);
    }

    public static MediaPlayer loadMusic(String path, double volume) {
        File f = new File(path);
        Media m = new Media(f.toURI().toString());
        MediaPlayer mp = new MediaPlayer(m);
        mp.setVolume(volume);
        return mp;
    }

    /**
     * Cette méthode permet de lancer le Stage Principal de l'application
     *
     * @param stage correspond au stage par défaut de l'application, mais non utilisé ici
     */
    @Override
    public void start(Stage stage) {
        ModelMain modelMain = new ModelMain();
        new MainView(modelMain);
        new ParameterView(modelMain);
        new HunterView(modelMain);
        new MonsterView(modelMain);
    }

}
