package fr.univlille.sae.model.score;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestPlayerScore {

    PlayerScore ps1;
    PlayerScore ps2;
    PlayerScore ps3;

    @BeforeEach
    public void init() {
        ps1 = new PlayerScore("Nathan", 10);
        ps2 = new PlayerScore("Nathan", 7);
        ps3 = new PlayerScore("Armand", 10);
    }

    @Test
    void testEquals() {
        assertEquals(ps1, ps2);
        assertNotEquals(ps1, ps3);
    }

    @Test
    void testCompareTo() {
        assertEquals(0, ps1.compareTo(ps3));
        assertTrue(ps1.compareTo(ps2) < 0);
        assertTrue(ps2.compareTo(ps3) > 0);
    }

    @Test
    void testIncrementScore() {
        ps1.incrementScore();
        assertEquals(ps1.getScore(), 11);
    }
}
