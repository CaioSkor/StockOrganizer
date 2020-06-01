package controllers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class GetTicker {
    private BufferedReader BUFFEREDREADER;
    private FileReader FIREADER;
    private String FILECONTENT;
    private String[] FILEDATA;      
    private String[] INVNAMES;
    private ToolsUse TOOLS;
    private Integer POS;
    
    public String[] GetTicker(String FILEME, Integer CHOICE) throws FileNotFoundException, IOException{
        TOOLS = new ToolsUse();
        POS = 0;
        FIREADER = new FileReader(FILEME);
        BUFFEREDREADER = new BufferedReader(FIREADER);
        INVNAMES = new String[TOOLS.FileMeasure("data/investment.txt",CHOICE)];

        while((FILECONTENT = BUFFEREDREADER.readLine()) != null) {
            FILEDATA = FILECONTENT.split(",");
            if(CHOICE == 1){
                if(FILEDATA[5].equals("000000")){
                    INVNAMES[POS]= FILEDATA[0];
                    POS++;
                }
            }else{
                if(!FILEDATA[5].equals("000000")){
                    INVNAMES[POS]= FILEDATA[0];
                    POS++;
                }
            }
        }
        FIREADER.close();
        return INVNAMES;
    }
}
