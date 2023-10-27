import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.sae.model.Cell;
import fr.univlille.sae.model.Coordinate;
import fr.univlille.sae.model.Maze;
import org.junit.Assert;
import org.junit.Test;

public class TestMaze {
    Maze maze = new Maze();

    @Test
    public void testInitializeMaze() {
        Coordinate c = new Coordinate(0,0);
        Assert.assertEquals(maze.getCell(c), new Cell(c, ICellEvent.CellInfo.WALL));

    }
}
