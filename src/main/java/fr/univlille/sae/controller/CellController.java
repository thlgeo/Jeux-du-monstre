package fr.univlille.sae.controller;

import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import fr.univlille.sae.Main;
import fr.univlille.sae.model.Coordinate;
import fr.univlille.sae.model.ModelMain;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.io.File;

/**
 * Cette classe coorespond au bouton d'une cellule du labyrinthe
 *
 * @author Nathan Desmee, Valentin Thuillier, Armand Sady, Théo Lenglart
 * @version 1.0
 */
public class CellController extends Button {
    public static final int SIZE = 40;
    private final boolean isMonster;
    private final int a;
    private final int o;
    private final ModelMain modelMain;

    public CellController(int a, int o, ModelMain modelMain, boolean isMonsterCell) {
        super(" ");
        this.modelMain = modelMain;
        this.a = a;
        this.o = o;
        this.isMonster = isMonsterCell;
        setMinHeight(SIZE);
        setMinWidth(SIZE);
        setMaxHeight(SIZE);
        setMaxWidth(SIZE);
        setStyle("-fx-background-color: #9B9B9B; -fx-border-color: #000000");
        setFont(Main.loadFont("arcade_classic_2" + File.separator + "ARCADECLASSIC.TTF", 15));
        this.setOnAction(e -> setAction());
    }

    /**
     * Cette méthode permet de récupérer les coordonnées de la cellule
     *
     * @return (ICoordinate) Les coordonnées de la cellule
     */
    protected ICoordinate getCoord() {
        return new Coordinate(a, o);
    }

    /**
     * Cette méthode permet de définir l'action du bouton
     */
    private void setAction() {
        if(modelMain.isFullIA()) return;
        if(this.isMonster) {
            this.modelMain.deplacementMonstre(this.getCoord());
        } else {
            try {
                this.modelMain.tirerChasseur(this.getCoord());
            } catch(Exception e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
            }
        }
    }
}
