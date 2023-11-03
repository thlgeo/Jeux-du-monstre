package fr.univlille.sae.model.players;

import fr.univlille.iutinfo.cam.player.monster.IMonsterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import fr.univlille.iutinfo.utils.Subject;
import fr.univlille.sae.model.Cell;
import fr.univlille.sae.model.events.CellEvent;

/**
 * Classe Monster - Un monstre est un joueur humain qui peut se déplacer sur une cellule (si la cellule peut être atteinte).
 *
 * @author Valentin THUILLIER, Armand SADY, Nathan DESMEE, Théo LENGLART
 * @version 1.0.0
 * @see IMonsterStrategy
 * @see ICellEvent
 * @see ICoordinate
 * @see Cell
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
     * Définie le labyrinthe du monstre.
     *
     * @param maze (Cell[][])   Le labyrinthe
     */
    public void setMaze(Cell[][] maze) {
        discoveredMaze = maze;
        this.maze = convert();
        coordinateMonster = null;
        lastShotHunter = null;
    }

    /**
     * Convertie le labyrinthe de type Cell[][] à boolean[][]. Les cellules du nouveau labyrinthe sont égales à true si la cellule est vide ou égale à zéro, sinon false.
     *
     * @return (boolean[][])   Le labyrinthe converti
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

    /**
     * La fonction play n'est pas implémentée pour le monstre.
     *
     * @throws UnsupportedOperationException Exception levée si la fonction est appelée
     */
    @Override
    public ICoordinate play() {
        throw new UnsupportedOperationException("Unimplemented method 'play'");
    }

    /**
     * Met à jour le labyrinthe découvert avec l'information reçu.
     *
     * @param cellule (ICellEvent)    Information reçu à mettre à jour
     */
    @Override
    public void update(ICellEvent cellule) {
        if(cellule.getState() == CellInfo.HUNTER) {
            notifyObservers(cellule);
            if(lastShotHunter != null) {
                Cell cell = get(lastShotHunter);
                if(cell != null) notifyObservers(new CellEvent(cell.getTurn(), cell.getInfo(), lastShotHunter));
                else notifyObservers(new CellEvent(0, CellInfo.EMPTY, lastShotHunter));
            }
            lastShotHunter = cellule.getCoord();
        } else {
            ICoordinate coord = cellule.getCoord();
            Cell updateCell = this.discoveredMaze[coord.getRow()][coord.getCol()];
            updateCell.setInfo(cellule.getState());
            updateCell.setTurn(cellule.getTurn());
        }
        notifyObservers(cellule);
    }

    /**
     * Initialise le labyrinthe avec un tableau à deux dimensions de booléen.
     *
     * @param maze (boolean[][])  Le labyrinthe.
     */
    @Override
    public void initialize(boolean[][] maze) {
        this.maze = maze;
    }

    /**
     * Vérifie si le monstre peut se déplacer aux coordonnées indiquées.
     *
     * @param coord (ICoordinate)  Les coordonnées à verifier
     * @return (boolean)   True si le monstre peut s'y déplacer sinon false
     */
    public boolean canMove(ICoordinate coord) {
        if((coord.getCol() <= coordinateMonster.getCol() + DEPLACEMENT && coord.getCol() >= coordinateMonster.getCol() - DEPLACEMENT) && (coord.getRow() <= coordinateMonster.getRow() + DEPLACEMENT && coord.getRow() >= coordinateMonster.getRow() - DEPLACEMENT)) {
            return maze[coord.getRow()][coord.getCol()] && !coord.equals(coordinateMonster);
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDiscoveredMaze(Cell[][] discoveredMaze) {
        this.discoveredMaze = discoveredMaze;
    }

    public ICoordinate getCoordinateMonster() {
        return coordinateMonster;
    }

    public void setCoordinateMonster(ICoordinate coordinateMonster) {
        this.coordinateMonster = coordinateMonster;
    }

    @Override
    public String toString() {
        return "Monster " + this.name;
    }

    /**
     * Notifie les observateurs avec le labyrinthe découvert par le monstre.
     */
    public void notifyDiscoveredMaze() {
        notifyObservers(discoveredMaze);
    }

    /**
     * Notifie les observateurs que le monstre ne peut pas se déplacer.
     */
    public void notifyCantMove() {
        notifyObservers("cantMove");
    }

    /**
     * Notifie les observateurs que la partie est terminée.
     */
    public void notifyEndGame() {
        notifyObservers("endGame");
    }

    /**
     * Récupère la cellule à la coordonnée spécifiée en paramètre.
     *
     * @param coord (ICoordinate)   Coordonnée
     * @return (Cell)  Cellule à la coordonnée
     */
    private Cell get(ICoordinate coord) {
        if(coord == null) {
            return null;
        }
        if(coord.getRow() < 0 || coord.getRow() >= discoveredMaze.length) {
            return null;
        }
        if(coord.getCol() < 0 || coord.getCol() >= discoveredMaze[0].length) {
            return null;
        }
        return discoveredMaze[coord.getRow()][coord.getCol()];
    }

    /**
     * Notifie les observateurs du changement de tour.
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
}
