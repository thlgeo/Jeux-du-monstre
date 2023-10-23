package fr.univlille.sae.players;

import fr.univlille.iutinfo.cam.player.hunter.IHunterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import fr.univlille.sae.Cell;

public class Hunter implements IHunterStrategy {

    protected int nbRows;
    protected int nbCols;
    protected final String NAME;
    protected Cell[][] maze;
    private static final int DEFAULT_NB_ROWS = 10;
    private static final int DEFAULT_NB_COLS = 10;
    private static final String DEFAULT_NAME = "Hunter";

    public Hunter(String name, int nbRows, int nbCols) {
        this.nbCols = nbCols;
        this.nbRows = nbRows;
        this.NAME = name;
        this.initialize(this.nbRows, this.nbCols);
    }

    public Hunter(String name) {
        this(name, DEFAULT_NB_ROWS, DEFAULT_NB_COLS);
    }

    public Hunter() {
        this(DEFAULT_NAME);
    }

    @Override
    public void initialize(int rows, int cols) {
        this.maze = new Cell[rows][cols];
    }

    @Override
    public ICoordinate play() {
        throw new PlayException();
    }

    @Override
    public void update(ICellEvent iCellEvent) {

    }

    private static class PlayException extends RuntimeException { }

}
