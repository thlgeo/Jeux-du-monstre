package fr.univlille.sae.controller;

import fr.univlille.sae.model.Maze;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

/**
 * Cette classe correspond GridPane représentant le labyrinthe du jeu
 * @Author Nathan Desmee, Valentin Thuillier, Armand Sady, Théo Lenglart
 * @Version 1.0
 */
public class MazeController extends GridPane {

    Button[][] mazeTable;
    private Maze maze;

    public MazeController(Maze maze, boolean isMonsterMaze){
        this.maze = maze;
        mazeTable = new Button[maze.getNbRows()][maze.getNbCols()];
        setDefaultMaze(isMonsterMaze);
        setAlignment(Pos.CENTER);
    }

    /**
     * Cette méthode permet de créer le labyrinthe par défaut
     */
    public void setDefaultMaze(boolean isMonsterMaze){
        for(int i = 0; i < maze.getNbRows(); i++){
            for(int j = 0; j < maze.getNbCols(); j++){
                CellController cell = new CellController(j, i, maze, isMonsterMaze);
                mazeTable[i][j] = cell;
                add(cell, i, j);
            }
        }
    }

    /**
     * Cette méthode permet de changer la valeur d'une case du labyrinthe
     * @param x absisse de la case
     * @param y ordonnée de la case
     * @param text nouvelle valeur de la case
     */
    public void setText(int x, int y, String text){
        mazeTable[y][x].setText(text);
    }
}
