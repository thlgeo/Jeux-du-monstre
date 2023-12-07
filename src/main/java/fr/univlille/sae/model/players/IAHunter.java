package fr.univlille.sae.model.players;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.univlille.iutinfo.cam.player.hunter.IHunterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;
import fr.univlille.sae.model.Cell;
import fr.univlille.sae.model.Coordinate;

public class IAHunter implements IHunterStrategy {
    protected Cell[][] maze;
    public static final Random rd = new Random();
    protected ICellEvent lastPositionMonster;
    protected int portee;
    protected int turn;

    @Override
    public ICoordinate play() {
        ICoordinate coord;
        if(lastPositionMonster != null)
        {
            List<ICoordinate> around = around(lastPositionMonster.getCoord());
            coord = around.remove(rd.nextInt(around.size()));
            while(maze[coord.getRow()][coord.getCol()].getInfo() == CellInfo.WALL)
            {
                coord = around.remove(rd.nextInt(around.size()));
            }
        }else{
            coord = new Coordinate(rd.nextInt(maze.length), rd.nextInt(maze[0].length));
            while(maze[coord.getRow()][coord.getCol()].getInfo() == CellInfo.WALL)
            {
                coord = new Coordinate(rd.nextInt(maze.length), rd.nextInt(maze[0].length));
            }
        }
        
        return coord;
    }

    private boolean inRange(ICoordinate coord)
    {
        return (coord.getRow() >= 0 && coord.getRow() < maze.length) && (coord.getCol() >= 0 && coord.getCol() < maze[0].length);
    }

    private List<ICoordinate> around(ICoordinate coordonnee)
    {
        List<ICoordinate> l = new ArrayList<>();
        int row = coordonnee.getRow();
        int colonne = coordonnee.getCol();
        ICoordinate coord;
        for(int lig=row-portee;lig<=row+portee;lig++)
        {
            for(int col=colonne-portee;col<=colonne+portee;col++)
            {
                coord = new Coordinate(lig, col);
                if((col != colonne || lig != row) && inRange(coord))
                {
                    l.add(coord);
                }
                
            }
        }
        return l;
    }

    @Override
    public void update(ICellEvent arg0) {
        ICoordinate coord = arg0.getCoord();
        Cell updateCell = this.maze[coord.getRow()][coord.getCol()];
        updateCell.setInfo(arg0.getState());
        turn++;
        if(arg0.getState() == CellInfo.MONSTER && (lastPositionMonster == null || lastPositionMonster.getTurn() < arg0.getTurn()))
        {
            lastPositionMonster = arg0;
            portee = turn - arg0.getTurn();
        }
        else if(lastPositionMonster != null)
        {
            portee++;
        }
        updateCell.setTurn(arg0.getTurn());
    }

    @Override
    public void initialize(int arg0, int arg1) {
        portee = 1;
        turn = 1;
        lastPositionMonster = null;
        this.maze = new Cell[arg0][arg1];
        for(int i = 0; i < arg0; i++) {
            for(int j = 0; j < arg1; j++) {
                this.maze[i][j] = new Cell(ICellEvent.CellInfo.EMPTY);
        
            }
        }
    }
    
}
