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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + row;
        result = prime * result + col;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Coordinate other = (Coordinate) obj;
        if (row != other.row)
            return false;
        if (col != other.col)
            return false;
        return true;
    }

    
    
}
