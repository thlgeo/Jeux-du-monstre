package fr.univlille.sae.controller;

import fr.univlille.sae.Main;
import javafx.scene.control.Button;

import java.io.File;

/**
 * Cette classe correspond au bouton de validation des paramètres
 * @Author Nathan Desmee, Valentin Thuillier, Armand Sady, Théo Lenglart
 * @Version 1.0
 */
public class ValidationController extends Button {
    NameController nameMonster;
    NameController nameHunter;
    SizeController height;
    SizeController width;

    public ValidationController(NameController nameMonster, NameController nameHunter, SizeController height, SizeController width) {
        this.nameMonster = nameMonster;
        this.nameHunter = nameHunter;
        this.height = height;
        this.width = width;
        setText("Enregistrer les parametres");
        setFont(Main.loadFont("arcade_classic_2" + File.separator + "ARCADECLASSIC.TTF", 20));
    }
}
