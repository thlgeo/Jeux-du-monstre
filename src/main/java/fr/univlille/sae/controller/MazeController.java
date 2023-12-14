package fr.univlille.sae.controller;

import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.sae.model.Cell;
import fr.univlille.sae.model.ModelMain;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

/**
 * Cette classe correspond GridPane représentant le labyrinthe du jeu
 *
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

    /**
     * Cette méthode permet de changer le labyrinthe
     */
    public void resize() {
        mazeTable = new Button[modelMain.getNbRows()][modelMain.getNbCols()];
        setDefaultMaze(isMonsterMaze);
    }

    /**
     * Cette méthode permet de créer le labyrinthe par défaut
     *
     * @param isMonsterMaze (boolean) Si le labyrinthe est celui du monstre
     */
    private void setDefaultMaze(boolean isMonsterMaze) {
        getChildren().clear();
        for(int i = 0; i < modelMain.getNbRows(); i++) {
            for(int j = 0; j < modelMain.getNbCols(); j++) {
                CellController cell = new CellController(i, j, modelMain, isMonsterMaze);
                mazeTable[i][j] = cell;
                add(cell, j, i);
            }
        }
    }

    /**
     * Cette méthode permet de changer la valeur d'une case du labyrinthe
     *
     * @param i ordonnee de la case
     * @param j abscisse de la case
     */
    public void setRender(int i, int j, ICellEvent.CellInfo info, int turn) {
        Button b = mazeTable[i][j];
        if(turn < 0) {
            b.setStyle("-fx-background-color: #9B9B9B; -fx-border-color: #000000");
            b.setText(" ");
        } else if(info == ICellEvent.CellInfo.WALL) {
            b.setStyle("-fx-background-color: #000000; -fx-border-color: #000000");
            b.setText(" ");
        } else if(!isMonsterMaze && info == ICellEvent.CellInfo.EXIT) {
            b.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000");
            b.setText(" ");
        } else if(info == ICellEvent.CellInfo.MONSTER) {
            b.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000");
            b.setText("" + turn);
        } else if(info == ICellEvent.CellInfo.HUNTER) {
            b.setText("h");
        }else if(info == ICellEvent.CellInfo.EXIT) {
            b.setStyle("-fx-background-image: url('https://minesweeper.online/img/skins/xp/flag.png?v=3'); -fx-background-size:"+CellController.SIZE+"; -fx-background-position: center center; -fx-border-color: #000000");
        }else{
            b.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000");
            b.setText(" ");
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
                setRender(i, j, discoveredMaze[i][j].getInfo(), discoveredMaze[i][j].getTurn());
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
