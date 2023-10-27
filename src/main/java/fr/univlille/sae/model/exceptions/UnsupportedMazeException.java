package fr.univlille.sae.model.exceptions;

public class UnsupportedMazeException extends Exception{


    public UnsupportedMazeException(String message) { super(message); }

    public UnsupportedMazeException() { super("Unvalid Maze, the one you put is too big"); }

}
