package fr.univlille.sae.model.players;

import fr.univlille.iutinfo.cam.player.monster.IMonsterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import fr.univlille.sae.model.Cell;

public class RightWallIAMonster implements IMonsterStrategy {

    protected Cell[][] maze;
    protected ICoordinate coordonnee;
    private Direction direction = Direction.RIGHT;

    public RightWallIAMonster() {
        // Do nothing
    }

    @Override
    public void initialize(boolean[][] booleans) {
        this.maze = new Cell[booleans.length][booleans[0].length];
        for (int i = 0; i < booleans.length; i++) {
            for (int j = 0; j < booleans[0].length; j++) {
                maze[i][j] = new Cell(booleans[i][j] ? ICellEvent.CellInfo.WALL : ICellEvent.CellInfo.EMPTY);
            }
        }
    }

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

    boolean nextCellIsEmpty() {
        if(!nextCoordIsValid()) { return false; }
        ICoordinate nextCoord = direction.next(coordonnee);
        return maze[nextCoord.getRow()][nextCoord.getCol()].getInfo() != ICellEvent.CellInfo.EMPTY;
    }

    boolean rightCellIsEmpty() {
        if(!nextCoordIsValid()) { return false; }
        ICoordinate nextCoord = direction.next().next(coordonnee);
        return maze[nextCoord.getRow()][nextCoord.getCol()].getInfo() != ICellEvent.CellInfo.EMPTY;
    }

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

        public ICoordinate next(ICoordinate coord) {
            return new ICoordinate() {
                @Override
                public int getRow() {
                    return coord.getRow() + row;
                }

                @Override
                public int getCol() {
                    return coord.getCol() + col;
                }

            };
        }

    }

}
