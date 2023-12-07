package fr.univlille.sae.model;

import java.util.Random;

import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;
import fr.univlille.iutinfo.utils.Observer;
import fr.univlille.sae.model.events.CellEvent;
import fr.univlille.sae.model.exceptions.MonsterNotFoundException;
import fr.univlille.sae.model.players.IAHunter;

public class MainHunterBot extends ModelMain implements ModelMainInterface {
    // private static final int DEFAULT_DIMENSION = 10;
    // protected int nbRows;
    // protected int nbCols;
    // protected int turn;
    // protected Monster monster;
    protected IAHunter IAhunter;
    // protected boolean deplacementDiag;
    // protected Cell[][] maze;
    // protected boolean fog = false;
    public static final Random RDM = new Random();

    public MainHunterBot(){
        super();
        // turn = 0;
        // nbRows = DEFAULT_DIMENSION;
        // nbCols = DEFAULT_DIMENSION;
        // maze = new Cell[nbRows][nbCols];
        IAhunter = new IAHunter();
        // monster = new Monster("monster", maze);
        // deplacementDiag = false;
        IAhunter.initialize(nbRows, nbCols);
    }

    @Override
    public void deplacementMonstre(ICoordinate newCoord) {
        try {
            if(monster.getCoordinateMonster() == null) throw new MonsterNotFoundException();
            if(!this.monster.canMove(newCoord,deplacementDiag)) {
                monster.notify("cantMove");
                return;
            }
            if(getCell(newCoord).getInfo() == ICellEvent.CellInfo.EXIT) {
                victory(true);
                return;
            }
        } catch(MonsterNotFoundException e) {
            newCoord = this.initMonsterPosition();
            IAhunter.initialize(nbRows, nbCols);
            ICoordinate coordExit = getExit();
            while(inRange(newCoord,coordExit))
            {
                newCoord = this.initMonsterPosition();
            }
        }
        ICellEvent event = new CellEvent(turn, ICellEvent.CellInfo.MONSTER, newCoord);
        update(newCoord, ICellEvent.CellInfo.MONSTER);
        monster.setCoordinateMonster(newCoord);
        if(fog){
            updateAround(newCoord);
        }
        monster.update(event);
        tirerChasseur(IAhunter.play());
    }

    // private ICoordinate getExit() {
    //     Cell c = null;
    //     for(int i=0;i<nbRows;i++)
    //     {
    //         for(int j=0;j<nbCols;j++)
    //         {
    //             c = getCell(new Coordinate(i, j));
    //             if(c.getInfo() == CellInfo.EXIT) return new Coordinate(i, j);
    //         }
    //     }
    //     return null;
    // }
    
    // private ICoordinate initMonsterPosition() {
    //     ICoordinate coord = new Coordinate(RDM.nextInt(this.nbRows), RDM.nextInt(this.nbCols));
    //     Cell c = getCell(coord);
    //     return c.getInfo() != ICellEvent.CellInfo.EMPTY ? initMonsterPosition() : coord;
    // }

    // private boolean inRange(ICoordinate coordMonster, ICoordinate coordExit) {
    //     int rowM = coordMonster.getRow();
    //     int colM = coordMonster.getCol();
    //     int rowE = coordExit.getRow();
    //     int colE = coordExit.getCol();
    //     return (colM < colE+5 && colM > colE-5) && (rowM < rowE+5 && rowM > rowE-5);
    // }
    // private Cell getCell(ICoordinate coordinate) {
    //     if(coordinate == null) return null;
    //     if(coordinate.getRow() < 0 || coordinate.getRow() >= nbRows || coordinate.getCol() < 0 || coordinate.getCol() >= nbCols)
    //         return null;
    //     return maze[coordinate.getRow()][coordinate.getCol()];
    // }

    // protected void victory(boolean isMonster) {
    //     monster.notify("endGame");
    //     reset();
    //     notifyObservers(isMonster ? monster.getName() : "IA hunter");
    // }

    // private void reset() {
    //     changerParam("IA hunter", monster.getName(), nbRows, nbCols, deplacementDiag, fog);
    // }

    // public void update(ICoordinate coordinate, ICellEvent.CellInfo cellInfo) {
    //     Cell updatedCell = getCell(coordinate);
    //     updatedCell.setInfo(cellInfo);
    //     updatedCell.setTurn(turn);
    // }

    // private void updateAround(ICoordinate newCoord)
    // {
    //     int newRow = newCoord.getRow();
    //     int newCol = newCoord.getCol();
    //     ICoordinate coord;
    //     for(int lig=newRow-1;lig<=newRow+1;lig++)
    //     {
    //         for(int col=newCol-1;col<=newCol+1;col++)
    //         {
    //             coord = new Coordinate(lig, col);
    //             Cell c = getCell(coord);
    //             if(c != null){
    //                 monster.update(new CellEvent(c.getTurn(), c.getInfo(), coord));
    //             }
    //         }
    //     }
    // }

    @Override
    public void tirerChasseur(ICoordinate coord) {
        if(coord.equals(monster.getCoordinateMonster())) {
            victory(false);
            return;
        }
        ICellEvent monsterEvent = new CellEvent(turn, CellInfo.HUNTER, coord);
        ICellEvent hunterEvent = new CellEvent(getCell(coord).getTurn(), getCell(coord).getInfo(), coord);
        monster.update(monsterEvent);
        IAhunter.update(hunterEvent);
        turn++;
        monster.notify(turn);
        monster.notify("changerTour");
    }

    // @Override
    // public void changerParam(String hunterName, String monsterName, int height, int width, boolean depDiag, boolean fog) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'changerParam'");
    // }

    // @Override
    // public void attachMonster(Observer o) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'attachMonster'");
    // }

    // @Override
    // public void attachHunter(Observer o) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'attachHunter'");
    // }

    // @Override
    // public void attach(Observer o) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'attach'");
    // }

    // @Override
    // public void notifyDiscoveredMaze() {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'notifyDiscoveredMaze'");
    // }

    // @Override
    // public void notifyShowParameter() {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'notifyShowParameter'");
    // }

    @Override
    public void notifyShowMH() {
        monster.notifyShow();
        // hunter.notifyShow();
        notifyObservers("close");
    }

    // @Override
    // public int getNbRows() {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'getNbRows'");
    // }

    // @Override
    // public int getNbCols() {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'getNbCols'");
    // }
    
}
