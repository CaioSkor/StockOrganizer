package controllers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Scanner;
import modules.Investment;
import modules.LastPerformance;
/**
 *
 * @author caio
 */
public class InvestmentController {
    private final FileReader FILEREADER, FILEREADER2, FILEREADER3;   
    private final BufferedReader BUFFEREDREADER, BUFFEREDREADER2, BUFFEREDREADER3;
    private String FILECONTENT, FILECONTENT2, FILECONTENT3;
    private String[] FILEDATA, FILEDATA2, FILEDATA3, ALLAMOUNTS, TOUTLASTPERF;
    private String[][] ALLCODES;
    private Integer MEDAMOUNT, REPETITION, POS;
    private Integer[] amounts;
    double[] prices;
    double MEDPRICE;
    private String MEDPRICESTRING, MEDAMOUNTSTRING;
    private final LinkedList<Investment> INVESTIMENTS;
    private LinkedList<Investment> REPEATEDINVESTMENTS;
    private LinkedList<LastPerformance> LASTPERFORMANCE;
    
    private Investment INVESTMENT;
    private PerformanceController PERFORMANCE;
    private LastPerformance LAST;
    
    public InvestmentController() throws IOException{
        FILEREADER = new FileReader("data/investment.txt");
        BUFFEREDREADER = new BufferedReader(FILEREADER);
        INVESTIMENTS = new LinkedList<Investment>();    
        while((FILECONTENT = BUFFEREDREADER.readLine()) != null) {
            FILEDATA = FILECONTENT.split(",");
            INVESTMENT = new Investment(FILEDATA[0], FILEDATA[1], FILEDATA[2], FILEDATA[3], FILEDATA[4], FILEDATA[5]);
            INVESTIMENTS.add(INVESTMENT);
        }
        FILEREADER.close();
        
        FILEREADER2 = new FileReader("data/repeatedInvestments.txt");
        BUFFEREDREADER2 = new BufferedReader(FILEREADER2);
        REPEATEDINVESTMENTS = new LinkedList<Investment>();
        while((FILECONTENT2 = BUFFEREDREADER2.readLine()) != null) {
            FILEDATA2 = FILECONTENT2.split(",");
            INVESTMENT = new Investment(FILEDATA2[0], FILEDATA2[1], FILEDATA2[2], FILEDATA2[3], FILEDATA2[4], FILEDATA2[5]);
            REPEATEDINVESTMENTS.add(INVESTMENT);
        }
        
        FILEREADER3 = new FileReader("data/lastperformance.txt");
        BUFFEREDREADER3 = new BufferedReader(FILEREADER3);
        LASTPERFORMANCE = new LinkedList<LastPerformance>();
        while((FILECONTENT3 = BUFFEREDREADER3.readLine()) != null){
            FILEDATA3 = FILECONTENT3.split(",");
            LAST = new LastPerformance(FILEDATA3[0], FILEDATA3[1]);
            LASTPERFORMANCE.add(LAST);
        }
    }
    
    // CRUD - Create INVESTMENT
    public void createInvestment(String code, String price, String amount, String date, String reason, String deletionDate) throws IOException{
        FileWriter FILEWRITER;
        
        FILEWRITER = new FileWriter("data/investment.txt", true);

        INVESTMENT = new Investment(code, price, amount, date, reason, deletionDate);
        INVESTIMENTS.add(INVESTMENT);
        FILEWRITER.write(INVESTMENT.getCode()+ "," + INVESTMENT.getPrice()+ "," + INVESTMENT.getAmount()+ "," + INVESTMENT.getDate()+ "," + INVESTMENT.getReason() + "," + INVESTMENT.getDeletionDate());
        FILEWRITER.write(System.lineSeparator());
        FILEWRITER.close();
        
        System.out.println("Investment " + INVESTMENT.getCode() + " created.");
    }
    
    public void createRepeatedInvestment(String code, String price, String amount, String date, String reason, String deletionDate) throws IOException{
        FileWriter FILEWRITER;
        
        FILEWRITER = new FileWriter("data/repeatedInvestments.txt", true);

        INVESTMENT = new Investment(code, price, amount, date, reason, deletionDate);
        REPEATEDINVESTMENTS.add(INVESTMENT);
        FILEWRITER.write(INVESTMENT.getCode()+ "," + INVESTMENT.getPrice()+ "," + INVESTMENT.getAmount()+ "," + INVESTMENT.getDate()+ "," + INVESTMENT.getReason() + "," + INVESTMENT.getDeletionDate());
        FILEWRITER.write(System.lineSeparator());
        FILEWRITER.close();
    }
    
    // CRUD - Delete INVESTMENT
    public void deleteInvestment(String code, String price, String amount, String date, String reason, String deletionDate) throws IOException{
        FileWriter fileWriter = new FileWriter("data/investment.txt");
        fileWriter.flush();
        for(int i = 0; i < INVESTIMENTS.size(); i++) {
            if( INVESTIMENTS.get(i).getCode().equals(code) && INVESTIMENTS.get(i).getPrice().equals(price) && INVESTIMENTS.get(i).getAmount().equals(amount) && INVESTIMENTS.get(i).getDate().equals(date) && INVESTIMENTS.get(i).getReason().equals(reason)){
                INVESTIMENTS.get(i).setDeletionDate(deletionDate);
                //INVESTIMENTS.set(i, null);
            }
            String fileContent = INVESTIMENTS.get(i).getCode() + "," + INVESTIMENTS.get(i).getPrice() + "," + INVESTIMENTS.get(i).getAmount() + "," + INVESTIMENTS.get(i).getDate() + "," + INVESTIMENTS.get(i).getReason() + "," + INVESTIMENTS.get(i).getDeletionDate();
            fileWriter.write(fileContent);
            fileWriter.write(System.lineSeparator());
            
        }     
        
        fileWriter.close();

        System.out.println("Investment " + code + " deleted.");
    }
    
    public void recoverDeletedInvestment(String code, String price, String amount, String date, String reason, String deletionDate, String newPrice, String newAmnt, String newDate, String newReason) throws IOException{
        FileWriter fileWriter = new FileWriter("data/investment.txt");
        fileWriter.flush();
        for(int i = 0; i < INVESTIMENTS.size(); i++) {
            if( INVESTIMENTS.get(i).getCode().equals(code) && INVESTIMENTS.get(i).getPrice().equals(price) && INVESTIMENTS.get(i).getAmount().equals(amount) && INVESTIMENTS.get(i).getDate().equals(date) && INVESTIMENTS.get(i).getReason().equals(reason)){
                INVESTIMENTS.get(i).setPrice(newPrice);
                INVESTIMENTS.get(i).setAmount(newAmnt);
                INVESTIMENTS.get(i).setDate(newDate);
                INVESTIMENTS.get(i).setReason(newReason);
                INVESTIMENTS.get(i).setDeletionDate("000000");
            }
            String fileContent = INVESTIMENTS.get(i).getCode()+ "," + INVESTIMENTS.get(i).getPrice()+ "," + INVESTIMENTS.get(i).getAmount()+ "," + INVESTIMENTS.get(i).getDate()+ "," +INVESTIMENTS.get(i).getReason()+ "," +INVESTIMENTS.get(i).getDeletionDate();
            fileWriter.write(fileContent);
            fileWriter.write(System.lineSeparator());
            
        }
        fileWriter.close();
        System.out.println("Investment " + code + "recovered");
        
        FileWriter FILEWRITER = new FileWriter("data/lastperformance.txt");
        FILEWRITER.flush();
        for(int i = 0; i < LASTPERFORMANCE.size(); i++){
            if(LASTPERFORMANCE.get(i).getCode().equals(code)){
                LASTPERFORMANCE.set(i, null);
            }else{
                String STRING = LASTPERFORMANCE.get(i).getCode()+","+LASTPERFORMANCE.get(i).getPerformance();
                FILEWRITER.write(STRING);
                FILEWRITER.write(System.lineSeparator());
            }
        }
        FILEWRITER.close();
    }
    
    public void updateInvestment(String code, String price, String amount, String date, String reason, String newPrice, String newAmnt, String newDate, String newReason, String deldate) throws IOException{
        DecimalFormat df = new DecimalFormat("#.##");
        Integer SURE = 0;
        for(int i = 0; i < REPEATEDINVESTMENTS.size(); i++){
            if(REPEATEDINVESTMENTS.get(i).getCode().equals(code)){
                SURE++;
            }
        }
        
        if(SURE>0){
            createRepeatedInvestment(code, newPrice, newAmnt, newDate, newReason, deldate);
        }else{
            createRepeatedInvestment(code, newPrice, newAmnt, newDate, newReason, deldate);
            createRepeatedInvestment(code, price, amount, date, reason, deldate);
        }
        FileWriter fileWriter = new FileWriter("data/investment.txt");
        fileWriter.flush();
        
        REPETITION = 0;
        MEDAMOUNT = 0;
        MEDPRICE = 0;
        for(int i = 0; i < REPEATEDINVESTMENTS.size(); i++){
            if(REPEATEDINVESTMENTS.get(i).getCode().equals(code)){
                REPETITION++;
            }       
        }
        amounts = new Integer[REPETITION];
        prices = new double[REPETITION];
        Integer INDEX = 0;
        for(int i = 0; i < REPEATEDINVESTMENTS.size(); i++){
            if(REPEATEDINVESTMENTS.get(i).getCode().equals(code)){
                amounts[INDEX] = Integer.parseInt(REPEATEDINVESTMENTS.get(i).getAmount());
                prices[INDEX] = Double.parseDouble(REPEATEDINVESTMENTS.get(i).getPrice());
                INDEX++;
            }
        }
        
        double TOTAL = 0;
        for(int i = 0; i < INDEX; i++){
            TOTAL = TOTAL + amounts[i]*prices[i];
            MEDAMOUNT = MEDAMOUNT + amounts[i];
        }
        MEDPRICE = TOTAL / MEDAMOUNT;
        MEDPRICE = Double.valueOf(df.format(MEDPRICE));
        MEDPRICESTRING = String.valueOf(MEDPRICE);
        MEDAMOUNTSTRING = String.valueOf(MEDAMOUNT);
        
        for(int i = 0; i < INVESTIMENTS.size(); i++) {
            if( INVESTIMENTS.get(i).getCode().equals(code) && INVESTIMENTS.get(i).getPrice().equals(price) && INVESTIMENTS.get(i).getAmount().equals(amount) && INVESTIMENTS.get(i).getDate().equals(date) && INVESTIMENTS.get(i).getReason().equals(reason) && INVESTIMENTS.get(i).getDeletionDate().equals(deldate)){
                INVESTIMENTS.get(i).setPrice(MEDPRICESTRING);
                INVESTIMENTS.get(i).setAmount(MEDAMOUNTSTRING);
                INVESTIMENTS.get(i).setDate(newDate);
                INVESTIMENTS.get(i).setReason(newReason);
                INVESTIMENTS.get(i).setDeletionDate(deldate);
            }
            fileWriter.write(INVESTIMENTS.get(i).getCode()+ "," + INVESTIMENTS.get(i).getPrice()+ "," +INVESTIMENTS.get(i).getAmount()+ "," +INVESTIMENTS.get(i).getDate()+ ","+ INVESTIMENTS.get(i).getReason()+ "," + INVESTIMENTS.get(i).getDeletionDate());
            fileWriter.write(System.lineSeparator());
        }
        fileWriter.close();
        
        System.out.println("Investment " + code + " updated.");
    }
    
    public void writeLastPerformance(String code, String sellprice, String price, String amount) throws IOException{
        DecimalFormat df = new DecimalFormat("#.##");
        FileWriter FILEWRITER;
        FILEWRITER = new FileWriter("data/lastperformance.txt", true);
            

        double PERF;
        PERF = (Double.parseDouble(sellprice) - Double.parseDouble(price))* Integer.parseInt(amount);
        PERF = Double.valueOf(df.format(PERF));
        String performance = String.valueOf(PERF);

        LAST = new LastPerformance(code, performance);
        LASTPERFORMANCE.add(LAST);
        FILEWRITER.write(LAST.getCode()+","+LAST.getPerformance());
        FILEWRITER.write(System.lineSeparator());
        FILEWRITER.close();
    }
    
    public void updateLastPerformance(String code, String price) throws IOException{
        FileWriter FILEWRITER;
        FILEWRITER = new FileWriter("data/lastperformance.txt");
        FILEWRITER.flush();
        Integer CHECK = 0;
        for(int i=0; i < LASTPERFORMANCE.size(); i++){
            if(LASTPERFORMANCE.get(i).getCode().equals(code)){
                CHECK = 1;
            }
            if(CHECK == 1){
                LASTPERFORMANCE.get(i).setPerformance(price);
            }
            
            FILEWRITER.write(LASTPERFORMANCE.get(i).getCode()+","+LASTPERFORMANCE.get(i).getPerformance());
            FILEWRITER.write(System.lineSeparator());
        }
        FILEWRITER.close();
        System.out.println("Deleted investment "+code+" edited");
    }
    
    public void deleteAll() throws IOException{
        FileWriter DELINVESTMENTS;
        DELINVESTMENTS = new FileWriter("data/investment.txt");
        
        if(INVESTIMENTS.size() == 0){
            DELINVESTMENTS.close();
        }else{
            for(int i=0; i < INVESTIMENTS.size(); i++){
                INVESTIMENTS.set(i, null);
            }
            DELINVESTMENTS.close();
        }
        
        FileWriter DELREPEATED;
        DELREPEATED = new FileWriter("data/repeatedInvestments.txt");
        
        if(REPEATEDINVESTMENTS.size() == 0){
            DELREPEATED.close();
        }else{
            for(int i=0; i < REPEATEDINVESTMENTS.size(); i++){
                REPEATEDINVESTMENTS.set(i, null);
            }
            DELREPEATED.close();
        }
        
        FileWriter DELLAST;
        DELLAST = new  FileWriter("data/lastperformance.txt");
        if(LASTPERFORMANCE.size() == 0){
            DELLAST.close();
        }else{
            for(int i=0; i < LASTPERFORMANCE.size(); i++){
                LASTPERFORMANCE.set(i, null);
            }
            DELLAST.close();
        }
    }
    
    public void allInfo(){
        POS = 0;
        for(int i = 0; i < INVESTIMENTS.size(); i++){
            if(INVESTIMENTS.get(i).getDeletionDate().equals("000000")){
                POS++;
            }
        }
        
        if(POS==0){
            ALLCODES = new String[1][1];
            ALLAMOUNTS = new String[1];
            
            ALLCODES[0][0] = "NULL";
            ALLAMOUNTS[0] = "NULL";
        }else{
            ALLCODES = new String[POS][2];
            ALLAMOUNTS = new String[POS];
            Integer INDEX = 0;
            for(int i = 0; i < INVESTIMENTS.size(); i++ ){
                if(INVESTIMENTS.get(i).getDeletionDate().equals("000000")){
                    ALLCODES[INDEX][0] = INVESTIMENTS.get(i).getCode();     
                    ALLCODES[INDEX][1] = INVESTIMENTS.get(i).getPrice();
                    ALLAMOUNTS[INDEX] = INVESTIMENTS.get(i).getAmount();
                    INDEX++;
                }
            }
        }
        if(LASTPERFORMANCE.size() == 0){
            TOUTLASTPERF = new String[1];
            TOUTLASTPERF[0] = "NULL";
        }else{
            TOUTLASTPERF = new String[LASTPERFORMANCE.size()];
            for(int i=0; i < LASTPERFORMANCE.size(); i++){
                TOUTLASTPERF[i] = LASTPERFORMANCE.get(i).getPerformance();
            }
        }   
    }    
       
    public LinkedList<String> readNasdaqTickers() throws IOException{
        LinkedList<String> TICKERS = new LinkedList<String>();
        FileReader TEXTFILEPATH = new FileReader("./data/nasdaqlisted.txt");
        Scanner TEXTFILE = new Scanner(TEXTFILEPATH);
        while(TEXTFILE.hasNext()) {
            String[] DATA = TEXTFILE.nextLine().split("\\|");
            TICKERS.add(DATA[0]);
        }
        TEXTFILE.close();
        TEXTFILEPATH.close();
        
        return TICKERS;
    }
    
    public String getLastInvestment(){
        String LASTCODE;
        LASTCODE = INVESTIMENTS.get(INVESTIMENTS.size()-1).getCode();
        return LASTCODE;
    }
    
    public LinkedList<String> readNyseTickers() throws FileNotFoundException, IOException {
        LinkedList<String> TICKERS = new LinkedList<String>();
        FileReader TEXTFILEPATH = new FileReader("data/nyse-listed.txt");
        Scanner TEXTFILE = new Scanner(TEXTFILEPATH);
        while(TEXTFILE.hasNext()){
            String[] DATA = TEXTFILE.nextLine().split(",");
            String[] CHARACT = DATA[0].split("");
            if(CHARACT.length >= 3){
                int check = CHARACT.length - 2;
                if(CHARACT[check].equals("$") || CHARACT[check].equals(".") || CHARACT[(CHARACT.length - 1)].equals("$")){
                }else{
                    TICKERS.add(DATA[0]);
                }
            }else{
                TICKERS.add(DATA[0]);
            }
        }
        TEXTFILE.close();
        TEXTFILEPATH.close();
        
        return TICKERS;
    }
    
    public LinkedList getLinkedListINVESTIMENTS() {
        return INVESTIMENTS;
    }
    
    public LinkedList getLinkedListLASTPERFORMANCE(){
        return LASTPERFORMANCE;
    }
    
    public String[][] getAllCodes(){
        allInfo();
        return ALLCODES;
    }
    
    public String[] getAllAmounts(){
        allInfo();
        return ALLAMOUNTS;
    }    
    
    public String[] getToutLastPerf(){
        allInfo();
        return TOUTLASTPERF;
    }
    
    public String getNewLastPerformance(String code, String price){
        String NEWLASTPERF = "";
        String PRICE = "";
        String AMOUNT = "";
        for(int i = 0; i < INVESTIMENTS.size(); i++){
            if(INVESTIMENTS.get(i).getCode().equals(code)){
                PRICE = INVESTIMENTS.get(i).getPrice();
                AMOUNT = INVESTIMENTS.get(i).getAmount();
                double NEWPRICE = Double.parseDouble(price);
                double OLDPRICE = Double.parseDouble(PRICE);
                double QUANTITY = Double.parseDouble(AMOUNT);
                double LASTPERF = (NEWPRICE - OLDPRICE)*QUANTITY;
                NEWLASTPERF = String.valueOf(LASTPERF);
                System.out.println(NEWLASTPERF);
            }
        }
        return NEWLASTPERF;
    }

}