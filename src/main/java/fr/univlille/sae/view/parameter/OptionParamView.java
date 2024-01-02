package fr.univlille.sae.view.parameter;

import fr.univlille.iutinfo.utils.Observer;
import fr.univlille.iutinfo.utils.Subject;
import fr.univlille.sae.controller.parameter.DepDiagParam;
import fr.univlille.sae.controller.parameter.FogParam;
import fr.univlille.sae.controller.validation.OptionValidController;
import fr.univlille.sae.view.Spacer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import fr.univlille.sae.Main;
import fr.univlille.sae.model.ModelMain;

/**
 * Cette classe est la fenêtre où l'utilisateur peut changer les paramètres de jeu
 *
 * @author Nathan Desmee, Valentin Thuillier, Armand Sady, Théo Lenglart
 * @version 1.0
 */
public class OptionParamView extends Stage implements Observer {
    public static final int WIDTH_VIEW = 500;
    public static final int HEIGHT_VIEW = 500;
    private OptionValidController validation;
    private FogParam fog;
    private DepDiagParam depDiag;
    private ModelMain modelMain;
    
    public OptionParamView(ModelMain modelMain) {
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
        root.getChildren().addAll(new Spacer(), fog);
        root.getChildren().addAll(new Spacer(), depDiag);
        root.getChildren().addAll(new Spacer(), validation);
        root.setAlignment(Pos.CENTER);
        setScene(new Scene(root, WIDTH_VIEW, HEIGHT_VIEW));
    }

    /**
     * Cette méthode permet de créer les différents éléments de la fenêtre
     */
    private void setParameterNodes() {
        fog = new FogParam();
        depDiag = new DepDiagParam();
        validation = new OptionValidController(fog, depDiag, modelMain);
    }

    /**
     * Cette méthode permet de mettre à jour la fenêtre
     * @param subject Correspond au sujet qui a notifié la vue
     */
    @Override
    public void update(Subject subject) {

    }

    /**
     * Cette méthode permet de mettre à jour la fenêtre en fonction de l'objet passé en paramètre
     * @param subject Correspond au sujet qui a notifié la vue
     * @param o Donnée envoyée par le sujet
     */
    @Override
    public void update(Subject subject, Object o) {
        if(o.equals("OptionParamSHOW")){
            show();
        } else {
            close();
        }
    }
}
