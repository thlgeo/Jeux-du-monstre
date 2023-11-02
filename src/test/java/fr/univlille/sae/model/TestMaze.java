package fr.univlille.sae.model;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import org.junit.Assert;
import org.junit.Test;

public class TestMaze {
    Maze maze;

    @Test
    public void testInitializeMaze() {
        maze = new Maze(3,3);
        Cell c1 = new Cell(ICellEvent.CellInfo.WALL);
        Cell c2 = new Cell(ICellEvent.CellInfo.EMPTY);
        Cell c3 = new Cell(ICellEvent.CellInfo.MONSTER);
        Cell c4 = new Cell(ICellEvent.CellInfo.HUNTER);
        Cell c5 = new Cell(ICellEvent.CellInfo.EXIT);
        Assert.assertEquals(c1, maze.getCell(0,0));
        Assert.assertEquals(c2, maze.getCell(0,1));
        Assert.assertEquals(c3, maze.getCell(1,1));
        Assert.assertEquals(c4, maze.getCell(1,0));
        Assert.assertEquals(c5, maze.getCell(2,1));

    }
}
