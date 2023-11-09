package fr.univlille.sae.model;

import org.junit.jupiter.api.Test;

public class TestMaze {
    Maze maze;

    @Test
    public void testGenerationMaze() {
        maze = new Maze(5,5);
        maze.generePrim();
    }
}
