package fr.univlille.sae;

import fr.univlille.sae.view.MainView;
import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main extends Application {
    public static final String FONT_DIR = System.getProperty("user.dir") + File.separator + "res" + File.separator + "font" + File.separator;

    @Override
    public void start(Stage stage) throws Exception {
        new MainView();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Font loadFont(String path, int size){
        try {
            return Font.loadFont(new FileInputStream(new File(FONT_DIR + path)), size);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return Font.font("Arial", size);
    }

}
