package fr.univlille.sae.controller;

import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import fr.univlille.sae.Main;
import fr.univlille.sae.model.Coordinate;
import fr.univlille.sae.model.Maze;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.io.File;

/**
 * Cette classe coorespond au bouton d'une cellule du labyrinthe
 * @Author Nathan Desmee, Valentin Thuillier, Armand Sady, Théo Lenglart
 * @Version 1.0
 */
public class CellController extends Button {
    private int a;
    private int o;
    private Maze maze;
    private final boolean isMonster;
    public static final int SIZE = 50;

    public CellController(int a, int o, Maze maze, boolean isMonsterCell){
        super(" ");
        this.maze = maze;
        this.a = a;
        this.o = o;
        this.isMonster = isMonsterCell;
        setMinHeight(SIZE);
        setMinWidth(SIZE);
        setMaxHeight(SIZE);
        setMaxWidth(SIZE);
        setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #000000");
        setFont(Main.loadFont("arcade_classic_2" + File.separator + "ARCADECLASSIC.TTF", 20));
        this.setOnAction(e -> setAction());
    }

    public int getA() {
        return a;
    }

    public int getO() {
        return o;
    }

    public ICoordinate getCoord() {
        return new Coordinate(a, o);
    }

    public void setAction(){
        if(this.isMonster) { this.maze.deplacementMonstre(this.getCoord()); }
        else {
            try { this.maze.tirerChasseur(this.getCoord()); }
            catch (Exception e) { new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait(); }
        }
    }
}
