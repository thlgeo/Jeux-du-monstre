package fr.univlille.sae.model;

import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import fr.univlille.iutinfo.utils.Observer;

public interface ModelMainInterface {

    void deplacementMonstre(ICoordinate newCoord);

    void tirerChasseur(ICoordinate coord);

    void changerParam(String hunterName, String monsterName, int height, int width, boolean depDiag, boolean fog, boolean generateMaze, boolean IAMonster, boolean IAHunter);

    void attachMonster(Observer o);

    void attachHunter(Observer o);

    void attach(Observer o);

    void notifyDiscoveredMaze();

    void notifyShowParameter();

    void notifyShowMH();

    int getNbRows();

    int getNbCols();
}
