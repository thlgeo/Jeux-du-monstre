package fr.univlille.sae.view;

import fr.univlille.iutinfo.utils.Observer;
import fr.univlille.iutinfo.utils.Subject;
import fr.univlille.sae.Main;
import fr.univlille.sae.controller.*;
import fr.univlille.sae.controller.validation.ValidationController;
import fr.univlille.sae.model.ModelMain;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Cette classe est la fenêtre où l'utilisateur peut changer les paramètres
 *
 * @author Nathan Desmee, Valentin Thuillier, Armand Sady, Théo Lenglart
 * @version 1.0
 */
public class ParameterView extends Stage implements Observer {
    public static final int WIDTH_VIEW = 500;
    public static final int HEIGHT_VIEW = 300;
    private final ModelMain modelMain;
    private MazeButton mazeButton;
    private OptionButton optionButton;
    private PlayerButton playerButton;
    private ValidationController validation;


    public ParameterView(ModelMain modelMain) {
        this.modelMain = modelMain;
        setTitle("S3.02_G1_Parametres");
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
        root.getChildren().addAll(mazeButton);
        root.getChildren().addAll(new Spacer(), new Spacer(), optionButton);
        root.getChildren().addAll(new Spacer(), new Spacer(), playerButton);
        root.getChildren().addAll(new Spacer(), new Spacer(), validation);
        root.setAlignment(Pos.CENTER);
        setScene(new Scene(root, WIDTH_VIEW, HEIGHT_VIEW));
    }


    /**
     * Cette méthode permet d'initialiser les éléments de la scène principale de la page de paramétrage
     */
    private void setParameterNodes() {
        mazeButton = new MazeButton(modelMain);
        optionButton = new OptionButton(modelMain);
        playerButton = new PlayerButton(modelMain);
        validation = new ValidationController(modelMain);
    }

    /**
     * Cette méthode permet de mettre à jour la fenêtre
     *
     * @param subject correspond au sujet observé
     */
    @Override
    public void update(Subject subject) {}

    /**
     * Cette méthode permet de mettre à jour la fenêtre à partir d'une donnée
     *
     * @param subject correspond au sujet observé
     * @param o       correspond à la donnée à partir de laquelle on met à jour la fenêtre
     */
    @Override
    public void update(Subject subject, Object o) {
        if(o.equals("ParamSHOW") || o.equals("MazeParamMAJ") || o.equals("OptionParamMAJ") || o.equals("PlayerParamMAJ")) {
            show();
        } else {
            close();
        }
    }
}
