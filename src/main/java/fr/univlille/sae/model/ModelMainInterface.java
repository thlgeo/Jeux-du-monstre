package fr.univlille.sae.model;

import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import fr.univlille.iutinfo.utils.Observer;
import fr.univlille.iutinfo.utils.Subject;

public interface ModelMainInterface{

    public void deplacementMonstre(ICoordinate newCoord);

    public void tirerChasseur(ICoordinate coord);

    public void changerParam(String hunterName, String monsterName, int height, int width, boolean depDiag, boolean fog);

    public void attachMonster(Observer o);

    public void attachHunter(Observer o);

    public void attach(Observer o);

    public void notifyDiscoveredMaze();

    public void notifyShowParameter();

    public void notifyShowMH();

    public int getNbRows();

    public int getNbCols();
}
