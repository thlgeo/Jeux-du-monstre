package fr.univlille.sae.controller;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class MazeController extends GridPane {

    Button[][] maze;

    public MazeController(){
        maze = new Button[16][16];
        setDefaultMaze();
        setAlignment(Pos.CENTER);
    }

    public void setDefaultMaze(){
        for(int i = 0; i < 16; i++){
            for(int j = 0; j < 16; j++){
                Button cell = new Button(" ");
                cell.setOnAction(e -> System.out.println("Cellule cliquée"));
                cell.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #000000");
                maze[i][j] = cell;
                add(cell, i, j);
            }
        }
    }
}
