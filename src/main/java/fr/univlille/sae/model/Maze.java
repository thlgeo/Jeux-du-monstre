package fr.univlille.sae.model;

import fr.univlille.iutinfo.cam.player.perception.ICellEvent;

import java.util.*;

public class Maze {
    protected Cell[][] maze;
    protected boolean[][] visited;
    private final int x;
    private final int y;
    private static final double PERCENT_WALL = 0.25;

    private final Random r = new Random();

    public Maze(int x, int y) {
        this.x = x;
        this.y = y;
        maze = new Cell[x][y];
        visited = new boolean[x][y];
        generePrim();
    }

    private void makeDefaultMaze() {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                maze[i][j] = new Cell(ICellEvent.CellInfo.WALL);
            }
        }
    }

    protected void generePrim() {
        makeDefaultMaze();
        List<ICoordinate> frontier = new ArrayList<>();
        ICoordinate coord = new Coordinate(r.nextInt(x-1), r.nextInt(y-1));
        maze[coord.getRow()][coord.getCol()].setInfo(ICellEvent.CellInfo.MONSTER);
        setVisited(coord);
        addToFrontier(frontier, getFrontierCoords(coord));
        Coordinate next = null;
        while(!frontier.isEmpty()) {
            next = (Coordinate) frontier.get(r.nextInt(frontier.size()));
            coord = getOriginCord(next);
            setVisited(next);
            frontier.remove(next);
            setVisited(getGateway(coord, next));
            addToFrontier(frontier, getFrontierCoords(next));
        }
        maze[next.getRow()][next.getCol()].setInfo(ICellEvent.CellInfo.EXIT);
        genereNotSet();
    }

    public void genereNotSet() {
        for(int row = 0; row < this.x; row++) {
            for(int col = 0; col < this.y; col++) {
                if(!isVisited(row, col) && r.nextDouble(1) <= PERCENT_WALL) {
                    this.setVisited(row, col);
                }
            }
        }
    }

    public ICoordinate getGateway(ICoordinate start, ICoordinate end) {
        Coordinate diff = new Coordinate(start.getRow() - end.getRow(), start.getCol() - end.getCol());
        if(diff.getCol() < 0) diff.incrementCol();
        else if(diff.getCol() > 0) diff.decrementCol();
        else if(diff.getRow() < 0) diff.incrementRow();
        else if(diff.getRow() > 0) diff.decrementRow();
        else System.out.println("Erreur: " + diff);
        return new Coordinate(start.getRow() - diff.getRow(), start.getCol() - diff.getCol());
    }

    private void setVisited(ICoordinate coord) {
        setVisited(coord.getRow(), coord.getCol());
    }

    private void setVisited(int row, int col) {
        maze[row][col].setInfo(ICellEvent.CellInfo.EMPTY);
        visited[row][col] = true;
    }
    
    private boolean isVisited(ICoordinate coord) {
        return isVisited(coord.getRow(), coord.getCol());
    }

    private boolean isVisited(int row, int col) {
        return visited[row][col];
    }

    private ICoordinate getOriginCord(ICoordinate cord) {
        List<ICoordinate> cordFrontiers = getFrontierCoords(cord);
        for (ICoordinate cordFrontier : cordFrontiers) {
            if (isValid(cordFrontier) && isVisited(cordFrontier)) {
                return cordFrontier;
            }
        }
        return null;
    }

    private List<ICoordinate> getFrontierCoords(ICoordinate cord) {
        Coordinate c = (Coordinate) cord;
        List<ICoordinate> frontier = new ArrayList<>();
        Coordinate north = c.north();
        north.decrementRow();
        Coordinate east = c.east();
        east.incrementCol();
        Coordinate west = c.west();
        west.decrementCol();
        Coordinate south = c.south();
        south.incrementRow();
        frontier.addAll(List.of(north, south, east, west));
        return frontier;
    }

    private void addToFrontier(List<ICoordinate> frontier, List<ICoordinate> toAdd) {
        for (ICoordinate cord: toAdd) {
            if (isPossibility(cord) && !frontier.contains(cord)) {
                frontier.add(cord);
            }
        }
    }

    private boolean isValid(Coordinate cord) {
        return (isInGrid(cord) && isWall(cord));
    }

    private boolean isInGrid(Coordinate cord) {
        return cord.getRow() >= 0 && cord.getRow() < x && cord.getCol() >= 0 && cord.getCol() < y;
    }
    private boolean isWall(Coordinate cord) {
        return maze[cord.getRow()][cord.getCol()].getInfo() == ICellEvent.CellInfo.WALL;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Cell[] row: maze) {
            for (Cell cell : row) {
                sb.append(cell.getInfo().name().charAt(0) + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
