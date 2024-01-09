package fr.univlille.sae.model;

import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import fr.univlille.sae.model.exceptions.UnsupportedMazeException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Classe MazeFactory - Permet de créer un labyrinthe
 *
 * @author Valentin THUILLIER, Armand SADY, Nathan DESMEE, Théo LENGLART
 * @version 1.0
 */
public class MazeFactory {
    protected static final String FS = File.separator;
    private double percent_wall;
    private final int x;
    private final int y;
    private final Random r = new Random();
    protected Cell[][] maze;
    protected boolean[][] visited;

    public MazeFactory(int x, int y, double percent_wall) {
        this.percent_wall = percent_wall;
        this.x = x;
        this.y = y;
        maze = new Cell[x][y];
        visited = new boolean[x][y];
    }

    /**
     * Génère un labyrinthe de taille x et de taille y.
     * @return Cell[][] - le labyrinthe généré
     */
    public Cell[][] generateMaze() {
        generePrim();
        return this.maze;
    }

    /**
     * Cette méthode permet de créer un labyrinthe
     * @param generate true si il faut créer automatiquement, false sinon
     * @return Cell[][] labyrinthe généré
     */
    public Cell[][] createMaze(boolean generate){
        if(generate) return generateMaze();
        return importMaze();
    }

    /**
     * Import un labyrinthe de la taille mise en paramètre.
     *
     * @return Cell[][] - le labyrinthe importé
     */
    public Cell[][] importMaze() {
        if(x != y) {
            return generateMaze();
        }
        generateImport(this.x, this.y);
        return this.maze;
    }

    /**
     * Génère un labyrinthe de taille x et y par défaut
     */
    private void makeDefaultMaze() {
        for(int i = 0; i < x; i++) {
            for(int j = 0; j < y; j++) {
                maze[i][j] = new Cell(ICellEvent.CellInfo.WALL);
            }
        }
    }

    /**
     * Algorithm permettant de générer un labyrinthe de taille x et y par défaut
     */
    protected void generePrim() {
        makeDefaultMaze();
        List<ICoordinate> frontier = new ArrayList<>();
        ICoordinate coord = new Coordinate(r.nextInt(x - 1), r.nextInt(y - 1));
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

    /**
     * Permet de retirer des murs aléatoirement dans le labyrinthe généré.
     */
    private void genereNotSet() {
        for(int row = 0; row < this.x; row++) {
            for(int col = 0; col < this.y; col++) {
                if(!isVisited(row, col) && r.nextDouble(1) <= percent_wall && isValidWall(row, col)) {
                    this.setVisited(row, col);
                }
            }
        }
    }

    /**
     * Vérifie si le mur est valide, c'est-à-dire qu'au moins une case adjacente au mur de coordonnées row & col est vide.
     * @param row
     * @param col
     * @return
     */
    private boolean isValidWall(int row, int col) {
        if (maze[row][col].getInfo() != ICellEvent.CellInfo.WALL) return false;
        ICoordinate coordNorth = new Coordinate(row - 1, col);
        ICoordinate coordSouth = new Coordinate(row + 1, col);
        ICoordinate coordEast = new Coordinate(row, col + 1);
        ICoordinate coordWest = new Coordinate(row, col - 1);
        ICoordinate[] neighbors = {coordNorth, coordSouth, coordEast, coordWest};
        for (ICoordinate neighbor : neighbors) {
            if (isValidCoords(neighbor) && maze[neighbor.getRow()][neighbor.getCol()].getInfo() == ICellEvent.CellInfo.EMPTY) {
                return true;
            }
        }
        return false;
    }

    /**
     * Permet de trouver la case qui est la "porte" entre deux cases, sert à générer le labyrinthe avec l'algorithme de Prim
     * @param start case de départ
     * @param end case d'arrivée
     * @return ICoordinate - la case qui est la "porte" entre les deux cases
     */
    private ICoordinate getGateway(ICoordinate start, ICoordinate end) {
        Coordinate diff = new Coordinate(start.getRow() - end.getRow(), start.getCol() - end.getCol());
        if(diff.getCol() < 0) diff.incrementCol();
        else if(diff.getCol() > 0) diff.decrementCol();
        else if(diff.getRow() < 0) diff.incrementRow();
        else if(diff.getRow() > 0) diff.decrementRow();
        else System.out.println("Erreur: " + diff);
        return new Coordinate(start.getRow() - diff.getRow(), start.getCol() - diff.getCol());
    }

    /**
     * Permet de marquer une case comme visitée (polymorphisme de la méthode setVisited)
     * @param coord
     */
    private void setVisited(ICoordinate coord) {
        setVisited(coord.getRow(), coord.getCol());
    }

    /**
     * Permet de marquer une case comme visitée, c'est-à-dire de l'indiquer dans le tableau visited
     * @param row
     * @param col
     */
    private void setVisited(int row, int col) {
        maze[row][col].setInfo(ICellEvent.CellInfo.EMPTY);
        visited[row][col] = true;
    }

    /**
     * Vérifie si la case a déjà été visitée (polymorphisme de la méthode isVisited)
     * @param coord coordonnées de la case
     * @return boolean - true si la case a déjà été visitée, false sinon
     */
    private boolean isVisited(ICoordinate coord) {
        return isVisited(coord.getRow(), coord.getCol());
    }

    /**
     * Vérifie si la case a déjà été visitée, c'est-à-dire si elle est dans le tableau visited
     * @param row
     * @param col
     * @return boolean - true si la case a déjà été visitée, false sinon
     */
    private boolean isVisited(int row, int col) {
        return visited[row][col];
    }

    /**
     * Permet de trouver la case d'origine d'une case, c'est-à-dire trouver une case déjà visitée adjacente à la case testée, sert à générer le labyrinthe avec l'algorithme de Prim.
     * @param cord coordonnées de la case
     * @return ICoordinate - la case d'origine de la case
     */
    private ICoordinate getOriginCord(ICoordinate cord) {
        List<ICoordinate> cordFrontiers = getFrontierCoords(cord);
        for(ICoordinate cordFrontier : cordFrontiers) {
            if(isValidCoords(cordFrontier) && isVisited(cordFrontier)) {
                return cordFrontier;
            }
        }
        return null;
    }

    /**
     * Permet de trouver les cases de la "frontière" à une case. C'est-à-dire les cases a 2 de distance de la case testée, sert à générer le labyrinthe avec l'algorithme de Prim.
     * @param cord coordonnées de la case
     * @return List<ICoordinate> - les cases adjacentes à la case
     */
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

    /**
     * Permet d'ajouter des cases à la "frontière", sert à générer le labyrinthe avec l'algorithme de Prim.
     * @param frontier liste de cases
     * @param toAdd liste de cases à ajouter
     */
    private void addToFrontier(List<ICoordinate> frontier, List<ICoordinate> toAdd) {
        for(ICoordinate cord : toAdd) {
            if(isPossibility(cord) && !frontier.contains(cord)) {
                frontier.add(cord);
            }
        }
    }

    /**
     * Vérifie s'il est possible de se déplacer sur la case
     * @param cord coordonnées de la case
     * @return boolean - true si la case est valide et est un mur, false sinon
     */
    private boolean isPossibility(ICoordinate cord) {
        return (isValidCoords(cord) && isWall(cord));
    }

    /**
     * Vérifie si les coordonnées sont valides (dans le labyrinthe)
     * @param cord coordonnées à vérifier
     * @return boolean - true si les coordonnées sont valides, false sinon
     */
    private boolean isValidCoords(ICoordinate cord) {
        return cord.getRow() >= 0 && cord.getRow() < x && cord.getCol() >= 0 && cord.getCol() < y;
    }

    /**
     * Vérifie si la case est un mur
     * @param cord coordonnées de la case
     * @return boolean - true si la case est un mur, false sinon
     */
    private boolean isWall(ICoordinate cord) {
        return maze[cord.getRow()][cord.getCol()].getInfo() == ICellEvent.CellInfo.WALL;
    }

    /**
     * Permet de récupérer le labyrinthe généré sous forme de String
     * @return Cell[][] - le labyrinthe généré
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Cell[] row : maze) {
            for(Cell cell : row) {
                sb.append(cell.getInfo().name().charAt(0) + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Import un labyrinthe de la taille mise en paramètre.
     *
     * @param nbRows nombre de lignes du labyrinthe
     * @param nbCols nombre de colonnes du labyrinthe
     */
    protected void generateImport(int nbRows, int nbCols) {
        BufferedReader reader = null;
        new Cell(); // Permet d'initialiser la map charToInfo
        try {
            if(nbRows > x && nbCols > y) {
                throw new UnsupportedMazeException();
            }
            this.maze = new Cell[x][y];
            String filePath = mazefilepath(nbRows, nbCols);
            reader = new BufferedReader(new FileReader(filePath));
            for(int rowId = 0; rowId < nbRows; rowId++) {
                String currentLine = reader.readLine();
                for(int colId = 0; colId < currentLine.length(); colId++) {
                    maze[rowId][colId] = new Cell(Cell.charToInfo.get(currentLine.charAt(colId)));
                }
            }
        } catch(UnsupportedMazeException | IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                assert reader != null;
                reader.close();
            } catch(Exception e) {
                //Do Nothing
            }
        }
    }

    /**
     * Prends les coordonnées et l'identifiant du labyrinthe et renvoi le chemin de ce labyrinthe (utilisé dans importMaze)
     *
     * @param nbCols Le nombre de colonnes du labyrinthe
     * @param nbRows Le nombre de lignes du labyrinthe
     * @return String - le chemin du fichier du labyrinthe associé aux paramètres
     */
    private String mazefilepath(int nbRows, int nbCols) {
        return System.getProperty("user.dir") + FS + "res" + FS + "mazes" + FS + "maze-" + nbCols + "-" + nbRows;
    }
}
