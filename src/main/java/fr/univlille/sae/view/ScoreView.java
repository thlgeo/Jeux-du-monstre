package fr.univlille.sae.view;

import fr.univlille.iutinfo.utils.Observer;
import fr.univlille.iutinfo.utils.Subject;
import fr.univlille.sae.Main;
import fr.univlille.sae.controller.validation.ScoreValidController;
import fr.univlille.sae.model.ModelMain;
import fr.univlille.sae.model.score.PlayerScore;
import fr.univlille.sae.model.score.ScoreNotif;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Cette classe est la page des scores
 *
 * @author Valentin THUILLIER, Armand SADY, Nathan DESMEE, Théo LENGLART
 * @version 1.0
 */
public class ScoreView extends Stage implements Observer {
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;
    private Label monsterTitle;
    private Label hunterTitle;
    private ListView<String> monsterScore;
    private ListView<String> hunterScore;
    private ScoreValidController scoreValidController;
    private ModelMain modelMain;

    public ScoreView(ModelMain modelMain){
        this.modelMain = modelMain;
        setMainNodes();
        setMainScene();
        setResizable(false);
        getIcons().add(Main.loadImage(Main.ICON_URL));
        setTitle("S3.02_G1_Scores");
        modelMain.attachScoreManager(this);
    }

    /**
     * Cette méthode permet d'initialiser les éléments de la page
     */
    private void setMainNodes(){
        monsterTitle = new Label("Monster Score");
        monsterTitle.setFont(Main.loadFont(Main.ARCADE_FONT, 30));
        hunterTitle = new Label("Hunter Score");
        hunterTitle.setFont(Main.loadFont(Main.ARCADE_FONT, 30));
        monsterScore = new ListView<>();
        hunterScore = new ListView<>();
        scoreValidController = new ScoreValidController(modelMain);
    }

    /**
     * Cette méthode permet de changer la scène de la fenêtre à la scène principale de la page des scores
     */
    private void setMainScene(){
        VBox root = new VBox();
        HBox main = new HBox();
        VBox monsterBox = new VBox();
        VBox hunterBox = new VBox();
        monsterBox.getChildren().addAll(monsterTitle, monsterScore);
        monsterBox.setAlignment(Pos.CENTER);
        hunterBox.getChildren().addAll(hunterTitle, hunterScore);
        hunterBox.setAlignment(Pos.CENTER);
        main.getChildren().addAll(new Spacer(), monsterBox, new Spacer(), hunterBox, new Spacer());
        root.getChildren().addAll(main, new Spacer(), scoreValidController);
        root.setAlignment(Pos.CENTER);
        setScene(new Scene(root, WIDTH, HEIGHT));
    }

    /**
     * Cette méthode permet de mettre à jour la page
     * @param subject le sujet qui a notifié la mise à jour
     */
    @Override
    public void update(Subject subject) {

    }

    /**
     * Cette méthode permet de mettre à jour la page avec une donnée
     * @param subject le sujet qui a notifié la mise à jour
     * @param o la donnée
     */
    @Override
    public void update(Subject subject, Object o) {
        if(o instanceof ScoreNotif notif){
            if(notif.isMonster()){
                setMonsterScore(notif.getScores());
            }else {
                setHunterScore(notif.getScores());
            }
        }else if(o.equals("ScoreSHOW")){
            show();
        }else {
            close();
        }
    }

    /**
     * Permet de mettre à jour la liste des scores du monstre à partir d'un tableau de scores
     * @param monsterScore tableau de scores
     */
    private void setMonsterScore(String[] monsterScore){
        this.monsterScore.getItems().clear();
        for(String s : monsterScore){
            this.monsterScore.getItems().add(s);
        }
    }

    /**
     * Permet de mettre à jour la liste des scores du chasseur à partir d'un tableau de scores
     * @param hunterScore tableau de scores
     */
    private void setHunterScore(String[] hunterScore){
        this.hunterScore.getItems().clear();
        for(String s : hunterScore){
            this.hunterScore.getItems().add(s);
        }
    }
}
