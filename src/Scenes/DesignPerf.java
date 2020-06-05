/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scenes;

import WindowManagement.GridPaneManagement;
import WindowManagement.TopManagement;
import com.intrinio.invoker.ApiException;
import controllers.InvestmentController;
import controllers.PerformanceController;
import controllers.ToolsUse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

/**
 *
 * @author caio
 */
public class DesignPerf {
    private Text STARTPORT, GAINSTOCKSTXT, GAINOVERRALTXT, PROFITTXT, GAINSTOCKS, GAINSTOCKSPERCENTAGE, GAINOVERRAL, GAINOVERRALPERCENTAGE, PROFIT, PROFITPERCENTAGE, ADDTXT, SUBTRACTTXT, PERCENTAGETXT;
    private Button RESETBTN, RESETCONFIRMBTN, STARTBTN, CANCELBTN;
    private TextFlow GAINSTOCKSFLOW, GAINOVERRALFLOW, PROFITFLOW;
    
    
    private ToolsUse TOOLS;
    private InvestmentController INVESTCONTROL;
    private PerformanceController PERF;
    private TopManagement TOPMANAGE;
    private GridPaneManagement MANAGE;
    private DesignAdd DSADD;
    private MyFont MYFONT;
    
    public GridPane DesignPerf(GridPane GRID, VBox TOP) throws IOException, ApiException{
        System.out.println("ola");
        MYFONT = new MyFont();
        PERF = new PerformanceController();
        
        ADDTXT = new Text("+");
        ADDTXT.setFont(MYFONT.getOswaldRegular());
        ADDTXT.setFill(Color.CHARTREUSE);
        
        SUBTRACTTXT = new Text("-");
        SUBTRACTTXT.setFont(MYFONT.getOswaldRegular());
        SUBTRACTTXT.setFill(MYFONT.getTitleColor());
        
        PERCENTAGETXT = new Text("%");
        PERCENTAGETXT.setFont(MYFONT.getOswaldRegular());
        
        GAINSTOCKSTXT = new Text("Current stocks balance");
        GAINSTOCKSTXT.setFont(MYFONT.getOswaldRegular());
        
        GAINOVERRALTXT = new Text("Current and sold stocks balance");
        GAINOVERRALTXT.setFont(MYFONT.getOswaldRegular());
        
        PROFITTXT = new Text("Gain/Loss from sold stocks");
        PROFITTXT.setFont(MYFONT.getOswaldRegular());
        
        String GAINSHARES = String.valueOf(PERF.getTotalPerformanceShare());
        String GAINSHARESPERCENTAGE = String.valueOf(PERF.getTotalPerformancePerc()); // Fix this
        String GAINTOTAL = String.valueOf(PERF.getTotalPerformanceAll());
        String GAINTOTALPERCENTAGE  = String.valueOf(PERF.getTotalPerformancePerc());
        String TOTALPROFIT = String.valueOf(PERF.getTotalProfit());
        String TOTALPROFITPERCENTAGE = String.valueOf(PERF.getTotalGainPercentage());
        
        GAINSTOCKS = new Text(GAINSHARES + " ");
        GAINSTOCKS.setFont(MYFONT.getOswaldRegular());
        
        GAINSTOCKSPERCENTAGE = new Text(GAINSHARESPERCENTAGE + " ");
        GAINSTOCKSPERCENTAGE.setFont(MYFONT.getOswaldRegular());
        
        GAINOVERRAL = new Text(GAINTOTAL + " ");
        GAINOVERRAL.setFont(MYFONT.getOswaldRegular());
        
        GAINOVERRALPERCENTAGE = new Text(GAINTOTALPERCENTAGE + " ");
        GAINOVERRALPERCENTAGE.setFont(MYFONT.getOswaldRegular());
        
        PROFIT = new Text(TOTALPROFIT + " ");
        PROFIT.setFont(MYFONT.getOswaldRegular());
        
        PROFITPERCENTAGE = new Text(TOTALPROFITPERCENTAGE + " ");
        PROFITPERCENTAGE.setFont(MYFONT.getOswaldRegular());
        
        GAINSTOCKSFLOW = new TextFlow();
        GAINOVERRALFLOW = new TextFlow();
        PROFITFLOW = new TextFlow();
        
        if(PERF.getTotalPerformanceShare()>0){
            GAINSTOCKSFLOW.getChildren().addAll(ADDTXT,GAINSTOCKS,GAINSTOCKSPERCENTAGE,PERCENTAGETXT);
        }else{
            GAINSTOCKSFLOW.getChildren().addAll(SUBTRACTTXT,GAINSTOCKS,GAINSTOCKSPERCENTAGE,PERCENTAGETXT);
        }
        
        if(PERF.getTotalPerformanceAll() > 0){
            GAINOVERRALFLOW.getChildren().addAll(ADDTXT,GAINOVERRAL,GAINOVERRALPERCENTAGE,PERCENTAGETXT);
        }else{
            GAINOVERRALFLOW.getChildren().addAll(SUBTRACTTXT,GAINOVERRAL,GAINOVERRALPERCENTAGE,PERCENTAGETXT);
        }
        
        if(PERF.getTotalProfit() > 0){
            PROFITFLOW.getChildren().addAll(ADDTXT,PROFIT,PROFITPERCENTAGE,PERCENTAGETXT);
        }else{
            PROFITFLOW.getChildren().addAll(SUBTRACTTXT,PROFIT,PROFITPERCENTAGE,PERCENTAGETXT);
        }
        
        RESETBTN = new Button("Reset Portfolio");
        RESETBTN.setFont(MYFONT.getOswaldButton());
        RESETBTN.getStyleClass().add("submitButton");
        RESETBTN.setOnAction(e->{
            try {
                GRID.getChildren().clear();
                DesignPerfConfirmReset(GRID, TOP);
            } catch (IOException ex) {
                Logger.getLogger(DesignPerf.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
       GRID.add(GAINSTOCKSFLOW, 0, 0);
       GRID.add(GAINOVERRALFLOW, 0, 1);
       GRID.add(PROFITFLOW, 0, 2);
       
       GRID.add(RESETBTN, 3, 7);
        
       return GRID; 
    }
    
    public GridPane DesignPerfStart(GridPane GRID, VBox TOP){
        MYFONT = new MyFont();
        DSADD = new DesignAdd();
        TOPMANAGE = new TopManagement();
        
        STARTPORT = new Text("No investment or sold investment recorded. Start portfolio");
        STARTPORT.setFont(MYFONT.getOswaldRegular());
        
        STARTBTN = new Button("Add investment");
        STARTBTN.setFont(MYFONT.getOswaldButton());
        STARTBTN.getStyleClass().add("submitButton");
        STARTBTN.setOnAction(e->{
            GRID.getChildren().clear();
            TOPMANAGE.ChangeTop(TOP, "Add investment");
            try {
                DSADD.DesignAdd(GRID, TOP);
            } catch (IOException ex) {
                Logger.getLogger(DesignPerf.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        GRID.setPadding(new Insets(0, 0, 15, 20));
        GRID.add(STARTPORT, 0, 0);
        GRID.add(STARTBTN, 0, 1);   
        return GRID;
    }
    
    public GridPane DesignPerfOnlySold(GridPane GRID, VBox TOP) throws IOException, ApiException{
        ADDTXT = new Text("+");
        ADDTXT.setFont(MYFONT.getOswaldRegular());
        ADDTXT.setFill(Color.CHARTREUSE);
        
        SUBTRACTTXT = new Text("-");
        SUBTRACTTXT.setFont(MYFONT.getOswaldRegular());
        SUBTRACTTXT.setFill(MYFONT.getTitleColor());
        
        PERCENTAGETXT = new Text("%");
        PERCENTAGETXT.setFont(MYFONT.getOswaldRegular());
        
        String TOTALPROFIT = String.valueOf(PERF.getTotalProfit());
        String TOTALPROFITPERCENTAGE = String.valueOf(PERF.getTotalGainPercentage());
        
        PROFIT = new Text(TOTALPROFIT + " ");
        PROFIT.setFont(MYFONT.getOswaldRegular());
        
        PROFITPERCENTAGE = new Text(TOTALPROFITPERCENTAGE + " ");
        PROFITPERCENTAGE.setFont(MYFONT.getOswaldRegular());
        
        if(PERF.getTotalProfit() > 0){
            PROFITFLOW.getChildren().addAll(ADDTXT,PROFIT,PROFITPERCENTAGE,PERCENTAGETXT);
        }else{
            PROFITFLOW.getChildren().addAll(SUBTRACTTXT,PROFIT,PROFITPERCENTAGE,PERCENTAGETXT);
        }
        
        GRID.add(PROFITFLOW, 0, 2);
       
        GRID.add(RESETBTN, 3, 7);
        
        
        return GRID;
    }
    
    public GridPane DesignPerfConfirmReset(GridPane GRID, VBox TOP) throws IOException{
        MYFONT = new MyFont();
        INVESTCONTROL = new InvestmentController();
        
        STARTPORT = new Text("Are you sure you want to reset the portfolio?");
        STARTPORT.setFont(MYFONT.getOswaldRegular());
        
        RESETBTN = new Button("Reset");
        RESETBTN.setFont(MYFONT.getOswaldButton());
        RESETBTN.getStyleClass().add("submitButton");
        RESETBTN.setOnAction(e->{
            try {
                GRID.getChildren().clear();
                INVESTCONTROL.deleteAll();
            } catch (IOException ex) {
                Logger.getLogger(DesignPerf.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        GRID.add(STARTPORT, 0, 0);
        GRID.add(RESETBTN, 0, 1);
        
        return GRID;
    }
    
    public GridPane DesignPerfPostReset(GridPane GRID, VBox TOP){
        MYFONT = new MyFont();
        MANAGE = new GridPaneManagement();
        TOPMANAGE = new TopManagement();
        
        STARTPORT = new Text("Portfolio successfully restarted");
        PauseTransition DELAY = new PauseTransition(Duration.seconds(2));
            DELAY.setOnFinished(ev->{
                GRID.getChildren().clear();
            try {
                MANAGE.DesignPerf(GRID, TOP);
            } catch (IOException ex) {
                Logger.getLogger(DesignPerf.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ApiException ex) {
                Logger.getLogger(DesignPerf.class.getName()).log(Level.SEVERE, null, ex);
            }
                TOPMANAGE.ChangeTop(TOP, "Portfolio Performance");
                });
        DELAY.play();
        
        GRID.add(STARTPORT, 0, 0);
        return GRID;
    }
}
