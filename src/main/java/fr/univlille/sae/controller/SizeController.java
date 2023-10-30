package fr.univlille.sae.controller;

import fr.univlille.sae.Main;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.File;

/**
 * Cette classe correspond à une zone pour changer la taille du labyrinthe
 * @Author Nathan Desmee, Valentin Thuillier, Armand Sady, Théo Lenglart
 * @Version 1.0
 */
public class SizeController extends HBox {
    Button moins = new Button("-");
    Button plus = new Button("+");
    Label size = new Label();
    public SizeController(){
        getChildren().addAll(moins,new Label("  "), size, new Label("  "), plus);
        setAction();
        size.setText("8");
        size.setFont(Main.loadFont(Main.ARCADE_FONT, 20));
        setAlignment(Pos.CENTER);
    }

    public void setAction(){
        plus.setOnAction(e -> {
            int actualSize = Integer.parseInt(size.getText());
            if(actualSize != 16){
                size.setText( "" + (actualSize + 1));
            }
        });
        moins.setOnAction(e -> {
            int actualSize = Integer.parseInt(size.getText());
            if(actualSize != 8){
                size.setText( "" + (actualSize - 1));
            }
        });
    }
}
