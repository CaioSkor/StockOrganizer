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
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

/**
 *
 * @author caio
 */
public class DesignPerf {
    private Text STARTPORT, GAINSTOCKSTXT, GAINOVERRALTXT, PROFITTXT, GAINSTOCKS, GAINSTOCKSPERCENTAGE, GAINOVERRAL, GAINOVERRALPERCENTAGE, PROFIT, PROFITPERCENTAGE, ADDTXT, SUBTRACTTXT, PERCENTAGETXT, REALGAIN;
    private Button RESETBTN, RESETCONFIRMBTN, STARTBTN, CANCELBTN;
    private HBox SHARESBOX, PROFITBOX, OVERRALBOX;
    
    
    private ToolsUse TOOLS;
    private InvestmentController INVESTCONTROL;
    private PerformanceController PERF;
    private TopManagement TOPMANAGE;
    private GridPaneManagement MANAGE;
    private DesignAdd DSADD;
    private MyFont MYFONT;
    
    public GridPane DesignPerf(GridPane GRID, VBox TOP) throws IOException, ApiException{
        GRID.setHgap(5);
        MYFONT = new MyFont();
        DSADD = new DesignAdd();
        TOPMANAGE = new TopManagement();
        PROFITBOX = new HBox(50);
        SHARESBOX = new HBox(50);
        OVERRALBOX = new HBox(50);
        PERF = new PerformanceController();
        
        GAINOVERRALTXT = new Text();
        GAINOVERRALTXT.setText("Balance current and sold stocks:");
        GAINOVERRALTXT.setFont(MYFONT.getOswaldRegular());
        GAINOVERRALTXT.setFill(Color.GRAY);
        
        GAINSTOCKS = new Text();
        GAINSTOCKS.setFont(MYFONT.getOswaldRegular());

        GAINSTOCKSPERCENTAGE = new Text();
        GAINSTOCKSPERCENTAGE.setFont(MYFONT.getOswaldRegular());
        
        GAINOVERRAL = new Text();
        GAINOVERRAL.setFont(MYFONT.getOswaldRegular());

        GAINOVERRALPERCENTAGE = new Text();
        GAINOVERRALPERCENTAGE.setFont(MYFONT.getOswaldRegular());
        
        if(PERF.getTotalPerformanceShare() > 0){
                    String MESSAGE1 = String.valueOf(PERF.getTotalPerformanceShare());
                    GAINSTOCKS.setText("+ "+MESSAGE1 + " USD   ");
                    GAINSTOCKS.setFill(Color.CHARTREUSE);
                    String MESSAGE2 = String.valueOf(PERF.getTotalPerformancePerc());
                    GAINSTOCKSPERCENTAGE.setText("+ "+MESSAGE2 + " %   ");
                    GAINSTOCKSPERCENTAGE.setFill(Color.CHARTREUSE);
        }else{
                    String MESSAGE1 = String.valueOf(PERF.getTotalPerformanceShare());
                    GAINSTOCKS.setText(MESSAGE1 + " USD   ");
                    GAINSTOCKS.setFill(MYFONT.getTitleColor());
                    String MESSAGE2 = String.valueOf(PERF.getTotalPerformancePerc());
                    GAINSTOCKSPERCENTAGE.setText(MESSAGE2 + " %   ");
                    GAINSTOCKSPERCENTAGE.setFill(MYFONT.getTitleColor());
        }
        
        if(PERF.getTotalPerformanceAll() > 0){
            System.out.println("a");
            String MESSAGE3 = String.valueOf(PERF.getTotalPerformanceAll());
            GAINOVERRAL.setText("+ "+MESSAGE3 + " USD   ");
            GAINOVERRAL.setFill(Color.CHARTREUSE);
            String MESSAGE4 = String.valueOf(PERF.getTotalGainPercentage());
            GAINOVERRALPERCENTAGE.setText("+ "+MESSAGE4 + " %   ");
            GAINOVERRALPERCENTAGE.setFill(Color.CHARTREUSE);  
        }else{
            String MESSAGE3 = String.valueOf(PERF.getTotalPerformanceAll());
            GAINOVERRAL.setText(MESSAGE3 + " USD   ");
            GAINOVERRAL.setFill(MYFONT.getTitleColor());
            String MESSAGE4 = String.valueOf(PERF.getTotalGainPercentage());
            GAINOVERRALPERCENTAGE.setText(MESSAGE4 + " %   ");
            GAINOVERRALPERCENTAGE.setFill(MYFONT.getTitleColor()); 
        }
       
        RESETBTN = new Button();
        RESETBTN.setText("RESET ALL PORTFOLIO");
        RESETBTN.setFont(MYFONT.getOswaldButton());
        RESETBTN.getStyleClass().add("submitButton");
        RESETBTN.setOnAction((ActionEvent e) -> {
            GRID.getChildren().clear();
            try {
                DesignPerfConfirmReset(GRID,TOP);
            } catch (IOException ex) {
                Logger.getLogger(DesignPerf.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        GAINSTOCKSTXT = new Text("Stocks performance:");
        GAINSTOCKSTXT.setTextAlignment(TextAlignment.CENTER);
        GAINSTOCKSTXT.setFont(MYFONT.getOswaldRegular());
        
        GAINOVERRALTXT = new Text("Total balance:");
        GAINOVERRALTXT.setTextAlignment(TextAlignment.CENTER);
        GAINOVERRALTXT.setFont(MYFONT.getOswaldRegular());
        
        PROFITTXT = new Text("Total profit/loss:");
        PROFITTXT.setTextAlignment(TextAlignment.CENTER);
        PROFITTXT.setFont(MYFONT.getOswaldRegular());
        PROFIT = new Text();
        PROFIT.setFont(MYFONT.getOswaldRegular());
        
        if(PERF.getTotalProfit() > 0){
            String MESSAGE5 = String.valueOf(PERF.getTotalProfit());
            PROFIT.setText("+ "+MESSAGE5 + " USD   ");
            PROFIT.setFill(Color.CHARTREUSE);
        }else{
            String MESSAGE5 = String.valueOf(PERF.getTotalProfit());
            PROFIT.setText(MESSAGE5 + " USD   ");
            PROFIT.setFill(MYFONT.getTitleColor());
              
        }
        PROFITBOX.getChildren().add(PROFIT);
        PROFITBOX.setPadding(new Insets(10,10,10,15));
        PROFITBOX.setAlignment(Pos.CENTER);
        
        SHARESBOX.getChildren().addAll(GAINSTOCKS, GAINSTOCKSPERCENTAGE);
        SHARESBOX.setPadding(new Insets(10,10,10,15));
        SHARESBOX.setAlignment(Pos.CENTER);
        
        OVERRALBOX.getChildren().addAll(GAINOVERRAL, GAINOVERRALPERCENTAGE);
        OVERRALBOX.setPadding(new Insets(10,10,10,15));
        OVERRALBOX.setAlignment(Pos.CENTER);
        
        
        GRID.setAlignment(Pos.CENTER);
        GRID.setPadding(new Insets(0, 0, 15, 20));
        GRID.setVgap(10);
        GRID.add(GAINSTOCKSTXT, 0, 0);
        GRID.add(SHARESBOX, 0, 1);
        GRID.add(GAINOVERRALTXT, 0, 2);
        GRID.add(OVERRALBOX, 0, 3);
        GRID.add(PROFITTXT, 0, 4);
        GRID.add(PROFITBOX, 0, 5); 
        GRID.add(RESETBTN, 1, 9);
        
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
        GRID.setHgap(5);
        MYFONT = new MyFont();
        DSADD = new DesignAdd();
        TOPMANAGE = new TopManagement();
        PROFITBOX = new HBox(50);
        PERF = new PerformanceController();
        
        RESETBTN = new Button();
        RESETBTN.setText("RESET ALL PORTFOLIO");
        RESETBTN.setFont(MYFONT.getOswaldButton());
        RESETBTN.getStyleClass().add("submitButton");
        RESETBTN.setOnAction((ActionEvent e) -> {
            GRID.getChildren().clear();
            try {
                DesignPerfConfirmReset(GRID,TOP);
            } catch (IOException ex) {
                Logger.getLogger(DesignPerf.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        STARTPORT = new Text("No current investment. Start portfolio");
        PROFITTXT = new Text("Total profit/loss:");
        PROFITTXT.setTextAlignment(TextAlignment.CENTER);
        PROFITTXT.setFont(MYFONT.getOswaldRegular());
        PROFIT = new Text();
        PROFIT.setFont(MYFONT.getOswaldRegular());
        
        if(PERF.getTotalProfit() > 0){
            String MESSAGE5 = String.valueOf(PERF.getTotalProfit());
            PROFIT.setText("+ "+MESSAGE5 + " USD   ");
            PROFIT.setFill(Color.CHARTREUSE);
        }else{
            String MESSAGE5 = String.valueOf(PERF.getTotalProfit());
            PROFIT.setText(MESSAGE5 + " USD   ");
            PROFIT.setFill(MYFONT.getTitleColor());
              
        }
        PROFITBOX = new HBox();
        PROFITBOX.getChildren().add(PROFIT);
        PROFITBOX.setPadding(new Insets(10,10,10,15));
        PROFITBOX.setAlignment(Pos.CENTER);
        
        
        STARTPORT.setFont(MYFONT.getOswaldRegular());
        
        GRID.setAlignment(Pos.CENTER);
        GRID.setPadding(new Insets(0, 0, 15, 20));
        GRID.add(STARTPORT, 0, 0);
        GRID.add(PROFITTXT, 0, 1);
        GRID.add(PROFITBOX, 0, 2); 
        GRID.add(RESETBTN, 1, 9);
        
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
                DesignPerfPostReset(GRID,TOP);
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
        STARTPORT.setFont(MYFONT.getOswaldRegular());
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