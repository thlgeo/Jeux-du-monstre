package fr.univlille.sae.model;

import fr.univlille.iutinfo.cam.player.hunter.IHunterStrategy;
import fr.univlille.iutinfo.cam.player.monster.IMonsterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import fr.univlille.sae.model.cellule.CellEvent;
import fr.univlille.sae.model.exceptions.MonsterNotFoundException;
import fr.univlille.sae.model.exceptions.UnsupportedMazeException;
import fr.univlille.sae.model.players.Monster;
import fr.univlille.sae.model.players.Hunter;

public class Maze {

    protected int turn;
    protected int nbRows;
    protected int nbCols;
    protected boolean isHunterTurn;
    protected Hunter hunter;
    protected Monster monster;
    public Cell[][] maze;

    protected static final String FS = "file.seperator";

    public static int DEFAULT_DIMENSION = 10;

    public Maze(int turn, int nbRows, int nbCols, String filepath) {
        this.turn = turn;
        this.nbRows = nbRows;
        this.nbCols = nbCols;
        initializeMaze(filepath);
    }

    public Maze() {
        this(0, DEFAULT_DIMENSION, DEFAULT_DIMENSION, "defaultMaze");
    }

    private void initializeMaze(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.dir")+System.getProperty(FS)+"res"+System.getProperty(FS)+"mazes"+System.getProperty(FS)+filePath));
            int mazeRow = Integer.parseInt(reader.readLine());
            int mazeCol = Integer.parseInt(reader.readLine());
            if (mazeRow > getNbRows() && mazeCol > getNbRows()) {
                throw new UnsupportedMazeException();
            }
            for(int rowId = 0 ; rowId < this.getNbRows(); rowId++) {
                String currentLine = reader.readLine();
                for (int colId = 0 ; colId < currentLine.length() ; colId++) {
                    maze[rowId][colId] = new Cell(new Coordinate(rowId, colId), Cell.charToInfo.get(currentLine.charAt(colId)));
                }
            }
        } catch (UnsupportedMazeException | IOException e) {
            System.out.println(e.getMessage());
        }


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

    public void deplacementMonstre(ICoordinate newCoord) {
        if(!this.monster.canMove(newCoord)) { return;  } //TODO: Notify

        if(getCell(newCoord).getInfo() == ICellEvent.CellInfo.EXIT) {
            victory(true);
            return;
        }

        ICellEvent event = new CellEvent(turn, ICellEvent.CellInfo.MONSTER, newCoord);
        monster.update(event);
        update(newCoord, ICellEvent.CellInfo.MONSTER);
    }

    public void victory(boolean isMonster) {

    }

    public void tireChasseur(ICoordinate coord) {
        
    }

    public ICoordinate getCoordMonster(int turn) throws MonsterNotFoundException {
        for(Cell[] line : maze) {
            for(Cell cell : line) {
                if(cell.getInfo() == ICellEvent.CellInfo.MONSTER && cell.getTurn() == turn - 1) return cell.getCoord();
            }
        }
        throw new MonsterNotFoundException();
    }

    public ICoordinate getCoordMonster() throws MonsterNotFoundException {
        return getCoordMonster(turn - 1);
    }

    /*
    public void entrerNom(String newNameMonster, String newNameHunter) {
        Hunter hunter = (Hunter) this.getHunter();
        hunter.setName(newNameHunter);
        // TODO -> change setter name
        Monster monster = (Monster) this.getMonster();
        monster.setName(newNameMonster);
        return;
    }
    */

    public void lancerJeu() {
        //TODO
        return;
    }

    public void changerParam(String hunterName, String monsterName, int nbRows, int nbCols) {
        this.hunter = new Hunter(hunterName, nbRows, nbCols);
        this.monster = new Monster(monsterName, this.maze);
    }

    public void changerTailleGrille(int newCols, int newRows) {
        this.setNbCols(newCols);
        this.setNbRows(newRows);
        return;
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

}