package fr.univlille.sae.model.players;

import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import fr.univlille.sae.model.Cell;
import fr.univlille.sae.model.Coordinate;
import fr.univlille.sae.model.ModelMain;
import fr.univlille.sae.model.exceptions.MonsterNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomMonster extends Monster {

    private static final Random RDM = new Random();

    protected ModelMain modelMain;

    public RandomMonster(String name, Cell[][] discoveredMaze, ModelMain modelMain) {
        super(name, discoveredMaze);
        this.modelMain = modelMain;
    }

    private ICoordinate[] possibility() throws MonsterNotFoundException {
        List<ICoordinate> list = new ArrayList<>();
        if(coordinateMonster == null) throw new MonsterNotFoundException();
        ICoordinate[] around = ((Coordinate) coordinateMonster).around();
        for(ICoordinate c : around) {
            if(c.getRow() >= 0 && c.getRow() < maze.length && c.getCol() >= 0 && c.getCol() < maze[0].length && maze[c.getRow()][c.getCol()]) {
                list.add(c);
            }
        }
        return list.toArray(new ICoordinate[0]);
    }

    @Override
    public ICoordinate play() {
        try {
            ICoordinate[] around = possibility();
            return around[RDM.nextInt(around.length)];
        } catch(MonsterNotFoundException e) {
            return null;
        }
    }

    @Override
    public void notifyTurnChange() {
        super.notifyTurnChange();
        ICoordinate played = play();
        if(played != null) modelMain.deplacementMonstre(played);
        else System.out.println("Monster not found !");
    }

    @Override
    public void notifyCantMove() {
        notifyTurnChange();
    }

}
