package fr.univlille.sae.controller;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ValidationController extends Button {
    NameController nameMonster;
    NameController nameHunter;
    SizeController size;

    public ValidationController(NameController nameMonster, NameController nameHunter, SizeController size) {
        this.nameMonster = nameMonster;
        this.nameHunter = nameHunter;
        this.size = size;
        setText("Enregistrer les paramètres");
    }
}
