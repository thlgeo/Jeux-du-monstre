package fr.univlille.sae.model.cellule;

import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

/**
 * CellEvent class - A cell event is a cell with a turn and a state.
 * @see ICellEvent
 * @see ICoordinate
 * @see CellInfo
 * @author Valentin THUILLIER, Armand SADY, Nathan DESMEE, Théo LENGLART
 * @version 1.0.0
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
