package fr.univlille.sae.model;

import fr.univlille.iutinfo.cam.player.monster.IMonsterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import fr.univlille.iutinfo.utils.Observer;
import fr.univlille.iutinfo.utils.Subject;
import fr.univlille.sae.model.events.CellEvent;
import fr.univlille.sae.model.exceptions.MonsterNotFoundException;
import fr.univlille.sae.model.players.Hunter;
import fr.univlille.sae.model.players.IAMonster;

import java.util.Random;

public class MainMonsterBot extends Subject implements ModelMainInterface {
    private static final Random RDM = new Random();
    private static final int NB_TOUR_MIN = 5;
    private static final int DEFAULT_DIMENSION = 10;
    protected int nbRows;
    protected int nbCols;
    protected int turn;
    protected IMonsterStrategy monster;
    protected Hunter hunter;
    protected Cell[][] maze;
    protected boolean deplacementDiag = false;
    protected boolean fog = false;
    protected boolean generateMaze = false;
    protected String IAName = "monster";

    public MainMonsterBot() {
        turn = 1;
        nbRows = DEFAULT_DIMENSION;
        nbCols = DEFAULT_DIMENSION;
        hunter = new Hunter("Hunter", nbRows, nbCols);
        monster = new IAMonster();
        createMaze();
        setMonster();
    }

    private void setMonster() {
        boolean[][] booleanMaze = new boolean[nbRows][nbCols];
        for(int i = 0; i < nbRows; i++) {
            for(int j = 0; j < nbCols; j++) {
                booleanMaze[i][j] = maze[i][j].getInfo() != ICellEvent.CellInfo.WALL;
            }
        }
        monster.initialize(booleanMaze);
        ICoordinate exit = getExit();
        ICellEvent event = new CellEvent(turn, ICellEvent.CellInfo.EXIT, exit);
        monster.update(event);
    }

    private ICoordinate getExit() {
        for(int i = 0; i < nbRows; i++) {
            for(int j = 0; j < nbCols; j++) {
                if(maze[i][j].getInfo() == ICellEvent.CellInfo.EXIT) {
                    return new Coordinate(i, j);
                }
            }
        }
        return null;
    }

    public void monsterTurn() {
        ICoordinate coord = monster.play();
        deplacementMonstre(coord);
    }

    private ICoordinate initMonsterPosition() {
        ICoordinate coord = new Coordinate(RDM.nextInt(this.nbRows), RDM.nextInt(this.nbCols));
        Cell c = getCell(coord);
        return c.getInfo() != ICellEvent.CellInfo.EMPTY ? initMonsterPosition() : coord;
    }

    private boolean inRange(ICoordinate coordMonster, ICoordinate coordExit) {
        int rowM = coordMonster.getRow();
        int colM = coordMonster.getCol();
        int rowE = coordExit.getRow();
        int colE = coordExit.getCol();
        return (colM < colE + NB_TOUR_MIN && colM > colE - NB_TOUR_MIN) && (rowM < rowE + NB_TOUR_MIN && rowM > rowE - NB_TOUR_MIN);
    }

    void update(ICoordinate coordinate, ICellEvent.CellInfo cellInfo) {
        Cell updatedCell = getCell(coordinate);
        updatedCell.setInfo(cellInfo);
        updatedCell.setTurn(turn);
    }

    @Override
    public void deplacementMonstre(ICoordinate newCoord) {
        try {
            System.out.println("Tour du monstre =>" + newCoord);
            if(getCoordinateMonster(true) == null) throw new MonsterNotFoundException();
            if(getCell(newCoord).getInfo() == ICellEvent.CellInfo.EXIT) {
                victory(true);
                return;
            }
        } catch(MonsterNotFoundException e) {
            newCoord = this.initMonsterPosition();
            ICoordinate coordExit = getExit();
            while(inRange(newCoord, coordExit)) {
                newCoord = this.initMonsterPosition();
            }
        }
        ICellEvent event = new CellEvent(turn, ICellEvent.CellInfo.MONSTER, newCoord);
        update(newCoord, ICellEvent.CellInfo.MONSTER);
        monster.update(event);
        hunter.notify("changerTour");
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

    protected void reset() {
        changerParam(hunter.getName(), IAName, nbRows, nbCols, deplacementDiag, fog, generateMaze, false, false);
    }

    protected void victory(boolean isMonster) {
        hunter.notify("endGame");
        reset();
        notifyObservers(isMonster ? IAName : hunter.getName());
    }

    @Override
    public void tirerChasseur(ICoordinate coord) {
        if(coord.equals(getCoordinateMonster(false))) {
            victory(false);
            return;
        }
        if(!hunter.canShoot(coord)) {
            return;
        }
        Cell cell = getCell(coord);
        ICellEvent hunterEvent = new CellEvent(cell.getTurn(), cell.getInfo(), coord);
        hunter.update(hunterEvent);
        turn++;
        hunter.notify(turn);
        monsterTurn();
    }

    private Cell getCell(ICoordinate coord) {
        return maze[coord.getRow()][coord.getCol()];
    }

    @Override
    public void changerParam(String hunterName, String monsterName, int height, int width, boolean depDiag, boolean fog, boolean generateMaze, boolean IAMonster, boolean IAHunter) {
        this.nbRows = height;
        this.nbCols = width;
        this.generateMaze = generateMaze;
        createMaze();
        this.deplacementDiag = depDiag;
        this.fog = fog;
        hunter.setName(hunterName);
        IAName = monsterName;
        hunter.setRowCol(nbRows, nbCols);
        setMonster();
        turn = 1;
        notifyObservers("ParamMAJ");
    }

    private void createMaze() {
        if(generateMaze) {
            this.maze = new MazeFactory(this.nbRows, this.nbCols).generateMaze();
        } else {
            this.maze = new MazeFactory(this.nbRows, this.nbCols).importMaze();
        }
    }

    @Override
    public void attachMonster(Observer o) {
        //Le monstre est une IA, donc pas besoin d'observer car pas de notifications vers sa vue
    }

    @Override
    public void attachHunter(Observer o) {
        hunter.attach(o);
    }

    @Override
    public void notifyDiscoveredMaze() {
        hunter.notifyDiscoveredMaze();
        deplacementMonstre(null); //on commence la jeu par l'initialisation du monstre
    }

    @Override
    public void notifyShowParameter() {
        notifyObservers();
    }

    @Override
    public void notifyShowMH() {
        hunter.notifyShow();
        notifyObservers("close");
    }

    @Override
    public int getNbRows() {
        return nbRows;
    }

    @Override
    public int getNbCols() {
        return nbCols;
    }
}
