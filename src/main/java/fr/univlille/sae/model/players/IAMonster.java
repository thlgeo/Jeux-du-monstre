package fr.univlille.sae.model.players;

import fr.univlille.iutinfo.cam.player.monster.IMonsterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import fr.univlille.iutinfo.utils.Subject;
import fr.univlille.sae.model.Cell;
import fr.univlille.sae.model.Coordinate;

import java.util.*;

public class IAMonster implements IMonsterStrategy{

    public static final Random rd = new Random();
    protected Cell[][] maze;
    protected ICoordinate coordonnee;
    protected List<ICoordinate> path = null;

    /**
     * Permet de récupérer la cellule ayant la distance + heuristique la plus petite
     *
     * @param set (Set<Cellule>)  - l'ensemble de cellules
     * @return (Cellule)   - la cellule ayant la distance la plus petite
     */
    private static Cellule getMin(Set<Cellule> set) {
        Cellule min = null;
        for(Cellule cell : set) {
            if(min == null || cell.calculateF() < min.calculateF()) {
                min = cell;
            }
        }
        set.remove(min);
        return min;
    }

    /**
     * Cette méthode permet de récupérer la position du monstre généré soit par aStarAlgorithm soit par une position aléatoire
     *
     * @return (ICoordinate)   - la position du monstre
     */
    @Override
    public ICoordinate play() {
        if(path == null) {
            List<ICoordinate> l = possibilities();
            return l.get(rd.nextInt(l.size()));
        } else {
            return path.remove(0);
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
     * Permet de récupérer les coordonnées autour du monstre si elles sont valides
     *
     * @return (List < ICoordinate >)   - la liste des coordonnées autour de la coordonnée passée en paramètre
     */
    private List<ICoordinate> possibilities() {
        List<ICoordinate> l = new ArrayList<>();
        for(ICoordinate coord : around()) {
            if(inRange(coord) && getCell(coord).getInfo() != CellInfo.WALL) {
                l.add(coord);
            }
        }
        return l;
    }

    /**
     * Permet de récupérer les coordonnées autour du monstre
     *
     * @return (List < ICoordinate >)   - la liste des coordonnées autour de la coordonnée passée en paramètre
     */
    private List<ICoordinate> around() {
        List<ICoordinate> l = new ArrayList<>();
        int row = coordonnee.getRow();
        int col = coordonnee.getCol();
        l.add(new Coordinate(row + 1, col));
        l.add(new Coordinate(row - 1, col));
        l.add(new Coordinate(row, col + 1));
        l.add(new Coordinate(row, col - 1));
        return l;
    }

    /**
     * Permet de récupérer une événement de la cellule
     *
     * @param cellEvent (ICellEvent) - l'événement de la cellule
     */
    @Override
    public void update(ICellEvent cellEvent) {
        if(cellEvent.getState() == CellInfo.MONSTER) {
            this.coordonnee = cellEvent.getCoord();
            if(path == null || path.isEmpty()) aStarAlgorithm();
        }
        ICoordinate coord = cellEvent.getCoord();
        Cell updateCell = this.maze[coord.getRow()][coord.getCol()];
        updateCell.setInfo(cellEvent.getState());
        updateCell.setTurn(cellEvent.getTurn());
        updateCell.visited();
    }

    /**
     * Permet d'initialiser le labyrinthe
     *
     * @param tab (boolean[][])  - le labyrinthe
     */
    @Override
    public void initialize(boolean[][] tab) {
        maze = new Cell[tab.length][tab[0].length];
        this.path = null;
        for(int lig = 0; lig < tab.length; lig++) {
            for(int col = 0; col < tab[lig].length; col++) {
                if(tab[lig][col]) {
                    maze[lig][col] = new Cell(CellInfo.EMPTY);
                } else {
                    maze[lig][col] = new Cell(CellInfo.WALL);
                }
            }
        }
    }

    public Cell getCell(ICoordinate coord) {
        return maze[coord.getRow()][coord.getCol()];
    }

    /**
     * Permet de récupérer la cellule de sortie
     *
     * @return (Cellule)   - la cellule de sortie
     */
    private Cellule getExit() {
        for(int lig = 0; lig < maze.length; lig++) {
            for(int col = 0; col < maze[lig].length; col++) {
                if(maze[lig][col].getInfo() == CellInfo.EXIT) {
                    return new Cellule(lig, col, new NullCellule(), new NullCellule());
                }
            }
        }
        return null;
    }

    /**
     * Permet de récupérer la cellule du monstre
     *
     * @return  (Cellule)   - la cellule du monstre
     */
    private Cellule getMonster() {
        Cellule m = new Cellule(coordonnee.getRow(), coordonnee.getCol(), Objects.requireNonNull(getExit()), new NullCellule());
        m.distance = 0;
        return m;
    }

    /**
     * Permet de savoir si une cellule est un mur
     *
     * @param c (Cellule) - la cellule à tester
     * @return (boolean)   - true si la cellule est un mur, false sinon
     */
    private boolean isWall(Cellule c) {
        return maze[c.row][c.col].getInfo() == CellInfo.WALL;
    }

    /**
     * Permet de récupérer le chemin le plus court entre le monstre et la sortie
     */
    public void aStarAlgorithm() {
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
            if(current.equals(exit)) {
                found = true;
            } else {
                for(Cellule cell : current.around()) {
                    if(isValidChild(cell, open, current, closed)) {
                        cell.distance = current.distance + 1;
                        cell.parent = current;
                        open.add(cell);
                    }
                }
            }
        }
        if(open.isEmpty() && !found) {
            throw new RuntimeException("Chemin impossible");
        }
        path = new ArrayList<>();
        while(current != null && !current.equals(new NullCellule())) {
            path.add(new Coordinate(current.row, current.col));
            current = current.parent;
        }
        Collections.reverse(path);
    }

    /**
     * Permet de savoir si une celulle peut être explorée
     * @param cell  - la cellule à tester
     * @param open  - l'ensemble des cellules ouvertes
     * @param current   - la cellule courante
     * @param closed    - l'ensemble des cellules fermées
     * @return  - true si la cellule peut être explorée, false sinon
     */
    private boolean isValidChild(Cellule cell, Set<Cellule> open, Cellule current, Set<Cellule> closed) {
        return cell != null && (!open.contains(cell) || cell.distance > current.distance + 1) && !closed.contains(cell) && !isWall(cell);
    }

    /**
     * Classe interne permettant de représenter une cellule du labyrinthe avec des paramètres supplémentaires
     */
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

        public int calculateF() {
            return distance + heuristic;
        }

        /**
         * Permet de savoir si une cellule est valide
         *
         * @param row (int)   - la ligne de la cellule
         * @param col (int)   - la colonne de la cellule
         * @return (boolean)   - true si la cellule est valide, false sinon
         */
        private boolean valid(int row, int col) {
            return (row >= 0 && row < maze.length) && (col >= 0 && col < maze[0].length);
        }

        /**
         * Permet de récupérer la cellule au nord de la cellule courante si elle est valide
         *
         * @return (Cellule)   - la cellule au nord de la cellule courante
         */
        private Cellule north() {
            if(valid(row - 1, col)) {
                return new Cellule(row - 1, col, distance + 1, heuristic, this);
            }
            return null;
        }

        /**
         * Permet de récupérer la cellule au sud de la cellule courante si elle est valide
         *
         * @return (Cellule)   - la cellule au sud de la cellule courante
         */
        private Cellule south() {
            if(valid(row + 1, col)) {
                return new Cellule(row + 1, col, distance + 1, heuristic, this);
            }
            return null;
        }

        /**
         * Permet de récupérer la cellule à l'est de la cellule courante si elle est valide
         *
         * @return (Cellule)   - la cellule à l'est de la cellule courante
         */
        private Cellule east() {
            if(valid(row, col + 1)) {
                return new Cellule(row, col + 1, distance + 1, heuristic, this);
            }
            return null;
        }

        /**
         * Permet de récupérer la cellule à l'ouest de la cellule courante si elle est valide
         *
         * @return (Cellule)   - la cellule à l'ouest de la cellule courante
         */
        private Cellule west() {
            if(valid(row, col - 1)) {
                return new Cellule(row, col - 1, distance + 1, heuristic, this);
            }
            return null;
        }

        /**
         * Permet de récupérer les cellules autour de la cellule courante
         *
         * @return (Cellule[])   - les cellules autour de la cellule courante
         */
        public Cellule[] around() {
            return new Cellule[]{north(), east(), south(), west()};
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

    /**
     * Classe interne permettant de représenter une cellule nulle
     */
    class NullCellule extends Cellule {
        public NullCellule() {
            super(-1, -1, -1, -1, null);
        }
    }
}
