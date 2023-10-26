package fr.univlille.sae;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fr.univlille.sae.model.Coordinate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import fr.univlille.sae.model.players.Monster;

public class TestMonster {
    Monster monstre;
    boolean[][] maze;

    @BeforeEach
    public void initialize()
    {
        maze = new boolean[5][5];
        maze[0][0] = false;
        maze[1][1] = false;
        monstre = new Monster("monstre", maze);
        monstre.setCoordinateMonster(new Coordinate(1,0));
    }

    @Test 
    public void testCanMove()
    {
        assertTrue(monstre.canMove(new Coordinate(2, 0))); // se déplace sur une case vide
        assertTrue(monstre.canMove(new Coordinate(2,1))); // se déplace en diagonal
        assertFalse(monstre.canMove(new Coordinate(1,0))); // se déplace sur lui même
        assertFalse(monstre.canMove(new Coordinate(0, 0))); // se déplace sur un mur
    }
}
