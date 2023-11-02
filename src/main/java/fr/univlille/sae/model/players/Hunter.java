package fr.univlille.sae.model.players;

import fr.univlille.iutinfo.cam.player.hunter.IHunterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import fr.univlille.sae.model.Cell;
import fr.univlille.sae.model.Coordinate;

/**
 * Classe Hunter - Un chasseur est un joueur humain qui peut tirer sur une cellule pour en découvrir son type.
 * @see IHunterStrategy
 * @see ICellEvent
 * @see ICoordinate
 * @see Cell
 * @author Valentin THUILLIER, Armand SADY, Nathan DESMEE, Théo LENGLART
 * @version 1.0.0
 */
public class Hunter implements IHunterStrategy {

    protected int nbRows;
    protected int nbCols;

    protected String name;
    protected Cell[][] maze;
    private static final int DEFAULT_NB_ROWS = 10;
    private static final int DEFAULT_NB_COLS = 10;
    private static final String DEFAULT_NAME = "Hunter";

    public Hunter(String name, int nbRows, int nbCols) {
        this.nbCols = nbCols;
        this.nbRows = nbRows;
        this.name = name;
        this.initialize(this.nbRows, this.nbCols);
    }

    public Hunter(String name) {
        this(name, DEFAULT_NB_ROWS, DEFAULT_NB_COLS);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Hunter() {
        this(DEFAULT_NAME);
    }

    /**
     * Initialise le labyrinthe avec le nombre de lignes et de colonnes spécifiés en paramètre.
     * @param rows  (int)   Nombre de lignes
     * @param cols  (int)   Nombre de colonnes
     */
    @Override
    public void initialize(int rows, int cols) {
        this.maze = new Cell[rows][cols];
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++)
                this.maze[i][j] = new Cell(new Coordinate(i, j), ICellEvent.CellInfo.EMPTY);
        }
    }

    /**
     * Play is not available for a hunter.
     * @throws UnsupportedOperationException    Play is not available for a hunter.
     */
    @Override
    public ICoordinate play() { throw new UnsupportedOperationException(); }

    /**
     * Met à jour le labyrinthe avec l'information sur la cellule tirée.
     * @param iCellEvent    (ICellEvent)    Cellule tiré
     */
    @Override
    public void update(ICellEvent iCellEvent) {
        ICoordinate coord = iCellEvent.getCoord();
        Cell updateCell = this.maze[coord.getRow()][coord.getCol()];
        updateCell.setInfo(iCellEvent.getState());
        updateCell.setTurn(iCellEvent.getTurn());
        notifyObservers(iCellEvent);
    }


    /**
     * Récupère la cellule à la coordonnée spécifiée en paramètre.
     * @param iCoordinate   (ICoordinate)   Coordonnée
     * @return  (Cell)  Cellule à la coordonnée
     */
    public Cell getCelule(ICoordinate iCoordinate) {
        return this.maze[iCoordinate.getRow()][iCoordinate.getCol()];
    }

    public boolean canShoot(ICoordinate coord) {
        int col = coord.getCol();
        int row = coord.getRow();
        return (col >= 0 && col < nbCols) && (row >= 0 && row < nbRows);
    }

}
