package fr.univlille.sae.view;

import fr.univlille.sae.controller.SizeController;
import fr.univlille.sae.controller.NameController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ParameterView extends Stage {
    public static final int HEIGHT = 500;
    public static final int WIDTH = 300;

    public ParameterView(){
        setTitle("S3.02_G1_Parametres");
        setParameterScene();
        setResizable(false);
        show();
    }

    public void setParameterScene(){
        VBox root = new VBox();
        root.getChildren().addAll(new NameController(true), new NameController(false), new SizeController());
        root.setAlignment(Pos.CENTER);
        setScene(new Scene(root, HEIGHT, WIDTH));
    }
}
