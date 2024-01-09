package fr.univlille.sae.model.score;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestScoreNotif {
    ScoreNotif sn1;
    ScoreNotif sn2;

    @BeforeEach
    public void init() {
        List<PlayerScore> scores = new ArrayList<>();
        scores.add(new PlayerScore("Nathan", 10));
        scores.add(new PlayerScore("Armand", 7));
        scores.add(new PlayerScore("Theo", 5));
        scores.add(new PlayerScore("Valentin", 3));
        scores.add(new PlayerScore("Raphael", 2));
        scores.add(new PlayerScore("Noa", 1));
        scores.add(new PlayerScore("Simon", 1));
        scores.add(new PlayerScore("Elise", 1));
        scores.add(new PlayerScore("Adham", 1));
        scores.add(new PlayerScore("Maxence", 1));
        scores.add(new PlayerScore("Florent", 1));
        sn1 = new ScoreNotif(scores, true);
        sn2 = new ScoreNotif(new ArrayList<>(), true);
    }

    @Test
    void testListeToTable() {
        String[] res = sn1.getScores();
        assertEquals(res.length, 10);
        res = sn2.getScores();
        assertEquals(res.length, 0);
    }
}
