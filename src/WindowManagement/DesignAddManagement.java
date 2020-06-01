/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WindowManagement;

import Scenes.DesignAdd;
import java.io.IOException;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author caio
 */
public class DesignAddManagement {
    private DesignAdd DSADD;
    
    public void InvestmentCreated(GridPane GRID, String TICKER, Stage MAINWINDOW, VBox TOP){
        DSADD = new DesignAdd();
        DSADD.DesignAddCreatedInvestment(GRID, TICKER, TOP);
    }
    
    public void DesignAddDefault(GridPane GRID, VBox TOP) throws IOException{
        DSADD = new DesignAdd();
        DSADD.DesignAdd(GRID, TOP);
    }
    
}
