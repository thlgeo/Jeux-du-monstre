package fr.univlille.sae.model.players;

import fr.univlille.iutinfo.cam.player.monster.IMonsterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import fr.univlille.sae.model.Cell;
import fr.univlille.sae.model.Coordinate;

import java.util.*;

/**
 * Classe DFSMonster - Stratégie du monstre utilisant l'algorithme DFS
 *
 * @author Valentin THUILLIER, Armand SADY, Nathan DESMEE, Théo LENGLART
 * @version 1.0
 */
public class DFSMonster implements IMonsterStrategy {

    Cell[][] maze;
    List<ICoordinate> path;
    Set<PathCoordinate> visited;
    ICoordinate monster;

    public DFSMonster() {
        this.maze = null;
        this.path = null;
        this.visited = null;
    }

    /**
     * Permet d'initialiser le labyrinthe
     *
     * @param tab (boolean[][])  - le labyrinthe
     */
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

    /**
     * Cette méthode permet de récupérer la position du monstre généré soit par aStarAlgorithm soit par une position aléatoire
     *
     * @return (ICoordinate)   - la position du monstre
     */
    @Override
    public ICoordinate play() {
        if(path == null) {
            dfs();
        }
        return path.remove(0);
    }

    /**
     * Permet de récupérer une événement de la cellule
     *
     * @param cellEvent (ICellEvent) - l'événement de la cellule
     */
    @Override
    public void update(ICellEvent cellEvent) {
        if(cellEvent.getState() == ICellEvent.CellInfo.MONSTER) {
            this.monster = cellEvent.getCoord();
            if(path == null || path.isEmpty()) dfs();
        }
        ICoordinate coord = cellEvent.getCoord();
        Cell updateCell = this.maze[coord.getRow()][coord.getCol()];
        updateCell.setInfo(cellEvent.getState());
        updateCell.setTurn(cellEvent.getTurn());
        updateCell.visited();
    }

    /**
     * Permet de trouver le chemin en utilisant l'algorithme DFS
     * Le chemin est stocké dans l'attribut path
     */
    private void dfs() {
        this.path = new ArrayList<>();
        this.visited = new HashSet<>();
        Deque<PathCoordinate> stack = new ArrayDeque<>();
        stack.push(new PathCoordinate(this.monster));
        boolean found = false;
        while(!stack.isEmpty() && !found) {
            PathCoordinate current = stack.pop();
            if(!visited.contains(current)) {
                visited.add(current);
                for(Coordinate neighbor : current.getCoord().around()) {
                    if(inRange(neighbor) && maze[neighbor.getRow()][neighbor.getCol()].getInfo() != ICellEvent.CellInfo.WALL && !visited.contains(new PathCoordinate(neighbor))) {
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
                path.remove(0); // Remove monster coord
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

    /**
     * Classe permettant de stocker une coordonnée et son parent
     */
    class PathCoordinate {
        private final ICoordinate coord;
        private final PathCoordinate parent;

        public PathCoordinate(ICoordinate coord, PathCoordinate parent) {
            this.coord = coord;
            this.parent = parent;
        }

        public PathCoordinate(ICoordinate coord) {
            this(coord, null);
        }

        /**
         * Permet de récupérer la coordonnée
         *
         * @return (ICoordinate)   - la coordonnée
         */
        public Coordinate getCoord() {
            return (Coordinate) coord;
        }

        /**
         * Permet de récupérer le parent
         *
         * @return (PathCoordinate)    - le parent
         */
        public PathCoordinate getParent() {
            return parent;
        }

        @Override
        public String toString() {
            return coord.toString();
        }

        @Override
        public boolean equals(Object o) {
            if(this == o) return true;
            if(!(o instanceof PathCoordinate that)) return false;
            return Objects.equals(getCoord(), that.getCoord());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getCoord());
        }
    }

}
