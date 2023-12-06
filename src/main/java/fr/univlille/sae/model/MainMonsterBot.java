package fr.univlille.sae.model;

import fr.univlille.iutinfo.cam.player.monster.IMonsterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import fr.univlille.iutinfo.utils.Observer;
import fr.univlille.iutinfo.utils.Subject;
import fr.univlille.sae.model.events.CellEvent;
import fr.univlille.sae.model.players.Hunter;
import fr.univlille.sae.model.players.IAMonster;

public class MainMonsterBot extends Subject implements ModelMainInterface {
    private static final int DEFAULT_DIMENSION = 10;
    protected int nbRows;
    protected int nbCols;
    protected int turn;
    protected IMonsterStrategy monster;
    protected Hunter hunter;
    protected Cell[][] maze;

    public MainMonsterBot(){
        turn = 0;
        nbRows = DEFAULT_DIMENSION;
        nbCols = DEFAULT_DIMENSION;
        hunter = new Hunter("Hunter" , nbRows, nbCols);
        monster = new IAMonster();

    }

    private void setMonster(){
    }

    @Override
    public void deplacementMonstre(ICoordinate newCoord) {

    }

    @Override
    public void tirerChasseur(ICoordinate coord) {
    }

    @Override
    public void changerParam(String hunterName, String monsterName, int height, int width, boolean depDiag, boolean fog, boolean generateMaze) {

    }

    @Override
    public void attachMonster(Observer o) {
        //Le monstre est une IA, donc pas besoin d'observer car pas de notifications vers sa vue
        return;
    }

    @Override
    public void attachHunter(Observer o) {
        hunter.attach(o);
    }

    @Override
    public void notifyDiscoveredMaze() {
        hunter.notifyDiscoveredMaze();
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
