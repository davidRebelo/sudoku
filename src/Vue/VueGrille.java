/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Controleur.ControleurGrille;
import Modele.Case;
import Modele.Groupe;
import Modele.Jeu;
import java.util.Observable;
import java.util.Observer;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author david
 */
public class VueGrille extends VueSudoku {
    
    protected ControleurGrille cGrille;
    
    public VueGrille(Jeu grilleJeu, ControleurGrille cGrille){
        super(grilleJeu);
        this.cGrille = cGrille;
        CreerGrille(grilleJeu);
    }

    private void CreerGrille(Jeu grilleJeu){
        gridPane = new GridPane();
        
        //config GridPane
        gridPane.setPadding(new Insets(10,10,10,10));
        gridPane.setStyle("-fx-background-color:white;");

        int colonne = 0;
        int rangee = 0;
        
        //parcourt de toute les valeurs du sudoku
        for (Groupe lignes_tableau:grilleJeu.getTabL()){
            for (Case case_tableau:lignes_tableau.getTab()){
                TextField txt = new TextField();
                
                configTextField(txt, case_tableau, colonne, rangee);
                
                gridPane.add(txt, colonne++, rangee);
 
                if (colonne > 8){
                    colonne = 0;
                    rangee++;
                }

            }
        }
        
    }

    private void configTextField(TextField txt, Case c, final int col, final int rangee){
        txt.setPrefHeight(45);
        txt.setPrefWidth(45);
        txt.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        txt.setStyle("-fx-border-color: black;");
        txt.setId(String.valueOf(((rangee*100)+col)));
        if(c.getV() != 0){
            txt.setDisable(true);
            txt.setText(String.valueOf(c.getV()));
            txt.setOpacity(0.6);
        }
        else{
            txt.setDisable(false);
            txt.setText("");
        }        
        
        txt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                txt.selectAll();
            }
        });
        
        //Verification si l'utilisateur introduit un numero
        txt.setTextFormatter(new TextFormatter<Integer>((TextFormatter.Change ch) -> {
            if(ch.getControlNewText().matches("-?\\d")){
                return ch;
            }
            else{   
                return null;
            }
        }));
        
        txt.textProperty().addListener((observable, oldValue, newValue) -> {
            if(cGrille.verifierConflit(newValue, c)) txt.setStyle("-fx-border-color: red;");
            else txt.setStyle("-fx-border-color: black;");
        });
        
        //En cas de mis a jour du jeu les case se mette a jour si elles sont differente 
        grilleJeu.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                Case c_up = grilleJeu.getTabL()[rangee].getTab()[col];
                txt.setStyle("-fx-border-color: black;");
                if(c_up != c){
                    if(c_up.getV() != 0){
                        txt.setDisable(true);
                        txt.setText(String.valueOf(c_up.getV()));
                        txt.setOpacity(0.6);
                    }                   
                }
                if(c_up.getV() == 0){
                    txt.setDisable(false);
                    txt.clear();
                    txt.setText("");
                    txt.setOpacity(1);
                } 
            }
        });

    }

}
