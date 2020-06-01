/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scenes;

import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 *
 * @author caio
 */
public class DesignHome {
    private Text WELCOMETXT;
    
    
    public GridPane DesignHome(GridPane GRID){
        WELCOMETXT = new Text();

        return GRID;   
    }
}
