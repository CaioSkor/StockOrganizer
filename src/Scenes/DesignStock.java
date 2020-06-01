/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scenes;

import com.intrinio.invoker.ApiException;
import controllers.PerformanceController;
import controllers.ToolsUse;
import java.io.IOException;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 *
 * @author caio
 */
public class DesignStock {
    private Text CODETXT, PRICETXT, AMNTTXT, DATETXT, REATXT, CODE, PRICE, AMOUNT, DATE, REASON;
    private Text TOTALPERFTXT, PERCENTAGEPERFTXT, PERSTOCKPERFTXT, TOTALPERF, PERCENTAGEPERF, PERSTOCKPERF;
    private Button DELETEBTN, SOLDBTN, GRAPHBTN;
    
    private MyFont MYFONT;
    private ToolsUse TOOLS;
    private PerformanceController PERFCONTROL;
    
    public GridPane DesignStock(GridPane GRID, String TICKER) throws IOException, ApiException{
        MYFONT = new MyFont();
        TOOLS = new ToolsUse();
        PERFCONTROL = new PerformanceController();
             
        CODETXT = new Text("Ticker");
        CODETXT.setFont(MYFONT.getOswaldRegular());
        
        PRICETXT = new Text("Price");
        PRICETXT.setFont(MYFONT.getOswaldRegular());
        
        AMNTTXT = new Text("Amount");
        AMNTTXT.setFont(MYFONT.getOswaldRegular());
        
        DATETXT = new Text("Date");
        DATETXT.setFont(MYFONT.getOswaldRegular());
        
        REATXT = new Text("Reason");
        REATXT.setFont(MYFONT.getOswaldRegular());
        
        CODE = new Text(TOOLS.TextBoxFiller("data/investment.txt", TICKER)[0]);
        CODE.setFont(MYFONT.getOswaldRegular());
        CODE.setTextAlignment(TextAlignment.RIGHT);
        
        PRICE = new Text(TOOLS.TextBoxFiller("data/investment.txt", TICKER)[1]);
        PRICE.setFont(MYFONT.getOswaldRegular());
        PRICE.setTextAlignment(TextAlignment.RIGHT);
        
        AMOUNT = new Text(TOOLS.TextBoxFiller("data/investment.txt", TICKER)[2]);
        AMOUNT.setFont(MYFONT.getOswaldRegular());
        AMOUNT.setTextAlignment(TextAlignment.RIGHT);
        
        DATE = new Text(TOOLS.TextBoxFiller("data/investment.txt", TICKER)[3]);
        DATE.setFont(MYFONT.getOswaldRegular());
        DATE.setTextAlignment(TextAlignment.RIGHT);
        
        REASON = new Text(TOOLS.TextBoxFiller("data/investment.txt", TICKER)[4]);
        REASON.setFont(MYFONT.getOswaldRegular());
        DATE.setTextAlignment(TextAlignment.RIGHT);
                
        TOTALPERFTXT = new Text("Total Performance");
        TOTALPERFTXT.setFont(MYFONT.getOswaldRegular());
        
        PERCENTAGEPERFTXT = new Text("Percentage Performance");
        PERCENTAGEPERFTXT.setFont(MYFONT.getOswaldRegular());
        
        PERSTOCKPERFTXT = new Text("Per Stock Performance");
        PERSTOCKPERFTXT.setFont(MYFONT.getOswaldRegular());
        
        PERSTOCKPERF = new Text(PERFCONTROL.PerformanceCalc(TICKER, TOOLS.TextBoxFiller("data/investment.txt", TICKER)[1]));
        PERSTOCKPERF.setFont(MYFONT.getOswaldRegular());
        PERSTOCKPERF.setTextAlignment(TextAlignment.RIGHT);
        
        TOTALPERF = new Text(PERFCONTROL.getTotalPerformance(Integer.parseInt(TOOLS.TextBoxFiller("data/investment.txt", TICKER)[2])));
        TOTALPERF.setFont(MYFONT.getOswaldRegular());
        TOTALPERF.setTextAlignment(TextAlignment.RIGHT);
        
        PERCENTAGEPERF = new Text(PERFCONTROL.getPercentageString());
        PERCENTAGEPERF.setFont(MYFONT.getOswaldRegular());
        PERCENTAGEPERF.setTextAlignment(TextAlignment.RIGHT);
        
        if(PERFCONTROL.getAbsolutePerformance() < 0){
            PERSTOCKPERF.setFill(MYFONT.getTitleColor());
            TOTALPERF.setFill(MYFONT.getTitleColor());
            PERCENTAGEPERF.setFill(MYFONT.getTitleColor());
        }else{
            PERSTOCKPERF.setFill(Color.CHARTREUSE);
            TOTALPERF.setFill(Color.CHARTREUSE);
            PERCENTAGEPERF.setFill(Color.CHARTREUSE);
        }
        
        DELETEBTN = new Button("Delete");
        DELETEBTN.setFont(MYFONT.getOswaldButton());
        DELETEBTN.getStyleClass().add("submitButton");
        
        SOLDBTN = new Button("Sold");
        SOLDBTN.setFont(MYFONT.getOswaldButton());
        SOLDBTN.getStyleClass().add("submitButton");
        
        GRAPHBTN = new Button("Graph");
        GRAPHBTN.setFont(MYFONT.getOswaldButton());
        GRAPHBTN.getStyleClass().add("submitButton");
        
        GRID.add(CODETXT, 0, 0);
        GRID.add(PRICETXT, 0, 1);
        GRID.add(AMNTTXT, 0, 2);
        GRID.add(DATETXT, 0, 3);
        GRID.add(REATXT, 0, 4);
        GRID.add(CODE, 2, 0);
        GRID.add(PRICE, 2, 1);
        GRID.add(AMOUNT, 2, 2);
        GRID.add(DATE, 2, 3);
        GRID.add(REASON, 2, 4);
        
        GRID.add(PERSTOCKPERFTXT, 0, 6);
        GRID.add(TOTALPERFTXT, 0, 7);
        GRID.add(PERCENTAGEPERFTXT, 0, 8);
        
        GRID.add(PERSTOCKPERF, 2, 6);
        GRID.add(TOTALPERF, 2, 7);
        GRID.add(PERCENTAGEPERF, 2, 8);
        
        GRID.add(DELETEBTN, 3, 8);
        GRID.add(SOLDBTN, 3, 7);
        GRID.add(GRAPHBTN, 3, 0);
        
        GRID.setVgap(15);
        GRID.setHgap(25);
        
        GRID.setPadding(new Insets(0, 0, 50, 65));
        
        return GRID;
    }
    
    public GridPane DesignDeletedStock(Stage MAINWINDOW, GridPane GRID, String TICKER){
        return GRID;
    }
}
