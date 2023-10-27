package fr.univlille.sae.controller;

import fr.univlille.sae.Main;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.File;

public class ValidationController extends Button {
    NameController nameMonster;
    NameController nameHunter;
    SizeController size;

    public ValidationController(NameController nameMonster, NameController nameHunter, SizeController size) {
        this.nameMonster = nameMonster;
        this.nameHunter = nameHunter;
        this.size = size;
        setText("Enregistrer les parametres");
        setFont(Main.loadFont("arcade_classic_2" + File.separator + "ARCADECLASSIC.TTF", 20));
    }
}
