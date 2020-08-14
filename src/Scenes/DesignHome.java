/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scenes;

import WindowManagement.GridPaneManagement;
import WindowManagement.TopManagement;
import com.intrinio.invoker.ApiException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author caio
 */
public class DesignHome {
    private Text WELCOMETXT,WELCOMETXT2;
    private Button TEXT1,TEXT2,TEXT3;
    
    private MyFont MYFONT;
    private DesignAdd DSADD;
    private TopManagement TOPMANAGE;
    private DesignInv DSINV;
    private DesignPerf DSPERF;
    private GridPaneManagement MANAGE;
    
    public GridPane DesignHome(GridPane GRID, VBox TOP){
        MYFONT = new MyFont();
        DSADD = new DesignAdd();
        TOPMANAGE = new TopManagement();
        DSINV = new DesignInv();
        MANAGE = new GridPaneManagement();
        
        WELCOMETXT = new Text("Welcome to our software.");
        WELCOMETXT.setFont(MYFONT.getOswaldRegular());
        WELCOMETXT2 = new Text("Here you can:");
        WELCOMETXT2.setFont(MYFONT.getOswaldRegular());
        
        TEXT1 = new Button("Register an investments");
        TEXT1.setFont(MYFONT.getOswaldRegular());
        TEXT1.getStyleClass().add("submitButton");
        TEXT1.setOnAction(e ->{
            GRID.getChildren().clear();
            TOPMANAGE.ChangeTop(TOP, "Add investment");
            try {
                DSADD.DesignAdd(GRID, TOP);
            } catch (IOException ex) {
                Logger.getLogger(Design.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        TEXT2 = new Button("Check your investment performance");
        TEXT2.getStyleClass().add("submitButton");
        TEXT2.setFont(MYFONT.getOswaldRegular());
        TEXT2.setOnAction(e->{
            TOPMANAGE.ChangeTop(TOP, "Portfolio Performance");
            GRID.getChildren().clear();
            try {
                MANAGE.DesignPerf(GRID, TOP);
            } catch (IOException ex) {
                Logger.getLogger(Design.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ApiException ex) {
                Logger.getLogger(Design.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        TEXT3 = new Button("See your investments");
        TEXT3.getStyleClass().add("submitButton");
        TEXT3.setFont(MYFONT.getOswaldRegular());
        TEXT3.setOnAction(e->{
            GRID.getChildren().clear();
            TOPMANAGE.ChangeTop(TOP, "Investments");
            try {
                MANAGE.DesignInv(GRID, TOP);
            } catch (IOException ex) {
                Logger.getLogger(Design.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        GRID.add(WELCOMETXT, 0, 0);
        GRID.add(WELCOMETXT2, 0, 1);
        GRID.add(TEXT1, 0, 2);
        GRID.add(TEXT2, 0, 3);
        GRID.add(TEXT3, 0, 4);
        
        GRID.setPadding(new Insets(0, 50, 50, 10));
        
        return GRID;   
    }
}
