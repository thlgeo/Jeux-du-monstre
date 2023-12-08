package fr.univlille.sae.model;

import fr.univlille.iutinfo.cam.player.hunter.IHunterStrategy;
import fr.univlille.iutinfo.cam.player.monster.IMonsterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import fr.univlille.iutinfo.utils.Observer;
import fr.univlille.iutinfo.utils.Subject;
import fr.univlille.sae.model.events.CellEvent;
import fr.univlille.sae.model.exceptions.MonsterNotFoundException;
import fr.univlille.sae.model.players.Hunter;
import fr.univlille.sae.model.players.IAHunter;
import fr.univlille.sae.model.players.IAMonster;
import fr.univlille.sae.model.players.Monster;

import java.io.File;
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
public class ModelMain extends Subject implements ModelMainInterface {

    protected static final String FS = File.separator;
    private static final int NB_TOUR_MIN = 5;
    private static final Random RDM = new Random();
    private static final int DEFAULT_DIMENSION = 10;
    private static final int DEFAULT_TURN = 1;
    private static final int VISION = 1;
    protected boolean generateMaze = true;
    protected int turn;
    protected int nbRows;
    protected int nbCols;
    protected Hunter hunter;
    protected Monster monster;
    protected IMonsterStrategy IAMonster;
    protected IHunterStrategy IAHunter;
    protected Cell[][] maze;
    protected boolean deplacementDiag = false;
    protected boolean fog = false;
    protected boolean monsterIA = false;
    protected boolean hunterIA = false;
    protected String IAMonsterName = "IA Monster";
    protected String IAHunterName = "IA Hunter";

    private ModelMain(int turn, int nbRows, int nbCols) {
        this.turn = turn;
        this.nbRows = nbRows;
        this.nbCols = nbCols;
        this.maze = null;
        createMaze();
        this.monster = new Monster("Monster", this.maze);
        this.hunter = new Hunter("Hunter", nbRows, nbCols);
        this.IAMonster = new IAMonster();
        this.IAHunter = new IAHunter();
        setMonsterIA();
        IAHunter.initialize(nbRows, nbCols);
    }

    ModelMain(int turn, int nbRows, int nbCols, boolean generateMaze) {
        this.generateMaze = generateMaze;
        this.turn = turn;
        this.nbRows = nbRows;
        this.nbCols = nbCols;
        this.maze = null;
        createMaze();
        this.monster = new Monster("Monster", this.maze);
        this.hunter = new Hunter("Hunter", nbRows, nbCols);
        this.IAMonster = new IAMonster();
        this.IAHunter = new IAHunter();
        setMonsterIA();
        IAHunter.initialize(nbRows, nbCols);
    }

    ModelMain(int nbRows, int nbCols) {
        this(DEFAULT_TURN, nbRows, nbCols);
    }

    public ModelMain() {
        this(DEFAULT_DIMENSION, DEFAULT_DIMENSION);
    }

    private void setMonsterIA() {
        boolean[][] booleanMaze = new boolean[nbRows][nbCols];
        for(int i = 0; i < nbRows; i++) {
            for(int j = 0; j < nbCols; j++) {
                booleanMaze[i][j] = maze[i][j].getInfo() != ICellEvent.CellInfo.WALL;
            }
        }
        IAMonster.initialize(booleanMaze);
        ICoordinate exit = getExit();
        ICellEvent event = new CellEvent(turn, ICellEvent.CellInfo.EXIT, exit);
        IAMonster.update(event);
    }

    private void createMaze() {
        if(generateMaze) {
            this.maze = new MazeFactory(this.nbRows, this.nbCols).generateMaze();
        } else {
            this.maze = new MazeFactory(this.nbRows, this.nbCols).importMaze();
        }
    }


    /**
     * Reinitialise le labyrinthe avec les paramètres déjà définis
     */
    protected void reset() {
        changerParam(hunter.getName(), monster.getName(), nbRows, nbCols, deplacementDiag, fog, generateMaze, monsterIA, hunterIA);
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

    protected ICoordinate getExit() {
        Cell c = null;
        for(int i = 0; i < nbRows; i++) {
            for(int j = 0; j < nbCols; j++) {
                c = getCell(i, j);
                if(c.getInfo() == CellInfo.EXIT) return new Coordinate(i, j);
            }
        }
        return null;
    }

    /**
     * Notifie aux observers si le monstre a bougé ou non, ou s'il a gagné.
     *
     * @param coord coordonnées choisies
     */
    public void deplacementMonstre(ICoordinate coord) {
        if(hunterIA && monsterIA) {
            deplacementIA(coord);
        } else if(hunterIA) {
            deplacementIAChasseur(coord);
        } else if(monsterIA) {
            deplacementIAMonster(coord);
        } else {
            deplacementHumain(coord);
        }
    }

    private void deplacementHumain(ICoordinate coord) {
        try {
            if(monster.getCoordinateMonster() == null) throw new MonsterNotFoundException();
            if(!this.monster.canMove(coord, deplacementDiag)) {
                monster.notify("cantMove");
                return;
            }
            if(getCell(coord).getInfo() == ICellEvent.CellInfo.EXIT) {
                victory(true);
                return;
            }
        } catch(MonsterNotFoundException e) {
            coord = this.initMonsterPosition();
            ICoordinate coordExit = getExit();
            while(inRange(coord, coordExit)) {
                coord = this.initMonsterPosition();
            }
        }
        ICellEvent event = new CellEvent(turn, ICellEvent.CellInfo.MONSTER, coord);
        update(coord, ICellEvent.CellInfo.MONSTER);
        monster.setCoordinateMonster(coord);
        if(fog) {
            updateAround(coord);
        }
        monster.update(event);
        hunter.notify("changerTour");
    }

    private void deplacementIAChasseur(ICoordinate coord) {
        try {
            if(monster.getCoordinateMonster() == null) throw new MonsterNotFoundException();
            if(!this.monster.canMove(coord, deplacementDiag)) {
                monster.notify("cantMove");
                return;
            }
            if(getCell(coord).getInfo() == ICellEvent.CellInfo.EXIT) {
                victory(true);
                return;
            }
        } catch(MonsterNotFoundException e) {
            coord = this.initMonsterPosition();
            ICoordinate coordExit = getExit();
            while(inRange(coord, coordExit)) {
                coord = this.initMonsterPosition();
            }
        }
        ICellEvent event = new CellEvent(turn, ICellEvent.CellInfo.MONSTER, coord);
        update(coord, ICellEvent.CellInfo.MONSTER);
        monster.setCoordinateMonster(coord);
        if(fog) {
            updateAround(coord);
        }
        monster.update(event);
        tirerChasseur(IAHunter.play());
    }

    private void deplacementIAMonster(ICoordinate coord) {
        try {
            if(getCoordinateMonster(true) == null) throw new MonsterNotFoundException();
            if(getCell(coord).getInfo() == ICellEvent.CellInfo.EXIT) {
                victory(true);
                return;
            }
        } catch(MonsterNotFoundException e) {
            coord = this.initMonsterPosition();
            ICoordinate coordExit = getExit();
            while(inRange(coord, coordExit)) {
                coord = this.initMonsterPosition();
            }
        }
        ICellEvent event = new CellEvent(turn, ICellEvent.CellInfo.MONSTER, coord);
        update(coord, ICellEvent.CellInfo.MONSTER);
        IAMonster.update(event);
        hunter.notify("changerTour");
    }

    private void deplacementIA(ICoordinate coord) {
        try {
            if(getCoordinateMonster(true) == null) throw new MonsterNotFoundException();
            if(getCell(coord).getInfo() == ICellEvent.CellInfo.EXIT) {
                victory(true);
                return;
            }
        } catch(MonsterNotFoundException e) {
            coord = this.initMonsterPosition();
            ICoordinate coordExit = getExit();
            while(inRange(coord, coordExit)) {
                coord = this.initMonsterPosition();
            }
        }
        ICellEvent event = new CellEvent(turn, ICellEvent.CellInfo.MONSTER, coord);
        update(coord, ICellEvent.CellInfo.MONSTER);
        IAMonster.update(event);
        tirerChasseur(IAHunter.play());
    }

    protected void updateAround(ICoordinate newCoord) {
        int newRow = newCoord.getRow();
        int newCol = newCoord.getCol();
        ICoordinate coord;
        for(int lig = newRow - VISION; lig <= newRow + VISION; lig++) {
            for(int col = newCol - VISION; col <= newCol + VISION; col++) {
                coord = new Coordinate(lig, col);
                Cell c = getCell(coord);
                if(c != null) {
                    monster.update(new CellEvent(c.getTurn(), c.getInfo(), coord));
                }
            }
        }
    }

    /**
     * Vérifie si les coordonnées du monstre sont à une portée de 5 cases ou moins de la sortie.
     *
     * @param coordMonster (ICoordinate) Coordonnée du monstre
     * @param coordExit    (ICoordinate) Coordonnée de la sortie
     * @return Vrai si le monstre est à une portée de moins de 5 cases de la sortie, faux sinon
     */
    protected boolean inRange(ICoordinate coordMonster, ICoordinate coordExit) {
        int rowM = coordMonster.getRow();
        int colM = coordMonster.getCol();
        int rowE = coordExit.getRow();
        int colE = coordExit.getCol();
        return (colM < colE + NB_TOUR_MIN && colM > colE - NB_TOUR_MIN) && (rowM < rowE + NB_TOUR_MIN && rowM > rowE - NB_TOUR_MIN);
    }

    /**
     * Fonction récursive initialisant le monstre sur la maze. On prend des coordonnées aléatoires puis on regarde si la cellule associée est bien vide.
     * Si elle ne l'est pas, on réessaye.
     *
     * @return ICoorinate, les coordonnées du monstre
     */
    protected ICoordinate initMonsterPosition() {
        ICoordinate coord = new Coordinate(RDM.nextInt(this.nbRows), RDM.nextInt(this.nbCols));
        Cell c = getCell(coord);
        return c.getInfo() != ICellEvent.CellInfo.EMPTY ? initMonsterPosition() : coord;
    }

    private ICoordinate getCoordinateMonster(boolean lastTurn) {
        int tour = lastTurn ? turn - 1 : turn;
        for(int i = 0; i < nbRows; i++) {
            for(int j = 0; j < nbCols; j++) {
                if(maze[i][j].getInfo() == ICellEvent.CellInfo.MONSTER && maze[i][j].getTurn() == tour) {
                    return new Coordinate(i, j);
                }
            }
        }
        return null;
    }

    /**
     * Demande à indiquer la fin de la partie à monstre à joueur, puis envois le nom du vainqueur a la MainView pour afficher la fin de partie
     *
     * @param isMonster booléen indiquant qui a gagné (true = monstre, false = hunter)
     */
    protected void victory(boolean isMonster) {
        monster.notify("endGame");
        hunter.notify("endGame");
        reset();
        if(isMonster) {
            notifyObservers(monsterIA ? IAMonsterName : monster.getName());
        } else {
            notifyObservers(hunterIA ? IAHunterName : hunter.getName());
        }
    }

    /**
     * Notifie aux observers si le chasseur a tiré sur le monstre (victoire), sinon informe le monstre des coordonnées du tir et informe le chasseur du type de la cellule choisie.
     *
     * @param coord coordonnées choisies
     */
    public void tirerChasseur(ICoordinate coord) {
        if(hunterIA && monsterIA) {
            tirerIA(coord);
        } else if(hunterIA) {
            tirerIAChasseur(coord);
        } else if(monsterIA) {
            tirerIAMonster(coord);
        } else {
            tirerHumain(coord);
        }
    }

    private void tirerHumain(ICoordinate coord) {
        if(coord.equals(monster.getCoordinateMonster())) {
            victory(false);
            return;
        }
        ICellEvent monsterEvent = new CellEvent(turn, CellInfo.HUNTER, coord);
        ICellEvent hunterEvent = new CellEvent(getCell(coord).getTurn(), getCell(coord).getInfo(), coord);
        monster.update(monsterEvent);
        hunter.update(hunterEvent);
        turn++;
        hunter.notify(turn);
        monster.notify(turn);
        monster.notify("changerTour");
    }

    private void tirerIAChasseur(ICoordinate coord) {
        if(coord.equals(monster.getCoordinateMonster())) {
            victory(false);
            return;
        }
        ICellEvent monsterEvent = new CellEvent(turn, CellInfo.HUNTER, coord);
        ICellEvent hunterEvent = new CellEvent(getCell(coord).getTurn(), getCell(coord).getInfo(), coord);
        monster.update(monsterEvent);
        IAHunter.update(hunterEvent);
        turn++;
        monster.notify(turn);
        monster.notify("changerTour");
    }

    private void tirerIAMonster(ICoordinate coord) {
        if(coord.equals(getCoordinateMonster(false))) {
            victory(false);
            return;
        }
        ICellEvent hunterEvent = new CellEvent(getCell(coord).getTurn(), getCell(coord).getInfo(), coord);
        hunter.update(hunterEvent);
        turn++;
        hunter.notify(turn);
        deplacementMonstre(IAMonster.play());
    }

    private void tirerIA(ICoordinate coord) {
        if(coord.equals(getCoordinateMonster(false))) {
            victory(false);
            return;
        }
        ICellEvent hunterEvent = new CellEvent(getCell(coord).getTurn(), getCell(coord).getInfo(), coord);
        IAHunter.update(hunterEvent);
        turn++;
        deplacementMonstre(IAMonster.play());
    }

    public void changerParam(String hunterName, String monsterName, int height, int width, boolean depDiag, boolean fog, boolean generateMaze) {
        //TODO
    }

    /**
     * Fonction permettant de changer les paramètre de la partie
     *
     * @param hunterName  (String)    Nom du chasseur
     * @param monsterName (String)    Nom du monstre
     * @param height      (int)       hauteur du labyrinthe
     * @param width       (int)       largeur du labyrinthe
     * @param depDiag     (boolean)       déplacement en diagonale
     */
    public void changerParam(String hunterName, String monsterName, int height, int width, boolean depDiag, boolean fog, boolean generateMaze, boolean IAMonster, boolean IAHunter) {
        this.nbRows = height;
        this.nbCols = width;
        this.generateMaze = generateMaze;
        createMaze();
        this.deplacementDiag = depDiag;
        this.fog = fog;
        monster.setFog(fog);
        hunter.setName(hunterName);
        monster.setName(monsterName);
        hunter.setRowCol(nbRows, nbCols);
        if(fog) {
            monster.setMazeEmpty(nbRows, nbCols);
        } else {
            monster.setMaze(this.maze);
        }
        IAMonsterName = monsterName;
        IAHunterName = hunterName;
        setMonsterIA();
        this.IAHunter.initialize(nbRows, nbCols);
        monsterIA = IAMonster;
        hunterIA = IAHunter;
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
        if(monsterIA) deplacementMonstre(null); //on commence la jeu par l'initialisation du monstre
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
        if(!monsterIA) monster.notifyShow();
        if(!hunterIA) hunter.notifyShow();
        notifyObservers("close");
    }
}