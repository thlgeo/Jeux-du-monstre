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
    public static final int MIN_SIZE = 8;
    public static final int DEFAULT_SIZE = 10;
    public static final int MAX_SIZE = 16;



    public SizeController() {
        getChildren().addAll(moins,new Label("  "), size, new Label("  "), plus);
        setAction();
        size.setText(String.valueOf(DEFAULT_SIZE));
        size.setFont(Main.loadFont(Main.ARCADE_FONT, 20));
        setAlignment(Pos.CENTER);
    }

    public void setAction(){
        plus.setOnAction(e -> {
            int actualSize = Integer.parseInt(size.getText());
            if(actualSize != MAX_SIZE){
                size.setText( "" + (actualSize + 1));
            }
        });
        moins.setOnAction(e -> {
            int actualSize = Integer.parseInt(size.getText());
            if(actualSize != MIN_SIZE){
                size.setText( "" + (actualSize - 1));
            }
        });
    }

    public int getValue() {
        return Integer.valueOf(this.size.getText());
    }

    public boolean setValue(int value) {
        if (value >= MIN_SIZE && value <= MAX_SIZE) {
            this.size.setText(String.valueOf(value));
            return true;
        }
        return false;
    }

    public boolean setValue(String value) {
        try {
            int v = Integer.valueOf(value);
            return setValue(v);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isValid() {
        int value = getValue();
        return value >= MIN_SIZE && value <= MAX_SIZE;
    }

}
