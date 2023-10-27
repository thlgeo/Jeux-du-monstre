package fr.univlille.sae.controller;

import javafx.scene.control.Button;

public class CellController extends Button {
    private int x;
    private int y;
    public CellController(int x, int y){
        super(" ");
        this.x = x;
        this.y = y;
        setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #000000");
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
