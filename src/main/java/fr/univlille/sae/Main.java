package fr.univlille.sae;

import fr.univlille.sae.view.MainView;
import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Cette classe permet de lancer l'application
 * @Author : Nathan Desmee, Valentin Thuillier, Armand Sady, Théo Lenglart
 * @Version : 1.0
 */
public class Main extends Application {
    public static final String FONT_DIR = System.getProperty("user.dir") + File.separator + "res" + File.separator + "font" + File.separator;

    /**
     * Cette méthode permet de lancer le Stage Principal de l'application
     * @param stage correspond au stage par défaut de l'application, mais non utilisé ici
     * @throws Exception retourne une exception si le stage ne peut pas être lancé
     */
    @Override
    public void start(Stage stage) throws Exception {
        new MainView();
    }

    /**
     * Cette méthode permet de lancer l'application
     * @param args correspond aux arguments de lancement, mais non utilisés ici
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
    * Cette méthode permet de charger une police d'écriture
    * @param path : le chemin vers la police d'écriture depuis le dossier res/font
    * @param size : la taille de la police d'écriture
    * @return Font : la police d'écriture chargée
     */
    public static Font loadFont(String path, int size){
        try {
            return Font.loadFont(new FileInputStream(new File(FONT_DIR + path)), size);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return Font.font("Arial", size);
    }

}
