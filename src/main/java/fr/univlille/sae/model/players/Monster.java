package fr.univlille.sae.model.players;

import fr.univlille.iutinfo.cam.player.monster.IMonsterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import fr.univlille.sae.model.Cell;
import fr.univlille.sae.model.Coordinate;
import fr.univlille.sae.model.Maze;

public class Monster implements IMonsterStrategy {
    protected boolean[][] maze;
    protected String name;
    protected Cell[][] discoveredMaze;
    protected ICoordinate coordinateMonster;

    public Monster(String name, boolean[][] maze)
    {
        this.name = name;
        this.maze = maze;
        this.discoveredMaze = Maze.getMaze();
        coordinateMonster = Maze.getCoordMonster();
    }

    public Monster()
    {
        this(null, null);
    }

    @Override
    public ICoordinate play() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'play'");
    }

    @Override
    public void update(ICellEvent arg0) {
        Coordinate coord = (Coordinate) arg0.getCoord();
        discoveredMaze[coord.getRow()][coord.getCol()].setInfo(arg0.getState());
    }

    @Override
    public void initialize(boolean[][] arg0) {
        this.maze = arg0;    
    }

    public boolean canMove(ICoordinate coord)
    {
        if((coord.getCol() == coordinateMonster.getCol()+1 || coord.getCol() == coordinateMonster.getCol()-1) && (coord.getRow() == coordinateMonster.getRow()+1 || coord.getRow() == coordinateMonster.getRow()-1))
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
