package fr.univlille.sae.model.exceptions;

/**
 * Cette classe est une exception qui est levée lorsque la taille du maze est trop grande
 * @author Valentin THUILLIER, Armand SADY, Nathan DESMEE, Théo LENGLART
 * @version 1.0.0
 */
public class UnsupportedMazeException extends Exception {

    public UnsupportedMazeException() {
        super("Unvalid Maze, the one you put is too big");
    }

}
