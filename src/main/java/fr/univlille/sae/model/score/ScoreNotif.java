package fr.univlille.sae.model.score;

import java.util.List;

/**
 * Classe ScoreNotif - permet de créer la notification qui sera envoyée à la vue
 *
 * @author Valentin THUILLIER, Armand SADY, Nathan DESMEE, Théo LENGLART
 * @version 1.0.0
 */
public class ScoreNotif {
    String[] scores;
    boolean isMonster;

    public ScoreNotif(List<PlayerScore> scores, boolean isMonster){
        this.scores = listeToTable(scores);
        this.isMonster = isMonster;
    }

    public boolean isMonster(){
        return isMonster;
    }

    public String[] getScores(){
        return scores;
    }

    /**
     * Permet de transformer les 10 premiers d'une liste de PlayerScore en tableau de String
     * @param  scores la liste de PlayerScore
     * @return       le tableau de String
     */
    private String[] listeToTable(List<PlayerScore> scores){
        int n = scores.size();
        if(n > 10) n = 10;
        String[] res = new String[n];
        for(int i = 0; i < n; i++){
            res[i] = scores.get(i).toString();
        }
        return res;
    }

}
