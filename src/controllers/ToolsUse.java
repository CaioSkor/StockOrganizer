package controllers;

//import Scene.DesignInv;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Arrays;
import java.util.Scanner;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class ToolsUse {
    private Integer POS,INDEX;
    private File REFERENCE;
    private String MINILINE, MINILINE2, CHECK;
    private String[] STRING,SORT, STRING2, LASTPERF;
    
   // private DesignInv DSINV;
    private Integer BOOL;
    
    public Integer FileMeasure(String FILEME, Integer CHOICE) throws FileNotFoundException, IOException{
        LineNumberReader LNR;
        FileReader FIREADER;
        POS = 0;
        REFERENCE = new File(FILEME);
        FIREADER = new FileReader(FILEME);
        LNR = new LineNumberReader(FIREADER); 
        Scanner READER = new Scanner(REFERENCE);
        while (LNR.readLine() != null){
            while (READER.hasNextLine()) {
                MINILINE2 = READER.nextLine();
                STRING2 = MINILINE2.split(",");
                if(CHOICE == 1){
                    if (STRING2[5].equals("000000")){
                        POS++;
                    }
                }else{
                    if (!STRING2[5].equals("000000")){
                        POS++;
                    }
                }
            }   
    	}
        LNR.close();        
        return POS;
    }
    
    
    public String[] TextBoxFiller(String FILEME, String INVESTNAME) throws IOException{
        STRING = new String[5];
        STRING[0] = "A";
        REFERENCE = new File (FILEME);
        Scanner READER = new Scanner (REFERENCE);
        while (READER.hasNextLine()) {
            MINILINE = READER.nextLine();
            STRING = MINILINE.split(",");  
            if (STRING[0].equals(INVESTNAME)){
                break;
            }
        } 
        return STRING;
    }
    
 /*    public void DeletedWindow(Stage MAINWINDOW) throws IOException{
        INDEX = 0;
        DSINV = new DesignInv(MAINWINDOW, INDEX);   
        MAINWINDOW.setScene(DSINV.getScreen());
        MAINWINDOW.setTitle("Deleted Investments");
     }

     public void BackDeletedWindow(Stage MAINWINDOW) throws IOException{
        INDEX = 1;
        DSINV = new DesignInv(MAINWINDOW, INDEX);   
        MAINWINDOW.setScene(DSINV.getScreen());
        MAINWINDOW.setTitle("Investments");
     }*/
     
    public Integer CheckSoldInvestments(String code) throws FileNotFoundException{
        STRING = new String[2];
        STRING[0] = "A";
        BOOL = 0;
        REFERENCE = new File("data/lastperformance.txt");
        Scanner READER = new Scanner (REFERENCE);
        while (READER.hasNextLine()) {
            MINILINE = READER.nextLine();
            LASTPERF = MINILINE.split(",");  
            if (LASTPERF[0].equals(code)){
               BOOL = 1;
               break;
            }
        } 
        return BOOL;
    }
    
    public String getLastPerf(String code) throws FileNotFoundException{
        STRING = new String[2];
        STRING[0] = "A";
        REFERENCE = new File("data/lastperformance.txt");
        Scanner READER = new Scanner (REFERENCE);
        while (READER.hasNextLine()) {
            MINILINE = READER.nextLine();
            LASTPERF = MINILINE.split(",");  
            if (LASTPERF[0].equals(code)){
               break;
            }
        } 
        System.out.println(LASTPERF[1]);
        return LASTPERF[1];
    }
    
    public Integer portfolioStart() throws FileNotFoundException, IOException{
        POS = 0;
        FileReader READER = new FileReader("data/investment.txt");
        LineNumberReader LNR = new LineNumberReader(READER);
        while(LNR.readLine() != null){
            POS++;
        }
        
        FileReader READER2 = new FileReader("data/repeatedInvestments.txt");
        LineNumberReader LNR2 = new LineNumberReader(READER2);
        while(LNR2.readLine() != null){
            POS++;
        }
        
        FileReader READER3 = new FileReader("data/lastperformance.txt");
        LineNumberReader LNR3 = new LineNumberReader(READER3);
        while(LNR3.readLine() != null){
            POS++;
        }
        LNR.close();
        LNR2.close();
        LNR3.close();
        return POS;
    }
    
}
