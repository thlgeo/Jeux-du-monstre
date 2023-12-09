package fr.univlille.sae.model;

import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import fr.univlille.sae.model.events.CellEvent;
import fr.univlille.sae.model.exceptions.MonsterNotFoundException;
import fr.univlille.sae.model.players.IAHunter;

public class MainHunterBot extends ModelMain implements ModelMainInterface {
    protected IAHunter IAhunter;

    public MainHunterBot() {
        super();
        IAhunter = new IAHunter();
        IAhunter.initialize(nbRows, nbCols);
    }

    @Override
    public void deplacementMonstre(ICoordinate newCoord) {
        try {
            if(monster.getCoordinateMonster() == null) throw new MonsterNotFoundException();
            if(!this.monster.canMove(newCoord, deplacementDiag)) {
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
            while(inRange(newCoord, coordExit)) {
                newCoord = this.initMonsterPosition();
            }
        }
        ICellEvent event = new CellEvent(turn, ICellEvent.CellInfo.MONSTER, newCoord);
        update(newCoord, ICellEvent.CellInfo.MONSTER);
        monster.setCoordinateMonster(newCoord);
        if(fog) {
            updateAround(newCoord);
        }
        monster.update(event);
        tirerChasseur(IAhunter.play());
    }

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

    @Override
    public void notifyShowMH() {
        monster.notifyShow();
        notifyObservers("close");
    }
}
