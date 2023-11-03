package fr.univlille.sae.controller;

import fr.univlille.sae.model.Cell;
import fr.univlille.sae.model.Maze;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

/**
 * Cette classe correspond GridPane représentant le labyrinthe du jeu
 */
public class MazeController extends GridPane {

    private final Maze maze;
    private final boolean isMonsterMaze;
    Button[][] mazeTable;

    public MazeController(Maze maze, boolean isMonsterMaze) {
        this.maze = maze;
        mazeTable = new Button[maze.getNbRows()][maze.getNbCols()];
        this.isMonsterMaze = isMonsterMaze;
        setDefaultMaze(isMonsterMaze);
        setAlignment(Pos.CENTER);
    }

    public void resize() {
        mazeTable = new Button[maze.getNbRows()][maze.getNbCols()];
        setDefaultMaze(isMonsterMaze);
    }

    /**
     * Cette méthode permet de créer le labyrinthe par défaut
     */
    public void setDefaultMaze(boolean isMonsterMaze) {
        getChildren().clear();
        for(int i = 0; i < maze.getNbRows(); i++) {
            for(int j = 0; j < maze.getNbCols(); j++) {
                CellController cell = new CellController(j, i, maze, isMonsterMaze);
                mazeTable[i][j] = cell;
                add(cell, i, j);
            }
        }
    }

    /**
     * Cette méthode permet de changer la valeur d'une case du labyrinthe
     *
     * @param a    absisse de la case
     * @param o    ordonnée de la case
     * @param text nouvelle valeur de la case
     */
    public void setRender(int o, int a, String text) {
        Button b = mazeTable[a][o];
        if(text.equals(Cell.IS_WALL)) {
            b.setStyle("-fx-background-color: #000000; -fx-border-color: #000000");
            b.setText(" ");
        } else if(!isMonsterMaze && text.equals("X")) {
            b.setText(" ");
        } else {
            b.setText(text);
        }
    }

    /**
     * Cette méthode permet d'initialiser le labyrinthe
     *
     * @param discoveredMaze (Cell[][])  Le labyrinthe à initialiser
     */
    public void initMaze(Cell[][] discoveredMaze) {
        for(int i = 0; i < discoveredMaze.length; i++) {
            for(int j = 0; j < discoveredMaze[i].length; j++) {
                setRender(i, j, Cell.render(discoveredMaze[i][j].getInfo(), discoveredMaze[i][j].getTurn()));
            }
        }
    }

    /**
     * Cette méthode permet de récupérer la taille du labyrinthe
     *
     * @return (int)   La taille du labyrinthe
     */
    public int getSize() {
        return maze.getNbCols();
    }
}
