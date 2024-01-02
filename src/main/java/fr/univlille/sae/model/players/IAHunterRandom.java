package fr.univlille.sae.model.players;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.univlille.iutinfo.cam.player.hunter.IHunterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import fr.univlille.sae.model.Cell;
import fr.univlille.sae.model.Coordinate;

/**
 * Classe IAHunterRandom - IA du chasseur qui peut tirer sur une cellule aléatoirement.
 *
 * @author Valentin THUILLIER, Armand SADY, Nathan DESMEE, Théo LENGLART
 * @version 1.0
 */
public class IAHunterRandom implements IHunterStrategy {
    protected int portee;
    protected Cell[][] maze;
    public static final Random rd = new Random();

    /**
     * Méthode permettant de tirer aléatoirement.
     * @return ICoordinate coordonnées du tir
     */
    @Override
    public ICoordinate play() {
        ICoordinate coord;
        int row;
        int col;
        do{
            row = rd.nextInt(maze.length);
            col = rd.nextInt(maze[0].length);
            coord = new Coordinate(row, col);
        }while(!inRange(coord) && maze[coord.getRow()][coord.getCol()].getInfo() == CellInfo.WALL);

        return coord;
    }

    /**
     * Permet de vérifier si le tir est bien dans le labyrinthe
     * @param coord (ICoordinate) coordonnées du tir
     */
    private boolean inRange(ICoordinate coord) {
        return (coord.getRow() >= 0 && coord.getRow() < maze.length) && (coord.getCol() >= 0 && coord.getCol() < maze[0].length);
    }

    /**
     * Permet de récupérer les coordonnées des cases autour d'une case
     * @param arg0 (ICoordinate) coordonnées de la case
     */
    @Override
    public void update(ICellEvent arg0) {
        ICoordinate coord = arg0.getCoord();
        Cell updateCell = this.maze[coord.getRow()][coord.getCol()];
        updateCell.setInfo(arg0.getState());
        updateCell.setTurn(arg0.getTurn());
    }

    /**
     * Permet d'initialiser le labyrinthe
     * @param arg0 (int) nombre de lignes
     * @param arg1 (int) nombre de colonnes
     */
    @Override
    public void initialize(int arg0, int arg1) {
        portee = 1;
        this.maze = new Cell[arg0][arg1];
        for(int i = 0; i < arg0; i++) {
            for(int j = 0; j < arg1; j++) {
                this.maze[i][j] = new Cell(ICellEvent.CellInfo.EMPTY);

            }
        }
    }
    
}
