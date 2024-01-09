package fr.univlille.sae.view;

import fr.univlille.iutinfo.utils.Observer;
import fr.univlille.iutinfo.utils.Subject;
import fr.univlille.sae.model.ModelMain;
import javafx.stage.Stage;

import java.awt.*;

public class ScoreView extends Stage implements Observer {
    private Label monsterTitle;
    private Label hunterTitle;
    private ModelMain modelMain;

    public ScoreView(ModelMain modelMain){
        this.modelMain = modelMain;
        modelMain.attachScoreManager(this);
    }

    @Override
    public void update(Subject subject) {

    }

    @Override
    public void update(Subject subject, Object o) {

    }
}
