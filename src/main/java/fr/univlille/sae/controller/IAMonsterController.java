package fr.univlille.sae.controller;

import fr.univlille.sae.Main;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class IAMonsterController extends HBox {
    protected CheckBox IAMonster = new CheckBox();
    protected Label texte;

    public IAMonsterController() {
        texte = new Label("Jouer contre IA monstre ");
        texte.setFont(Main.loadFont(Main.ARCADE_FONT, 20));
        getChildren().addAll(texte, IAMonster);
        setAlignment(Pos.CENTER);
    }

    public boolean isSelected() {
        return IAMonster.isSelected();
    }
}
