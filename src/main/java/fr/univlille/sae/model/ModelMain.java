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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

    private static int NB_TOUR_MIN = 5;
    public static final Random RDM = new Random();
    private static final int DEFAULT_DIMENSION = 10;
    private static final int DEFAULT_TURN = 1;
    private static final int VISION = 1;
    public static final String CHANGER_TOUR = "changerTour";
    public static final String CHANGER_TOUR_IA = "changerTourIA";
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
    protected boolean monsterIsIA = false;
    protected boolean hunterIsIA = false;
    protected double percent_wall;

    protected String IAMonsterName = "IA Monster";
    protected String IAHunterName = "IA Hunter";

    private ModelMain(int turn, int nbRows, int nbCols) {
        this.turn = turn;
        this.nbRows = nbRows;
        this.nbCols = nbCols;
        this.maze = null;
        percent_wall = 0.35;
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

    /**
     * Initialise un tableau de boolean pour l'IA du monstre et lui transmet l'emplacement de la sortie
     */
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

    /**
     * Permet créer un labyrinthe soit importé, soit généré en fonction de la valeur de generateMaze
     */
    private void createMaze() {
        if(generateMaze) {
            this.maze = new MazeFactory(this.nbRows, this.nbCols,percent_wall).generateMaze();
        } else {
            this.maze = new MazeFactory(this.nbRows, this.nbCols,percent_wall).importMaze();
        }
    }


    /**
     * Reinitialise le labyrinthe avec les paramètres déjà définis
     */
    protected void reset() {
        rebuildMaze(nbRows, nbCols, generateMaze, percent_wall);
        rebuildPlayers(hunter.getName(), monster.getName(), monsterIsIA, hunterIsIA, IAMonster.getClass().getName(), IAHunter.getClass().getName());
        rebuildOption(deplacementDiag, fog);
        turn = DEFAULT_TURN;
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

    public double getPercent_wall() {
        return percent_wall;
    }

    /**
     * Renvoie les coordonnées de la sortie
     *
     * @return (ICoordinate) Coordonnées de la sortie
     */
    protected ICoordinate getExit() {
        Cell c;
        for(int i = 0; i < nbRows; i++) {
            for(int j = 0; j < nbCols; j++) {
                c = getCell(i, j);
                if(c.getInfo() == CellInfo.EXIT) return new Coordinate(i, j);
            }
        }
        return null;
    }

    /**
     * Permet de lancer le tour du monstre lorsqu'il est une IA
     */
    public void lancerTourMonstre() {
        if(turn == 1) {
            deplacementMonstre(null);
        } else {
            deplacementMonstre(IAMonster.play());
        }
    }

    /**
     * Notifie aux observers du mouvement du monstre s'il a bougé ou non, ou s'il a gagné.
     *
     * @param coord coordonnées choisies
     */
    public void deplacementMonstre(ICoordinate coord) {
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
        IAMonster.update(event);
        notificationDeplacement();
    }

    /**
     * Permet de gérer la notification aux views selon si les joueurs sont des IA ou non
     */
    public void notificationDeplacement() {
        if(hunterIsIA && monsterIsIA) {
            tirerChasseur(IAHunter.play());
        } else if(hunterIsIA) {
            tirerChasseur(IAHunter.play());
        } else if(monsterIsIA) {
            hunter.notify(CHANGER_TOUR_IA);
        } else {
            hunter.notify(CHANGER_TOUR);
        }
    }

    /**
     * Mets à jour les cellules autour du monstre pour le mode brouillard
     *
     * @param newCoord (ICoordinate) Nouvelles coordonnées du monstre
     */
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
    
    public boolean monsterIsIA() {
        return monsterIsIA;
    }

    public boolean hunterIsIA() {
        return hunterIsIA;
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
            notifyObservers(monsterIsIA ? IAMonsterName : monster.getName());
        } else {
            notifyObservers(hunterIsIA ? IAHunterName : hunter.getName());
        }
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
        ICellEvent monsterEvent = new CellEvent(turn, CellInfo.HUNTER, coord);
        ICellEvent hunterEvent = new CellEvent(getCell(coord).getTurn(), getCell(coord).getInfo(), coord);
        monster.update(monsterEvent);
        hunter.update(hunterEvent);
        IAMonster.update(monsterEvent);
        IAHunter.update(hunterEvent);
        turn++;
        hunter.notify(turn);
        monster.notify(turn);
        notificationTirer();
    }

    /**
     * Permet de gérer la notification aux views selon si les joueurs sont des IA ou non
     */
    public void notificationTirer() {
        if(hunterIsIA && monsterIsIA) {
            monster.notify("showIA");
        } else if(hunterIsIA) {
            monster.notify(CHANGER_TOUR_IA);
        } else if(monsterIsIA) {
            deplacementMonstre(IAMonster.play());
        } else {
            monster.notify(CHANGER_TOUR);
        }
    }

    /**
     * Permet de changer le labyrinthe avec les paramètres choisis
     * @param height hauteur du labyrinthe
     * @param width largeur du labyrinthe
     * @param generateMaze booléen indiquant si le labyrinthe est généré ou importé
     * @param percent_wall pourcentage de perfection du labyrinthe
     */
    public void rebuildMaze(int height, int width, boolean generateMaze, double percent_wall) {
        this.nbRows = height;
        this.nbCols = width;
        this.percent_wall = percent_wall;
        this.generateMaze = generateMaze;
        createMaze();
        hunter.setRowCol(nbRows, nbCols);
        this.IAHunter.initialize(nbRows, nbCols);
        setMonsterIA();
        if (fog) {
            monster.setMazeEmpty(nbRows, nbCols);
        } else {
            monster.setMaze(this.maze);
        }
        notifyObservers("MazeParamMAJ");
    }

    /**
     * Permet de changer les joueurs avec les paramètres choisis
     * @param hunterName nom du chasseur
     * @param monsterName nom du monstre
     * @param IAMonster booléen indiquant si le monstre est une IA
     * @param IAHunter booléen indiquant si le chasseur est une IA
     */
    public void rebuildPlayers(String hunterName, String monsterName, boolean IAMonster, boolean IAHunter, String monsterChoice, String hunterChoice) {
        monsterIsIA = IAMonster;
        hunterIsIA = IAHunter;
        hunter.setName(hunterName);
        monster.setName(monsterName);
        IAMonsterName = monsterName;
        IAHunterName = hunterName;
        setChoiceIA(monsterChoice, hunterChoice);
        if(monsterIsIA) { // on enlève le brouillard si le monstre est une IA
            this.fog = false;
            monster.setFog(false);
            monster.setMaze(this.maze);
        }
        notifyObservers("PlayerParamMAJ");
    }

    /**
     * Permet de mettre en place les choix d'IA pour le monstre et le chasseur
     * @param monsterChoice choix de l'IA du monstre
     * @param hunterChoice choix de l'IA du chasseur
     */
    private void setChoiceIA(String monsterChoice, String hunterChoice) {
        try {
            this.IAMonster = (IMonsterStrategy) Class.forName(monsterChoice).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            this.IAMonster = new IAMonster();
        } finally {
            setMonsterIA();
        }
        try {
            this.IAHunter = (IHunterStrategy) Class.forName(hunterChoice).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            this.IAHunter = new IAHunter();
        } finally {
            this.IAHunter.initialize(nbRows, nbCols);
        }
    }

    /**
     * Permet de changer les options de jeu avec les paramètres choisis
     * @param depDiag booléen indiquant si le monstre peut se déplacer en diagonale
     * @param fog booléen indiquant si le brouillard est activé
     */
    public void rebuildOption(boolean depDiag, boolean fog) {
        this.deplacementDiag = depDiag;
        if(!monsterIsIA) this.fog = fog;
        else this.fog = false;
        monster.setFog(this.fog);
        if(this.fog) {
            monster.setMazeEmpty(nbRows, nbCols);
        } else {
            monster.setMaze(this.maze);
        }
        notifyObservers("OptionParamMAJ");
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

    public void setPercentWall(double percent_wall)
    {
        this.percent_wall = percent_wall;
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
        if(monsterIsIA && !hunterIsIA) deplacementMonstre(null); //on commence la jeu par l'initialisation du monstre
        if(monsterIsIA && hunterIsIA) {
            monster.notify(turn);
            monster.notify("showIA");
        }
    }

    public void notify(String s) {
        notifyObservers(s);
    }

    /**
     * Notifie aux observers d'afficher les pages monstre et le chasseur
     */
    public void notifyShowMH() {
        if(!monsterIsIA) monster.notifyShow();
        if(!hunterIsIA) hunter.notifyShow();
        notifyObservers("close");
    }

    public static void setNbTourMin(int nbTourMin) {
        NB_TOUR_MIN = nbTourMin;
    }

    public boolean isFullIA() {
        return monsterIsIA && hunterIsIA;
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        System.out.println(list.getClass().getName());
    }
}