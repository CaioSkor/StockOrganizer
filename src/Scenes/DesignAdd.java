/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scenes;

import WindowManagement.DesignAddManagement;
import WindowManagement.TopManagement;
import com.intrinio.invoker.ApiException;
import controllers.InvestmentController;
import controllers.ToolsUse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author caio
 */
public class DesignAdd {
    private Text CODETXT, PRICETXT, AMNTTXT, DATETXT, REATXT, TITLE, MESSAGE;
    private TextField PRC, AMNT, MYDATE, REASON;
    private String[]/* MSG*/ FIELDSINFO /*TEXTFIELDS FORMATMSG*/;
    private Button SUBBTN;
    private RadioButton NYSE, NASDAQ, CONTINUE, ADDANOTHER;
    private ToggleGroup GROUP;
    private HBox EXCHANGES;
    private VBox CHOICES;
    private ComboBox COMBO;
    
    private InvestmentController INVESTCONTROL;
    private MyFont MYFONT;
    private ToolsUse TOOLS;
    private DesignAddManagement MANAGE;
    private TopManagement TOPMANAGE;
    private Design DESIGN;
    private DesignStock DSSTOCK;

    public GridPane DesignAdd(GridPane GRID, Stage MAINWINDOW, VBox TOP) throws IOException{
        DESIGN = new Design();
        INVESTCONTROL = new InvestmentController();
        MYFONT = new MyFont();
        MANAGE = new DesignAddManagement();
        
        GRID.setVgap(30);
        GRID.setHgap(40);
        GRID.setPadding(new Insets(0, 50, 50, 75));
        
        EXCHANGES = new HBox(5);
        EXCHANGES.setPadding(new Insets(5, 0, 0, 0));
        
        CODETXT = new Text("Exchange");
        CODETXT.setFont(MYFONT.getOswaldRegular());
        
        PRICETXT = new Text("Price");
        PRICETXT.setFont(MYFONT.getOswaldRegular());
        
        AMNTTXT = new Text("Amount");
        AMNTTXT.setFont(MYFONT.getOswaldRegular());
        
        DATETXT = new Text("Date");
        DATETXT.setFont(MYFONT.getOswaldRegular());
        
        REATXT = new Text("Reason");
        REATXT.setFont(MYFONT.getOswaldRegular());
        
        PRC = new TextField();
        PRC.setFont(MYFONT.getOswaldButton());
        PRC.getStyleClass().add("submitTextfields");
        
        AMNT = new TextField();
        AMNT.setFont(MYFONT.getOswaldButton());
        AMNT.getStyleClass().add("submitTextfields");
        
        MYDATE = new TextField();
        MYDATE.setFont(MYFONT.getOswaldButton());
        MYDATE.getStyleClass().add("submitTextfields");
        
        REASON = new TextField();
        REASON.setFont(MYFONT.getOswaldButton());
        REASON.getStyleClass().add("submitTextfields");
        
        GRID.add(EXCHANGES, 1, 1);
        
        COMBO = new ComboBox();
               
        GROUP = new ToggleGroup();
        
        NYSE = new RadioButton("NYSE");
        NYSE.setFont(MYFONT.getOswaldButton());
        NYSE.setToggleGroup(GROUP);
        NYSE.setOnAction(e->{
           EXCHANGES.getChildren().clear();
           CODETXT.setText("Ticker    ");
            try {
                COMBO.setItems(FXCollections.observableList(INVESTCONTROL.readNyseTickers()));
            } catch (IOException ex) {
                Logger.getLogger(DesignAdd.class.getName()).log(Level.SEVERE, null, ex);
            }
            GRID.add(COMBO, 1, 1);
        });
        NYSE.getStyleClass().add("radioButtons");
        
        NASDAQ = new RadioButton("NASDAQ");
        NASDAQ.setFont(MYFONT.getOswaldButton());
        NASDAQ.setToggleGroup(GROUP);
        NASDAQ.setOnAction(e->{
           EXCHANGES.getChildren().clear();
           CODETXT.setText("Ticker    ");
            try {
                COMBO.setItems(FXCollections.observableList(INVESTCONTROL.readNasdaqTickers()));
            } catch (IOException ex) {
                Logger.getLogger(DesignAdd.class.getName()).log(Level.SEVERE, null, ex);
            }
            GRID.add(COMBO, 1, 1);
        });
        NASDAQ.getStyleClass().add("radioButtons");
        
        FIELDSINFO = new String[5];
        SUBBTN = new Button("Submit");
        SUBBTN.setFont(MYFONT.getOswaldButton());
        SUBBTN.getStyleClass().add("submitButton");
        SUBBTN.setOnAction(e->{
            String TICKER = COMBO.getSelectionModel().getSelectedItem().toString();
            FIELDSINFO[0] = TICKER;
            FIELDSINFO[1] = PRC.getText();
            FIELDSINFO[2] = AMNT.getText();
            FIELDSINFO[3] = MYDATE.getText();
            FIELDSINFO[4] = REASON.getText();
            
            try {
                INVESTCONTROL.createInvestment(
                        FIELDSINFO[0],
                        FIELDSINFO[1],
                        FIELDSINFO[2],
                        FIELDSINFO[3],
                        FIELDSINFO[4],
                        "000000"
                        );
            } catch (IOException ex) {
                Logger.getLogger(DesignAdd.class.getName()).log(Level.SEVERE, null, ex);
            }
            GRID.getChildren().clear();
            MANAGE.InvestmentCreated(GRID, TICKER, MAINWINDOW, TOP);
        });
       
        EXCHANGES.getChildren().addAll(NYSE, NASDAQ);
        
        GRID.add(CODETXT, 0, 1);
        GRID.add(PRICETXT, 0, 2);
        GRID.add(AMNTTXT, 0, 3);
        GRID.add(DATETXT, 0, 4);
        GRID.add(REATXT, 0, 5);
        GRID.add(SUBBTN, 2, 5);
        
        GRID.add(PRC, 1, 2);
        GRID.add(AMNT, 1, 3);
        GRID.add(MYDATE, 1, 4);
        GRID.add(REASON, 1, 5);
        
        return GRID;
    }
    
    public GridPane DesignAddCreatedInvestment (GridPane GRID, String TICKER, Stage MAINWINDOW, VBox TOP){
        MYFONT = new MyFont();
        MANAGE = new DesignAddManagement();
        TOPMANAGE = new TopManagement();
        DESIGN = new Design(MAINWINDOW);
        DSSTOCK = new DesignStock();
        
        String TEXT = "";
        TOPMANAGE.ChangeTop(TOP, TEXT);
        
        MESSAGE = new Text("Investment " + TICKER + " successfuly created.");
        MESSAGE.setFont(MYFONT.getOswaldRegular());
        
        GROUP = new ToggleGroup();
        
        CONTINUE = new RadioButton("Continue to investment");
        CONTINUE.setFont(MYFONT.getOswaldButton());
        CONTINUE.getStyleClass().add("radioButtons");
        CONTINUE.setToggleGroup(GROUP);
        CONTINUE.setOnAction(e ->{
            GRID.getChildren().clear();
            String TEXT2 = "Investment: "+ TICKER;
            TOPMANAGE.ChangeTop(TOP, TEXT2);
            try {
                try {
                    DSSTOCK.DesignStock(MAINWINDOW, GRID, TICKER);
                } catch (ApiException ex) {
                    Logger.getLogger(DesignAdd.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (IOException ex) {
                Logger.getLogger(DesignAdd.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        ADDANOTHER = new RadioButton("Add another investment");
        ADDANOTHER.setFont(MYFONT.getOswaldButton());
        ADDANOTHER.getStyleClass().add("radioButtons");
        ADDANOTHER.setToggleGroup(GROUP);
        ADDANOTHER.setOnAction(e ->{
            GRID.getChildren().clear();
            try {
                MANAGE.DesignAddDefault(GRID, MAINWINDOW, TOP);
            } catch (IOException ex) {
                Logger.getLogger(DesignAdd.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        CHOICES = new VBox(10);
        CHOICES.getChildren().addAll(ADDANOTHER, CONTINUE);
        
        GRID.add(MESSAGE, 0, 0);
        GRID.add(CHOICES, 0, 1);
        
        return GRID;
    }
    
    
}
