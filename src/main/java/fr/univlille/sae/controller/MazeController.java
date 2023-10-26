package fr.univlille.sae.controller;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class MazeController extends GridPane {

    Button[][] maze;

    public MazeController(){
        maze = new Button[8][8];
        setDefaultMaze();
    }

    public void setDefaultMaze(){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                Button cell = new Button(" ");
                maze[i][j] = cell;
                add(cell, i, j);
            }
        }
    }
}
