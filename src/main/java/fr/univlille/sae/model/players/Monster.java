package fr.univlille.sae.model.players;

import fr.univlille.iutinfo.cam.player.monster.IMonsterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import fr.univlille.iutinfo.utils.Subject;
import fr.univlille.sae.model.Cell;
import fr.univlille.sae.model.Coordinate;
import fr.univlille.sae.model.events.CellEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    protected boolean fog;

    public Monster(String name, Cell[][] discoveredMaze) {
        this.name = name;
        this.discoveredMaze = copyOf(discoveredMaze);
        this.maze = convert();
        coordinateMonster = null;
        lastShotHunter = null;
        fog = false;
    }

    /**
     * Définie le labyrinthe du monstre.
     *
     * @param maze (Cell[][])   Le labyrinthe
     */
    public void setMaze(Cell[][] maze) {
        discoveredMaze = copyOf(maze);
        this.maze = convert();
        coordinateMonster = null;
        lastShotHunter = null;
    }

    public void setMazeEmpty(int nbRow, int nbCol) {
        this.discoveredMaze = new Cell[nbRow][nbCol];
        for(int i = 0; i < nbRow; i++) {
            for(int j = 0; j < nbCol; j++) {
                this.discoveredMaze[i][j] = new Cell(ICellEvent.CellInfo.EMPTY);
            }
        }
        this.maze = convert();
        coordinateMonster = null;
        lastShotHunter = null;
    }

    /**
     * Convertie le labyrinthe de type Cell[][] à boolean[][]. Les cellules du nouveau labyrinthe sont égales à true si la cellule est vide ou égale à zéro, sinon false.
     *
     * @return (boolean[][])   Le labyrinthe converti
     */
    protected boolean[][] convert() {
        boolean[][] mazeB = new boolean[discoveredMaze.length][discoveredMaze[0].length];
        for(int i = 0; i < discoveredMaze.length; i++) {
            for(int j = 0; j < discoveredMaze[0].length; j++) {
                mazeB[i][j] = convertCell(discoveredMaze[i][j]);
            }
        }
        return mazeB;
    }

    private boolean convertCell(Cell cellule) {
        return cellule.getInfo().equals(CellInfo.EMPTY) || cellule.getInfo().equals(CellInfo.EXIT) || cellule.getInfo().equals(CellInfo.MONSTER);
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
                if(fog && !cell.isVisited()) {
                    notifyObservers(new CellEvent(-1, cell.getInfo(), lastShotHunter));
                } else {
                    notifyObservers(new CellEvent(cell.getTurn(), cell.getInfo(), lastShotHunter));
                }
            }
            lastShotHunter = cellule.getCoord();
        } else {
            ICoordinate coord = cellule.getCoord();
            Cell updateCell = this.discoveredMaze[coord.getRow()][coord.getCol()];
            updateCell.setInfo(cellule.getState());
            updateCell.setTurn(cellule.getTurn());
            updateCell.visited();
            if(fog) {
                maze[coord.getRow()][coord.getCol()] = convertCell(updateCell);
            }
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

    public boolean canMove(ICoordinate coord, boolean diag) {
        if(diag) return canMoveDiag(coord);
        return canMoveNotDiag(coord);
    }

    /**
     * Vérifie si le monstre peut se déplacer aux coordonnées indiquées (diagonales incluses).
     *
     * @param coord (ICoordinate)  Les coordonnées à verifier
     * @return (boolean)   True si le monstre peut s'y déplacer sinon false
     */
    private boolean canMoveDiag(ICoordinate coord) {
        if((coord.getCol() <= coordinateMonster.getCol() + DEPLACEMENT && coord.getCol() >= coordinateMonster.getCol() - DEPLACEMENT) && (coord.getRow() <= coordinateMonster.getRow() + DEPLACEMENT && coord.getRow() >= coordinateMonster.getRow() - DEPLACEMENT)) {
            return maze[coord.getRow()][coord.getCol()] && !coord.equals(coordinateMonster);
        }
        return false;
    }

    /**
     * Vérifie si le monstre peut se déplacer aux coordonnées indiquées (diagonales exclues).
     *
     * @param coord (ICoordinate)  Les coordonnées à verifier
     * @return (boolean)   True si le monstre peut s'y déplacer sinon false
     */
    private boolean canMoveNotDiag(ICoordinate coord) {
        return around().contains(coord) && (maze[coord.getRow()][coord.getCol()] && !coord.equals(coordinateMonster));
    }

    /**
     * Renvoie les coordonnées autour du monstre.
     *
     * @return (List < ICoordinate >) liste des coordonnées atteignables.
     */
    private List<ICoordinate> around() {
        List<ICoordinate> l = new ArrayList<>();
        int row = coordinateMonster.getRow();
        int col = coordinateMonster.getCol();
        l.add(new Coordinate(row + DEPLACEMENT, col));
        l.add(new Coordinate(row - DEPLACEMENT, col));
        l.add(new Coordinate(row, col + DEPLACEMENT));
        l.add(new Coordinate(row, col - DEPLACEMENT));
        return l;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ICoordinate getCoordinateMonster() {
        return coordinateMonster;
    }

    public void setCoordinateMonster(ICoordinate coordinateMonster) {
        this.coordinateMonster = coordinateMonster;
    }

    public ICoordinate getLastShotHunter() {
        return lastShotHunter;
    }

    @Override
    public String toString() {
        return "Monster " + this.name;
    }

    /**
     * Notifie les observateurs avec une data.
     *
     * @param data data à notifier
     */
    public void notify(Object data) {
        notifyObservers(data);
    }

    /**
     * Notifie les observateurs avec le labyrinthe découvert par le monstre.
     */
    public void notifyDiscoveredMaze() {
        if(fog) {
            Cell[][] mazeTemps = new Cell[discoveredMaze.length][discoveredMaze[0].length];
            for(Cell[] row : mazeTemps) {
                for(int i = 0; i < row.length; i++) {
                    Cell cell = new Cell(CellInfo.EMPTY);
                    cell.setTurn(-1);
                    row[i] = cell;
                }
            }
            notifyObservers(mazeTemps);
        } else {
            notifyObservers(discoveredMaze);
        }
    }

    /**
     * Récupère la cellule à la coordonnée spécifiée en paramètre.
     *
     * @param coord (ICoordinate)   Coordonnée
     * @return (Cell)  Cellule à la coordonnée
     */
    public Cell get(ICoordinate coord) {
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
     * Notifie les observateurs que la partie est en cours.
     */
    public void notifyShow() {
        notifyObservers();
    }

    private Cell[][] copyOf(Cell[][] maze) {
        Cell[][] newMaze = new Cell[maze.length][];
        for(int i = 0; i < maze.length; i++) {
            newMaze[i] = Arrays.copyOf(maze[i], maze[i].length);
        }
        return newMaze;
    }

    public void setFog(boolean fog) {
        this.fog = fog;
    }

    public Cell[][] getDiscoveredMaze() {
        return discoveredMaze;
    }
}
