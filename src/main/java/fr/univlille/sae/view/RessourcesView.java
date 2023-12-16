package fr.univlille.sae.view;

import fr.univlille.iutinfo.utils.Observer;
import fr.univlille.iutinfo.utils.Subject;
import fr.univlille.sae.Main;
import fr.univlille.sae.model.ModelMain;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RessourcesView extends Stage implements Observer {
    public static final int WIDTH_VIEW = 500;
    public static final int HEIGHT_VIEW = 700;
    private final ModelMain modelMain;

    private Label wallLabel;

    private Label emptyLabel;


    public RessourcesView(ModelMain modelMain) {
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
        root.getChildren().addAll(wallLabel, emptyLabel);
        setScene(new Scene(root, WIDTH_VIEW, HEIGHT_VIEW));

    }

    private void setParameterNodes() {
        wallLabel = new Label("Texture des murs : ");
        emptyLabel = new Label("Texture des cases vides : ");
    }

    @Override
    public void update(Subject subject) {
        this.show();
    }

    @Override
    public void update(Subject subject, Object o) {
        if (o.equals("RessourcesMAJ")) {
            close();
        }
    }
}
