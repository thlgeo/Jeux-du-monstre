package fr.univlille.sae.cellule;

import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

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
