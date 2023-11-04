package fr.univlille.sae.model.players;

import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import fr.univlille.sae.model.Cell;
import fr.univlille.sae.model.Coordinate;
import fr.univlille.sae.model.events.CellEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class TestHunter {

    Hunter h1;

    @BeforeEach
    public void setUp() {
        h1 = new Hunter();
    }

    @Test
    void test_initialize() {
        int rows = 5, cols = 5;
        Hunter h2 = new Hunter("h2", rows, cols);
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                assertEquals(ICellEvent.CellInfo.EMPTY, h2.maze[i][j].getInfo());
            }
        }
    }

    @Test
    void test_update() {
        ICoordinate coord = new Coordinate(2, 2);
        CellEvent event = new CellEvent(5, ICellEvent.CellInfo.MONSTER, coord);
        h1.update(event);
        Cell c = h1.getCelule(coord);
        assertEquals(ICellEvent.CellInfo.MONSTER, c.getInfo());
        assertEquals(5, c.getTurn());
    }

    @Test
    void test_play() {
        try {
            h1.play();
            fail("Play is executed for a hunter.");
        } catch (Exception e) {
            // Do nothing
        }
    }

}
