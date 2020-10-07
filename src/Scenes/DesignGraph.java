/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scenes;

import WindowManagement.GridPaneManagement;
import WindowManagement.TopManagement;
import com.intrinio.invoker.ApiException;
import controllers.PerformanceController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author caio
 */
public class DesignGraph {
    Text TITLE, CHOICE;
    HBox BOTTOM, MID;
    GridPane MIDCHOICE, MIDGRAPH, TOP;
    Button BACKBTN;
    ComboBox FREQUENCY;
    CategoryAxis XAXIS;
    NumberAxis YAXIS;
    LineChart<String, Number> LINECHART;
    XYChart.Series SERIES;
    String[][] HISTORICAL;
    String[] DATE;
    BorderPane LAYOUT;
    Scene ENTRANCE;
    
    private DesignStock DSSTOCK;
    private TopManagement TOPMANAGE;
    private MyFont MYFONT;
    private PerformanceController PERF;
    
    public GridPane DesignGraph(GridPane GRID, VBox TOP, String TICKER, boolean DELETED) throws IOException, ApiException{
        TOPMANAGE = new TopManagement();
        PERF = new PerformanceController();
        MYFONT = new MyFont();
        DSSTOCK = new DesignStock();
        
        GRID.setPadding(new Insets(0, 50, 50, 60));
        
        MIDGRAPH = new GridPane();
        MIDGRAPH.setHgap(15);
        MIDGRAPH.setVgap(10);
        MIDGRAPH.setAlignment(Pos.CENTER);
        MIDGRAPH.setMaxWidth(380);
        
        CHOICE = new Text();
        CHOICE.setText("Choose graph frequency");
        CHOICE.setFont(MYFONT.getOswaldRegular());
        CHOICE.setFill(Color.GRAY);
        
        ObservableList<String> OPTIONS = FXCollections.observableArrayList("daily", "weekly", "monthly", "quarterly", "yearly");
        FREQUENCY = new ComboBox();
        FREQUENCY.setItems(OPTIONS);
        FREQUENCY.setOnAction(event ->{
            
            MIDGRAPH.getChildren().clear();
            try {
                SERIES = new XYChart.Series();
                SERIES.setName("line");
                
                String freq = (String) FREQUENCY.getValue();
                HISTORICAL = PERF.getHistPrices(TICKER, freq);
                
                // Setting upper and lower bounds and adding to series
                Integer i = 0;
                Double UPPER = Double.valueOf(HISTORICAL[0][0]);
                Double LOWER = Double.valueOf(HISTORICAL[0][0]);
                while(i < HISTORICAL.length){
                    // adding to series
                     SERIES.getData().add(new XYChart.Data(HISTORICAL[i][1],Double.parseDouble(HISTORICAL[i][0])));
                    // Find the maximum vlaue
                    if(UPPER < Double.valueOf(HISTORICAL[i][0])){
                        UPPER = Double.valueOf(HISTORICAL[i][0]);
                    }
                    // Find the minimum value
                    else if(LOWER > Double.valueOf(HISTORICAL[i][0])){
                        LOWER = Double.valueOf(HISTORICAL[i][0]);
                    }
                    i++;
                }
                // Set maximum and minimum on graph
                UPPER = UPPER + (UPPER*10/100);
                LOWER = LOWER - (LOWER*15/100);
               
                XAXIS = new CategoryAxis();
                YAXIS = new NumberAxis(LOWER, UPPER, 5);

                YAXIS.setUpperBound(UPPER);
                YAXIS.setLowerBound(LOWER);
                
                LINECHART = new LineChart<String, Number>(XAXIS, YAXIS);
                LINECHART.getData().add(SERIES);
                LINECHART.setCreateSymbols(false);
                LINECHART.setLegendVisible(false);
                
                Node line = SERIES.getNode().lookup(".SERIES-SERIES-area-line;");
                Integer LENGTH = HISTORICAL.length -1;
                if((Double.valueOf(HISTORICAL[0][0]) - Double.valueOf(HISTORICAL[LENGTH][0])) > 0){
                    SERIES.getNode().setStyle("-fx-stroke: #cc0000ff;");
                }else{
                    SERIES.getNode().setStyle("-fx-stroke: #7fff00;");
                }
                MIDGRAPH.add(LINECHART, 0, 0);   
            } catch (IOException ex) {
                Logger.getLogger(DesignGraph.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
 //       MIDCHOICE.add(FREQUENCY, 0, 0);
        
        BACKBTN = new Button();
        BACKBTN.setText("BACK");
        BACKBTN.getStyleClass().add("submitButton");
        BACKBTN.setFont(MYFONT.getOswaldButton());
        BACKBTN.setOnAction(event ->{
            String TOPTITLE = "Investment: " + TICKER;
            GRID.getChildren().clear();
            TOPMANAGE.ChangeTop(TOP, TOPTITLE);
            if(DELETED){
                try {
                DSSTOCK.DesignStock(GRID, TICKER, TOP);
                } catch (IOException ex) {
                    Logger.getLogger(DesignGraph.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ApiException ex) {
                    Logger.getLogger(DesignGraph.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                try {
                    DSSTOCK.DesignDeletedStock(GRID, TICKER, TOP);
                } catch (IOException ex) {
                    Logger.getLogger(DesignGraph.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        GRID.add(CHOICE, 0, 0);
        GRID.add(FREQUENCY, 0, 1);
        GRID.add(MIDGRAPH, 0, 2);
        GRID.add(BACKBTN, 0, 3);
        
        return GRID; 
    }
}