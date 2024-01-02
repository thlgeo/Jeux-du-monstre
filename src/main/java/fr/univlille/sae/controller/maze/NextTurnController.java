package fr.univlille.sae.controller.maze;

import fr.univlille.sae.Main;
import fr.univlille.sae.model.ModelMain;
import javafx.scene.control.Button;

/**
 * Cette classe correspond au bouton pour lancer le tour suivant dans un jeu IA contre IA
 *
 * @author Nathan Desmee, Valentin Thuillier, Armand Sady, Théo Lenglart
 * @version 1.0
 */
public class NextTurnController extends Button {

    private final ModelMain modelMain;

    public NextTurnController(ModelMain modelMain){
        super("Tour 1");
        this.modelMain = modelMain;
        setAction();
        setMinSize(200, 30);
        setFont(Main.loadFont(Main.ARCADE_FONT, 20));
    }

    /**
     * Remet le texte du bouton à "Tour 1" lorsqu'une nouvelle partie est lancée
     */
    public void reset(){
        setText("Tour 1");
    }

    /**
     * Met en place l'action du bouton pour lancer le tour suivant
     */
    private void setAction(){
        setOnAction(event -> modelMain.lancerTourMonstre());
    }
}
