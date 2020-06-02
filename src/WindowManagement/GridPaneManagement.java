/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WindowManagement;

import Scenes.DesignAdd;
import Scenes.DesignInv;
import Scenes.DesignPerf;
import controllers.InvestmentController;
import controllers.ToolsUse;
import java.io.IOException;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author caio
 */
public class GridPaneManagement {
    private DesignAdd DSADD;
    private DesignPerf DSPERF;
    private DesignInv DSINV;
    
    private ToolsUse TOOLS;
    private InvestmentController INVESTCONTROL;
    
    public void InvestmentCreated(GridPane GRID, String TICKER, Stage MAINWINDOW, VBox TOP){
        DSADD = new DesignAdd();
        DSADD.DesignAddCreatedInvestment(GRID, TICKER, TOP);
    }
    
    public void DesignAddDefault(GridPane GRID, VBox TOP) throws IOException{
        DSADD = new DesignAdd();
        DSADD.DesignAdd(GRID, TOP);
    }
    
    public void DesignInv(GridPane GRID, VBox TOP) throws IOException{
        DSINV = new DesignInv();
        INVESTCONTROL = new InvestmentController();
        
        if(!INVESTCONTROL.getAllCodes()[0][0].equals("NULL")){
            DSINV.DesignInv(GRID, TOP);
        }else if (!INVESTCONTROL.getToutLastPerf()[0].equals("NULL")){
            DSINV.DesignInvOnlyDeleted(GRID, TOP);
        }else{
            DSINV.DesignInvStart(GRID, TOP);
        }
    }
    
    public void DesignPerf(GridPane GRID, VBox TOP) throws IOException{
        DSPERF = new DesignPerf();
        INVESTCONTROL = new InvestmentController();
        
        if(!INVESTCONTROL.getAllCodes()[0][0].equals("NULL")){
            DSPERF.DesignPerf(GRID, TOP);
        }else if (!INVESTCONTROL.getToutLastPerf()[0].equals("NULL")){
            DSPERF.DesignPerfOnlySold(GRID, TOP);
        }else{
            DSPERF.DesignPerfStart(GRID, TOP);
        }
    }
    
}
