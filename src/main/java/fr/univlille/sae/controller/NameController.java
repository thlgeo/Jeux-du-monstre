package fr.univlille.sae.controller;

import javafx.scene.control.TextField;

public class NameController extends TextField {
    public final boolean monster;
    public NameController(boolean monster){
        this.monster = monster;
    }
}
