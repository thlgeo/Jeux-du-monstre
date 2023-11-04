package fr.univlille.sae.model;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class TestMaze {
    Maze maze;

    @BeforeEach
    void setUp(){
        maze = new Maze(3,3);
        maze.maze[1][1].setTurn(1);
        maze.monster.setCoordinateMonster(new Coordinate(1, 1));
    }

    @Test
    void testInitializeMaze() {
        Cell c1 = new Cell(ICellEvent.CellInfo.WALL);
        Cell c2 = new Cell(ICellEvent.CellInfo.EMPTY);
        Cell c3 = new Cell(ICellEvent.CellInfo.MONSTER, 1);
        Cell c4 = new Cell(ICellEvent.CellInfo.HUNTER);
        Cell c5 = new Cell(ICellEvent.CellInfo.EXIT);
        assertEquals(c1, maze.getCell(0,0));
        assertEquals(c2, maze.getCell(0,1));
        assertEquals(c3, maze.getCell(1,1));
        assertEquals(c4, maze.getCell(1,0));
        assertEquals(c5, maze.getCell(2,1));
    }

    @Test
    void testDeplacerMonstre(){
        ICoordinate coordPass = new Coordinate(0, 1);
        ICoordinate coordNotPass = new Coordinate(0, 0);
        maze.deplacementMonstre(coordPass);
        maze.deplacementMonstre(coordNotPass);
        Cell cellMazePass = maze.getCell(coordPass);
        Cell cellMazeNotPass = maze.getCell(coordNotPass);
        assertEquals(ICellEvent.CellInfo.MONSTER, cellMazePass.getInfo());
        assertEquals(1, cellMazePass.getTurn());
        assertEquals(ICellEvent.CellInfo.WALL, cellMazeNotPass.getInfo());
        assertEquals(0, cellMazeNotPass.getTurn());
        Cell cellMonsterPass = maze.monster.get(coordPass);
        Cell cellMonsterNotPass = maze.monster.get(coordNotPass);
        assertEquals(ICellEvent.CellInfo.MONSTER, cellMonsterPass.getInfo());
        assertEquals(1, cellMonsterPass.getTurn());
        assertEquals(ICellEvent.CellInfo.WALL, cellMonsterNotPass.getInfo());
        assertEquals(0, cellMonsterNotPass.getTurn());
        maze.deplacementMonstre(new Coordinate(1, 1));
        maze.deplacementMonstre(new Coordinate(2, 1)); //victoire donc reset
        Cell reset = maze.getCell(coordPass);
        assertEquals(ICellEvent.CellInfo.EMPTY, reset.getInfo());
        assertEquals(0, reset.getTurn());
    }

    @Test
    void testInitMonster() {
        maze.monster.setCoordinateMonster(null);
        maze.deplacementMonstre(new Coordinate(0, 0));
        Cell c1 = maze.getCell(new Coordinate(0, 1));
        Cell c2 = maze.getCell(new Coordinate(1, 2));
        assertTrue(ICellEvent.CellInfo.MONSTER == c1.getInfo() || ICellEvent.CellInfo.MONSTER == c2.getInfo());
        assertTrue(1 == c1.getTurn() || 1 == c2.getTurn());
    }

    @Test
    void testTirerChasseur(){
        ICoordinate coord = new Coordinate(0, 0);
        maze.tirerChasseur(coord);
        Cell cellHunter = maze.hunter.getCell(coord);
        assertEquals(ICellEvent.CellInfo.WALL, cellHunter.getInfo());
        assertEquals(0, cellHunter.getTurn());
        ICoordinate lastShot = maze.monster.getLastShotHunter();
        assertEquals(new Coordinate(0, 0), lastShot);
    }

    @Test
    void testTirerChasseurVitoire(){
        ICoordinate coord = new Coordinate(1, 1);
        maze.tirerChasseur(coord);
        Cell cellHunter = maze.hunter.getCell(coord);
        assertEquals(ICellEvent.CellInfo.EMPTY, cellHunter.getInfo());
        assertEquals(0, cellHunter.getTurn());
        ICoordinate lastShot = maze.monster.getLastShotHunter();
        assertNull(lastShot);
    }

    @Test
    void testChangerParam(){
        maze.changerParam("Nathan", "Valentin", 8);
        assertEquals("Valentin", maze.monster.getName());
        assertEquals("Nathan", maze.hunter.getName());
        assertEquals(8, maze.getNbRows());
        assertEquals(8, maze.getNbCols());
        assertEquals(8, maze.hunter.getNbRows());
        assertEquals(8, maze.hunter.getNbCols());
        assertEquals(8, maze.monster.getDiscoveredMaze().length);
        assertEquals(8, maze.monster.getDiscoveredMaze()[0].length);
    }
}
