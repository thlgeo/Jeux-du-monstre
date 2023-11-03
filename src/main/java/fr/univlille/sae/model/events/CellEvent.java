package fr.univlille.sae.model.events;

import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

/**
 * Classe CellEvent - une CellEvent est une cellule avec un type et un tour.
 *
 * @author Valentin THUILLIER, Armand SADY, Nathan DESMEE, Théo LENGLART
 * @version 1.0.0
 * @see ICellEvent
 * @see ICoordinate
 * @see CellInfo
 */
public class CellEvent implements ICellEvent {

    protected int turn;
    protected CellInfo cellInfo;
    protected ICoordinate coord;

    public CellEvent(int turn, CellInfo cellInfo, ICoordinate coord) {
        this.turn = turn;
        this.cellInfo = cellInfo;
        this.coord = coord;
    }

    @Override
    public CellInfo getState() {
        return this.cellInfo;
    }

    @Override
    public int getTurn() {
        return this.turn;
    }

    @Override
    public ICoordinate getCoord() {
        return this.coord;
    }
}
