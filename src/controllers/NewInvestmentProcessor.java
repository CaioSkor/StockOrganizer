/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

/**
 *
 * @author caio
 */
public class NewInvestmentProcessor {
    private String[] INPUTS;
    private String[] WRONGINPUTS;
    
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
}
