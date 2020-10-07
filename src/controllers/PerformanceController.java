
package controllers;

import com.intrinio.api.SecurityApi;
import com.intrinio.invoker.ApiClient;
import com.intrinio.invoker.ApiException;
import com.intrinio.invoker.Configuration;
import com.intrinio.invoker.auth.ApiKeyAuth;
import com.intrinio.models.ApiResponseSecurityStockPrices;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.LocalDate;


/**
 *
 * @author Caio Skornicki
 */
public class PerformanceController {
    private String PERFORMANCESTRING, TOTALPERFSTRING, PERCENTAGESTRING; 
    private double PERFORMANCE, PERCENTAGEPERF, CURRENTPRICE, TOTALPERF, TOTALPERFORMANCEUNIT, TOTALPRICES, TOTALPERFORMANCEPERC, TOTALPERFORMANCEALL, TOTALGAINPERCENTAGE,  TOTALPROFIT;
    String LINE, URLSTRING;
    String[] COMPONENTS;
    private String[][] HISTORICALINFO;
    URL URL;
    Scanner SCANNER;
    private Integer COUNT;
    private DecimalFormat DF; 
    private StringBuilder BUILDER;
    private ToolsUse TOOLS;
    
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
        URLSTRING = "https://api-v2.intrinio.com/securities/"+code+"/prices?&frequency="+freq+"&api_key=OjExODcwODU1MGNkODYwY2Y4MWViZjQxM2FjZTMzY2Iw";
        
        InputStream INPUT = new URL(URLSTRING).openStream();
        BufferedReader READER = new BufferedReader(new InputStreamReader(INPUT, Charset.forName("UTF-8")));
        BUILDER = new StringBuilder();
        int CP;
        
        
        while ((CP = READER.read()) != -1) {
            BUILDER.append((char) CP);
        }
        
        String JSONTEXT = BUILDER.toString();
        JSONObject JSON = new JSONObject(JSONTEXT);
        
          
        JSONArray ARRAY = JSON.getJSONArray("stock_prices");
        HISTORICALINFO = new String[ARRAY.length()][2];
        COUNT = 0;
        System.out.println(ARRAY.length());
        for (int i = 0; i < ARRAY.length(); i++) {
            System.out.println(COUNT);
            HISTORICALINFO[ARRAY.length()-COUNT-1][0] = String.valueOf(ARRAY.getJSONObject(i).getDouble("close"));
            HISTORICALINFO[ARRAY.length()-COUNT-1][1] = ARRAY.getJSONObject(i).getString("date");
            COUNT++;
        }
        
        INPUT.close();
    }
    
   
    
    public String PerformanceCalc(String code, String price) throws IOException, ApiException{
        getCurrentPrice(code);
        DF = new DecimalFormat("#.##");
        
        try{
            CURRENTPRICE = Double.valueOf(DF.format(CURRENTPRICE));
        }catch (Exception e){
            DF = new DecimalFormat("#.##");
            CURRENTPRICE = Double.valueOf(DF.format(CURRENTPRICE));
        }
    
        PERFORMANCE = CURRENTPRICE - Double.parseDouble(price);
        PERFORMANCE = Double.valueOf(DF.format(PERFORMANCE));
        
        PERCENTAGEPERF = PERFORMANCE/Double.parseDouble(price) * 100;
        PERCENTAGEPERF = Double.valueOf(DF.format(PERCENTAGEPERF));
        
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
        DF = new DecimalFormat("#,##");
        try{
            TOTALPERF = Double.valueOf(DF.format(TOTALPERF));
        }catch (Exception e){
            DF = new DecimalFormat("#.##");
            TOTALPERF = Double.valueOf(DF.format(TOTALPERF));
        }

        
        if(TOTALPERF > 0){
            TOTALPERFSTRING = "+ " + String.valueOf(TOTALPERF) + " USD";
        }else{
            TOTALPERFSTRING = String.valueOf(TOTALPERF) + " USD";
        }
        
        return TOTALPERFSTRING;
    }
    
    public void portTotalPerf() throws IOException, ApiException{
        DF = new DecimalFormat("#,##");
        INVESTCONTROL = new InvestmentController();
        TOTALPERFORMANCEUNIT = 0;
        
        TOOLS = new ToolsUse();
        
        if(INVESTCONTROL.getAllCodes()[0][0].equals("NULL")){
            TOTALPROFIT = 0;
            for(int i=0; i<INVESTCONTROL.getToutLastPerf().length; i++){
                TOTALPROFIT = TOTALPROFIT + Double.parseDouble(INVESTCONTROL.getToutLastPerf()[i]);
            }
            try{
                TOTALPROFIT = Double.valueOf(DF.format(TOTALPERF));
            }catch (Exception e){
                DF = new DecimalFormat("#.##");
                TOTALPROFIT = Double.valueOf(DF.format(TOTALPERF));
            }          
        }else{
            System.out.println(INVESTCONTROL.getAllCodes().length);
            for(int i = 0; i < INVESTCONTROL.getAllCodes().length; i++){
                PerformanceCalc(INVESTCONTROL.getAllCodes()[i][0], INVESTCONTROL.getAllCodes()[i][1]);
                TOTALPERFORMANCEUNIT = TOTALPERFORMANCEUNIT + (PERFORMANCE*Integer.parseInt(INVESTCONTROL.getAllAmounts()[i]));
            }
            try{
                TOTALPERFORMANCEUNIT = Double.valueOf(DF.format(TOTALPERFORMANCEUNIT));
            }catch (Exception e){
                DF = new DecimalFormat("#.##");
                TOTALPERFORMANCEUNIT = Double.valueOf(DF.format(TOTALPERFORMANCEUNIT));
            }   
            
            TOTALPRICES = 0;
            for(int i=0; i < INVESTCONTROL.getAllCodes().length; i++){
                TOTALPRICES = TOTALPRICES + (Double.parseDouble(INVESTCONTROL.getAllCodes()[i][1]) * Integer.parseInt(INVESTCONTROL.getAllAmounts()[i]));
            }
            TOTALPERFORMANCEPERC = TOTALPERFORMANCEUNIT/TOTALPRICES*100;
            try{
                TOTALPERFORMANCEPERC = Double.valueOf(DF.format(TOTALPERFORMANCEPERC));
            }catch (Exception e){
                DF = new DecimalFormat("#.##");
                TOTALPERFORMANCEPERC = Double.valueOf(DF.format(TOTALPERFORMANCEPERC));
            }  
            System.out.println(TOTALPERFORMANCEUNIT);
            System.out.println("AAAA" + TOOLS.FileMeasure("data/investment.txt", 0));
            if(!INVESTCONTROL.getToutLastPerf()[0].equals("NULL")){
                TOTALPROFIT = 0;
                TOTALPERFORMANCEALL = TOTALPERFORMANCEUNIT;
                System.out.println("ALALALALU");
                for(int i=0; i<INVESTCONTROL.getToutLastPerf().length; i++){
                    TOTALPERFORMANCEALL = TOTALPERFORMANCEALL + Double.parseDouble(INVESTCONTROL.getToutLastPerf()[i]);
                    TOTALPROFIT = TOTALPROFIT + Double.parseDouble(INVESTCONTROL.getToutLastPerf()[i]);
                }
                
                try{
                    TOTALPERFORMANCEALL = Double.valueOf(DF.format(TOTALPERFORMANCEALL));
                    TOTALPROFIT = Double.valueOf(DF.format(TOTALPROFIT));
                }catch (Exception e){
                    DF = new DecimalFormat("#.##");
                    TOTALPERFORMANCEALL = Double.valueOf(DF.format(TOTALPERFORMANCEALL));
                    TOTALPROFIT = Double.valueOf(DF.format(TOTALPROFIT));
                }  
                

                TOTALGAINPERCENTAGE = TOTALPERFORMANCEALL/TOTALPRICES*100;
                System.out.println(TOTALPERFORMANCEALL);
                try{
                    TOTALGAINPERCENTAGE = Double.valueOf(DF.format(TOTALGAINPERCENTAGE));
                }catch (Exception e){
                    DF = new DecimalFormat("#.##");
                    TOTALGAINPERCENTAGE = Double.valueOf(DF.format(TOTALGAINPERCENTAGE));
                }    
                
                System.out.println("AAAA" + TOOLS.FileMeasure("data/investment.txt", 0));
            }else if(TOOLS.FileMeasure("data/investment.txt", 0) >=0){
                System.out.println("AAAA" + TOOLS.FileMeasure("data/investment.txt", 0));
                DF = new DecimalFormat("#.##");
                TOTALPERFORMANCEALL = TOTALPERFORMANCEUNIT;
                System.out.println("ALALALALA");
                TOTALPERFORMANCEALL = Double.valueOf(DF.format(TOTALPERFORMANCEALL));
                TOTALGAINPERCENTAGE = TOTALPERFORMANCEUNIT/TOTALPRICES*100;
                TOTALGAINPERCENTAGE = Double.valueOf(DF.format(TOTALGAINPERCENTAGE));
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
        return HISTORICALINFO;
    }
    
}