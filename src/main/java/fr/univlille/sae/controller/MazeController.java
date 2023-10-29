package fr.univlille.sae.controller;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

/**
 * Cette classe correspond GridPane représentant le labyrinthe du jeu
 * @Author Nathan Desmee, Valentin Thuillier, Armand Sady, Théo Lenglart
 * @Version 1.0
 */
public class MazeController extends GridPane {

    Button[][] maze;

    public MazeController(){
        maze = new Button[8][8]; //TODO: utiliser les data données par l'utilisateur
        setDefaultMaze();
        setAlignment(Pos.CENTER);
    }

    public void setDefaultMaze(){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                CellController cell = new CellController(j, i);
                cell.setOnAction(e -> System.out.println(cell.getX() + " " + cell.getY()));
                maze[i][j] = cell; //TODO: utiliser une méthode, ce qui permettra de notifier les autres classes
                add(cell, i, j);
            }
        }
    }
}
