/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modules;

/**
 *
 * @author caio
 */
public class LastPerformance {
    private String code;
    private String performance;
    
    public LastPerformance(String c, String p){
        this.code = c;
        this.performance = p;
    }
    
    public String getCode(){
        return code;
    }
    
    public void setCode(String c){
        this.code = c;
    }
    
    public String getPerformance(){
        return performance;
    }
    
    public void setPerformance(String p){
        this.performance = p;
    }
}
