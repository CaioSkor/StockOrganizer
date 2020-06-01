/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Scenes.DesignAdd;
import java.io.IOException;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author caio
 */
public class NewInvestmentProcessor {
    private String[] INPUTS, INPUT, BLANK, CORRECT;
    private String[] WRONGINPUTS;
    private boolean[] TYPECHECK, INPUTCHECK;
    private Integer INDEX, COUNT, CHECK;
    
    private InvestmentController INVESTCONTROL;
    private DesignAdd DSADD;
    
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
            if(INPUTS[0] != null && !INPUTS[0].trim().isEmpty()){
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
        
        return WRONGINPUTS;
    }
    
    public void createInvestment(String TICKER, String PRICE, String AMOUNT, String DATE, String REASON, String DELDATE, GridPane GRID, VBox TOP) throws IOException{
        INVESTCONTROL = new InvestmentController();
        DSADD = new DesignAdd();
        
        COUNT = 0;
        CHECK = 0;
        
        INPUT = new String[4];
        INPUT[0] = "price";
        INPUT[1] = "amount";
        INPUT[2] = "date";
        INPUT[3] = "reason";
        
        for(int i=0; i < checkInputs(PRICE, AMOUNT, DATE, REASON).length; i++){
            if(checkInputs(PRICE, AMOUNT, DATE, REASON)[i] == "0"){
                COUNT++;
            }
        }
        
        INPUTCHECK = new boolean[4];
        BLANK = new String[COUNT];
        TYPECHECK = new boolean[4];
        
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
                System.out.println(INPUT[i]);
                System.out.println(BLANK[POS]);
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
            INVESTCONTROL.createInvestment(TICKER, PRICE, AMOUNT, DATE, REASON, DELDATE);
        }else{
            DSADD.DesignAddWrongfulInputs(GRID, BLANK, TYPECHECK, COUNT, TOP);
        }
    }
    
}
