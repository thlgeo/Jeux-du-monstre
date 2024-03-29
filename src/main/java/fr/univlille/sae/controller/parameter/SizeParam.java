package fr.univlille.sae.controller.parameter;

import fr.univlille.sae.Main;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Cette classe correspond à une zone pour changer la taille du labyrinthe
 *
 * @author Nathan Desmee, Valentin Thuillier, Armand Sady, Théo Lenglart
 * @version 1.0
 */
public class SizeParam extends HBox {
    public static final int MIN_SIZE = 8;
    public static final int DEFAULT_SIZE = 10;
    public static final int MAX_SIZE = 16;
    Button moins = new Button("-");
    Button plus = new Button("+");
    Label size = new Label();


    public SizeParam() {
        getChildren().addAll(moins, new Label("  "), size, new Label("  "), plus);
        setAction();
        size.setText(String.valueOf(DEFAULT_SIZE));
        size.setFont(Main.loadFont(Main.ARCADE_FONT, 20));
        setAlignment(Pos.CENTER);
    }

    /**
     * Cette méthode permet de paramétrer les actions des boutons plus et moins
     */
    private void setAction() {
        plus.setOnAction(e -> {
            int actualSize = Integer.parseInt(size.getText());
            if(actualSize != MAX_SIZE) {
                size.setText("" + (actualSize + 1));
            }
        });
        moins.setOnAction(e -> {
            int actualSize = Integer.parseInt(size.getText());
            if(actualSize != MIN_SIZE) {
                size.setText("" + (actualSize - 1));
            }
        });
    }

    /**
     * Cette méthode permet de récupérer la valeur de la taille
     *
     * @return (int) La valeur de la taille
     */
    public int getValue() {
        return Integer.parseInt(this.size.getText());
    }

    /**
     * Cette méthode permet de savoir si la valeur est correcte
     *
     * @return (boolean) true si la valeur est correcte, false sinon
     */
    public boolean isValid() {
        int value = getValue();
        return value >= MIN_SIZE && value <= MAX_SIZE;
    }

}
