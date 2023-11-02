package fr.univlille.sae.model;

import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Classe représentant une cellule de la grille.
 * Une cellule est caractérisée une coordonnée, son type et le tour auquel elle a été découverte.
 * @see ICoordinate
 * @see CellInfo
 * @author Valentin THUILLIER, Armand SADY, Nathan DESMEE, Théo LENGLART
 * @version 1.0.0
 */
public class Cell {
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
        this(null);
    }

    public CellInfo getInfo() {
        return info;
    }

    public void setInfo(CellInfo info) {
        this.info = info;
    }

    public int getTurn() { return turn; }

    public void setTurn(int turn) { this.turn = turn; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return Objects.equals(coord, cell.coord) && info == cell.info;
    }
}
