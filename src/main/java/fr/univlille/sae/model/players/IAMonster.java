package fr.univlille.sae.model.players;

import java.util.*;

import fr.univlille.iutinfo.cam.player.monster.IMonsterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;
import fr.univlille.sae.model.Cell;
import fr.univlille.sae.model.Coordinate;

public class IAMonster implements IMonsterStrategy {

    protected Cell[][] maze;
    protected ICoordinate coordonnee;
    public static final Random rd = new Random();
    protected List<ICoordinate> path = null;

    @Override
    public ICoordinate play() {
        if(path == null) {
            List<ICoordinate> l = possibilities();
            return l.get(rd.nextInt(l.size()));
        } else {
            System.out.println("Chemin =>" + path);
            return path.remove(0);
        }
    }

    private boolean inRange(ICoordinate coord)
    {
        return (coord.getRow() >= 0 && coord.getRow() < maze.length) && (coord.getCol() >= 0 && coord.getCol() < maze[0].length);
    }

    private List<ICoordinate> possibilities()
    {
        List<ICoordinate> l = new ArrayList<>();
        for(ICoordinate coord : around())
        {
            if(inRange(coord) && getCell(coord).getInfo() != CellInfo.WALL)
            {
                l.add(coord);
            }
        }
        return l;
    }

    private List<ICoordinate> around()  {
        List<ICoordinate> l = new ArrayList<>();
        int row = coordonnee.getRow();
        int col = coordonnee.getCol();
        l.add(new Coordinate(row+1, col));
        l.add(new Coordinate(row-1, col));
        l.add(new Coordinate(row, col+1));
        l.add(new Coordinate(row, col-1));
        return l;
    }

    @Override
    public void update(ICellEvent arg0) {
        if(arg0.getState() == CellInfo.MONSTER) {
            System.out.println("Mise à jour de la position du monstre");
            this.coordonnee = arg0.getCoord();
            if(path == null || path.isEmpty()) this.path = aStarAlgorithm();
        }
        ICoordinate coord = arg0.getCoord();
        Cell updateCell = this.maze[coord.getRow()][coord.getCol()];
        updateCell.setInfo(arg0.getState());
        updateCell.setTurn(arg0.getTurn());
        updateCell.visited();
    }

    @Override
    public void initialize(boolean[][] arg0) {
        maze = new Cell[arg0.length][arg0[0].length];
        for(int lig=0;lig<arg0.length;lig++)
        {
            for(int col=0;col<arg0[lig].length;col++)
            {
                if(arg0[lig][col])
                {
                    maze[lig][col] = new Cell(CellInfo.EMPTY);
                }else 
                {
                    maze[lig][col] = new Cell(CellInfo.WALL);
                }
            }
        }
    }

    public Cell getCell(ICoordinate coord)
    {
        return maze[coord.getRow()][coord.getCol()];
    }


    class Cellule {
        int row;
        int col;
        int distance;
        int heuristic;
        Cellule parent;

        public Cellule(int row, int col, int distance, int heuristic, Cellule parent) {
            this.row = row;
            this.col = col;
            this.distance = distance;
            this.heuristic = heuristic;
            this.parent = parent;
        }

        public Cellule(int row, int col, Cellule exit, Cellule parent) {
            this(row, col, Integer.MAX_VALUE, Math.abs(row - exit.row) + Math.abs(col - exit.col), parent);
        }

        private int calculateHeuristic(ICoordinate coord) {
            return Math.abs(row - coord.getRow()) + Math.abs(col - coord.getCol());
        }

        public boolean is(Cellule c) {
            return row == c.row && col == c.col;
        }

        private boolean valid(int row, int col) {
            return (row >= 0 && row < maze.length) && (col >= 0 && col < maze[0].length);
        }

        private Cellule north() {
            if(valid(row - 1, col)) {
                return new Cellule(row - 1, col, distance + 1, heuristic, this);
            }
            return null;
        }

        private Cellule south() {
            if(valid(row + 1, col)) {
                return new Cellule(row + 1, col, distance + 1, heuristic, this);
            }
            return null;
        }

        private Cellule east() {
            if(valid(row, col + 1)) {
                return new Cellule(row, col + 1, distance + 1, heuristic, this);
            }
            return null;
        }

        private Cellule west() {
            if(valid(row, col - 1)) {
                return new Cellule(row, col - 1, distance + 1, heuristic, this);
            }
            return null;
        }

        public Cellule[] around() {
            return new Cellule[] { north(), east(), south(), west() };
        }

        @Override
        public String toString() {
            return "Cellule [row=" + row + ", col=" + col + ", distance=" + distance + ", heuristic=" + heuristic
                    + ", parent=" + parent + "]";
        }

        @Override
        public boolean equals(Object o) {
            if(this == o) return true;
            if(!(o instanceof Cellule cellule)) return false;
            return row == cellule.row && col == cellule.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }
    }

    class NullCellule extends Cellule {
        public NullCellule() {
            super(-1, -1, -1, -1, null);
        }
    }

    public Cellule getExit() {
        for(int lig=0;lig<maze.length;lig++) {
            for(int col=0;col<maze[lig].length;col++) {
                if(maze[lig][col].getInfo() == CellInfo.EXIT) {
                    return new Cellule(lig, col, new NullCellule(), new NullCellule());
                }
            }
        }
        return null;
    }

    public Cellule getMonster() {
        Cellule m = new Cellule(coordonnee.getRow(), coordonnee.getCol(), getExit(), new NullCellule());
        m.distance = 0;
        return m;
    }

    public static Cellule getMin(Set<Cellule> set) {
        Cellule min = null;
        for(Cellule cell : set) {
            if(min == null || cell.distance < min.distance) {
                min = cell;
            }
        }
        set.remove(min);
        return min;
    }

    private boolean isWall(Cellule c) {
        return maze[c.row][c.col].getInfo() == CellInfo.WALL;
    }

    public List<ICoordinate> aStarAlgorithm() {
        Set<Cellule> open = new HashSet<>();
        Set<Cellule> closed = new HashSet<>();
        open.add(getMonster());
        Cellule exit = getExit();
        boolean found = false;
        Cellule current = null;
        while(!open.isEmpty() && !found) {
            current = getMin(open);
            closed.add(current);
            if(current == null) throw new RuntimeException("Current is null !");
            if(current.is(exit)) {
                found = true;
            } else {
                for(Cellule cell : current.around()) {
                    if(cell != null && (!open.contains(cell) || cell.distance > current.distance + 1) && !closed.contains(cell) && !isWall(cell)) {
                        cell.distance = current.distance + 1;
                        cell.parent = current;
                        open.add(cell);
                    }
                }
            }
        }
        if(open.isEmpty()) {
            throw new RuntimeException("Chemin impossible");
        }
        List<ICoordinate> path = new ArrayList<>();
        while(current != null && !current.is(new NullCellule())) {
            path.add(new Coordinate(current.row, current.col));
            current = current.parent;
        }
        Collections.reverse(path);
        return path;
    }
}
