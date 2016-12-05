/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Controleur.ControleurJeu;
import javafx.scene.layout.GridPane;
import Modele.Jeu;

/**
 *
 * @author david
 */
public class VueSudoku{
    
    protected Jeu grilleJeu;
    protected GridPane gridPane;
    
    public VueSudoku(Jeu grilleJeu) {
        this.grilleJeu = grilleJeu;
    }

    public GridPane getGridPane() {
        return gridPane;
    }
}
