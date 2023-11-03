package fr.univlille.sae.model.exceptions;

public class MonsterNotFoundException extends Exception {

    public MonsterNotFoundException() {
        super("Monster not found on Maze !");
    }

}
