package fr.univlille.sae.model.score;

import fr.univlille.iutinfo.cam.player.monster.IMonsterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import fr.univlille.sae.model.Cell;

import java.util.Objects;

/**
 * Classe PlayerScore - permet de gérer les scores des joueurs
 *
 * @author Valentin THUILLIER, Armand SADY, Nathan DESMEE, Théo LENGLART
 * @version 1.0.0
 */
public class PlayerScore implements Comparable<PlayerScore>{

    private String name;
    private int score;

    public PlayerScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    /**
     * permet d'augmenter le score de 1
     */
    public void incrementScore(){
        score++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerScore that = (PlayerScore) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * Permet de comparer deux PlayerScore selon le score
     * @param playerScore le PlayerScore avec lequel comparer
     * @return < 0 si score plus grand, 0 si identique et > 0 si score plus petit
     */
    @Override
    public int compareTo(PlayerScore playerScore) {
        return playerScore.getScore() - getScore();
    }

    @Override
    public String toString(){
        return name + "\t\t\t\t\t\t\t" + score;
    }
}
