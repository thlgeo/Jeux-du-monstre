package fr.univlille.sae.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TestCoordinate {

    Coordinate c1;
    Coordinate c2;
    Coordinate c3;

    @BeforeEach
    void setUp() {
        c1 = new Coordinate(1, 1);
        c2 = new Coordinate(1, 1);
        c3 = new Coordinate(2, 2);
    }

    @Test
    void testEquals() {
        assertEquals(c1, c2);
        assertNotEquals(c1, c3);
    }

    @Test
    void testToString() {
        assertEquals("Coordinate [row=1, col=1]", c1.toString());
    }
}
