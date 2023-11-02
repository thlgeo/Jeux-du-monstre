package fr.univlille.sae.model;

import fr.univlille.iutinfo.cam.player.hunter.IHunterStrategy;
import fr.univlille.iutinfo.cam.player.monster.IMonsterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;

import java.io.*;
import java.util.Random;

import fr.univlille.iutinfo.utils.Observer;
import fr.univlille.iutinfo.utils.Subject;
import fr.univlille.sae.model.events.CellEvent;
import fr.univlille.sae.model.events.ParameterEvent;
import fr.univlille.sae.model.exceptions.MonsterNotFoundException;
import fr.univlille.sae.model.exceptions.UnsupportedMazeException;
import fr.univlille.sae.model.players.Monster;
import fr.univlille.sae.model.players.Hunter;

/**
 * Classe Maze - La classe contient un tableau à deux dimensions de cellules permettant le lancement du jeu et son bon déroulement.
 * @see Monster
 * @see Hunter
 * @see Cell
 * @author Valentin THUILLIER, Armand SADY, Nathan DESMEE, Théo LENGLART
 * @version 1.0.0
 */
public class
Maze extends Subject {

    public static final Random RDM = new Random();
    protected int turn;
    protected int nbRows;
    protected int nbCols;
    protected boolean isHunterTurn;
    protected Hunter hunter;
    protected Monster monster;
    protected Cell[][] maze;

    protected static final String FS = File.separator;

    public static final int DEFAULT_DIMENSION = 10;
    private static final int DEFAULT_TURN = 0;

    private Maze(int turn, int nbRows, int nbCols) {
        this.turn = turn;
        this.nbRows = nbRows;
        this.nbCols = nbCols;
        this.maze = new Cell[nbRows][nbCols];
        importMaze(nbRows, nbCols);
        this.monster = new Monster("Monster", this.maze);
        this.hunter = new Hunter("Hunter", nbRows, nbCols);
    }

    public Maze(int nbRows, int nbCols) {
        this(DEFAULT_TURN, nbRows, nbCols);
    }

    public Maze() {
        this(DEFAULT_DIMENSION, DEFAULT_DIMENSION);
    }

    /**
     * Import un labyrinthe de la taille mise en paramètre.
     * @param nbRows nombre de lignes du labyrinthe
     * @param nbCols nombre de colonnes du labyrinthe
     * @param id l'identifiant du labyrinthe
     */
    private void importMaze(int nbRows, int nbCols, int id) {
        BufferedReader reader = null;
        try {
            if (nbRows > getNbRows() && nbCols > getNbRows()) {
                throw new UnsupportedMazeException();
            }
            this.maze = new Cell[this.getNbRows()][this.getNbCols()];
            String filePath = mazefilepath(nbRows, nbCols, id);
            reader = new BufferedReader(new FileReader(filePath));
            for(int rowId = 0 ; rowId < nbRows; rowId++) {
                String currentLine = reader.readLine();
                for (int colId = 0 ; colId < currentLine.length() ; colId++) {
                    maze[rowId][colId] = new Cell(Cell.charToInfo.get(currentLine.charAt(colId)));
                }
            }
        } catch (UnsupportedMazeException | IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                assert reader != null;
                reader.close(); }
            catch(Exception e) {
                //Do Nothing
            }
        }
    }

    public void reset() {
        changerParam(hunter.getName(), monster.getName(), nbRows, nbCols);
    }

    /**
     * Prends les coordonnées et l'identifiant du labyrinthe et renvoit le chemin de ce labyrinthe (utilisé dans importMaze)
     * @param nbCols Le nombre de colonnes du labyrinthe
     * @param nbRows Le nombre de lignes du labyrinthe
     * @param id Identifiant du labyrinthe
     * @return String - le chemin du fichier du labyrinthe associé aux paramètres
     */
    private String mazefilepath(int nbRows, int nbCols, int id) {
        return System.getProperty("user.dir")+FS+"res"+FS+"mazes"+FS+"maze-"+nbCols+"-"+nbRows+"-"+id;
    }

    /**
     * Importe le labyrinthe par défaut (id=0) de ces paramètres
     * @param nbRows
     * @param nbCols
     */
    private void importMaze(int nbRows, int nbCols) {
        importMaze(nbRows, nbCols, 0);
    }

    public Cell[][] getMaze() {
        return maze;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getNbRows() {
        return nbRows;
    }

    public void setNbRows(int nbRows) {
        this.nbRows = nbRows;
    }

    public int getNbCols() {
        return nbCols;
    }

    public void setNbCols(int nbCols) {
        this.nbCols = nbCols;
    }

    public IHunterStrategy getHunter() {
        return hunter;
    }

    public void setHunter(Hunter hunter) {
        this.hunter = hunter;
    }

    public IMonsterStrategy getMonster() {
        return monster;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    public Cell getCell(int row, int col) {
        return maze[row][col];
    }

    /**
     * Notifie aux observeurs si le monstre a bougé ou non, ou s'il a gagné.
     * @param newCoord coordinate chosen by the player/AI.
     */
    public void deplacementMonstre(ICoordinate newCoord) {
        try {
            this.getCoordMonster(this.getTurn());
            if(!this.monster.canMove(newCoord)) { monster.notifyCantMove();  }

            if(getCell(newCoord).getInfo() == ICellEvent.CellInfo.EXIT) {
                victory(true);
                return;
            }
        } catch(MonsterNotFoundException e) {
            newCoord = this.initMonsterPosition();
        }
        ICellEvent event = new CellEvent(turn, ICellEvent.CellInfo.MONSTER, newCoord);
        monster.update(event);
        monster.setCoordinateMonster(newCoord);
        update(newCoord, ICellEvent.CellInfo.MONSTER);
        monster.notifyTurnChange();
    }

    /**
     * Fonction récursive initialisant le monstre sur la maze. On prends des coordonnées aléatoires puis on regarde si la cellule associée est bien vide.
     * Si elle ne l'est pas, on réessaye.
     * @return ICoorinate, les coordonnées du monstre
     */
    private ICoordinate initMonsterPosition() {
        ICoordinate coord = new Coordinate(RDM.nextInt(this.nbRows), RDM.nextInt(this.nbCols));
        Cell c = getCell(coord);
        return c.getInfo() != ICellEvent.CellInfo.EMPTY ? initMonsterPosition() : coord;
    }

    /**
     * Demande a indiquer la fin de la partie à monstre & à joueur, puis envois le nom du vainqueur a la MainView pour afficher la fin de partie
     * @param isMonster booléen indiquant qui a gagné (true = monstre, false = hunter)
     */
    public void victory(boolean isMonster) {
        monster.notifyEndGame();
        hunter.notifyEndGame();
        reset();
        notifyObservers(isMonster ? monster.getName() : hunter.getName());
    }

    /**
     * Notifie aux observers si le chasseur a tiré sur le monstre (victoire), sinon informe le monstre des coordonnées du tir et informe le chasseur du type de la cellule choisie.
     * @param coord coordinate chosen by the player/AI.
     * @throws MonsterNotFoundException
     */
    public void tirerChasseur(ICoordinate coord) throws MonsterNotFoundException {
        if(coord.equals(getCoordMonster(turn)))
        {
            victory(false);
        }
        ICellEvent monsterEvent = new CellEvent(turn, CellInfo.HUNTER, coord);
        ICellEvent hunterEvent = new CellEvent(turn, getCell(coord).getInfo(), coord);
        monster.update(monsterEvent);
        hunter.update(hunterEvent);
        turn++;
        hunter.notifyTurnChange();
    }

    /**
     * Cherche dans la maze pour trouver la coordonnée du monstre dans la maze
     * @param turn
     * @return ICoordinate les coordonnées du monstre
     * @throws MonsterNotFoundException
     */
    public ICoordinate getCoordMonster(int turn) throws MonsterNotFoundException {
        for(int i = 0; i < maze.length; i++) {
            for(int j = 0; j < maze[i].length; j++) {
                if(maze[i][j].getInfo() == ICellEvent.CellInfo.MONSTER) return new Coordinate(i, j);
            }
        }
        throw new MonsterNotFoundException();
    }

    /**
     * Fonction permettant de changer les paramètre de la partie
     * @param hunterName
     * @param monsterName
     * @param nbRows
     * @param nbCols
     */
    public void changerParam(String hunterName, String monsterName, int nbRows, int nbCols) {
        this.nbRows = nbRows;
        this.nbCols = nbCols;
        importMaze(nbRows, nbCols);
        this.hunter = new Hunter(hunterName, nbRows, nbCols);
        this.monster = new Monster(monsterName, this.maze);
        notifyObservers("ParamMAJ");
    }

    public Cell getCell(ICoordinate coordinate) {
        return maze[coordinate.getRow()][coordinate.getCol()];
    }

    public void update(ICoordinate coordinate, ICellEvent.CellInfo cellInfo) {
        Cell updatedCell = getCell(coordinate);
        updatedCell.setInfo(cellInfo);
        updatedCell.setTurn(turn);
    }

    public void update(ICellEvent event) {
        update(event.getCoord(), event.getState());
    }

    public void attachMonster(Observer o){
        monster.attach(o);
        monster.notifyDiscoveredMaze();
        notifyObservers("close");
    }

    public void attachHunter(Observer o){
        hunter.attach(o);
    }

    public void attachParameter(Observer o){
        attach(o);
        ParameterEvent pe = new ParameterEvent();
        pe.setValue(ParameterEvent.NB_COLS, "" + nbCols);
        pe.setValue(ParameterEvent.NB_ROWS, "" + nbRows);
        pe.setValue(ParameterEvent.NAME_MONSTER, monster.getName());
        pe.setValue(ParameterEvent.NAME_HUNTER, hunter.getName());
        notifyObservers(pe);
    }

}