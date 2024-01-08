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
        return col == other.col && row == other.row;
    }

    /**
     * Incrémente l'ordonnée de 1.
     */
    public void incrementRow() {
        this.row++;
    }

    /**
     * Incrémente l'abscisse de 1.
     */
    public void incrementCol() {
        this.col++;
    }

    /**
     * Decrémente l'ordonnée de 1.
     */
    public void decrementRow() {
        this.row--;
    }

    /**
     * Decrémente l'abscisse de 1.
     */
    public void decrementCol() {
        this.col--;
    }

    @Override
    public String toString() {
        return "Coordinate [row=" + row + ", col=" + col + "]";
    }

    /**
     * Retourne la coordonnée au nord de la coordonnée actuelle.
     * @return Coordinate La coordonnée au nord de la coordonnée actuelle.
     */
    public Coordinate north() {
        return new Coordinate(row - 1, col);
    }

    /**
     * Retourne la coordonnée au sud de la coordonnée actuelle.
     * @return Coordinate La coordonnée au sud de la coordonnée actuelle.
     */
    public Coordinate south() {
        return new Coordinate(row + 1, col);
    }

    /**
     * Retourne la coordonnée à l'est de la coordonnée actuelle.
     * @return Coordinate La coordonnée à l'est de la coordonnée actuelle.
     */
    public Coordinate east() {
        return new Coordinate(row, col + 1);
    }

    /**
     * Retourne la coordonnée à l'ouest de la coordonnée actuelle.
     * @return Coordinate La coordonnée à l'ouest de la coordonnée actuelle.
     */
    public Coordinate west() {
        return new Coordinate(row, col - 1);
    }

    /**
     * Retourne les coordonnées autour de la coordonnée actuelle.
     * @return Coordinate[] Les coordonnées autour de la coordonnée actuelle.
     */
    public Coordinate[] around() {
        return new Coordinate[]{north(), south(), east(), west()};
    }

    /**
     * Calcule la distance de Manhattan entre deux coordonnées.
     *
     * @param coord La coordonnée de départ.
     * @return int La distance de Manhattan entre les deux coordonnées.
     */
    public int heuristic(ICoordinate coord) {
        return Math.abs(row - coord.getRow()) + Math.abs(col - coord.getCol());
    }

}
