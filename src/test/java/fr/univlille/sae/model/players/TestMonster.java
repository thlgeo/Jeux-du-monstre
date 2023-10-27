package fr.univlille.sae.model.players;

import fr.univlille.sae.model.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestMonster {
    Monster monstre;
    boolean[][] maze;

    @BeforeEach
    public void initialize()
    {
        maze = new boolean[5][5];
        for(int i=0;i<maze.length;i++)
        {
            for(int j=0;j<maze[i].length;j++)
            {
                maze[i][j] = true;
            }
        }
        maze[0][0] = false;
        maze[1][1] = false;
        monstre = new Monster("monstre", maze);
        monstre.setCoordinateMonster(new Coordinate(1,0));
    }

    @Test 
    public void testCanMove()
    {
        assertTrue(monstre.canMove(new Coordinate(2, 0))); // se déplace vers le bas sur une case vide
        assertTrue(monstre.canMove(new Coordinate(2,1))); // se déplace en diagonal
        assertFalse(monstre.canMove(new Coordinate(1,0))); // se déplace sur lui même
        assertFalse(monstre.canMove(new Coordinate(0, 0))); // se déplace sur un mur
        monstre.setCoordinateMonster(new Coordinate(3,3));
        assertTrue(monstre.canMove(new Coordinate(3,4))); // se déplace à gauche sur une case vide
    }
}
