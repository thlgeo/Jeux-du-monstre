package fr.univlille.sae.controller;

import fr.univlille.sae.Main;
import javafx.scene.control.Button;

import java.io.File;

/**
 * Cette classe correspond au bouton de passage de tour
 * @Author Nathan Desmee, Valentin Thuillier, Armand Sady, Théo Lenglart
 * @Version 1.0
 */
public class PassTurnController extends Button{
    public PassTurnController(String text){
        super("Passer au tour du " + text);
        setMinSize(200, 50);
        setFont(Main.loadFont(Main.ARCADE_FONT, 20));
    }
}
