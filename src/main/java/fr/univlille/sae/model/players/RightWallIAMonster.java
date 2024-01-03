package fr.univlille.sae.model.players;

import fr.univlille.iutinfo.cam.player.monster.IMonsterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import fr.univlille.sae.model.Cell;
import fr.univlille.sae.model.Coordinate;

/**
 * Classe RightWallIAMonster - IA du monstre qui se déplace en suivant le mur de droite.
 *
 * @author Valentin THUILLIER, Armand SADY, Nathan DESMEE, Théo LENGLART
 * @version 1.0
 */
public class RightWallIAMonster implements IMonsterStrategy {

    protected Cell[][] maze;
    protected ICoordinate coordonnee;
    private Direction direction = Direction.RIGHT;

    public RightWallIAMonster() {
        // Do nothing
    }

    /**
     * Initialise le labyrinthe avec le tableau de booleen spécifié en paramètre.
     * @param booleans (boolean[][])   Le labyrinthe
     */
    @Override
    public void initialize(boolean[][] booleans) {
        this.maze = new Cell[booleans.length][booleans[0].length];
        for (int i = 0; i < booleans.length; i++) {
            for (int j = 0; j < booleans[0].length; j++) {
                maze[i][j] = new Cell(booleans[i][j] ? ICellEvent.CellInfo.WALL : ICellEvent.CellInfo.EMPTY);
            }
        }
    }

    /**
     * Méthode permettant de calculer le déplacement à faire selon les informations que le monstre possède.
     * @return ICoordinate coordonnées du déplacement
     */
    @Override
    public ICoordinate play() {
        if(rightCellIsEmpty()) {
            this.direction = this.direction.next();
            return this.direction.next(coordonnee);
        } else if(nextCellIsEmpty()) {
            return this.direction.next(coordonnee);
        } else {
            this.direction = this.direction.next();
            return play();
        }
    }

    /**
     * Permet de mettre à jour le labyrinthe du monstre selon une cellule.
     * @param cellEvent (ICellEvent) informations de la cellule
     */
    @Override
    public void update(ICellEvent cellEvent) {
        if(cellEvent.getState() == ICellEvent.CellInfo.MONSTER) {
            this.coordonnee = cellEvent.getCoord();
        }
        ICoordinate coord = cellEvent.getCoord();
        Cell updateCell = this.maze[coord.getRow()][coord.getCol()];
        updateCell.setInfo(cellEvent.getState());
        updateCell.setTurn(cellEvent.getTurn());
        updateCell.visited();
    }

    /**
     * Renvoie false si la cellule suivante est vide, sinon true.
     * @return boolean
     */
    boolean nextCellIsEmpty() {
        if(!nextCoordIsValid()) { return false; }
        ICoordinate nextCoord = direction.next(coordonnee);
        return maze[nextCoord.getRow()][nextCoord.getCol()].getInfo() != ICellEvent.CellInfo.EMPTY;
    }

    /**
     * Renvoie false si la cellule suivante est vide, sinon true.
     * @return boolean
     */
    boolean rightCellIsEmpty() {
        if(!nextCoordIsValid()) { return false; }
        ICoordinate nextCoord = direction.next().next(coordonnee);
        return maze[nextCoord.getRow()][nextCoord.getCol()].getInfo() != ICellEvent.CellInfo.EMPTY;
    }

    /**
     * Renvoie true si la cellule suivante est dans le labyrinthe, sinon false.
     * @return boolean
     */
    boolean nextCoordIsValid() {
        ICoordinate nextCoord = direction.next(coordonnee);
        return nextCoord.getRow() >= 0 && nextCoord.getRow() < maze.length && nextCoord.getCol() >= 0 && nextCoord.getCol() < maze[0].length;
    }

    /**
     * Enumération des directions possibles pour la stratégie de la main droite
     * @see RightWallIAMonster#play()
     */
    enum Direction {
        UP(-1, 0),
        DOWN(1, 0),
        LEFT(0, -1),
        RIGHT(0, 1);

        private final int row;
        private final int col;

        Direction(int row, int col) {
            this.row = row;
            this.col = col;
        }

        /**
         * Retourne la direction suivante dans le sens des aiguilles d'une montre
         * @return  (Direction)    -    La direction suivante
         */
        public Direction next() {
            switch (this) {
                case UP:
                    return RIGHT;
                case DOWN:
                    return LEFT;
                case LEFT:
                    return UP;
                case RIGHT:
                    return DOWN;
                default:
                    return null;
            }
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }

        /**
         * Retourne la coordonnée suivante dans la direction actuelle
         * @param coord (ICoordinate)   -   La coordonnée actuelle
         * @return  (ICoordinate)    -    La coordonnée suivante
         */
        public ICoordinate next(ICoordinate coord) {
            return new Coordinate(coord.getRow() + row, coord.getCol() + col);
        }

    }

}
