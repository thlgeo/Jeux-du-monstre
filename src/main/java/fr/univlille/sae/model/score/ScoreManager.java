package fr.univlille.sae.model.score;

import fr.univlille.iutinfo.utils.Subject;
import javafx.collections.transformation.SortedList;

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
    private static String monsterScoreFile = System.getProperty("user.dir") + File.separator + "res" + File.separator + "score" + File.separator + "monsterScore";
    private static String hunterScoreFile = System.getProperty("user.dir") + File.separator + "res" + File.separator + "score" + File.separator + "hunterScore";
    List<PlayerScore> monsterScore = new ArrayList<>();
    List<PlayerScore> hunterScore = new ArrayList<>();

    public ScoreManager() {
        importMonsterScore();
        importHunterScore();
    }

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

    public void changeMonsterScore(String name) {
        for (PlayerScore ps : monsterScore) {
            if (ps.getName().equals(name)) {
                ps.incrementScore();
                Collections.sort(monsterScore);
                return;
            }
        }
        monsterScore.add(new PlayerScore(name, 1));
        Collections.sort(monsterScore);
        saveMonsterScore();
    }

    public void changeHunterScore(String name) {
        for (PlayerScore ps : hunterScore) {
            if (ps.getName().equals(name)) {
                ps.incrementScore();
                Collections.sort(hunterScore);
                return;
            }
        }
        hunterScore.add(new PlayerScore(name, 1));
        Collections.sort(hunterScore);
        saveHunterScore();
    }

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
}
