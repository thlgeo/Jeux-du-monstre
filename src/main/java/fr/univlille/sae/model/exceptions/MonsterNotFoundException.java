package fr.univlille.sae.model.exceptions;

import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

/**
 * Cette classe est une exception qui est levée lorsque le monstre n'est pas trouvé sur le labyrinthe
 * @author Valentin THUILLIER, Armand SADY, Nathan DESMEE, Théo LENGLART
 * @version 1.0.0
 */
public class MonsterNotFoundException extends Exception {

    public MonsterNotFoundException() {
        super("Monster not found on Maze !");
    }

}
