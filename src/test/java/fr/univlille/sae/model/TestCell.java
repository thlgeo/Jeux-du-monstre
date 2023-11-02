package fr.univlille.sae.model;

import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

public class TestCell {

    Cell c1;
    Cell c2;
    Cell c3;

    @Before
    public void init() {
        c1 = new Cell(ICellEvent.CellInfo.EXIT);
        c2 = new Cell('X');
        c3 = new Cell(ICellEvent.CellInfo.EXIT, 5);
    }

    @Test
    public void testEquals() {
        Assertions.assertNotEquals(c1, c3);
        Assertions.assertEquals(c1, c2);
    }

}
