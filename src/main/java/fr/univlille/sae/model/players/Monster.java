package fr.univlille.sae.model.players;

import fr.univlille.iutinfo.cam.player.monster.IMonsterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import fr.univlille.sae.model.Cell;
import fr.univlille.sae.model.Coordinate;
import fr.univlille.sae.model.Maze;

/**
 * Monster class - A monster is a human player that can move on a cell.
 * @see IMonsterStrategy
 * @see ICellEvent
 * @see ICoordinate
 * @see Cell
 * @author Valentin THUILLIER, Armand SADY, Nathan DESMEE, Théo LENGLART
 * @version 1.0.0
 */
public class Monster implements IMonsterStrategy {
    private static final int DEPLACEMENT = 1;
    protected boolean[][] maze;
    protected String name;
    protected Cell[][] discoveredMaze;
    protected ICoordinate coordinateMonster;

    public Monster(String name, Cell[][] discorveredMaze)
    {
        this.name = name;
        this.discoveredMaze = discorveredMaze;
        this.maze = convert();
        coordinateMonster = null;
    }

    /**
     * Convert the maze from Cell[][] to boolean[][]. True if the cell is empty or the exit otherwise false.
     * @return  (boolean[][])   The maze converted
     */
    public boolean[][] convert() {
        boolean[][] mazeB = new boolean[discoveredMaze.length][discoveredMaze[0].length];
        for(int i = 0; i < discoveredMaze.length; i++) {
            for(int j = 0; j < discoveredMaze[0].length; j++) {
                mazeB[i][j] = discoveredMaze[i][j].getInfo().equals(ICellEvent.CellInfo.EMPTY) || discoveredMaze[i][j].getInfo().equals(ICellEvent.CellInfo.EXIT);
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
     * Update the maze with the new information.
     * @param arg0  (ICellEvent)    The new information
     */
    @Override
    public void update(ICellEvent arg0) {
        Coordinate coord = (Coordinate) arg0.getCoord();
        discoveredMaze[coord.getRow()][coord.getCol()].setInfo(arg0.getState());
    }

    /**
     * Initialize the maze with the boolean[][].
     * @param maze  (boolean[][])  The maze
     */
    @Override
    public void initialize(boolean[][] maze) {
        this.maze = maze;
    }

    /**
     * Check if the monster can move to the coordinate.
     * @param coord (ICoordinate)  The coordinate to check
     * @return  (boolean)   True if the monster can move to the coordinate otherwise false
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

    public void setDiscoveredMaze(Cell[][] discoveredMaze) {
        this.discoveredMaze = discoveredMaze;
    }

    public void setCoordinateMonster(ICoordinate coordinateMonster) {
        this.coordinateMonster = coordinateMonster;
    }
}
