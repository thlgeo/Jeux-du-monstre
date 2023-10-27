package fr.univlille.sae.model.players;

import fr.univlille.iutinfo.cam.player.monster.IMonsterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;
import fr.univlille.sae.model.Cell;
import fr.univlille.sae.model.Coordinate;
import fr.univlille.sae.model.Maze;

public class Monster implements IMonsterStrategy {
    private static final int DEPLACEMENT = 1;
    protected boolean[][] maze;
    protected String name;
    protected Cell[][] discoveredMaze;
    protected ICoordinate coordinateMonster;
    protected ICoordinate lastShotHunter;

    public Monster(String name, Cell[][] discorveredMaze)
    {
        this.name = name;
        this.discoveredMaze = discorveredMaze;
        this.maze = convert();
        coordinateMonster = null;
        lastShotHunter = null;
    }

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

    @Override
    public ICoordinate play() {
        throw new UnsupportedOperationException("Unimplemented method 'play'");
    }

    @Override
    public void update(ICellEvent cellule) {
        if(cellule.getState() == CellInfo.HUNTER)
        {
            lastShotHunter = cellule.getCoord();
        }else{
            Coordinate coord = (Coordinate) cellule.getCoord();
            discoveredMaze[coord.getRow()][coord.getCol()].setInfo(cellule.getState());
        }
    }

    @Override
    public void initialize(boolean[][] maze) {
        this.maze = maze;
    }

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
