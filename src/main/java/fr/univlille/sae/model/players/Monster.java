package fr.univlille.sae.model.players;

import fr.univlille.iutinfo.cam.player.monster.IMonsterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;
import fr.univlille.iutinfo.utils.Subject;
import fr.univlille.sae.model.Cell;
import fr.univlille.sae.model.events.CellEvent;

/**
 * Classe Monster - Un monstre est un joueur humain qui peut se déplacer sur une cellule (si la cellule peut être atteinte).
 * @see IMonsterStrategy
 * @see ICellEvent
 * @see ICoordinate
 * @see Cell
 * @author Valentin THUILLIER, Armand SADY, Nathan DESMEE, Théo LENGLART
 * @version 1.0.0
 */
public class Monster extends Subject implements IMonsterStrategy {
    private static final int DEPLACEMENT = 1;
    protected boolean[][] maze;
    protected String name;
    protected Cell[][] discoveredMaze;
    protected ICoordinate coordinateMonster;
    protected ICoordinate lastShotHunter;

    public Monster(String name, Cell[][] discoveredMaze) {
        this.name = name;
        this.discoveredMaze = discoveredMaze;
        this.maze = convert();
        coordinateMonster = null;
        lastShotHunter = null;
    }

    /**
     * Convertie le labyrinthe de type Cell[][] à boolean[][]. Les cellules du nouveau labyrinthe sont égales à true si la cellule est vide ou égale à zéro, sinon false.
     * @return  (boolean[][])   Le labyrinthe converti
     */
    public boolean[][] convert() {
        boolean[][] mazeB = new boolean[discoveredMaze.length][discoveredMaze[0].length];
        for(int i = 0; i < discoveredMaze.length; i++) {
            for(int j = 0; j < discoveredMaze[0].length; j++) {
                mazeB[i][j] = discoveredMaze[i][j].getInfo().equals(CellInfo.EMPTY) || discoveredMaze[i][j].getInfo().equals(CellInfo.EXIT) || discoveredMaze[i][j].getInfo().equals(CellInfo.MONSTER);
            }
        }
        return mazeB;
    }

    private Monster()
    {
        this(null, null);
    }

    /**
     * Play is not implemented for the moment.
     * @throws UnsupportedOperationException    If the method is not implemented
     */
    @Override
    public ICoordinate play() {
        throw new UnsupportedOperationException("Unimplemented method 'play'");
    }

    /**
     * Met à jour le labyrinthe découvert avec l'information reçu.
     * @param cellule  (ICellEvent)    Information reçu à mettre à jour
     */
    @Override
    public void update(ICellEvent cellule) {
        if(cellule.getState() == CellInfo.HUNTER)  {
            notifyObservers(cellule);
            Cell cell = get(lastShotHunter);
            notifyObservers(new CellEvent(cell.getTurn(), cell.getInfo(), lastShotHunter));
            lastShotHunter = cellule.getCoord();
        } else {
            ICoordinate coord = cellule.getCoord();
            Cell updateCell = this.discoveredMaze[coord.getRow()][coord.getCol()];
            updateCell.setInfo(cellule.getState());
            updateCell.setTurn(cellule.getTurn());
            notifyObservers(cellule);
        }
    }

    /**
     * Initialise le labyrinthe avec un tableau à deux dimensions de booléen.
     * @param maze  (boolean[][])  Le labyrinthe.
     */
    @Override
    public void initialize(boolean[][] maze) {
        this.maze = maze;
    }

    /**
     * Vérifie si le monstre peut se déplacer aux coordonnées indiquées.
     * @param coord (ICoordinate)  Les coordonnées à verifier
     * @return  (boolean)   True si le monstre peut s'y déplacer sinon false
     */
    public boolean canMove(ICoordinate coord)
    {
        if((coord.getCol() <= coordinateMonster.getCol()+DEPLACEMENT && coord.getCol() >= coordinateMonster.getCol()-DEPLACEMENT) && (coord.getRow() <= coordinateMonster.getRow()+DEPLACEMENT && coord.getRow() >= coordinateMonster.getRow()-DEPLACEMENT))
        {
            return maze[coord.getRow()][coord.getCol()] && !coord.equals(coordinateMonster);
        }
        return false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDiscoveredMaze(Cell[][] discoveredMaze) {
        this.discoveredMaze = discoveredMaze;
    }

    public void setCoordinateMonster(ICoordinate coordinateMonster) {
        this.coordinateMonster = coordinateMonster;
    }

    @Override
    public String toString() {
        return "Monster " + this.name;
    }

    public void notifyDiscoveredMaze(){
        notifyObservers(discoveredMaze);
    }

    public void notifyCantMove(){
        notifyObservers("cantMove");
    }

    public void notifyEndGame() {
        notifyObservers("endGame");
    }

    private Cell get(ICoordinate coord){
        return discoveredMaze[coord.getRow()][coord.getCol()];
    }

    public void notifyTurnChange() { notifyObservers("changerTour"); }
}
