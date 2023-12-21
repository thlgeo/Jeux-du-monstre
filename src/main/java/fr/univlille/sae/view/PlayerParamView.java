package fr.univlille.sae.view;

import fr.univlille.iutinfo.utils.Observer;
import fr.univlille.iutinfo.utils.Subject;
import fr.univlille.sae.model.ModelMain;
import fr.univlille.sae.controller.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import fr.univlille.sae.Main;

public class PlayerParamView extends Stage implements Observer {
    private final ModelMain modelMain;
    public static final int WIDTH_VIEW = 500;
    public static final int HEIGHT_VIEW = 500;
    private Label nameMonster;
    private Label nameHunter;
    private NameController monsterName;
    private NameController hunterName;
    private IAMonsterController IAMonstre;
    private IAHunterController IAHunter;
    private PlayerValidController validation;

    public PlayerParamView(ModelMain modelMain) {
        this.modelMain = modelMain;
        setTitle("S3.02_G1_ParametresJoueur");
        setParameterNodes();
        setParameterScene();
        setResizable(false);
        getIcons().add(Main.loadImage(Main.ICON_URL));
        modelMain.attach(this);
        show();
    }

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

    private void setParameterNodes() {
        nameMonster = new Label("Nom du Monstre");
        nameMonster.setFont(Main.loadFont(Main.ARCADE_FONT, 20));
        nameHunter = new Label("Nom du Chasseur");
        nameHunter.setFont(Main.loadFont(Main.ARCADE_FONT, 20));
        monsterName = new NameController(true);
        hunterName = new NameController(false);
        IAMonstre = new IAMonsterController();
        IAHunter = new IAHunterController();
        validation = new PlayerValidController(monsterName, hunterName, IAMonstre, IAHunter, modelMain);
    }

    @Override
    public void update(Subject subject) {

    }

    @Override
    public void update(Subject subject, Object o) {

    }
}
