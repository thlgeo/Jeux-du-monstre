package fr.univlille.sae;

import fr.univlille.iutinfo.cam.player.hunter.IHunterStrategy;
import fr.univlille.iutinfo.cam.player.monster.IMonsterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import fr.univlille.sae.Cell;

public class Maze {

    protected int turn;
    protected int nbRows;
    protected int nbCols;
    protected boolean isHunterTurn;
    protected IHunterStrategy hunter;
    protected IMonsterStrategy monster;
    public static Cell[][] maze;

    public Maze(int turn, int nbRows, int nbCols, boolean isHunterTurn, IHunterStrategy hunter, IMonsterStrategy monster) {
        this.turn = turn;
        this.nbRows = nbRows;
        this.nbCols = nbCols;
        this.isHunterTurn = isHunterTurn;
        this.hunter = hunter;
        this.monster = monster;
        initializeMaze("firstMaze");
    }

    private void initializeMaze(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.dir")+System.getProperty("file.separator")+"res"+System.getProperty("file.separator")+"mazes"+System.getProperty("file.separator")+filePath));
            for(int i = 0 ; i < this.getNbRows(); i++) {
                for(int j = 0 ; j < this.getNbCols(); j++) {
                    maze[i][j] = new Cell(new Coordinate(i, j),Cell.charToInfo.get(reader.read()));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public static Cell[][] getMaze() {
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

    public void changerParam() {

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
    }
}