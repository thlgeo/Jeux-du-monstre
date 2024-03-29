package fr.univlille.sae.model;

import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Classe représentant une cellule de la grille.
 * Une cellule est caractérisée une coordonnée, son type et le tour auquel elle a été découverte.
 *
 * @author Valentin THUILLIER, Armand SADY, Nathan DESMEE, Théo LENGLART
 * @version 1.0.0
 * @see ICoordinate
 * @see CellInfo
 */
public class Cell {
    public static final String IS_WALL = "W";
    protected static Map<Character, CellInfo> charToInfo = new HashMap<>();
    protected CellInfo info;
    protected int turn;
    protected boolean visited = false;

    Cell(CellInfo info, int turn) {
        if(charToInfo.isEmpty()) initialiseCharToInfo();
        this.info = info;
        this.turn = turn;
    }

    Cell(char car) {
        this(charToInfo.get(car));
    }

    public Cell(CellInfo info) {
        this(info, 0);
    }

    public Cell() {
        this(null);
    }

    /**
     * Permet d'associer un caractère à un type de cellule dans une map.
     */
    protected static void initialiseCharToInfo() {
        charToInfo.put('W', CellInfo.WALL);
        charToInfo.put('E', CellInfo.EMPTY);
        charToInfo.put('H', CellInfo.HUNTER);
        charToInfo.put('M', CellInfo.MONSTER);
        charToInfo.put('X', CellInfo.EXIT);
    }

    public CellInfo getInfo() {
        return info;
    }

    public void setInfo(CellInfo info) {
        this.info = info;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return info == cell.info && turn == cell.turn;
    }

    /**
     * Permet de mettre l'état visité à true.
     */
    public void visited() {
        visited = true;
    }

    /**
     * Permet de savoir si la cellule a été visitée.
     * @return boolean true si la cellule a été visitée, false sinon.
     */
    public boolean isVisited() {
        return visited;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInfo(), getTurn());
    }

}
