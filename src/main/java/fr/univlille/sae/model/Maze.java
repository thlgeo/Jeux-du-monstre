package fr.univlille.sae.model;

import fr.univlille.iutinfo.cam.player.perception.ICellEvent;

import java.util.*;

public class Maze {
    protected Cell[][] maze;
    protected boolean[][] visited;
    private final int x;
    private final int y;

    private final Random r = new Random();

    public Maze(int x, int y) {
        this.x = x;
        this.y = y;
        maze = new Cell[x][y];
        visited = new boolean[x][y];
        makeDefaultMaze();
    }

    private void makeDefaultMaze() {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                maze[i][j] = new Cell(ICellEvent.CellInfo.WALL);
            }
        }
    }
    private void generateMazePrim() {
        Coordinate cord = new Coordinate(r.nextInt(x), r.nextInt(y));
        maze[cord.getRow()][cord.getCol()].setInfo(ICellEvent.CellInfo.EMPTY);
        System.out.println(cord);
        ArrayList<Coordinate> frontier = new ArrayList<>();
        frontier.add(cord);
        frontier.addAll(getFrontierCoords(cord));
        /*
        for (Coordinate cord: frontier) {
            maze[cord.getRow()][cord.getCol()].setInfo(ICellEvent.CellInfo.EMPTY);
        }
        maze[start.getRow()][start.getCol()].setInfo(ICellEvent.CellInfo.EMPTY);
        */
        for (int i = 0 ; i < 20 ; i++) {
            Coordinate chosenCord = frontier.get(r.nextInt(frontier.size()));
            System.out.println(chosenCord);
            removeWall(cord, chosenCord);
            frontier.addAll(getFrontierCoords(chosenCord));
            System.out.println(frontier);
            frontier.remove(chosenCord);
        }
    }

    private Collection<? extends Coordinate> getFrontierCoords(Coordinate cord) {
        ArrayList<Coordinate> frontier = new ArrayList<>();
        Coordinate northFrontier = new Coordinate(cord.getRow()-2, cord.getCol());
        Coordinate southFrontier = new Coordinate(cord.getRow()+2, cord.getCol());
        Coordinate westFrontier = new Coordinate(cord.getRow(), cord.getCol()-2);
        Coordinate eastFrontier = new Coordinate(cord.getRow(), cord.getCol()+2);
        if (isValid(northFrontier)) {
            frontier.add(northFrontier);
        }
        if (isValid(southFrontier)) {
            frontier.add(southFrontier);
        }
        if (isValid(westFrontier)) {
            frontier.add(westFrontier);
        }
        if (isValid(eastFrontier)) {
            frontier.add(eastFrontier);
        }
        return frontier;
    }

    private void removeWall(Coordinate firstCord, Coordinate secondCord) {
        int row = (firstCord.getRow() + secondCord.getRow()) / 2;
        int col = (firstCord.getCol() + secondCord.getCol()) / 2;
        maze[row][col].setInfo(ICellEvent.CellInfo.EMPTY);
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
    public static void main(String[] args) {
        Maze maze = new Maze(11, 11);
        maze.generateMazePrim();
        System.out.println(maze);
    }
}
