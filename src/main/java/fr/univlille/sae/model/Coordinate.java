package fr.univlille.sae.model;

import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

public class Coordinate implements ICoordinate {
    protected int row;
    protected int col;

    public Coordinate(int row, int col)
    {
        this.row = row;
        this.col = col;
    }

    public Coordinate()
    {
        this(-1,-1);
    }

    @Override
    public int getCol() {
        return col;
    }

    @Override
    public int getRow() {
        return row;
    }
    
}
