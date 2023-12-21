package fr.univlille.sae.view;

import fr.univlille.iutinfo.utils.Observer;
import fr.univlille.iutinfo.utils.Subject;
import fr.univlille.sae.controller.DepDiagController;
import fr.univlille.sae.controller.FogController;
import fr.univlille.sae.controller.OptionValidController;
import javafx.stage.Stage;
import fr.univlille.sae.Main;
import fr.univlille.sae.model.ModelMain;

public class OptionParamView extends Stage implements Observer {
    public static final int WIDTH_VIEW = 500;
    public static final int HEIGHT_VIEW = 500;
    private OptionValidController validation;
    private FogController fog;
    private DepDiagController depDiag;
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
    }

    private void setParameterNodes() {
        validation = new OptionValidController(fog, depDiag, modelMain);
        fog = new FogController();
        depDiag = new DepDiagController();
    }

    @Override
    public void update(Subject subject) {

    }

    @Override
    public void update(Subject subject, Object o) {

    }
}
