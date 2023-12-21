package fr.univlille.sae.model;

import org.junit.jupiter.api.Test;

public class TestMazeFactory {
    MazeFactory mazeFactory;

    @Test
    public void testGenerationMaze() {
        mazeFactory = new MazeFactory(5,5,0.35);
        mazeFactory.generePrim();
    }
}
