package fr.univlille.sae.model;

import fr.univlille.iutinfo.cam.player.hunter.IHunterStrategy;
import fr.univlille.iutinfo.cam.player.monster.IMonsterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import fr.univlille.sae.model.Cell;
import fr.univlille.sae.model.Coordinate;
import fr.univlille.sae.model.players.Hunter;
import fr.univlille.sae.model.players.Monster;

public class Maze {

    protected int turn;
    protected int nbRows;
    protected int nbCols;
    protected boolean isHunterTurn;
    protected IHunterStrategy hunter;
    protected IMonsterStrategy monster;
    public Cell[][] maze;

    protected static final String FS = "file.seperator";

    public static int DEFAULT_DIMENSION = 10;

    public Maze(int turn, int nbRows, int nbCols, boolean isHunterTurn, IHunterStrategy hunter, IMonsterStrategy monster, String filepath) {
        this.turn = turn;
        this.nbRows = nbRows;
        this.nbCols = nbCols;
        this.isHunterTurn = isHunterTurn;
        this.hunter = hunter;
        this.monster = monster;
        initializeMaze(filepath);
    }

    public Maze() {
        this(0, DEFAULT_DIMENSION, DEFAULT_DIMENSION, false, new Hunter(), new Monster(), "defaultMaze");
    }

    private void initializeMaze(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.dir")+System.getProperty(FS)+"res"+System.getProperty(FS)+"mazes"+System.getProperty(FS)+filePath));
            for(int rowId = 0 ; rowId < this.getNbRows(); rowId++) {
                String currentLine = reader.readLine();
                if (currentLine.length() > this.getNbCols()) { };
                for (int colId = 0 ; colId < currentLine.length() ; colId++) {
                    maze[rowId][colId] = new Cell(new Coordinate(rowId, colId), Cell.charToInfo.get(currentLine.charAt(colId)));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
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

    public void setHunter(IHunterStrategy hunter) {
        this.hunter = hunter;
    }

    public IMonsterStrategy getMonster() {
        return monster;
    }

    public void setMonster(IMonsterStrategy monster) {
        this.monster = monster;
    }

    public boolean deplacementHunter() {
        return true;
    }

    public boolean tireChasseur() {
        return true;
    }

    public static ICoordinate getCoordMonster(){
        //TODO
        return null;
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
    }

    public void changerParam() {
        //TODO
    }

    public void changerTailleGrille(int newCols, int newRows) {
        this.setNbCols(newCols);
        this.setNbRows(newRows);
    }

    public Cell getCell(ICoordinate coordinate) {
        return maze[coordinate.getRow()][coordinate.getCol()];
    }
    public void update(ICoordinate coordinate, ICellEvent.CellInfo cellInfo) {
        Cell updatedCell = getCell(coordinate);
        updatedCell.setInfo(cellInfo);
    }
}