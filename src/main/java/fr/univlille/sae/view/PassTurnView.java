package fr.univlille.sae.view;

import fr.univlille.sae.controller.PassTurnController;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Cette classe est une fenêtre où l'utilisateur peut valider le passage au tour adverse.
 * @Author Nathan Desmee, Valentin Thuillier, Armand Sady, Théo Lenglart
 * @Version 1.0
 */
public class PassTurnView extends Stage {
    public static final int WIDTH = 500;
    public static final int HEIGHT = 300;

    boolean isMonster;

    public PassTurnView(boolean isMonster){
        this.isMonster = isMonster;
        setTitle("S3.02_G1_PassTurn" + getIsMonstreText());
        setPassTurnView();
        setResizable(false);
        show();
    }

    public void setPassTurnView(){
        VBox root = new VBox();
        root.getChildren().add(new PassTurnController(getIsMonstreText()));
        root.setAlignment(Pos.CENTER);
        setScene(new javafx.scene.Scene(root, WIDTH, HEIGHT));
    }

    private String getIsMonstreText(){
        if(isMonster) {
            return "Monstre";
        } else {
            return "Chasseur";
        }
    }
}
