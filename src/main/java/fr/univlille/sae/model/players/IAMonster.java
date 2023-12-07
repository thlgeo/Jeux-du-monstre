package fr.univlille.sae.model.players;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.univlille.iutinfo.cam.player.monster.IMonsterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;
import fr.univlille.sae.model.Cell;
import fr.univlille.sae.model.Coordinate;

public class IAMonster implements IMonsterStrategy {
    protected Cell[][] maze;
    protected ICoordinate coordonnee;
    public static final Random rd = new Random();

    @Override
    public ICoordinate play() {
        List<ICoordinate> around = possibilities();
        return around.get(rd.nextInt(around.size()));
    }

    private boolean inRange(ICoordinate coord)
    {
        return (coord.getRow() >= 0 && coord.getRow() < maze.length) && (coord.getCol() >= 0 && coord.getCol() < maze[0].length);
    }

    private List<ICoordinate> possibilities()
    {
        List<ICoordinate> l = new ArrayList<>();
        for(ICoordinate coord : around())
        {
            if(inRange(coord))
            {
                l.add(coord);
            }
        }
        return l;
    }

    private List<ICoordinate> around()
    {
        List<ICoordinate> l = new ArrayList<>();
        int row =   coordonnee.getRow();
        int col = coordonnee.getCol();
        l.add(new Coordinate(row+1, col));
        l.add(new Coordinate(row-1, col));
        l.add(new Coordinate(row, col+1));
        l.add(new Coordinate(row, col-1));
        return l;
    }

    @Override
    public void update(ICellEvent arg0) {
        if(arg0.getState() == CellInfo.MONSTER)
        {
            this.coordonnee = arg0.getCoord();
        }
        ICoordinate coord = arg0.getCoord();
        Cell updateCell = this.maze[coord.getRow()][coord.getCol()];
        updateCell.setInfo(arg0.getState());
        updateCell.setTurn(arg0.getTurn());
        updateCell.visited();
    }

    @Override
    public void initialize(boolean[][] arg0) {
        maze = new Cell[arg0.length][arg0[0].length];
        for(int lig=0;lig<arg0.length;lig++)
        {
            for(int col=0;col<arg0[lig].length;col++)
            {
                if(arg0[lig][col])
                {
                    maze[lig][col] = new Cell(CellInfo.EMPTY);
                }else 
                {
                    maze[lig][col] = new Cell(CellInfo.WALL);
                }
            }
        }
    }
    
}
