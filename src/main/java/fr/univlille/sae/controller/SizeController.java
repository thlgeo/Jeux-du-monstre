package fr.univlille.sae.controller;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class SizeController extends HBox {
    Button moins = new Button("-");
    Button plus = new Button("+");
    TextField size = new TextField();
    public SizeController(){
        getChildren().addAll(moins, size, plus);
        size.setText("0");
    }

    public void setAction(){
        plus.setOnAction(e -> {
            int actualSize = Integer.parseInt(size.getText());
            if(actualSize != 16){
                size.setText( " " + (actualSize + 1));
            }
        });
        moins.setOnAction(e -> {
            int actualSize = Integer.parseInt(size.getText());
            if(actualSize != 0){
                size.setText( " " + (actualSize - 1));
            }
        });
    }
}
