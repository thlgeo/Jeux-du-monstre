package fr.univlille.sae.model.players;

import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import fr.univlille.sae.model.Cell;
import fr.univlille.sae.model.Coordinate;
import fr.univlille.sae.model.events.CellEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TestMonster {
    Monster monstre;
    Cell[][] maze;

    @BeforeEach
    void initialize()
    {
        maze = new Cell[5][5];
        for(int i=0;i<maze.length;i++)
        {
            for(int j=0;j<maze[i].length;j++)
            {
                maze[i][j] = new Cell(CellInfo.EMPTY);
            }
        }
        maze[0][0].setInfo(CellInfo.WALL);
        maze[1][1].setInfo(CellInfo.WALL);;
        monstre = new Monster("monstre", maze);
        monstre.setCoordinateMonster(new Coordinate(1,0));
    }

    // @Test 
    // void testCanMove()
    // {
    //     assertTrue(monstre.canMove(new Coordinate(2, 0))); // se déplace vers le bas sur une case vide
    //     assertTrue(monstre.canMove(new Coordinate(2,1))); // se déplace en diagonal
    //     assertFalse(monstre.canMove(new Coordinate(1,0))); // se déplace sur lui-même
    //     assertFalse(monstre.canMove(new Coordinate(0, 0))); // se déplace sur un mur
    //     monstre.setCoordinateMonster(new Coordinate(3,3));
    //     assertTrue(monstre.canMove(new Coordinate(3,4))); // se déplace à gauche sur une case vide
    // }

    @Test
    void test_update() {
        ICoordinate coord = new Coordinate(2, 2);
        CellEvent event = new CellEvent(5, ICellEvent.CellInfo.MONSTER, coord);
        ICoordinate coordH = new Coordinate(0, 0);
        CellEvent eventH = new CellEvent(5, CellInfo.HUNTER, coordH);
        monstre.update(event);
        monstre.update(eventH);
        Cell c = monstre.get(coord);
        assertEquals(ICellEvent.CellInfo.MONSTER, c.getInfo());
        assertEquals(5, c.getTurn());
        assertEquals(new Coordinate(0, 0), monstre.getLastShotHunter());
    }
}
