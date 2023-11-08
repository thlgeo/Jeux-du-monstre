package fr.univlille.sae.model.players;

import fr.univlille.iutinfo.cam.player.hunter.IHunterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import fr.univlille.iutinfo.utils.Subject;
import fr.univlille.sae.model.Cell;

/**
 * Classe Hunter - Un chasseur est un joueur humain qui peut tirer sur une cellule pour en découvrir son type.
 *
 * @author Valentin THUILLIER, Armand SADY, Nathan DESMEE, Théo LENGLART
 * @version 1.0.0
 * @see IHunterStrategy
 * @see ICellEvent
 * @see ICoordinate
 * @see Cell
 */
public class Hunter extends Subject implements IHunterStrategy {

    private static final int DEFAULT_NB_ROWS = 10;
    private static final int DEFAULT_NB_COLS = 10;
    private static final String DEFAULT_NAME = "Hunter";
    protected int nbRows;
    protected int nbCols;
    protected String name;
    protected Cell[][] maze;

    public Hunter(String name, int nbRows, int nbCols) {
        this.nbCols = nbCols;
        this.nbRows = nbRows;
        this.name = name;
        this.initialize(this.nbRows, this.nbCols);
    }

    Hunter() {
        this(DEFAULT_NAME, DEFAULT_NB_ROWS, DEFAULT_NB_COLS);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNbRows(){
        return nbRows;
    }

    public int getNbCols(){
        return nbCols;
    }

    /**
     * Reinitialise le labyrinthe avec le nombre de lignes et de colonnes spécifié en paramètre.
     *
     * @param row (int)   Nombre de lignes
     * @param col (int)   Nombre de colonnes
     */
    public void setRowCol(int row, int col) {
        this.nbRows = row;
        this.nbCols = col;
        initialize(row, col);
    }

    /**
     * Initialise le labyrinthe avec le nombre de lignes et de colonnes spécifiés en paramètre.
     *
     * @param rows (int)   Nombre de lignes
     * @param cols (int)   Nombre de colonnes
     */
    @Override
    public void initialize(int rows, int cols) {
        this.maze = new Cell[rows][cols];
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++)
                this.maze[i][j] = new Cell(ICellEvent.CellInfo.EMPTY);
        }
    }

    /**
     * Play is not available for a hunter.
     *
     * @throws UnsupportedOperationException Play is not available for a hunter.
     */
    @Override
    public ICoordinate play() {
        throw new UnsupportedOperationException();
    }

    /**
     * Met à jour le labyrinthe avec l'information sur la cellule tirée.
     *
     * @param iCellEvent (ICellEvent)    Cellule tiré
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
     * Regarde si la coordonnée spécifiée en paramètre est dans le labyrinthe.
     *
     * @param coord (ICoordinate)   Coordonnée
     * @return (boolean)   Vrai si la coordonnée est dans le labyrinthe, faux sinon
     */
    public boolean canShoot(ICoordinate coord) {
        int col = coord.getCol();
        int row = coord.getRow();
        return (col >= 0 && col < nbCols) && (row >= 0 && row < nbRows);
    }

    @Override
    public String toString() {
        return "Hunter " + this.name;
    }

    /**
     * Notifie les observateurs de la fin de la partie.
     */
    public void notifyEndGame() {
        notifyObservers("endGame");
    }

    /**
     * Notifie les observateurs du passage de tour à l'adversaire.
     */
    public void notifyTurnChange() {
        notifyObservers("changerTour");
    }

    /**
     * Notifie les observateurs que la partie est en cours.
     */
    public void notifyShow() {
        notifyObservers();
    }

    /**
     * Notifie les observateurs avec le labyrinthe découvert par le chasseur.
     */
    public void notifyDiscoveredMaze() {
        notifyObservers(maze);
    }

    /**
     * Notifie les observateurs que le numéro de tour change.
     * @param turn nouveau tour
     */
    public void notifyTurnPlus(int turn) {
        notifyObservers(turn);
    }

    public Cell getCell(ICoordinate coord){
        if(coord == null) {
            return null;
        }
        if(coord.getRow() < 0 || coord.getRow() >= maze.length) {
            return null;
        }
        if(coord.getCol() < 0 || coord.getCol() >= maze[0].length) {
            return null;
        }
        return maze[coord.getRow()][coord.getCol()];
    }
}
