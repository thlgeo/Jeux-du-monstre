package fr.univlille.sae.model;

import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

import java.util.HashMap;
import java.util.Map;

public class Cell {
    protected ICoordinate coord;
    protected CellInfo info;
    protected int turn;
    public static final Map<Character, CellInfo> charToInfo = new HashMap<>() {{
        put('W', CellInfo.WALL);
        put('E', CellInfo.EMPTY);
        put('H', CellInfo.HUNTER);
        put('M', CellInfo.MONSTER);
        put('X', CellInfo.EXIT);
    }};

    public Cell(ICoordinate coord, CellInfo info, int turn)
    {
        this.coord = coord;
        this.info = info;
        this.turn = turn;
    }

    public Cell(ICoordinate coord, char car) {
        this(coord, charToInfo.get(car));
    }

    public Cell(ICoordinate coord, CellInfo info) {
        this(coord, info, -1);
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

    public ICoordinate getCoord() {
        return coord;
    }

    public void setInfo(CellInfo info) {
        this.info = info;
    }

    public int getTurn() { return turn; }

    public void setTurn(int turn) { this.turn = turn; }
}
