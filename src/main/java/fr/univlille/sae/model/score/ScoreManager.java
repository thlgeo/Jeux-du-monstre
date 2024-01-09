package fr.univlille.sae.model.score;

import fr.univlille.iutinfo.utils.Observer;
import fr.univlille.iutinfo.utils.Subject;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Classe ScoreManager - Permet de gérer le tableau des scores
 *
 * @author Valentin THUILLIER, Armand SADY, Nathan DESMEE, Théo LENGLART
 * @version 1.0.0
 */
public class ScoreManager extends Subject {
    private final static String monsterScoreFile = System.getProperty("user.dir") + File.separator + "res" + File.separator + "score" + File.separator + "monsterScore.csv";
    private final static String hunterScoreFile = System.getProperty("user.dir") + File.separator + "res" + File.separator + "score" + File.separator + "hunterScore.csv";
    List<PlayerScore> monsterScore = new ArrayList<>();
    List<PlayerScore> hunterScore = new ArrayList<>();

    public ScoreManager() {
        importMonsterScore();
        importHunterScore();
    }

    /**
     * Permet d'importer les scores des monstres depuis les fichiers
     */
    public void importMonsterScore(){
        try(BufferedReader br = new BufferedReader(new FileReader(monsterScoreFile))){
            String[] line;
            while(br.ready()){
                line = br.readLine().split(";");
                monsterScore.add(new PlayerScore(line[0], Integer.parseInt(line[1])));
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Permet d'importer les scores des chasseurs depuis les fichiers
     */
    public void importHunterScore(){
        try(BufferedReader br = new BufferedReader(new FileReader(hunterScoreFile))){
            String[] line;
            while(br.ready()){
                line = br.readLine().split(";");
                hunterScore.add(new PlayerScore(line[0], Integer.parseInt(line[1])));
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Permet de changer le score d'un monstre
     * @param name nom du monstre dont le score change
     */
    public void changeMonsterScore(String name) {
        for (PlayerScore ps : monsterScore) {
            if (ps.getName().equals(name)) {
                ps.incrementScore();
                Collections.sort(monsterScore);
                saveMonsterScore();
                notifyObservers(new ScoreNotif(monsterScore, true));
                return;
            }
        }
        monsterScore.add(new PlayerScore(name, 1));
        Collections.sort(monsterScore);
        saveMonsterScore();
        notifyObservers(new ScoreNotif(monsterScore, true));
    }

    /**
     * Permet de changer le score d'un chasseur
     * @param name nom du chasseur dont le score change
     */
    public void changeHunterScore(String name) {
        for (PlayerScore ps : hunterScore) {
            if (ps.getName().equals(name)) {
                ps.incrementScore();
                Collections.sort(hunterScore);
                saveHunterScore();
                notifyObservers(new ScoreNotif(hunterScore, false));
                return;
            }
        }
        hunterScore.add(new PlayerScore(name, 1));
        Collections.sort(hunterScore);
        saveHunterScore();
        notifyObservers(new ScoreNotif(hunterScore, false));
    }

    /**
     * Permet de sauvegarder les scores des monstres dans les fichiers
     */
    public void saveMonsterScore(){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(monsterScoreFile))){
            for(PlayerScore ps : monsterScore){
                bw.write(ps.getName() + ";" + ps.getScore());
                bw.newLine();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Permet de sauvegarder les scores des chasseurs dans les fichiers
     */
    public void saveHunterScore(){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(hunterScoreFile))){
            for(PlayerScore ps : hunterScore){
                bw.write(ps.getName() + ";" + ps.getScore());
                bw.newLine();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Permet de notifier la vue
     * @param s la donnée à notifier
     */
    public void notifyView(String s){
        notifyObservers(s);
    }

    /**
     * Permet d'attacher un observateur
     * @param o l'observateur à attacher
     */
    public void attachObserver(Observer o){
        attach(o);
        notifyObservers(new ScoreNotif(monsterScore, true));
        notifyObservers(new ScoreNotif(hunterScore, false));
    }

    PlayerScore getPlayerScore(int i, boolean isMonster) {
        if(isMonster)
            return monsterScore.get(i);
        else
            return hunterScore.get(i);
    }

    void clear() {
        monsterScore.clear();
        hunterScore.clear();
    }
}
