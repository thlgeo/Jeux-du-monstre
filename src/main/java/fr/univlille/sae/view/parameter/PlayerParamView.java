package fr.univlille.sae.view.parameter;

import fr.univlille.iutinfo.utils.Observer;
import fr.univlille.iutinfo.utils.Subject;
import fr.univlille.sae.controller.parameter.IAHunterParam;
import fr.univlille.sae.controller.parameter.IAMonsterParam;
import fr.univlille.sae.controller.parameter.NameParam;
import fr.univlille.sae.controller.validation.PlayerValidController;
import fr.univlille.sae.model.ModelMain;
import fr.univlille.sae.view.Spacer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import fr.univlille.sae.Main;

/**
 * Cette classe est la fenêtre où l'utilisateur peut changer les paramètres des joueurs
 *
 * @author Nathan Desmee, Valentin Thuillier, Armand Sady, Théo Lenglart
 * @version 1.0
 */
public class PlayerParamView extends Stage implements Observer {
    private final ModelMain modelMain;
    public static final int WIDTH_VIEW = 500;
    public static final int HEIGHT_VIEW = 500;
    private Label nameMonster;
    private Label nameHunter;
    private NameParam monsterName;
    private NameParam hunterName;
    private IAMonsterParam IAMonstre;
    private IAHunterParam IAHunter;
    private PlayerValidController validation;

    public PlayerParamView(ModelMain modelMain) {
        this.modelMain = modelMain;
        setTitle("S3.02_G1_ParametresJoueur");
        setParameterNodes();
        setParameterScene();
        setResizable(false);
        getIcons().add(Main.loadImage(Main.ICON_URL));
        modelMain.attach(this);
    }

    /**
     * Cette méthode permet de changer la scène de la fenêtre à la scène principale de la page de paramétrage
     */
    private void setParameterScene() {
        VBox root = new VBox();
        root.getChildren().addAll(nameMonster, monsterName);
        root.getChildren().addAll(new Spacer(), nameHunter, hunterName);
        root.getChildren().addAll(new Spacer(), IAMonstre);
        root.getChildren().addAll(new Spacer(), IAHunter);
        root.getChildren().addAll(new Spacer(), validation);
        root.setAlignment(Pos.CENTER);
        setScene(new Scene(root, WIDTH_VIEW, HEIGHT_VIEW));
    }

    /**
     * Cette méthode permet de créer les différents éléments de la fenêtre
     */
    private void setParameterNodes() {
        nameMonster = new Label("Nom du Monstre");
        nameMonster.setFont(Main.loadFont(Main.ARCADE_FONT, 20));
        nameHunter = new Label("Nom du Chasseur");
        nameHunter.setFont(Main.loadFont(Main.ARCADE_FONT, 20));
        monsterName = new NameParam(true);
        hunterName = new NameParam(false);
        IAMonstre = new IAMonsterParam();
        IAHunter = new IAHunterParam();
        validation = new PlayerValidController(monsterName, hunterName, IAMonstre, IAHunter, modelMain);
    }

    /**
     * Cette méthode permet de mettre à jour la fenêtre
     * @param subject correspond au sujet observé
     */
    @Override
    public void update(Subject subject) {

    }

    /**
     * Cette méthode permet de mettre à jour la fenêtre en fonction de l'objet passé en paramètre
     * @param subject correspond au sujet observé
     * @param o Object correspond à la donnée à partir de laquelle on met à jour la fenêtre
     */
    @Override
    public void update(Subject subject, Object o) {
        if(o.equals("PlayerParamSHOW")){
            show();
        } else {
            close();
        }
    }
}
