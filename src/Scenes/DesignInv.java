/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scenes;

import WindowManagement.TopManagement;
import com.intrinio.invoker.ApiException;
import controllers.GetTicker;
import controllers.ToolsUse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author caio
 */
public class DesignInv {
    private Button[] STOCKBTN;
    private HBox[] MIDDLE;
    private VBox MID;
    private Integer INDEXA, INDEX, RESTE, PIECES;
    
    private GetTicker TICKERS;
    private ToolsUse TOOLS;
    private MyFont MYFONT;
    private DesignStock DSSTOCK;
    private TopManagement TOPMANAGE;
    
    public GridPane DesignInv(GridPane GRID, VBox TOP, Stage MAINWINDOW) throws IOException{
        TOOLS = new ToolsUse();
        TICKERS = new GetTicker();
        TOPMANAGE = new TopManagement();
        MYFONT = new MyFont();
        
        STOCKBTN = new Button[TOOLS.FileMeasure("data/investment.txt", 1)];
        
        RESTE = TOOLS.FileMeasure("data/investment.txt", 1)%3;
        PIECES = TOOLS.FileMeasure("data/investment.txt", 1)/3;
        
        if (RESTE > 0){
            MIDDLE = new HBox[PIECES+1];
        }else{
            MIDDLE = new HBox[PIECES];        
        }
        for (INDEXA=0; INDEXA< PIECES; INDEXA++){    
            MIDDLE[INDEXA] = new HBox(10);
            for(INDEX=0; INDEX<3; INDEX++){         
                STOCKBTN[INDEX+(3*INDEXA)] = new Button();
                STOCKBTN[INDEX+(3*INDEXA)].setPrefWidth(110);
                STOCKBTN[INDEX+(3*INDEXA)].setText(TICKERS.GetTicker("data/investment.txt",1)[INDEX+(3*INDEXA)]); 
                STOCKBTN[INDEX+(3*INDEXA)].setOnAction((ActionEvent e) -> {
                   GRID.getChildren().clear();
                   String TITLE = "Investment: " + ((Button) e.getSource()).getText();
                   TOPMANAGE.ChangeTop(TOP, TITLE);
                   final Integer BUTTONID = INDEX;
                   System.out.println("Button pressed " + ((Button) e.getSource()).getText());
                   int LASTBUTTON = BUTTONID;                
                   try {
                        DSSTOCK = new DesignStock();
                        DSSTOCK.DesignStock(GRID,((Button) e.getSource()).getText());
                    } catch (IOException ex) {
                        System.out.println("PROBLEMS");
                    } catch (ApiException ex) {
                       Logger.getLogger(DesignInv.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });   
                STOCKBTN[INDEX+(3*INDEXA)].getStyleClass().add("stockButtons");
                STOCKBTN[INDEX+(3*INDEXA)].setFont(MYFONT.getOswaldButton());
                MIDDLE[INDEXA].getChildren().add(STOCKBTN[INDEX+(3*INDEXA)]);     
            }
            MIDDLE[INDEXA].setAlignment(Pos.CENTER);   
        }
        if (RESTE > 0){
            MIDDLE[INDEXA] = new HBox(10);
            for(INDEX=0; INDEX< RESTE; INDEX++){         
                STOCKBTN[INDEX+(3*INDEXA)] = new Button();
                STOCKBTN[INDEX+(3*INDEXA)].setPrefWidth(110);
                STOCKBTN[INDEX+(3*INDEXA)].setText(TICKERS.GetTicker("data/investment.txt",1)[INDEX+(3*INDEXA)]); 
                STOCKBTN[INDEX+(3*INDEXA)].setOnAction((ActionEvent e) -> {
                    GRID.getChildren().clear();
                    String TITLE = "Investment: " + ((Button) e.getSource()).getText();
                    TOPMANAGE.ChangeTop(TOP, TITLE);
                    final Integer BUTTONID = INDEX;
                    System.out.println("Button pressed " + ((Button) e.getSource()).getText());
                    int LASTBUTTON = BUTTONID;                
                    try {
                        DSSTOCK = new DesignStock();
                        DSSTOCK.DesignStock(GRID,((Button) e.getSource()).getText());
                    } catch (IOException ex) {
                        System.out.println("PROBLEMS");
                    } catch (ApiException ex) {
                        Logger.getLogger(DesignInv.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });          
                STOCKBTN[INDEX+(3*INDEXA)].getStyleClass().add("stockButtons");
                STOCKBTN[INDEX+(3*INDEXA)].setFont(MYFONT.getOswaldButton());
                MIDDLE[INDEXA].getChildren().add(STOCKBTN[INDEX+(3*INDEXA)]);     
            }
            MIDDLE[INDEXA].setPadding(new Insets(0,0,0,55));            
        }
        
        MID = new VBox(10);
        MID.setAlignment(Pos.CENTER);
        for(INDEX=0; INDEX< PIECES; INDEX++){                     
            MID.getChildren().add(MIDDLE[INDEX]); 
        }
        if (RESTE > 0){
            MID.getChildren().add(MIDDLE[PIECES]);        
        }    
        
        GRID.setPadding(new Insets(0, 50, 50, 75));
        GRID.add(MID, 0, 0);
        
        return GRID;
    }
    
    public GridPane DesignInvDeleted(GridPane GRID, VBox TOP){
        return GRID;
    }
    
 /*   public void CurrentPopulateMe(Stage MAINWINDOW, Integer RESTE, Integer CHOICE, GridPane GRID) throws IOException{
        MIDDLE[INDEXA] = new HBox(10);
        for(INDEX=0; INDEX< RESTE; INDEX++){         
            STOCKBTN[INDEX+(3*INDEXA)] = new Button();
            STOCKBTN[INDEX+(3*INDEXA)].setPrefWidth(110);
            STOCKBTN[INDEX+(3*INDEXA)].setText(TICKERS.GetTicker("data/investment.txt",CHOICE)[INDEX+(3*INDEXA)]); 
            STOCKBTN[INDEX+(3*INDEXA)].setOnAction((ActionEvent e) -> {
                final Integer BUTTONID = INDEX;
                System.out.println("Button pressed " + ((Button) e.getSource()).getText());
                int LASTBUTTON = BUTTONID;                
                try {
                    DSSTOCK = new DesignStock();
                    DSSTOCK.DesignStock(MAINWINDOW, GRID,((Button) e.getSource()).getText());
                } catch (IOException ex) {
                    System.out.println("PROBLEMS");
                } catch (ApiException ex) {
                    Logger.getLogger(DesignInv.class.getName()).log(Level.SEVERE, null, ex);
                }
            });                
            MIDDLE[INDEXA].getChildren().add(STOCKBTN[INDEX+(3*INDEXA)]);     
        }
    }*/
}
