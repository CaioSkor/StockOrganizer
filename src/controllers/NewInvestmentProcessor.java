/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Scenes.DesignAdd;
import WindowManagement.TopManagement;
import java.io.IOException;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author caio
 */
public class NewInvestmentProcessor {
    private String[] INPUTS, INPUT, BLANK;
    private String[] WRONGINPUTS;
    private boolean[] TYPECHECK, INPUTCHECK;
    private Integer INDEX, COUNT;
    
    private InvestmentController INVESTCONTROL;
    private DesignAdd DSADD;
    private ToolsUse TOOLS;
    private TopManagement TOPMANAGE;
    
    public String[] checkInputs(String PRICE, String AMOUNT, String DATE, String REASON) {
        WRONGINPUTS = new String[4];
        
        INPUTS = new String[4];
        INPUTS[0] = PRICE;
        INPUTS[1] = AMOUNT;
        INPUTS[2] = DATE;
        INPUTS[3] = REASON;
        
        try{
            Double.parseDouble(PRICE);
            WRONGINPUTS[0] = null;
        }catch(Exception e){
            if(INPUTS[0] != null && !INPUTS[0].trim().isEmpty()){
                WRONGINPUTS[0] = "1";
            }else{
                WRONGINPUTS[0] = "0";
            }
        }
        
        try{
            Integer.parseInt(AMOUNT);
            WRONGINPUTS[1] = null;
        }catch(Exception e){
            if(INPUTS[1] != null && !INPUTS[1].trim().isEmpty()){
                WRONGINPUTS[1] = "1";
            }else{
                WRONGINPUTS[1] = "0";
            }
        }
        
        for(int i=2; i < INPUTS.length; i++){
            if(INPUTS[i] != null && !INPUTS[i].trim().isEmpty()){
                WRONGINPUTS[i] = null;
            }else{
                WRONGINPUTS[i] = "0";
            }
        }
        System.out.println(WRONGINPUTS[1]);
        
        return WRONGINPUTS;
    }
    
    public void createInvestment(String TICKER, String PRICE, String AMOUNT, String DATE, String REASON, String DELDATE, GridPane GRID, VBox TOP) throws IOException{
        INVESTCONTROL = new InvestmentController();
        DSADD = new DesignAdd();
        TOOLS = new ToolsUse();
        TOPMANAGE = new TopManagement();
        
        COUNT = 0;
        Integer CHECK = 0;
        
        INPUT = new String[4];
        INPUT[0] = "price";
        INPUT[1] = "amount";
        INPUT[2] = "date";
        INPUT[3] = "reason";
        
        INPUTCHECK = new boolean[4];
        TYPECHECK = new boolean[4];
        
        for(int i=0; i < checkInputs(PRICE, AMOUNT, DATE, REASON).length; i++){
            if(checkInputs(PRICE, AMOUNT, DATE, REASON)[i] == "0"){
                COUNT++;
                TYPECHECK[i] = false;
                INPUTCHECK[i] = true;
            }else if(checkInputs(PRICE, AMOUNT, DATE, REASON)[i] == "1"){
                TYPECHECK[i] = true;
                INPUTCHECK[i] = false;
            }
        }
        
        BLANK = new String[COUNT];
        
        for(int i=0; i < checkInputs(PRICE, AMOUNT, DATE, REASON).length; i++){
            if(checkInputs(PRICE, AMOUNT, DATE, REASON)[i] == "0"){
                TYPECHECK[i] = false;
                INPUTCHECK[i] = true;
            }else if(checkInputs(PRICE, AMOUNT, DATE, REASON)[i] == "1"){
                TYPECHECK[i] = true;
                INPUTCHECK[i] = false;
            }
        }
        Integer POS = 0;
        for(int i=0; i < INPUTCHECK.length; i++){
            if(INPUTCHECK[i]){
               BLANK[POS] = INPUT[i];
               POS++;
            }
        }
        
        INDEX = 0;
        for(int i=0; i < 4; i++){
            if(INPUTCHECK[i] || TYPECHECK[i]){
                INDEX++;
            }
        }
        
        if(INDEX == 0){
            if(TICKER.equals(TOOLS.TextBoxFiller("data/investment.txt", TICKER)[0])){
                String OLDPRICE = TOOLS.TextBoxFiller("data/investment.txt", TICKER)[1];
                String OLDAMOUNT = TOOLS.TextBoxFiller("data/investment.txt", TICKER)[2];
                String OLDDATE = TOOLS.TextBoxFiller("data/investment.txt", TICKER)[3];
                String OLDREASON = TOOLS.TextBoxFiller("data/investment.txt", TICKER)[4];
                String OLDDELDATE = TOOLS.TextBoxFiller("data/investment.txt", TICKER)[5];
                if(!TOOLS.TextBoxFiller("data/investment.txt", TICKER)[5].equals("000000")){
                    GRID.getChildren().clear();
                    TOPMANAGE.ChangeTop(TOP, " ");
                    DSADD.DesignAddDeleted(GRID, TOP, TICKER, OLDPRICE, OLDAMOUNT, OLDDATE, OLDREASON, OLDDELDATE, PRICE, AMOUNT, DATE, REASON);
                }else{
                    GRID.getChildren().clear();
                    TOPMANAGE.ChangeTop(TOP, " ");
                    DSADD.DesignAddUpdate(GRID, TOP, TICKER, OLDPRICE, OLDAMOUNT, OLDDATE, OLDREASON, PRICE, AMOUNT, DATE, REASON);
                }      
            }else{
                INVESTCONTROL.createInvestment(TICKER, PRICE, AMOUNT, DATE, REASON, DELDATE);
                GRID.getChildren().clear();
                TOPMANAGE.ChangeTop(TOP, " ");
                DSADD.DesignAddCreatedInvestment(GRID, TICKER, TOP);
            }
        }else{
            DSADD.DesignAddWrongfulInputs(GRID, BLANK, TYPECHECK, COUNT, TOP);
        }
    }
    
}