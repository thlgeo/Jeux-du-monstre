package fr.univlille.sae.model;

import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import fr.univlille.sae.Main;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class TestModelMain {
    ModelMain modelMain;

    @BeforeEach
    void setUp(){
        modelMain = new ModelMain(1,3,3, false);
        modelMain.maze[1][1].setTurn(1);
        modelMain.monster.setCoordinateMonster(new Coordinate(1, 1));
    }

    @Test
    void testInitializeMaze() {
        Cell c1 = new Cell(ICellEvent.CellInfo.WALL);
        Cell c2 = new Cell(ICellEvent.CellInfo.EMPTY);
        Cell c3 = new Cell(ICellEvent.CellInfo.MONSTER, 1);
        Cell c4 = new Cell(ICellEvent.CellInfo.HUNTER);
        Cell c5 = new Cell(ICellEvent.CellInfo.EXIT);
        assertEquals(c1, modelMain.getCell(0,0));
        assertEquals(c2, modelMain.getCell(0,1));
        assertEquals(c3, modelMain.getCell(1,1));
        assertEquals(c4, modelMain.getCell(1,0));
        assertEquals(c5, modelMain.getCell(2,1));
    }

    @Test
    void testDeplacerMonstre(){
        ICoordinate coordPass = new Coordinate(0, 1);
        modelMain.deplacementMonstre(coordPass);
        Cell cellMazePass = modelMain.getCell(coordPass);
        assertEquals(ICellEvent.CellInfo.MONSTER, cellMazePass.getInfo());
        assertEquals(1, cellMazePass.getTurn());
        Cell cellMonsterPass = modelMain.monster.get(coordPass);
        assertEquals(ICellEvent.CellInfo.MONSTER, cellMonsterPass.getInfo());
        assertEquals(1, cellMonsterPass.getTurn());
    }

    @Test
    void testDeplacerMonstreMur(){
        ICoordinate coordNotPass = new Coordinate(0, 0);
        modelMain.deplacementMonstre(coordNotPass);
        Cell cellMazeNotPass = modelMain.getCell(coordNotPass);
        Cell cellMonsterNotPass = modelMain.monster.get(coordNotPass);
        assertEquals(ICellEvent.CellInfo.WALL, cellMazeNotPass.getInfo());
        assertEquals(0, cellMazeNotPass.getTurn());
        assertEquals(ICellEvent.CellInfo.WALL, cellMonsterNotPass.getInfo());
        assertEquals(0, cellMonsterNotPass.getTurn());
    }

    @Test
    void testDeplacerMonstreVictoire(){
        ICoordinate coordPass = new Coordinate(0, 1);
        modelMain.deplacementMonstre(new Coordinate(1, 1));
        modelMain.deplacementMonstre(new Coordinate(2, 1)); //victoire donc reset
        Cell reset = modelMain.getCell(coordPass);
        assertEquals(ICellEvent.CellInfo.EMPTY, reset.getInfo());
        assertEquals(0, reset.getTurn());
    }

    @Test
    void testInitMonster() {
        modelMain.setNbTourMin(0);
        modelMain.monster.setCoordinateMonster(null);
        modelMain.deplacementMonstre(new Coordinate(0, 0));
        Cell c1 = modelMain.getCell(new Coordinate(0, 1));
        Cell c2 = modelMain.getCell(new Coordinate(1, 2));
        assertTrue(ICellEvent.CellInfo.MONSTER == c1.getInfo() || ICellEvent.CellInfo.MONSTER == c2.getInfo());
        assertTrue(1 == c1.getTurn() || 1 == c2.getTurn());
    }

    @Test
    void testTirerChasseur(){
        ICoordinate coord = new Coordinate(0, 0);
        modelMain.tirerChasseur(coord);
        Cell cellHunter = modelMain.hunter.getCell(coord);
        assertEquals(ICellEvent.CellInfo.WALL, cellHunter.getInfo());
        assertEquals(0, cellHunter.getTurn());
        ICoordinate lastShot = modelMain.monster.getLastShotHunter();
        assertEquals(new Coordinate(0, 0), lastShot);
    }

    @Test
    void testTirerChasseurVitoire(){
        ICoordinate coord = new Coordinate(1, 1);
        modelMain.tirerChasseur(coord);
        Cell cellHunter = modelMain.hunter.getCell(coord);
        assertEquals(ICellEvent.CellInfo.EMPTY, cellHunter.getInfo());
        assertEquals(0, cellHunter.getTurn());
        ICoordinate lastShot = modelMain.monster.getLastShotHunter();
        assertNull(lastShot);
    }

    @Test
    void testRebuildMaze(){
        modelMain.rebuildMaze(16, 15, false, 0.0);
        assertEquals(16, modelMain.getNbRows());
        assertEquals(15, modelMain.getNbCols());
        assertFalse(modelMain.generateMaze);
        assertEquals(0.0, modelMain.getPercent_wall());
        assertEquals(16, modelMain.hunter.getNbRows());
        assertEquals(15, modelMain.hunter.getNbCols());
    }

    @Test
    void testRebuildPlayer(){
        modelMain.rebuildPlayers("nathan", "armand", true, true, Main.IA_PACKAGE + "DFSMonster", Main.IA_PACKAGE + "IAHunterRandom");
        assertEquals("nathan", modelMain.hunter.getName());
        assertEquals("armand", modelMain.monster.getName());
        assertTrue(modelMain.monsterIsIA);
        assertTrue(modelMain.hunterIsIA);
        assertEquals(Main.IA_PACKAGE + "DFSMonster", modelMain.IAMonster.getClass().getName());
        assertEquals(Main.IA_PACKAGE + "IAHunterRandom", modelMain.IAHunter.getClass().getName());
        assertFalse(modelMain.fog);
    }

    @Test
    void testRebuildOption(){
        modelMain.rebuildOption(true, true);
        assertTrue(modelMain.deplacementDiag);
        assertTrue(modelMain.fog);
    }
}
