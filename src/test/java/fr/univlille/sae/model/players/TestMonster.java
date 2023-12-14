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
        maze[1][1].setInfo(CellInfo.WALL);
        monstre = new Monster("monstre", maze);
        monstre.setCoordinateMonster(new Coordinate(3,3));
    }

    @Test
    public void test_deplacement_sans_diagonale()
    {   
        assertTrue(monstre.canMove(new Coordinate(3,4),false));
        assertTrue(monstre.canMove(new Coordinate(3,2),false));
        assertTrue(monstre.canMove(new Coordinate(4,3),false));
        assertTrue(monstre.canMove(new Coordinate(2,3),false));
    }

    @Test
    public void test_deplacement_avec_diagonale()
    {
        assertTrue(monstre.canMove(new Coordinate(3,4),true));
        assertTrue(monstre.canMove(new Coordinate(3,2),true));
        assertTrue(monstre.canMove(new Coordinate(4,3),true));
        assertTrue(monstre.canMove(new Coordinate(2,3),true));
        // déplacement en diagonale
        assertTrue(monstre.canMove(new Coordinate(4,2),true));
        assertTrue(monstre.canMove(new Coordinate(2,4),true));
    }

    @Test
    public void test_deplacement_mur()
    {
        monstre.setCoordinateMonster(new Coordinate(1,0));
        assertFalse(monstre.canMove(new Coordinate(1, 1), false));
    }

    @Test
    void testUpdate() {
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
