package fr.univlille.sae.controller;

import fr.univlille.sae.model.Cell;
import fr.univlille.sae.model.ModelMain;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

/**
 * Cette classe correspond GridPane représentant le labyrinthe du jeu
 * @author Nathan Desmee, Valentin Thuillier, Armand Sady, Théo Lenglart
 * @version 1.0
 */
public class MazeController extends GridPane {

    private final ModelMain modelMain;
    private final boolean isMonsterMaze;
    Button[][] mazeTable;

    public MazeController(ModelMain modelMain, boolean isMonsterMaze) {
        this.modelMain = modelMain;
        mazeTable = new Button[modelMain.getNbRows()][modelMain.getNbCols()];
        this.isMonsterMaze = isMonsterMaze;
        setDefaultMaze(isMonsterMaze);
        setAlignment(Pos.CENTER);
    }

    /*
        * Cette méthode permet de changer le labyrinthe
     */
    public void resize() {
        mazeTable = new Button[modelMain.getNbRows()][modelMain.getNbCols()];
        setDefaultMaze(isMonsterMaze);
    }

    /**
     * Cette méthode permet de créer le labyrinthe par défaut
     * @param isMonsterMaze (boolean) Si le labyrinthe est celui du monstre
     */
    private void setDefaultMaze(boolean isMonsterMaze) {
        getChildren().clear();
        for(int i = 0; i < modelMain.getNbRows(); i++) {
            for(int j = 0; j < modelMain.getNbCols(); j++) {
                CellController cell = new CellController(j, i, modelMain, isMonsterMaze);
                mazeTable[i][j] = cell;
                add(cell, i, j);
            }
        }
    }

    /**
     * Cette méthode permet de changer la valeur d'une case du labyrinthe
     *
     * @param a abscisse de la case
     * @param o ordonnée de la case
     * @param text nouvelle valeur de la case
     */
    public void setRender(int o, int a, String text) {
        Button b = mazeTable[a][o];
        b.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000");
        if(text.equals(Cell.IS_WALL)) {
            b.setStyle("-fx-background-color: #000000; -fx-border-color: #000000");
            b.setText(" ");
        }else if(!isMonsterMaze && text.equals("X")) {
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
     * @return (int) La taille du labyrinthe
     */
    public int getSize() {
        return modelMain.getNbCols();
    }
}
