package fr.univlille.sae.controller;

import fr.univlille.sae.Main;
import fr.univlille.sae.view.ParameterView;
import javafx.scene.control.Button;

import java.io.File;

/**
 * Cette classe correspond au bouton de lancement de la page de paramètres
 * @Author Nathan Desmee, Valentin Thuillier, Armand Sady, Théo Lenglart
 * @Version 1.0
 */
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
