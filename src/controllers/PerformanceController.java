/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.intrinio.api.SecurityApi;
import com.intrinio.invoker.ApiClient;
import com.intrinio.invoker.ApiException;
import com.intrinio.invoker.Configuration;
import com.intrinio.invoker.auth.ApiKeyAuth;
import com.intrinio.models.ApiResponseSecurityStockPrices;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.Scanner;
import org.threeten.bp.LocalDate;


/**
 *
 * @author Caio Skornicki
 */
public class PerformanceController {
    private String PERFORMANCESTRING, TOTALPERFSTRING, PERCENTAGESTRING; 
    private URLConnection CON;
    private double PERFORMANCE, PERCENTAGEPERF, CURRENTPRICE, TOTALPERF, TOTALPERFORMANCEUNIT, TOTALPRICES, TOTALPERFORMANCEPERC, TOTALPERFORMANCEALL, TOTALGAINPERCENTAGE,  TOTALPROFIT, TOTALPROFITPERC;
    String LINE, URLSTRING;
    String[] COMPONENTS;
    private String[][] HISTORICALINFO, HISTORICAL;
    URL URL;
    Scanner SCANNER;
    private Integer COUNT;
    
    private InvestmentController INVESTCONTROL;
    
    public void getCurrentPrice(String code) throws IOException, ApiException {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        ApiKeyAuth auth = (ApiKeyAuth) defaultClient.getAuthentication("ApiKeyAuth");
        auth.setApiKey("OjExODcwODU1MGNkODYwY2Y4MWViZjQxM2FjZTMzY2Iw");

        SecurityApi securityApi = new SecurityApi();

        String identifier = code; 
        String tag = "close_price";

        try {
            BigDecimal RESULT = securityApi.getSecurityDataPointNumber(identifier, tag);
            String RESULT2 = String.valueOf(RESULT);
            CURRENTPRICE = Double.parseDouble(RESULT2);
            System.out.println(CURRENTPRICE);
        } catch (ApiException e) {
            System.err.println("Exception when calling SecurityApi#getSecurityStockPrices");
        }
    }
    
    public void getHistoricalPrices(String code, String freq) throws MalformedURLException, IOException{   
        URLSTRING = "https://api.intrinio.com/prices.csv?identifier="+code+"&start_date=1950-01-01&frequency="+freq+"&sort_order=desc&page_size=90&api_key=OjExODcwODU1MGNkODYwY2Y4MWViZjQxM2FjZTMzY2Iw";
        URL = new URL(URLSTRING);
        
        HISTORICALINFO = new String[90][2];
        COUNT = 0;
        SCANNER = new Scanner(URL.openStream());
  
        while (SCANNER.hasNext()) {
            LINE = SCANNER.nextLine();
            COMPONENTS = LINE.split(",");
            try{
                Double.parseDouble(COMPONENTS[2]);
                HISTORICALINFO[89-COUNT][0] = COMPONENTS[0];
                HISTORICALINFO[89-COUNT][1] = COMPONENTS[4];
                COUNT++;
            }catch(NumberFormatException er){
                System.out.println("NOT DATE");
            }
        }
        SCANNER.close();
        
        HISTORICAL = new String[COUNT][2];
        Integer CHECK = 0;
        for(int i=1; i <= COUNT; i++){
            HISTORICAL[COUNT-i][0] = HISTORICALINFO[89-CHECK][0];
            HISTORICAL[COUNT-i][1] = HISTORICALINFO[89-CHECK][1];
            CHECK++;
        }     
    }
    
    public String PerformanceCalc(String code, String price) throws IOException, ApiException{
        getCurrentPrice(code);
        DecimalFormat df = new DecimalFormat("#.##");
        CURRENTPRICE = Double.valueOf(df.format(CURRENTPRICE));
    
        PERFORMANCE = CURRENTPRICE - Double.parseDouble(price);
        PERFORMANCE = Double.valueOf(df.format(PERFORMANCE));
        
        PERCENTAGEPERF = PERFORMANCE/Double.parseDouble(price) * 100;
        PERCENTAGEPERF = Double.valueOf(df.format(PERCENTAGEPERF));
        
        if(PERFORMANCE > 0){
            PERFORMANCESTRING = "+ " + String.valueOf(PERFORMANCE) + " USD";
            PERCENTAGESTRING = "+ " + String.valueOf(PERCENTAGEPERF) + " %";
        }else{
            PERFORMANCESTRING = String.valueOf(PERFORMANCE) + " USD";
            PERCENTAGESTRING =  String.valueOf(PERCENTAGEPERF) + " %";
        }
        
        return PERFORMANCESTRING;
    } 
    
    public double getAbsolutePerformance(){
        return PERFORMANCE;
    }
    
    public String getTotalPerformance(Integer amount){
        TOTALPERF = PERFORMANCE * amount;
        DecimalFormat df = new DecimalFormat("#.##");
        TOTALPERF = Double.valueOf(df.format(TOTALPERF));
        
        if(TOTALPERF > 0){
            TOTALPERFSTRING = "+ " + String.valueOf(TOTALPERF) + " USD";
        }else{
            TOTALPERFSTRING = String.valueOf(TOTALPERF) + " USD";
        }
        
        return TOTALPERFSTRING;
    }
    
    public void portTotalPerf() throws IOException, ApiException{
        DecimalFormat df = new DecimalFormat("#.##");
        INVESTCONTROL = new InvestmentController();
        TOTALPERFORMANCEUNIT = 0;
        if(INVESTCONTROL.getAllCodes()[0][0].equals("NULL")){
            TOTALPROFIT = 0;
            for(int i=0; i<INVESTCONTROL.getToutLastPerf().length; i++){
                TOTALPROFIT = TOTALPROFIT + Double.parseDouble(INVESTCONTROL.getToutLastPerf()[i]);
            }
            TOTALPROFIT = Double.valueOf(df.format(TOTALPROFIT));            
        }else{
            System.out.println(INVESTCONTROL.getAllCodes().length);
            for(int i = 0; i < INVESTCONTROL.getAllCodes().length; i++){
                PerformanceCalc(INVESTCONTROL.getAllCodes()[i][0], INVESTCONTROL.getAllCodes()[i][1]);
                TOTALPERFORMANCEUNIT = TOTALPERFORMANCEUNIT + (PERFORMANCE*Integer.parseInt(INVESTCONTROL.getAllAmounts()[i]));
            }
            TOTALPERFORMANCEUNIT = Double.valueOf(df.format(TOTALPERFORMANCEUNIT));

            TOTALPRICES = 0;
            for(int i=0; i < INVESTCONTROL.getAllCodes().length; i++){
                TOTALPRICES = TOTALPRICES + (Double.parseDouble(INVESTCONTROL.getAllCodes()[i][1]) * Integer.parseInt(INVESTCONTROL.getAllAmounts()[i]));
            }
            TOTALPERFORMANCEPERC = TOTALPERFORMANCEUNIT/TOTALPRICES*100;
            TOTALPERFORMANCEPERC = Double.valueOf(df.format(TOTALPERFORMANCEPERC));
            System.out.println(TOTALPERFORMANCEUNIT);

            if(!INVESTCONTROL.getToutLastPerf()[0].equals("NULL")){
                TOTALPROFIT = 0;
                TOTALPERFORMANCEALL = TOTALPERFORMANCEUNIT;
                for(int i=0; i<INVESTCONTROL.getToutLastPerf().length; i++){
                    TOTALPERFORMANCEALL = TOTALPERFORMANCEALL + Double.parseDouble(INVESTCONTROL.getToutLastPerf()[i]);
                    TOTALPROFIT = TOTALPROFIT + Double.parseDouble(INVESTCONTROL.getToutLastPerf()[i]);
                }
                TOTALPERFORMANCEALL = Double.valueOf(df.format(TOTALPERFORMANCEALL));
                TOTALPROFIT = Double.valueOf(df.format(TOTALPROFIT));

                TOTALGAINPERCENTAGE = TOTALPERFORMANCEALL/TOTALPRICES*100;
                System.out.println(TOTALPERFORMANCEALL);
                TOTALGAINPERCENTAGE = Double.valueOf(df.format(TOTALGAINPERCENTAGE));
            }
        }
    }
    
    public String getPercentageString(){
        return PERCENTAGESTRING;
    }
    
    public double getTotalPerformanceShare() throws IOException, ApiException{
        portTotalPerf();
        return TOTALPERFORMANCEUNIT;
    }
    
    public double getTotalPerformancePerc() throws IOException, ApiException{
        portTotalPerf();
        return TOTALPERFORMANCEPERC;
    }
    
    public double getTotalPerformanceAll() throws IOException, ApiException{
        portTotalPerf();
        return TOTALPERFORMANCEALL;
    }
    
    public double getTotalGainPercentage() throws IOException, ApiException{
        portTotalPerf();
        return TOTALGAINPERCENTAGE;
    }
    
    public double getTotalProfit() throws IOException, ApiException{
        portTotalPerf();
        return TOTALPROFIT;
    }
    
    public String[][] getHistPrices(String code, String freq) throws IOException{
        getHistoricalPrices(code, freq);
        return HISTORICAL;
    }
    
}
