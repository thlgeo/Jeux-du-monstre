package fr.univlille.sae.view;

import fr.univlille.iutinfo.utils.Observer;
import fr.univlille.iutinfo.utils.Subject;
import fr.univlille.sae.controller.parameter.DepDiagParam;
import fr.univlille.sae.controller.parameter.FogParam;
import fr.univlille.sae.controller.validation.OptionValidController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import fr.univlille.sae.Main;
import fr.univlille.sae.model.ModelMain;

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

    private void setParameterScene() {
        VBox root = new VBox();
        root.getChildren().addAll(new Spacer(), fog);
        root.getChildren().addAll(new Spacer(), depDiag);
        root.getChildren().addAll(new Spacer(), validation);
        root.setAlignment(Pos.CENTER);
        setScene(new Scene(root, WIDTH_VIEW, HEIGHT_VIEW));
    }

    private void setParameterNodes() {
        fog = new FogParam();
        depDiag = new DepDiagParam();
        validation = new OptionValidController(fog, depDiag, modelMain);
    }

    @Override
    public void update(Subject subject) {

    }

    @Override
    public void update(Subject subject, Object o) {
        if(o.equals("OptionParamSHOW")){
            show();
        } else {
            close();
        }
    }
}
