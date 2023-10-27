package fr.univlille.sae.controller;

import fr.univlille.sae.Main;
import javafx.scene.control.Button;

import java.io.File;

public class PassTurnController extends Button{
    public PassTurnController(String text){
        super("Passer au tour du " + text);
        setMinSize(200, 50);
        setFont(Main.loadFont("arcade_classic_2" + File.separator + "ARCADECLASSIC.TTF", 20));
    }
}
