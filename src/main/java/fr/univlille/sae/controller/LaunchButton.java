package fr.univlille.sae.controller;

import fr.univlille.sae.Main;
import fr.univlille.sae.view.HunterView;
import fr.univlille.sae.view.MainView;
import fr.univlille.sae.view.MonsterView;
import fr.univlille.sae.view.PassTurnView;
import javafx.scene.control.Button;

import java.io.File;

/**
 * Cette classe coorespond au bouton de lancement du jeu
 * @Author Nathan Desmee, Valentin Thuillier, Armand Sady, Théo Lenglart
 * @Version 1.0
 */
public class LaunchButton extends Button {
    public LaunchButton(){
        super("Jouer");
        setAction();
        setMinSize(200, 30);
        setFont(Main.loadFont("arcade_classic_2" + File.separator + "ARCADECLASSIC.TTF", 20));
    }

    public void setAction(){
        setOnAction(event -> {
            new MonsterView();
            new HunterView();
            new PassTurnView(true);
            new PassTurnView(false);
        });
    }
}
