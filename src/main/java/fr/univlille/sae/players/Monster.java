package fr.univlille.sae.players;

import java.util.List;

import fr.univlille.iutinfo.cam.player.monster.IMonsterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;
import fr.univlille.sae.Cell;
import fr.univlille.sae.Coordinate;

public class Monster implements IMonsterStrategy {
    protected boolean[][] maze;
    protected String name;
    protected Cell[][] discoveredMaze;

    public Monster(String name, boolean[][] maze)
    {
        this.name = name;
        this.maze = maze;
        this.discoveredMaze = Maze.getMaze();
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
        return maze[coord.getRow()][coord.getCol()];
    }
}
