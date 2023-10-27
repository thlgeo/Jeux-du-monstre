package fr.univlille.sae.model.players;

import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import fr.univlille.sae.model.Cell;
import fr.univlille.sae.model.Coordinate;
import fr.univlille.sae.model.cellule.CellEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class TestHunter {

    Hunter h1;

    @Before
    public void setUp() {
        h1 = new Hunter();
    }

    @Test
    public void test_initialize() {
        int rows = 5, cols = 5;
        Hunter h2 = new Hunter("h2", rows, cols);
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                assert(h2.maze[i][j].getInfo() == ICellEvent.CellInfo.EMPTY);
            }
        }
    }

    @Test
    public void test_update() {
        ICoordinate coord = new Coordinate(2, 2);
        CellEvent event = new CellEvent(5, ICellEvent.CellInfo.MONSTER, coord);
        h1.update(event);
        Cell c = h1.getCelule(coord);
        assert(c.getInfo() == ICellEvent.CellInfo.MONSTER);
        assert(c.getTurn() == 5);
    }

    @Test
    public void test_play() {
        try {
            h1.play();
            Assertions.fail("Play is executed for a hunter.");
        } catch (Exception e) {
            // Do nothing
        }
    }

}
