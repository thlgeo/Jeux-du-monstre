package fr.univlille.sae.model;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import org.junit.Assert;
import org.junit.Test;


public class TestMaze {
    Maze maze;

    @Test
    public void testInitializeMaze() {
        maze = new Maze(1, 4, 4, "testMaze");
        Cell c1 = new Cell(new Coordinate(0,0), ICellEvent.CellInfo.WALL);
        Cell c2 = new Cell(new Coordinate(0,1), ICellEvent.CellInfo.EMPTY);
        Cell c3 = new Cell(new Coordinate(1,1), ICellEvent.CellInfo.MONSTER);
        Cell c4 = new Cell(new Coordinate(1,0), ICellEvent.CellInfo.HUNTER);
        Cell c5 = new Cell(new Coordinate(2,1), ICellEvent.CellInfo.EXIT);
        Assert.assertEquals(c1, maze.getCell(0,0));
        Assert.assertEquals(c2, maze.getCell(0,1));
        Assert.assertEquals(c3, maze.getCell(1,1));
        Assert.assertEquals(c4, maze.getCell(1,0));
        Assert.assertEquals(c5, maze.getCell(2,1));

    }
}
