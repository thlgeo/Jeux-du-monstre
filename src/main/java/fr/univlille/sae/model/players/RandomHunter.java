package fr.univlille.sae.model.players;

import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import fr.univlille.sae.model.Coordinate;
import fr.univlille.sae.model.ModelMain;

import java.util.Random;

public class RandomHunter extends Hunter{

    protected ModelMain modelMain;
    private static final Random RDM = new Random();

    public RandomHunter(String name, int nbRows, int nbCols, ModelMain modelMain) {
        super(name, nbRows, nbCols);
        this.modelMain = modelMain;
    }

    @Override
    public ICoordinate play() {
        return new Coordinate(RDM.nextInt(nbRows), RDM.nextInt(nbCols));
    }

    @Override
    public void notifyTurnChange() {
        super.notifyTurnChange();
        modelMain.tirerChasseur(play());
    }
}
