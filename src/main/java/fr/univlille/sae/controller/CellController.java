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
 * @author Nathan Desmee, Valentin Thuillier, Armand Sady, Théo Lenglart
 * @version  1.0
 */
public class CellController extends Button {
    public static final int SIZE = 50;
    private final boolean isMonster;
    private final int a;
    private final int o;
    private final Maze maze;

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
        if(!isMonster){
            setStyle("-fx-background-color: #9B9B9B; -fx-border-color: #000000");
        }else {
            setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #000000");
        }
        setFont(Main.loadFont("arcade_classic_2" + File.separator + "ARCADECLASSIC.TTF", 15));
        this.setOnAction(e -> setAction());
    }

    /**
     * Cette méthode permet de récupérer les coordonnées de la cellule
     *
     * @return (ICoordinate) Les coordonnées de la cellule
     */
    public ICoordinate getCoord() {
        return new Coordinate(a, o);
    }

    public int getA() {
        return a;
    }

    public int getO() {
        return o;
    }

    /**
     * Cette méthode permet de définir l'action du bouton
     */
    public void setAction() {
        if(this.isMonster) {
            this.maze.deplacementMonstre(this.getCoord());
        } else {
            try {
                this.maze.tirerChasseur(this.getCoord());
            } catch(Exception e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
            }
        }
    }
}
