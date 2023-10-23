package fr.univlille.sae;

public class Cell {
    protected Coordinate coord;
    protected CellInfo info;

    public Cell(Coordinate coord, CellInfo info)
    {
        this.coord = coord;
        this.info = info;
    }

    public Cell()
    {
        this(null, null);
    }

    public int getCol()
    {
        return coord.getCol();
    }

    public int getRow()
    {
        return coord.getRow();
    }

    public CellInfo getInfo() {
        return info;
    }

    public Coordinate getCoord() {
        return coord;
    }

    public void setInfo(CellInfo info) {
        this.info = info;
    }
}
