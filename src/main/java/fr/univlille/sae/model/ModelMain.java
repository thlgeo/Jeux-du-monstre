package fr.univlille.sae.model;

import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import fr.univlille.iutinfo.utils.Observer;
import fr.univlille.iutinfo.utils.Subject;
import fr.univlille.sae.model.events.CellEvent;
import fr.univlille.sae.model.exceptions.MonsterNotFoundException;
import fr.univlille.sae.model.exceptions.UnsupportedMazeException;
import fr.univlille.sae.model.players.Hunter;
import fr.univlille.sae.model.players.Monster;
import fr.univlille.sae.model.players.RandomHunter;
import fr.univlille.sae.model.players.RandomMonster;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

/**
 * Classe Maze - La classe contient un tableau à deux dimensions de cellules permettant le lancement du jeu et son bon déroulement.
 *
 * @author Valentin THUILLIER, Armand SADY, Nathan DESMEE, Théo LENGLART
 * @version 1.0.0
 * @see Monster
 * @see Hunter
 * @see Cell
 */
public class ModelMain extends Subject {

    private static final int NB_TOUR_MIN = 5;
    private static final Random RDM = new Random();
    private static final int DEFAULT_DIMENSION = 10;
    protected static final String FS = File.separator;
    private static final int DEFAULT_TURN = 1;
    protected int turn;
    protected int nbRows;
    protected int nbCols;
    protected Hunter hunter;
    protected Monster monster;
    protected Cell[][] maze;
    protected boolean deplacementDiag;

    private ModelMain(int turn, int nbRows, int nbCols) {
        this.turn = turn;
        this.nbRows = nbRows;
        this.nbCols = nbCols;
        this.maze = new Cell[nbRows][nbCols];
        importMaze(nbRows, nbCols);
        this.monster = new Monster("Monster", this.maze);
        this.hunter = new Hunter("Hunter", nbRows, nbCols);
        this.deplacementDiag = false;
    }

    ModelMain(int nbRows, int nbCols) {
        this(DEFAULT_TURN, nbRows, nbCols);
    }

    public ModelMain() {
        this(DEFAULT_DIMENSION, DEFAULT_DIMENSION);
    }

    /**
     * Import un labyrinthe de la taille mise en paramètre.
     *
     * @param nbRows nombre de lignes du labyrinthe
     * @param nbCols nombre de colonnes du labyrinthe
     * @param id     l'identifiant du labyrinthe
     */
    protected void importMaze(int nbRows, int nbCols, int id) {
        BufferedReader reader = null;
        new Cell(); // Permet d'initialiser la map charToInfo
        try {
            if(nbRows > getNbRows() && nbCols > getNbRows()) {
                throw new UnsupportedMazeException();
            }
            this.maze = new Cell[this.getNbRows()][this.getNbCols()];
            String filePath = mazefilepath(nbRows, nbCols, id);
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
     * Reinitialise le labyrinthe avec les paramètres déjà définis
     */
    protected void reset() {
        changerParam(hunter.getName(), monster.getName(), nbRows, nbCols, deplacementDiag, false);
    }

    /**
     * Prends les coordonnées et l'identifiant du labyrinthe et renvoi le chemin de ce labyrinthe (utilisé dans importMaze)
     *
     * @param nbCols Le nombre de colonnes du labyrinthe
     * @param nbRows Le nombre de lignes du labyrinthe
     * @param id     Identifiant du labyrinthe
     * @return String - le chemin du fichier du labyrinthe associé aux paramètres
     */
    private String mazefilepath(int nbRows, int nbCols, int id) {
        return System.getProperty("user.dir") + FS + "res" + FS + "mazes" + FS + "maze-" + nbCols + "-" + nbRows + "-" + id;
    }

    /**
     * Importe le labyrinthe par défaut (id=0) de ces paramètres
     *
     * @param nbRows    (int)   Nombre de lignes du labyrinthe
     * @param nbCols    (int)   Nombre de colonnes du labyrinthe
     */
    private void importMaze(int nbRows, int nbCols) {
        importMaze(nbRows, nbCols, 0);
    }

    public int getNbRows() {
        return nbRows;
    }

    public int getNbCols() {
        return nbCols;
    }

    protected Cell getCell(int row, int col) {
        return maze[row][col];
    }

    protected ICoordinate getExit()
    {
        Cell c = null;
        for(int i=0;i<nbRows;i++)
        {
            for(int j=0;j<nbCols;j++)
            {
                c = getCell(i, j);
                if(c.getInfo() == CellInfo.EXIT) return new Coordinate(i, j);
            }
        }
        return null;
    }

    /**
     * Notifie aux observers si le monstre a bougé ou non, ou s'il a gagné.
     *
     * @param newCoord coordonnées choisies
     */
    public void deplacementMonstre(ICoordinate newCoord) {
        try {
            if(monster.getCoordinateMonster() == null) throw new MonsterNotFoundException();
            if(!this.monster.canMove(newCoord,deplacementDiag)) {
                monster.notifyCantMove();
                return;
            }
            if(getCell(newCoord).getInfo() == ICellEvent.CellInfo.EXIT) {
                victory(true);
                return;
            }
        } catch(MonsterNotFoundException e) {
            newCoord = this.initMonsterPosition();
            /*
            ICoordinate coordExit = getExit();
            while(inRange(newCoord,coordExit))
            {
                newCoord = this.initMonsterPosition();
            }

             */
        }
        ICellEvent event = new CellEvent(turn, ICellEvent.CellInfo.MONSTER, newCoord);
        monster.update(event);
        monster.setCoordinateMonster(newCoord);
        update(newCoord, ICellEvent.CellInfo.MONSTER);
        hunter.notifyTurnChange();
    }

    /**
     * Vérifie si les coordonnées du monstre sont à une portée de 5 cases ou moins de la sortie.
     * 
     * @param coordMonster (ICoordinate) Coordonnée du monstre
     * @param coordExit (ICoordinate) Coordonnée de la sortie
     * @return Vrai si le monstre est à une portée de moins de 5 cases de la sortie, faux sinon
     */
    private boolean inRange(ICoordinate coordMonster, ICoordinate coordExit)
    {
        int rowM = coordMonster.getRow();
        int colM = coordMonster.getCol();
        int rowE = coordExit.getRow();
        int colE = coordExit.getCol();
        return (colM < colE+NB_TOUR_MIN && colM > colE-NB_TOUR_MIN) && (rowM < rowE+NB_TOUR_MIN && rowM > rowE-NB_TOUR_MIN);
    }

    /**
     * Fonction récursive initialisant le monstre sur la maze. On prend des coordonnées aléatoires puis on regarde si la cellule associée est bien vide.
     * Si elle ne l'est pas, on réessaye.
     *
     * @return ICoorinate, les coordonnées du monstre
     */
    private ICoordinate initMonsterPosition() {
        ICoordinate coord = new Coordinate(RDM.nextInt(this.nbRows), RDM.nextInt(this.nbCols));
        Cell c = getCell(coord);
        return c.getInfo() != ICellEvent.CellInfo.EMPTY ? initMonsterPosition() : coord;
    }

    /**
     * Demande à indiquer la fin de la partie à monstre à joueur, puis envois le nom du vainqueur a la MainView pour afficher la fin de partie
     *
     * @param isMonster booléen indiquant qui a gagné (true = monstre, false = hunter)
     */
    protected void victory(boolean isMonster) {
        monster.notifyEndGame();
        hunter.notifyEndGame();
        reset();
        notifyObservers(isMonster ? monster.getName() : hunter.getName());
    }

    /**
     * Notifie aux observers si le chasseur a tiré sur le monstre (victoire), sinon informe le monstre des coordonnées du tir et informe le chasseur du type de la cellule choisie.
     *
     * @param coord coordonnées choisies
     */
    public void tirerChasseur(ICoordinate coord) {
        if(coord.equals(monster.getCoordinateMonster())) {
            victory(false);
            return;
        }
        if(!hunter.canShoot(coord)) {
            return;
        }
        ICellEvent monsterEvent = new CellEvent(turn, CellInfo.HUNTER, coord);
        ICellEvent hunterEvent = new CellEvent(getCell(coord).getTurn(), getCell(coord).getInfo(), coord);
        monster.update(monsterEvent);
        hunter.update(hunterEvent);
        turn++;
        hunter.notifyTurnPlus(turn);
        monster.notifyTurnPlus(turn);
        monster.notifyTurnChange();
    }

    /**
     * Fonction permettant de changer les paramètre de la partie
     *
     * @param hunterName    (String)    Nom du chasseur
     * @param monsterName   (String)    Nom du monstre
     * @param height    (int)       hauteur du labyrinthe
     * @param width   (int)       largeur du labyrinthe
     * @param depDiag   (boolean)       déplacement en diagonale
     */
    public void changerParam(String hunterName, String monsterName, int height, int width, boolean depDiag, boolean fog) {
        this.nbRows = height;
        this.nbCols = height;
        this.deplacementDiag = depDiag;
        importMaze(nbRows, nbCols);
        hunter.setName(hunterName);
        monster.setName(monsterName);
        hunter.setRowCol(nbRows, nbCols);
        monster.setMaze(this.maze);
        turn = DEFAULT_TURN;
        notifyObservers("ParamMAJ");
    }

    /**
     * Renvoie la cellule associée aux coordonnées
     *
     * @param coordinate (ICoordinate)   Coordonnées de la cellule
     * @return (Cell)  Cellule associée aux coordonnées
     */
    Cell getCell(ICoordinate coordinate) {
        if(coordinate == null) return null;
        if(coordinate.getRow() < 0 || coordinate.getRow() >= nbRows || coordinate.getCol() < 0 || coordinate.getCol() >= nbCols)
            return null;
        return maze[coordinate.getRow()][coordinate.getCol()];
    }

    /**
     * Mets à jour une cellule avec les informations de l'évènement
     *
     * @param coordinate (ICoordinate)   Coordonnées de la cellule
     * @param cellInfo   (CellInfo)  Informations de la cellule
     */
    void update(ICoordinate coordinate, ICellEvent.CellInfo cellInfo) {
        Cell updatedCell = getCell(coordinate);
        updatedCell.setInfo(cellInfo);
        updatedCell.setTurn(turn);
    }

    public void attachMonster(Observer o) {
        monster.attach(o);
    }

    public void attachHunter(Observer o) {
        hunter.attach(o);
    }

    /**
     * Notifie aux observers le labyrinthe déjà découvert
     */
    public void notifyDiscoveredMaze() {
        monster.notifyDiscoveredMaze();
        hunter.notifyDiscoveredMaze();
    }

    /**
     * Notifie aux observers d'afficher les paramètres
     */
    public void notifyShowParameter() {
        notifyObservers();
    }

    /**
     * Notifie aux observers d'afficher les pages monstre et le chasseur
     */
    public void notifyShowMH() {
        monster.notifyShow();
        hunter.notifyShow();
        notifyObservers("close");
    }
}