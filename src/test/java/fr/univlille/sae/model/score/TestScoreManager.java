package fr.univlille.sae.model.score;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestScoreManager {

    ScoreManager sm;

    @BeforeEach
    public void init() {
        sm = new ScoreManager();
        sm.clear();
    }

    @Test
    void testChangeMonsterScore() {
        sm.changeMonsterScore("Nathan");
        PlayerScore first = sm.getPlayerScore(0, true);
        assertEquals("Nathan", first.getName());
        assertEquals(1, first.getScore());
        sm.changeMonsterScore("Theo");
        sm.changeMonsterScore("Theo");
        first = sm.getPlayerScore(0, true);
        PlayerScore second = sm.getPlayerScore(1, true);
        assertEquals("Theo", first.getName());
        assertEquals(2, first.getScore());
        assertEquals("Nathan", second.getName());
        assertEquals(1, second.getScore());
    }

    @Test
    void testChangeHunterScore() {
        sm.changeHunterScore("Nathan");
        PlayerScore first = sm.getPlayerScore(0, false);
        assertEquals("Nathan", first.getName());
        assertEquals(1, first.getScore());
        sm.changeHunterScore("Nathan");
        sm.changeHunterScore("Theo");
        first = sm.getPlayerScore(0, false);
        PlayerScore second = sm.getPlayerScore(1, false);
        assertEquals("Nathan", first.getName());
        assertEquals(2, first.getScore());
        assertEquals("Theo", second.getName());
        assertEquals(1, second.getScore());
    }
}
