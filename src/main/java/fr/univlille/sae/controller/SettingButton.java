package fr.univlille.sae.controller;

import fr.univlille.sae.Main;
import fr.univlille.sae.view.ParameterView;
import javafx.scene.control.Button;

import java.io.File;

public class SettingButton extends Button {

    public SettingButton(){
        super("Changer Parametres");
        setAction();
        setMinSize(200, 30);
        setFont(Main.loadFont("arcade_classic_2" + File.separator + "ARCADECLASSIC.TTF", 20));
    }

    public void setAction(){
        setOnAction(event -> {
            new ParameterView();
        });
    }
}
