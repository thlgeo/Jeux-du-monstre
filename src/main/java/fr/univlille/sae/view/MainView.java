package fr.univlille.sae.view;

import fr.univlille.iutinfo.utils.Observer;
import fr.univlille.iutinfo.utils.Subject;
import fr.univlille.sae.Main;
import fr.univlille.sae.controller.LaunchButton;
import fr.univlille.sae.controller.RessourcesButton;
import fr.univlille.sae.controller.SettingButton;
import fr.univlille.sae.model.ModelMain;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Cette classe est la fenêtre principale de l'application
 *
 * @author Nathan Desmee, Valentin Thuillier, Armand Sady, Théo Lenglart
 * @version 1.0
 */
public class MainView extends Stage implements Observer {

    public static final int WIDTH = 500;
    public static final int HEIGHT = 300;
    public static final Rectangle2D BOUNDS = Screen.getPrimary().getBounds();
    private final ModelMain modelMain;
    private Label titre;
    private SettingButton settingButton;
    private LaunchButton launchButton;
    private Button quitButton;
    private Button noMusicButton;
    private MediaPlayer mp;

    public MainView(ModelMain modelMain) {
        this.modelMain = modelMain;
        setTitle("S3.02_G1_ChasseAuMonstre");
        setResizable(false);
        setMainNodes();
        setMainScene();
        getIcons().add(Main.loadImage(Main.ICON_URL));
        modelMain.attach(this);
        show();
    }

    /**
     * Cette méthode permet de mettre en place la scène principale
     */
    private void setMainScene() {
        VBox root = new VBox();
        VBox.setMargin(titre, new Insets(10, 0, 10, 0));
        VBox.setMargin(settingButton, new Insets(10, 0, 10, 0));
        VBox.setMargin(launchButton, new Insets(10, 0, 10, 0));
        HBox footer = new HBox();
        footer.getChildren().addAll(noMusicButton, new Spacer(), quitButton);
        footer.setAlignment(Pos.CENTER);
        root.getChildren().addAll(titre, launchButton, settingButton, new Spacer(), footer);
        root.setAlignment(Pos.CENTER);
        setScene(new Scene(root, WIDTH, HEIGHT));
    }

    /**
     * Cette méthode permet d'initialiser les éléments de la fenêtre principale
     */
    private void setMainNodes() {
        titre = new Label("Chasse Au Monstre");
        titre.setFont(Main.loadFont(Main.ARCADE_FONT, 30));
        settingButton = new SettingButton(modelMain);
        launchButton = new LaunchButton(modelMain);

        quitButton = new Button("Quitter");
        quitButton.setFont(Main.loadFont(Main.ARCADE_FONT, 20));

        noMusicButton = new Button("Musique");
        noMusicButton.setFont(Main.loadFont(Main.ARCADE_FONT, 20));

        quitButton.setOnAction(e -> close());
        noMusicButton.setOnAction(e -> {
            if(mp.getStatus() == MediaPlayer.Status.PLAYING) {
                mp.pause();
            } else {
                mp.play();
            }
        });

        mp = Main.loadMusic(Main.MUSIC_DIR + "music.mp3", 0.5);
        mp.setCycleCount(MediaPlayer.INDEFINITE);
        mp.play();
    }

    /**
     * Cette méthode permet d'initialiser les éléments de la fenêtre de victoire
     *
     * @param winner (String)    Le nom du gagnant
     */
    private void setVictoryScene(String winner) {
        VBox root = new VBox();
        Label message = new Label("Victoire de " + winner);
        message.setFont(Main.loadFont(Main.ARCADE_FONT, 30));
        Button validation = new Button("OK");
        validation.setFont(Main.loadFont(Main.ARCADE_FONT, 20));
        validation.setOnAction(e -> setMainScene());
        root.getChildren().addAll(message, validation);
        root.setAlignment(Pos.CENTER);
        setScene(new Scene(root, WIDTH, HEIGHT));
    }

    /**
     * Cette méthode permet de mettre à jour la fenêtre
     *
     * @param subject correspond au sujet observé
     */
    @Override
    public void update(Subject subject) {

    }

    /**
     * Cette méthode permet de mettre à jour la fenêtre à partir d'une donnée
     *
     * @param subject correspond au sujet observé
     * @param o       correspond à la donnée à partir de laquelle on met à jour la fenêtre
     */
    @Override
    public void update(Subject subject, Object o) {
        if(o.equals("ParamMAJ")) {
            show();
        } else if(o.equals("close") || paramNotif((String) o)) {
            close();
        } else if(o instanceof String winner) {
            show();
            setVictoryScene(winner);
        }
    }

    public boolean paramNotif(String text) {
        return text.endsWith("SHOW") || text.endsWith("MAJ");
    }
}
