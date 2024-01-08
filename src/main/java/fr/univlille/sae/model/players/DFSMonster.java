package fr.univlille.sae.model.players;

import fr.univlille.iutinfo.cam.player.monster.IMonsterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import fr.univlille.sae.model.Cell;
import fr.univlille.sae.model.Coordinate;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class DFSMonster implements IMonsterStrategy {

    Cell[][] maze;
    List<ICoordinate> path;
    Set<PathCoordinate> visited;

    public DFSMonster() {
        this.maze = null;
        this.path = null;
    }

    @Override
    public void initialize(boolean[][] tab) {
        this.maze = new Cell[tab.length][tab[0].length];
        this.path = null;
        for(int lig = 0; lig < tab.length; lig++) {
            for(int col = 0; col < tab[lig].length; col++) {
                if(tab[lig][col]) {
                    maze[lig][col] = new Cell(ICellEvent.CellInfo.EMPTY);
                } else {
                    maze[lig][col] = new Cell(ICellEvent.CellInfo.WALL);
                }
            }
        }
    }

    @Override
    public ICoordinate play() {
        if(path == null) {
            dfs();
        }
        return path.remove(0);
    }

    @Override
    public void update(ICellEvent cellEvent) {
        if(cellEvent.getState() == ICellEvent.CellInfo.MONSTER) {
            if(path == null || path.isEmpty()) dfs();
        }
        ICoordinate coord = cellEvent.getCoord();
        Cell updateCell = this.maze[coord.getRow()][coord.getCol()];
        updateCell.setInfo(cellEvent.getState());
        updateCell.setTurn(cellEvent.getTurn());
        updateCell.visited();
    }

    private PathCoordinate searchMonster() {
        for(int lig = 0; lig < maze.length; lig++) {
            for(int col = 0; col < maze[lig].length; col++) {
                if(maze[lig][col].getInfo() == ICellEvent.CellInfo.MONSTER) {
                    return new PathCoordinate(new Coordinate(lig, col));
                }
            }
        }
        return null;
    }

    private void dfs() {
        Stack<PathCoordinate> stack = new Stack<>();
        stack.push(searchMonster());
        boolean found = false;
        while(!stack.isEmpty() && !found) {
            PathCoordinate current = stack.pop();
            if(!visited.contains(current)) {
                visited.add(current);
                for(Coordinate neighbor : current.getCoord().around()) {
                    if(inRange(neighbor) && maze[neighbor.getRow()][neighbor.getCol()].getInfo() != ICellEvent.CellInfo.WALL && !visited.contains(neighbor)) {
                        stack.push(new PathCoordinate(neighbor, current));
                    }
                }
            }
            if(maze[current.getCoord().getRow()][current.getCoord().getCol()].getInfo() == ICellEvent.CellInfo.EXIT) {
                found = true;
                PathCoordinate tmp = current;
                while(tmp != null) {
                    path.add(tmp.getCoord());
                    tmp = tmp.getParent();
                }
                Collections.reverse(path);
            }
        }
    }

    /**
     * Permet de savoir si la coordonnée est dans le labyrinthe
     *
     * @param coord (ICoordinate) - la coordonnée à tester
     * @return (boolean)   - true si la coordonnée est dans le labyrinthe, false sinon
     */
    private boolean inRange(ICoordinate coord) {
        return (coord.getRow() >= 0 && coord.getRow() < maze.length) && (coord.getCol() >= 0 && coord.getCol() < maze[0].length);
    }

    class PathCoordinate {
        private ICoordinate coord;
        private PathCoordinate parent;

        public PathCoordinate(ICoordinate coord, PathCoordinate parent) {
            this.coord = coord;
            this.parent = parent;
        }

        public PathCoordinate(ICoordinate coord) {
            this(coord, null);
        }

        public Coordinate getCoord() {
            return (Coordinate) coord;
        }

        public PathCoordinate getParent() {
            return parent;
        }
    }

}
