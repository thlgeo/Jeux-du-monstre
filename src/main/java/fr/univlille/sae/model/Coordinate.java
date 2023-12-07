package fr.univlille.sae.model;

import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

/**
 * Classe Coordinate - Une coordonnée est une position dans le labyrinthe, composée d'un numéro de ligne et de colonne.
 *
 * @author Valentin THUILLIER, Armand SADY, Nathan DESMEE, Théo LENGLART
 * @version 1.0.0
 * @see ICoordinate
 */
public class Coordinate implements ICoordinate {
    protected int row;
    protected int col;

    public Coordinate(int row, int col) {
        this.row = row;
        this.col = col;
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
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(getClass() != obj.getClass())
            return false;
        Coordinate other = (Coordinate) obj;
        if(row != other.row)
            return false;
        return col == other.col;
    }

    public void incrementRow() {
        this.row++;
    }

    public void incrementCol() {
        this.col++;
    }

    public void decrementRow() {
        this.row--;
    }

    public void decrementCol() {
        this.col--;
    }

    @Override
    public String toString() {
        return "Coordinate [row=" + row + ", col=" + col + "]";
    }

    public Coordinate north() {
        return new Coordinate(row - 1, col);
    }

    public Coordinate south() {
        return new Coordinate(row + 1, col);
    }

    public Coordinate east() {
        return new Coordinate(row, col + 1);
    }

    public Coordinate west() {
        return new Coordinate(row, col - 1);
    }

    public Coordinate[] around() {
        return new Coordinate[] { north(), south(), east(), west() };
    }

    public int heuristic(ICoordinate coord) {
        return Math.abs(row - coord.getRow()) + Math.abs(col - coord.getCol());
    }

}
