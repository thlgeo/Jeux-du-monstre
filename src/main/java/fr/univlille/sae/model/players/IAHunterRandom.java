package fr.univlille.sae.model.players;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.univlille.iutinfo.cam.player.hunter.IHunterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import fr.univlille.sae.model.Cell;
import fr.univlille.sae.model.Coordinate;

public class IAHunterRandom implements IHunterStrategy {
    protected int portee;
    protected Cell[][] maze;
    public static final Random rd = new Random();

    @Override
    public ICoordinate play() {
        ICoordinate coord;
        int row;
        int col;
        do{
            row = rd.nextInt(maze.length);
            col = rd.nextInt(maze[0].length);
            coord = new Coordinate(row, col);
        }while(!inRange(coord) && maze[coord.getRow()][coord.getCol()].getInfo() == CellInfo.WALL);

        return coord;
    }

    private boolean inRange(ICoordinate coord) {
        return (coord.getRow() >= 0 && coord.getRow() < maze.length) && (coord.getCol() >= 0 && coord.getCol() < maze[0].length);
    }

    @Override
    public void update(ICellEvent arg0) {
        ICoordinate coord = arg0.getCoord();
        Cell updateCell = this.maze[coord.getRow()][coord.getCol()];
        updateCell.setInfo(arg0.getState());
        updateCell.setTurn(arg0.getTurn());
    }

    @Override
    public void initialize(int arg0, int arg1) {
        portee = 1;
        this.maze = new Cell[arg0][arg1];
        for(int i = 0; i < arg0; i++) {
            for(int j = 0; j < arg1; j++) {
                this.maze[i][j] = new Cell(ICellEvent.CellInfo.EMPTY);

            }
        }
    }
    
}
